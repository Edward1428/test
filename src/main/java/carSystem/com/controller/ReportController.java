package carSystem.com.controller;

import carSystem.com.annotation.LoginRequired;
import carSystem.com.bean.Customer;
import carSystem.com.bean.Report;
import carSystem.com.bean.Role;
import carSystem.com.bean.User;
import carSystem.com.bean.report.ali.AliApi;
import carSystem.com.bean.report.baiRong.BankFourPro;
import carSystem.com.bean.report.baiRong.TelChecks;
import carSystem.com.bean.report.baiRong.TelPeriod;
import carSystem.com.bean.report.baiRong.TelStatus;
import carSystem.com.bean.report.baiRong.strategy.Strategy;
import carSystem.com.bean.report.jd.JdApi;
import carSystem.com.service.*;
import carSystem.com.service.report.ApiService;
import carSystem.com.service.report.jd.JdService;
import carSystem.com.utils.IdcardValidatorUtil;
import carSystem.com.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private JdService jdService;

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @Autowired
    private IntegralService integralService;

    @LoginRequired(role = Role.ALL)
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Result newReport(@RequestBody CustomerVO customerVO, @RequestAttribute User user) {
        Customer customer = customerVO.getCustomer();
        customer.setName(customer.getName().trim());
        List<Integer> serviceList = customerVO.getServiceList();
        if (serviceList.isEmpty()) {
            return Result.failed("至少需要选择一种服务");
        } else {
            Set<Integer> set = new HashSet<>(serviceList);
            if (set.size() != serviceList.size()) {
                return Result.failed("非法操作1");
            }
        }

        if (!IdcardValidatorUtil.isValidatedAllIdcard(customer.getIdNum())) {
            return Result.failed("身份证号码不合法");
        }

        Integer point = integralService.sumPoint(serviceList);
        if (user.getIntegral() < point) {
            return Result.failed("积分不足，请联系管理员充值");
        } else {
            Integer check = reportService.checkCustomerExists(user.getId(), customer);
            if (check != -1) {
                return Result.success(check);
            }

            Integer integral = user.getIntegral();
            //更新用户积分
            user.setIntegral(integral - point);
            userService.update(user);

            Integer customerId = customerService.insert(customer);
            Report report = new Report();
            //记录消耗积分
            report.setPayout(point);
            report.setName(customer.getName());
            report.setNum(Report.generateCode(9));
            report.setUserId(user.getId());
            report.setCustomerId(customerId);
            report.setServiceList(CustomerVO.listToString(serviceList));
            Integer reportId = reportService.insert(report);

            for (Integer id : serviceList) {
                switch (id) {
                    case 1:
                        Strategy strategy = apiService.strategyApi(customer, reportId);
                        break;
                    case 2:
                        BankFourPro bankFourPro = apiService.bankFourProApi(customer, reportId);
                        break;
                    case 3:
                        TelChecks telChecks = apiService.telChecksApi(customer, reportId);
                        break;
                    case 4:
                        TelPeriod telPeriod = apiService.telPeriodApi(customer, reportId);
                        break;
                    case 5:
                        TelStatus telStatus = apiService.telStatusApi(customer, reportId);
                        break;
                    case 6:
//                        AliApi aliApi = apiService.aliApi(customer, reportId);
//                        JdApi jdApi = apiService.jdApi(customer, reportId);
                        apiService.xinShuApi(customer, reportId);
                        break;
                }
            }
            return Result.success(reportId);
        }

    }

    @LoginRequired(role = Role.ALL)
    @RequestMapping(method = RequestMethod.GET, value = "/{reportId}")
    public @ResponseBody
    Result getReport(@PathVariable Integer reportId, @RequestAttribute User user) {
        Report report = reportService.findById(reportId);
        if (user.getRole() != Role.ADMIN.getRole()) {
            if (report.getUserId() != user.getId()) {
                return Result.failed("非法操作");
            }
        }

        TableData tableData = tableService.tableData(reportId, Report.PC_Request_Type);
        return Result.success(tableData);
    }

    @LoginRequired(role = Role.ALL)
    @RequestMapping(method = RequestMethod.POST, value = "/listQuery")
    public @ResponseBody
    Result getAllReport(@RequestAttribute User user, @RequestBody(required = false) ListQuery listQuery) {
        List<ReportTableVO> reportTableVOList = new ArrayList<>();
        List<Report> reportList = new ArrayList<>();
        Integer total = 0;

        if (listQuery == null) {
            if (user.getRole() == Role.ADMIN.getRole()) {
                reportList = reportService.findAll();
            } else if (user.getRole() == Role.CHANNEL.getRole()) {
                reportList = reportService.findAllByUserId(user.getId());
            }
            total = reportList.size();
        } else {
            reportList = reportService.listQuery(user, listQuery);
            total = reportService.listQuerySize(user, listQuery);
        }

        if (reportList.size() > 0) {
            for (Report report : reportList) {
                User author = userService.findById(report.getUserId());
                ReportTableVO reportTableVO = new ReportTableVO();
                reportTableVO.setNickName(author.getNickName());
                reportTableVO.setReport(report);
                reportTableVO.setService(ReportTableVO.toService(report.getServiceList()));
                reportTableVOList.add(reportTableVO);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", reportTableVOList);
        map.put("total", total);
        return Result.success(map);
    }

    @Deprecated
    @LoginRequired(role = Role.ALL)
    @RequestMapping(method = RequestMethod.GET, value = "/detail/{reportId}")
    public @ResponseBody
    Result getReportDetail(@PathVariable Integer reportId) {
        ReportVO reportVO = reportService.findByReportId(reportId);
        ReportJson reportJson = reportService.explain(reportVO);
        return Result.success(reportJson);
    }

    @LoginRequired(role = Role.ALL)
    @RequestMapping(method = RequestMethod.GET, value = "/countByDay")
    public @ResponseBody
    Result countAllReportByDay(@RequestAttribute User user, @RequestParam String start, @RequestParam String end) {
        if (user.getRole() == Role.ADMIN.getRole()) {
            List<User> userList = userService.findAll();
            HashMap<String, Object> map = new HashMap<>();
            for (User u : userList) {
                List<ReportDayCount> reportDayCountList = reportService.countDayReportByUserId(u.getId(), start, end);
                map.put(u.getNickName(), reportDayCountList);
            }
            return Result.success(map);
        } else {
            return Result.success(reportService.countDayReportByUserId(user.getId(), start, end));
        }

    }

    @LoginRequired(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.GET, value = "/detail")
    public @ResponseBody
    Result detail(@RequestAttribute User user, @RequestParam Integer page, @RequestParam Integer limit) {
        return Result.success(reportService.findDetail(page, limit));
    }
}
