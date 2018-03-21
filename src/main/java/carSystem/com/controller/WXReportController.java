package carSystem.com.controller;

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
import carSystem.com.service.CustomerService;
import carSystem.com.service.ReportService;
import carSystem.com.service.TableService;
import carSystem.com.service.UserService;
import carSystem.com.service.report.ApiService;
import carSystem.com.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/api/wx/report")
public class WXReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private TableService tableService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Result newReport(@RequestBody CustomerVO customerVO, @RequestHeader("sid") String sid) {
        User user = userService.findSid(sid);
        if (user == null) {
            return Result.failed("请登陆");
        } else {
            Customer customer = customerVO.getCustomer();
            List<Integer> serviceList = customerVO.getServiceList();
            if (serviceList.isEmpty()) {
                return Result.failed("至少需要选择一种服务");
            } else {
                Set<Integer> set = new HashSet<>(serviceList);
                if (set.size() != serviceList.size()) {
                    return Result.failed("非法操作1");
                }
            }

            Integer customerId = customerService.insert(customer);
            Report report = new Report();
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
                        AliApi aliApi = apiService.aliApi(customer, reportId);
                        JdApi jdApi = apiService.jdApi(customer, reportId);
                        break;
                }
            }
            return Result.success(reportId);
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{reportId}")
    public @ResponseBody
    Result getReport(@PathVariable Integer reportId, @RequestHeader("sid") String sid) {
        User user = userService.findSid(sid);
        if (user == null) {
            return Result.failed("请登陆");
        } else {
            Report report = reportService.findById(reportId);
            if (user.getRole() != Role.ADMIN.getRole()) {
                if (report.getUserId() != user.getId()) {
                    return Result.failed("非法操作");
                }
            }

            TableData tableData = tableService.tableData(reportId);
            return Result.success(tableData);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/listQuery")
    public @ResponseBody
    Result getAllReport(@RequestHeader("sid") String sid, @RequestBody(required = false) ListQuery listQuery) {
        User user = userService.findSid(sid);
        if (user == null) {
            return Result.failed("请登陆");
        } else {
            List<ReportTableVO> reportTableVOList = new ArrayList<>();
            List<Report> reportList = new ArrayList<>();
            Integer total = 0;

            if (listQuery == null) {
                if (user.getRole() == Role.ADMIN.getRole()) {
                    reportList = reportService.findAll();
                } else if (user.getRole() == Role.CHANNEL.getRole()){
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

    }

}
