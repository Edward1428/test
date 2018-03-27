package carSystem.com.service;

import carSystem.com.bean.Customer;
import carSystem.com.bean.Report;
import carSystem.com.bean.Role;
import carSystem.com.bean.User;
import carSystem.com.bean.report.ali.*;
import carSystem.com.bean.report.baiRong.BankFourPro;
import carSystem.com.bean.report.baiRong.TelChecks;
import carSystem.com.bean.report.baiRong.TelPeriod;
import carSystem.com.bean.report.baiRong.TelStatus;
import carSystem.com.bean.report.baiRong.strategy.*;
import carSystem.com.bean.report.jd.*;
import carSystem.com.dao.CustomerDAO;
import carSystem.com.dao.ReportDAO;
import carSystem.com.service.report.ali.AliService;
import carSystem.com.service.report.baiRong.BankFourProService;
import carSystem.com.service.report.baiRong.TelChecksService;
import carSystem.com.service.report.baiRong.TelPeriodService;
import carSystem.com.service.report.baiRong.TelStatusService;
import carSystem.com.service.report.baiRong.strategy.*;
import carSystem.com.service.report.jd.JdService;
import carSystem.com.utils.FileUtils;
import carSystem.com.vo.ListQuery;
import carSystem.com.vo.ReportJson;
import carSystem.com.vo.ReportVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportDAO reportDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private RiskStrategyService riskStrategyService;

    @Autowired
    private ApplyLoanService applyLoanService;

    @Autowired
    private InfoRelationService infoRelationService;

    @Autowired
    private TelChecksService telChecksService;

    @Autowired
    private TelPeriodService telPeriodService;

    @Autowired
    private TelStatusService telStatusService;

    @Autowired
    private BankFourProService bankFourProService;

    @Autowired
    private ExecutionService executionService;

    @Autowired
    private SpecialListService specialListService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AliService aliService;

    @Autowired
    private JdService jdService;

    public Integer insert(@NotNull Report report) {
        return reportDAO.insert(report);
    }

    public ReportVO findByReportId(@NotNull Integer reportId) {
        ReportVO reportVO = new ReportVO();
        Strategy strategy = new Strategy();

        RiskStrategy riskStrategy = riskStrategyService.findByReportId(reportId);
        ApplyLoan applyLoan = applyLoanService.findByReportId(reportId);
        InfoRelation infoRelation = infoRelationService.findByReportId(reportId);
        Execution execution = executionService.findByReportId(reportId);
        SpecialList specialList = specialListService.findByReportId(reportId);
        strategy.setRiskStrategy(riskStrategy);
        strategy.setApplyLoan(applyLoan);
        strategy.setInfoRelation(infoRelation);
        strategy.setExecution(execution);
        strategy.setSpecialList(specialList);

        //阿里
        AliApi aliApi = new AliApi();
        Cell cell = aliService.findCell(reportId);
        BankCard bankCard = aliService.findBankCard(reportId);
        IdCard idCard = aliService.findIdCard(reportId);
        aliApi.setCell(cell);
        aliApi.setBankCard(bankCard);
        aliApi.setIdCard(idCard);

        //狗东
        JdApi jdApi = new JdApi();
        BlackName blackName = jdService.findBlackName(reportId);
        List<BlackNameList> blackNameLists = jdService.findBlackNameList(blackName.getId());
        BadRecord badRecord = jdService.findBadRecord(reportId);
        CellLong cellLong = jdService.findCellLong(reportId);
        jdApi.setBlackName(blackName);
        jdApi.setBlackNameLists(blackNameLists);
        jdApi.setBadRecord(badRecord);
        jdApi.setCellLong(cellLong);

        reportVO.setStrategy(strategy);
        TelChecks telChecks = telChecksService.findByReportId(reportId);
        TelPeriod telPeriod = telPeriodService.findByReportId(reportId);
        TelStatus telStatus = telStatusService.findByReportId(reportId);
        reportVO.setTelChecks(telChecks);
        reportVO.setTelPeriod(telPeriod);
        reportVO.setTelStatus(telStatus);
        BankFourPro bankFourPro = bankFourProService.findByReportId(reportId);
        reportVO.setBankFourPro(bankFourPro);
        reportVO.setAliApi(aliApi);
        reportVO.setJdApi(jdApi);
        return reportVO;
    }

    public ReportJson explain(@NotNull ReportVO reportVO) {
        Strategy strategy = reportVO.getStrategy();
        RiskStrategy riskStrategy = strategy.getRiskStrategy();
        ApplyLoan applyLoan = strategy.getApplyLoan();
        SpecialList specialList = strategy.getSpecialList();
        ReportJson reportJson = new ReportJson();

        if (riskStrategy.getFlag() == 1) {
            JSONObject riskJson = (JSONObject) JSON.toJSON(riskStrategy);
            JSONObject document = JSON.parseObject(FileUtils.readResource("/document/riskStrategy.md"));
            reportJson.setRiskStrategy(toJsonArray(riskJson, document));
        }

        if (applyLoan.getFlag() == 1) {
            JSONObject applyLoanJson = (JSONObject) JSON.toJSON(applyLoan);
            JSONObject document = JSON.parseObject(FileUtils.readResource("/document/applyLoan.md"));
            reportJson.setApplyLoan(toJsonArray(applyLoanJson, document));
        }

        if (specialList.getFlag() == 1) {
            JSONObject specialListJson = (JSONObject) JSON.toJSON(specialList);
            JSONObject document = JSON.parseObject(FileUtils.readResource("/document/specialList.md"));
            reportJson.setSpecialList(toJsonArray(specialListJson, document));
        }

        return reportJson;
    }

    public List<Report> findAllByUserId(Integer userId) {
        return reportDAO.findAllByUserId(userId);
    }

    public List<Report> findAll() {
        return reportDAO.findAll();
    }

    public List<Report> listQuery(User user, ListQuery query) {
        String sql = " 1=1 ";

        String nameSql = "";
        String numSql = "";
        String roleSql = "";
        String channelSql = "";
        String orderBySql = " order by created_at desc ";
        String limitSql = "";


        if (StringUtils.isNotBlank(query.getName())) {
            nameSql = " and name like '%" +query.getName() +"%' ";
            sql = sql + nameSql;
        }

        if (StringUtils.isNotBlank(query.getNum())) {
            numSql = " and num like '%" + query.getNum() +"%' ";
            sql = sql + numSql;
        }

        if (user.getRole() != Role.ADMIN.getRole()) {
            roleSql = " and userId = " + user.getId().toString();
            sql = sql + roleSql;
        } else if (user.getRole() == Role.ADMIN.getRole() && query.getUserId() != null) {
            channelSql = " and userId = " + query.getUserId();
            sql = sql + channelSql;
        }
        sql = sql + orderBySql;

        Integer page = query.getPage();
        Integer limit = query.getLimit();
        if (page > 0 && limit > 0) {
            Integer start = (page - 1) * limit;
            limitSql = " limit "+start.toString()+","+limit.toString();
            sql = sql + limitSql;
        }

        return reportDAO.findAll(sql);
    }

    public Integer listQuerySize(User user, ListQuery query) {
        String sql = " 1=1 ";

        String nameSql = "";
        String numSql = "";
        String roleSql = "";
        String channelSql = "";

        if (StringUtils.isNotBlank(query.getName())) {
            nameSql = " and name like '%" +query.getName() +"%' ";
            sql = sql + nameSql;
        }

        if (StringUtils.isNotBlank(query.getNum())) {
            numSql = " and num like '%" + query.getNum() +"%' ";
            sql = sql + numSql;
        }

        if (user.getRole() != Role.ADMIN.getRole()) {
            roleSql = " and userId = " + user.getId().toString();
            sql = sql + roleSql;
        } else if (user.getRole() == Role.ADMIN.getRole() && query.getUserId() != null) {
            channelSql = " and userId = " + query.getUserId();
            sql = sql + channelSql;
        }
        return reportDAO.count(sql);
    }

    private JSONArray toJsonArray(JSONObject data, JSONObject document) {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue().toString()) && document.get(entry.getKey()) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data", entry.getValue());
                jsonObject.put("document", document.get(entry.getKey()).toString());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    public Report findById(@NotNull Integer reportId) {
        return reportDAO.findById(reportId);
    }
}
