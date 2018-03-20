package carSystem.com.service;

import carSystem.com.bean.Customer;
import carSystem.com.bean.Report;
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
import carSystem.com.vo.ReportJson;
import carSystem.com.vo.ReportVO;
import carSystem.com.vo.TableData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TableService {

    @Autowired
    private ReportService reportService;

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

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ReportDAO reportDAO;

    public TableData tableData(Integer reportId) {
        Report report = reportService.findById(reportId);
        Map<String, Boolean> show = toShow();
        TableData tableData = new TableData();
        String serviceList = report.getServiceList();
        List<String> stringList = stringToStringList(serviceList);
        for (String s : stringList) {
            switch (s) {
                //策略引擎合集
                case "1":
                    tableData.setTotal(toTotalTable(reportId));
                    tableData.setInfo(toInfo(reportId));
                    tableData.setCourt(toCourt(reportId));
                    tableData.setBad(toBad(reportId));
                    tableData.setSpecial(toSpecial(reportId));
                    tableData.setTimesById(toTimesById(reportId));
                    tableData.setTimesByTel(toTimesByTel(reportId));
                    tableData.setCustomer(toCustomer(reportId));
                    show.put("1", true);
                    break;
                //银行卡4要素(银行卡验证)
                case "2":
                    tableData.setBankChecks(toBankChecks(reportId));
                    show.put("2", true);
                    break;
                //手机3要素（手机验证）
                case "3":
                    tableData.setTelChecks(toTelChecks(reportId));
                    show.put("3", true);
                    break;
                //手机在网时长
                case "4":
                    tableData.setTelPeriod(toTelPeriod(reportId));
                    show.put("4", true);
                    break;
                //手机在网状态
                case "5":
                    tableData.setTelStatus(toTelStatus(reportId));
                    show.put("5", true);
                    break;
                //京东阿里合集
                case "6":
                    tableData.setCustomerTable(toCustomerTable(reportId));
                    tableData.setAliJdTable(toAliJdTable(reportId));
                    tableData.setBlackNameLists(toBlackNameList(reportId));
                    tableData.setCheck(toCheck(reportId));
                    show.put("aliJd", true);
            }
        }
        tableData.setShow(show);
        return tableData;
    }

    //百融策略合集汇总
    private JSONArray toTotalTable(@NotNull Integer reportId) {
        RiskStrategy riskStrategy = riskStrategyService.findByReportId(reportId);
        JSONArray jsonArray = new JSONArray();
        //身份验证信息
        JSONObject ruleInfoRelationJson = new JSONObject();
        String ruleInfoRelation = "";
        ruleInfoRelation = ruleInfoRelation + riskStrategy.getRule_name_CBC001() + riskStrategy.getRule_name_CBC002() +
                riskStrategy.getRule_name_CBC003() + riskStrategy.getRule_name_CBC004();
        ruleInfoRelationJson.put("data", "身份验证信息");
        ruleInfoRelationJson.put("document", ruleInfoRelation);
        jsonArray.add(ruleInfoRelationJson);
        //公检法信息
        JSONObject ruleExecutionJson = new JSONObject();
        String ruleExecution = "";
        ruleExecution = ruleExecution + riskStrategy.getRule_name_QJE010() + riskStrategy.getRule_name_QJE020() +
                riskStrategy.getRule_name_QJE040() + riskStrategy.getRule_name_QJE050();
        ruleExecutionJson.put("data", "公检法信息");
        ruleExecutionJson.put("document", ruleExecution);
        jsonArray.add(ruleExecutionJson);
        //特殊名单
        JSONObject ruleSpecialListJson = new JSONObject();
        String ruleSpecialList = "";
        ruleSpecialList = ruleSpecialList + riskStrategy.getRule_name_QJS150() + riskStrategy.getRule_name_QJS151() +
                riskStrategy.getRule_name_QJS152() + riskStrategy.getRule_name_QJS153() + riskStrategy.getRule_name_QJS154() +
                riskStrategy.getRule_name_QJS155() + riskStrategy.getRule_name_QJS156() + riskStrategy.getRule_name_QJS157() +
                riskStrategy.getRule_name_QJS158() + riskStrategy.getRule_name_QJS159() + riskStrategy.getRule_name_QJS160() +
                riskStrategy.getRule_name_QJS161() + riskStrategy.getRule_name_QJS162() + riskStrategy.getRule_name_QJS163() +
                riskStrategy.getRule_name_QJS164() + riskStrategy.getRule_name_QJS165() + riskStrategy.getRule_name_QJS166() +
                riskStrategy.getRule_name_QJS167();
        ruleSpecialListJson.put("data", "特殊名单");
        ruleSpecialListJson.put("document", ruleSpecialList);
        jsonArray.add(ruleSpecialListJson);
        //借贷意向
        JSONObject ruleApplyLoanJson = new JSONObject();
        String ruleApplyLoan = "";
        ruleApplyLoan = ruleApplyLoan + riskStrategy.getRule_name_CAC001() + riskStrategy.getRule_name_CAC002() +
                riskStrategy.getRule_name_CAC003() + riskStrategy.getRule_name_CAC004();
        ruleApplyLoanJson.put("data", "借贷意向");
        ruleApplyLoanJson.put("document", ruleApplyLoan);
        jsonArray.add(ruleApplyLoanJson);
        //手机通话详单
//        JSONObject ruleCallDetailJson = new JSONObject();
//        String ruleCallDetail = "";
//        ruleCallDetail = ruleCallDetail + riskStrategy.getRule_name_QCD010() + riskStrategy.getRule_name_QCD020() +
//                riskStrategy.getRule_name_QCD030() + riskStrategy.getRule_name_QCD040() + riskStrategy.getRule_name_QCD050() +
//                riskStrategy.getRule_name_QCD060() + riskStrategy.getRule_name_QCD070() + riskStrategy.getRule_name_QCD080();
//        ruleCallDetailJson.put("data", "手机通话详单");
//        ruleCallDetailJson.put("document", ruleCallDetail);
//        jsonArray.add(ruleCallDetailJson);
        //反欺诈风险识别决策结果
        JSONObject scoreAfJson = new JSONObject();
        String scoreAf = riskStrategy.getRs_ScoreAf_decision();
        scoreAfJson.put("data", "反欺诈风险识别决策结果");
        scoreAfJson.put("document", scoreAf);
        jsonArray.add(scoreAfJson);
        //汽车金融规则集决策结果
        JSONObject ruleJson = new JSONObject();
        String rule = riskStrategy.getRs_Rule_decision();
        ruleJson.put("data", "汽车金融规则集决策结果");
        ruleJson.put("document", rule);
        jsonArray.add(ruleJson);
        //最终决策结果
        JSONObject finalDecisionJson = new JSONObject();
        String finalDecision = riskStrategy.getRs_final_decision();
        finalDecisionJson.put("data", "最终决策结果");
        finalDecisionJson.put("document", finalDecision);
        jsonArray.add(finalDecisionJson);
        return jsonArray;
    }

    //百融策略合集实名信息验证
    private JSONArray toInfo(@NotNull Integer reportId) {
        JSONArray jsonArray = new JSONArray();
        InfoRelation infoRelation = infoRelationService.findByReportId(reportId);
        if (infoRelation == null || infoRelation.getFlag() == Strategy.DISABLE) {
            return jsonArray;
        }
        JSONObject one = new JSONObject();
        one.put("data", "身份证关联的手机号个数");
        one.put("document", infoRelation.getIr_id_x_cell_cnt());
        JSONObject two = new JSONObject();
        two.put("data", "手机号关联的身份证个数");
        two.put("document", infoRelation.getIr_cell_x_id_cnt());
        JSONObject three = new JSONObject();
        three.put("data", "身份证号是否关联异常");
        three.put("document", infoRelation.getIr_id_is_reabnormal());
        JSONObject four = new JSONObject();
        four.put("data", "手机号是否关联异常");
        four.put("document", infoRelation.getIr_cell_is_reabnormal());

        jsonArray.add(one);
        jsonArray.add(two);
        jsonArray.add(three);
        jsonArray.add(four);
        return jsonArray;
    }

    //百融策略合集法院被执行人（违法人）
    private JSONArray toCourt(@NotNull Integer reportId) {
        Execution execution = executionService.findByReportId(reportId);
        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        JSONObject three = new JSONObject();

        one.put("num", "1");
        two.put("num", "2");
        three.put("num", "3");
        one.put("name", execution.getEx_execut1_name());
        two.put("name", execution.getEx_execut2_name());
        three.put("name", execution.getEx_execut3_name());
        //执行案号
        one.put("casenum", execution.getEx_execut1_casenum());
        two.put("casenum", execution.getEx_execut2_casenum());
        three.put("casenum", execution.getEx_execut3_casenum());
        //立案日期
        one.put("date", execution.getEx_execut1_time());
        two.put("date", execution.getEx_execut2_time());
        three.put("date", execution.getEx_execut3_time());
        //执行标的
        one.put("money", execution.getEx_execut1_money());
        two.put("money", execution.getEx_execut2_money());
        three.put("money", execution.getEx_execut3_money());
        //执行法院
        one.put("court", execution.getEx_execut1_court());
        two.put("court", execution.getEx_execut2_court());
        three.put("court", execution.getEx_execut3_court());
        //执行状态
        one.put("status", execution.getEx_execut1_statute());
        two.put("status", execution.getEx_execut2_statute());
        three.put("status", execution.getEx_execut3_statute());
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isNotBlank(execution.getEx_execut1_name())) {
            jsonArray.add(one);
        }
        if (StringUtils.isNotBlank(execution.getEx_execut2_name())) {
            jsonArray.add(two);
        }
        if (StringUtils.isNotBlank(execution.getEx_execut3_name())) {
            jsonArray.add(three);
        }

        return jsonArray;
    }

    //百融策略合集法院被执行人(失信人)
    private JSONArray toBad(@NotNull Integer reportId) {
        Execution execution = executionService.findByReportId(reportId);
        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        one.put("num", "1");
        two.put("num", "2");

        one.put("name", execution.getEx_bad1_name());
        two.put("name", execution.getEx_bad2_name());

        //执行案号
        one.put("casenum", execution.getEx_bad1_casenum());
        two.put("casenum", execution.getEx_bad2_casenum());

        //立案日期
        one.put("date", execution.getEx_bad1_time());
        two.put("date", execution.getEx_bad2_time());

        //执行标的
        one.put("money", execution.getEx_bad1_money());
        two.put("money", execution.getEx_bad2_money());

        //执行法院
        one.put("court", execution.getEx_bad1_court());
        two.put("court", execution.getEx_bad2_court());

        //被执行人的履行情况
        one.put("status", execution.getEx_bad1_performance());
        two.put("status", execution.getEx_bad2_performance());

        //失信被执行人行为具体情形
        one.put("action", execution.getEx_bad1_concretesituation());
        two.put("action", execution.getEx_bad2_concretesituation());

        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isNotBlank(execution.getEx_bad1_name())) {
            jsonArray.add(one);
        }
        if (StringUtils.isNotBlank(execution.getEx_bad2_name())) {
            jsonArray.add(two);
        }

        return jsonArray;
    }

    //百融策略合集特殊名单
    private JSONArray toSpecial(@NotNull Integer reportId) {
        SpecialList specialList = specialListService.findByReportId(reportId);
        JSONObject specialListJson = (JSONObject) JSON.toJSON(specialList);
        JSONObject document = JSON.parseObject(FileUtils.readResource("/document/specialList.md"));

        JSONArray specialListArray = toJsonArray(specialListJson, document);
        JSONArray jsonArray = new JSONArray();
        if (specialListArray == null) {
            return jsonArray;
        }
        if (specialListArray.size() > 0) {
            for (int a = 0; a < specialListArray.size(); a++) {
                JSONObject jsonObject = specialListArray.getJSONObject(a);
                jsonObject.put("num", a+1);
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    //百融策略合集借贷意向验证(根据身份证查)
    private JSONArray toTimesById(@NotNull Integer reportId) {
        ApplyLoan applyLoan = applyLoanService.findByReportId(reportId);
        JSONObject specialJson = (JSONObject) JSON.toJSON(applyLoan);
        JSONArray jsonArray = new JSONArray();
        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        JSONObject three = new JSONObject();
        JSONObject four = new JSONObject();
        JSONObject five = new JSONObject();
        JSONObject six = new JSONObject();
        JSONObject seven = new JSONObject();

        one.put("num", "银行");
        two.put("num", "小贷");
        three.put("num", "p2p");
        four.put("num", "消费类分期");
        five.put("num", "现金类分期");
        six.put("num", "代偿类分期");
        seven.put("num", "非银其他");

        one.put("1m", specialJson.get("als_m1_id_bank_allnum"));
        one.put("3m", specialJson.get("als_m3_id_bank_allnum"));
        one.put("6m", specialJson.get("als_m6_id_bank_allnum"));
        one.put("12m", specialJson.get("als_m12_id_bank_allnum"));

        two.put("1m", specialJson.get("als_m1_id_nbank_mc_allnum"));
        two.put("3m", specialJson.get("als_m3_id_nbank_mc_allnum"));
        two.put("6m", specialJson.get("als_m6_id_nbank_mc_allnum"));
        two.put("12m", specialJson.get("als_m12_id_nbank_mc_allnum"));

        three.put("1m", specialJson.get("als_m1_id_nbank_p2p_allnum"));
        three.put("3m", specialJson.get("als_m3_id_nbank_p2p_allnum"));
        three.put("6m", specialJson.get("als_m6_id_nbank_p2p_allnum"));
        three.put("12m", specialJson.get("als_m12_id_nbank_p2p_allnum"));

        four.put("1m", specialJson.get("als_m1_id_nbank_cf_allnum"));
        four.put("3m", specialJson.get("als_m3_id_nbank_cf_allnum"));
        four.put("6m", specialJson.get("als_m6_id_nbank_cf_allnum"));
        four.put("12m", specialJson.get("als_m12_id_nbank_cf_allnum"));

        five.put("1m", specialJson.get("als_m1_id_nbank_ca_allnum"));
        five.put("3m", specialJson.get("als_m3_id_nbank_ca_allnum"));
        five.put("6m", specialJson.get("als_m6_id_nbank_ca_allnum"));
        five.put("12m", specialJson.get("als_m12_id_nbank_ca_allnum"));

        six.put("1m", specialJson.get("als_m1_id_nbank_com_allnum"));
        six.put("3m", specialJson.get("als_m3_id_nbank_com_allnum"));
        six.put("6m", specialJson.get("als_m6_id_nbank_com_allnum"));
        six.put("12m", specialJson.get("als_m12_id_nbank_com_allnum"));

        seven.put("1m", specialJson.get("als_m1_id_nbank_oth_allnum"));
        seven.put("3m", specialJson.get("als_m3_id_nbank_oth_allnum"));
        seven.put("6m", specialJson.get("als_m6_id_nbank_oth_allnum"));
        seven.put("12m", specialJson.get("als_m12_id_nbank_oth_allnum"));

        jsonArray.add(one);
        jsonArray.add(two);
        jsonArray.add(three);
        jsonArray.add(four);
        jsonArray.add(five);
        jsonArray.add(six);
        jsonArray.add(seven);
        return jsonArray;
    }

    //百融策略合集借贷意向验证(根据电话号码查)
    private JSONArray toTimesByTel(@NotNull Integer reportId) {
        ApplyLoan applyLoan = applyLoanService.findByReportId(reportId);
        JSONObject specialJson = (JSONObject) JSON.toJSON(applyLoan);
        JSONArray jsonArray = new JSONArray();
        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        JSONObject three = new JSONObject();
        JSONObject four = new JSONObject();
        JSONObject five = new JSONObject();
        JSONObject six = new JSONObject();
        JSONObject seven = new JSONObject();

        one.put("num", "银行");
        two.put("num", "小贷");
        three.put("num", "p2p");
        four.put("num", "消费类分期");
        five.put("num", "现金类分期");
        six.put("num", "代偿类分期");
        seven.put("num", "非银其他");

        one.put("1m", specialJson.get("als_m1_cell_bank_allnum"));
        one.put("3m", specialJson.get("als_m3_cell_bank_allnum"));
        one.put("6m", specialJson.get("als_m6_cell_bank_allnum"));
        one.put("12m", specialJson.get("als_m12_cell_bank_allnum"));

        two.put("1m", specialJson.get("als_m1_cell_nbank_mc_allnum"));
        two.put("3m", specialJson.get("als_m3_cell_nbank_mc_allnum"));
        two.put("6m", specialJson.get("als_m6_cell_nbank_mc_allnum"));
        two.put("12m", specialJson.get("als_m12_cell_nbank_mc_allnum"));

        three.put("1m", specialJson.get("als_m1_cell_nbank_p2p_allnum"));
        three.put("3m", specialJson.get("als_m3_cell_nbank_p2p_allnum"));
        three.put("6m", specialJson.get("als_m6_cell_nbank_p2p_allnum"));
        three.put("12m", specialJson.get("als_m12_cell_nbank_p2p_allnum"));

        four.put("1m", specialJson.get("als_m1_cell_nbank_cf_allnum"));
        four.put("3m", specialJson.get("als_m3_cell_nbank_cf_allnum"));
        four.put("6m", specialJson.get("als_m6_cell_nbank_cf_allnum"));
        four.put("12m", specialJson.get("als_m12_cell_nbank_cf_allnum"));

        five.put("1m", specialJson.get("als_m1_cell_nbank_ca_allnum"));
        five.put("3m", specialJson.get("als_m3_cell_nbank_ca_allnum"));
        five.put("6m", specialJson.get("als_m6_cell_nbank_ca_allnum"));
        five.put("12m", specialJson.get("als_m12_cell_nbank_ca_allnum"));

        six.put("1m", specialJson.get("als_m1_cell_nbank_com_allnum"));
        six.put("3m", specialJson.get("als_m3_cell_nbank_com_allnum"));
        six.put("6m", specialJson.get("als_m6_cell_nbank_com_allnum"));
        six.put("12m", specialJson.get("als_m12_cell_nbank_com_allnum"));

        seven.put("1m", specialJson.get("als_m1_cell_nbank_oth_allnum"));
        seven.put("3m", specialJson.get("als_m3_cell_nbank_oth_allnum"));
        seven.put("6m", specialJson.get("als_m6_cell_nbank_oth_allnum"));
        seven.put("12m", specialJson.get("als_m12_cell_nbank_oth_allnum"));

        jsonArray.add(one);
        jsonArray.add(two);
        jsonArray.add(three);
        jsonArray.add(four);
        jsonArray.add(five);
        jsonArray.add(six);
        jsonArray.add(seven);
        return jsonArray;
    }

    //被查人资料,用于百融策略引擎
    private JSONArray toCustomer(@NotNull Integer reportId) {
        Report report = reportDAO.findById(reportId);
        Customer customer = customerService.findById(report.getCustomerId());
        JSONObject object = (JSONObject) JSON.toJSON(customer);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(object);
        return jsonArray;
    }

    //百融手机三要素
    private JSONArray toTelChecks(@NotNull Integer reportId) {
        TelChecks telChecks = telChecksService.findByReportId(reportId);
        JSONObject one = new JSONObject();
        one.put("document", telChecks.getResult());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(one);
        return jsonArray;
    }

    //百融银行卡四要素
    private JSONArray toBankChecks(@NotNull Integer reportId) {
        BankFourPro bankFourPro = bankFourProService.findByReportId(reportId);
        JSONObject one = new JSONObject();
        one.put("document", bankFourPro.getResult());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(one);
        return jsonArray;
    }

    //百融手机在网时长
    private JSONArray toTelPeriod(@NotNull Integer reportId) {
        TelPeriod telPeriod = telPeriodService.findByReportId(reportId);
        JSONObject one = new JSONObject();
        one.put("document", telPeriod.getValue());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(one);
        return jsonArray;
    }

    //百融手机在网状态
    private JSONArray toTelStatus(@NotNull Integer reportId) {
        TelStatus telStatus = telStatusService.findByReportId(reportId);
        JSONObject one = new JSONObject();
        one.put("document", telStatus.getValue());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(one);
        return jsonArray;
    }

    //阿里京东用户资料表单
    private JSONArray toCustomerTable(@NotNull Integer reportId) {
        Report report = reportDAO.findById(reportId);
        Customer customer = customerService.findById(report.getCustomerId());

        JSONObject time = new JSONObject();
        time.put("key", "验证时间");
        time.put("value", report.getCreated_at().toString());
        JSONObject reportNum = new JSONObject();
        reportNum.put("key", "报告编号");
        reportNum.put("value", report.getNum());
        JSONObject name = new JSONObject();
        name.put("key", "被验证人姓名");
        name.put("value", customer.getName());
        JSONObject cell = new JSONObject();
        cell.put("key", "被验证人电话");
        cell.put("value", customer.getCell());
        JSONObject idCard = new JSONObject();
        idCard.put("key", "被验证人身份证");
        idCard.put("value", customer.getIdNum());
        JSONObject bank = new JSONObject();
        bank.put("key", "被验证人银行卡");
        bank.put("value", customer.getBankId());
        JSONObject three = new JSONObject();
        three.put("key", "被验证人三天内被查次数");
        three.put("value", customerDAO.threeDaysCount(customer.getIdNum()));
        JSONObject count = new JSONObject();
        count.put("key", "被验证人历史被查次数");
        count.put("value", customerDAO.countCustomer(customer.getIdNum()));

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(time);
        jsonArray.add(reportNum);
        jsonArray.add(name);
        jsonArray.add(cell);
        jsonArray.add(idCard);
        jsonArray.add(bank);
        jsonArray.add(three);
        jsonArray.add(count);
        return jsonArray;
    }

    //阿里京东表单
    private JSONArray toAliJdTable(@NotNull Integer reportId) {
        Carshield carshield = jdService.findCarshield(reportId);
        BadRecord badRecord = jdService.findBadRecord(reportId);
        CellLong cellLong = jdService.findCellLong(reportId);
        BlackName blackName = jdService.findBlackName(reportId);
        Cell cell = aliService.findCell(reportId);

        JSONObject one = new JSONObject();
        JSONObject two = new JSONObject();
        JSONObject three = new JSONObject();
        JSONObject four = new JSONObject();
        JSONObject five = new JSONObject();
        JSONObject six = new JSONObject();
        JSONObject seven = new JSONObject();
        JSONObject eight = new JSONObject();
        JSONObject nine = new JSONObject();

        one.put("key", "背景黑名单");
        one.put("value", carshield.getValue());
        one.put("color", stringColor(carshield.getValue()));

        two.put("key", "手机使用情况");
        two.put("value", cellLong.getDescription());
        two.put("color", stringColor(cellLong.getDescription()));

        three.put("key", "手机归属地");
        three.put("value", cell.getProv()+cell.getCity()+"（"+cell.getName()+"）");
        three.put("color", 0);

        four.put("key", blackName.getBlackType1());
        four.put("value", blackName.getBlackCount1());
        four.put("color", stringColor(blackName.getBlackCount1()));

        five.put("key", blackName.getBlackType2());
        five.put("value", blackName.getBlackCount2());
        five.put("color", stringColor(blackName.getBlackCount2()));

        six.put("key", blackName.getBlackType3());
        six.put("value", blackName.getBlackCount3());
        six.put("color", stringColor(blackName.getBlackCount3()));

        seven.put("key", blackName.getBlackType4());
        seven.put("value", blackName.getBlackCount4());
        seven.put("color", stringColor(blackName.getBlackCount4()));

        eight.put("key", blackName.getBlackType5());
        eight.put("value", blackName.getBlackCount5());
        eight.put("color", stringColor(blackName.getBlackCount5()));

        nine.put("key", "不良记录");
        nine.put("value", blackRecordToString(badRecord.getDescription()));
        nine.put("color", stringColor(badRecord.getDescription()));

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(one);
        jsonArray.add(two);
        jsonArray.add(three);
        jsonArray.add(four);
        jsonArray.add(five);
        jsonArray.add(six);
        jsonArray.add(seven);
        jsonArray.add(eight);
        jsonArray.add(nine);

        return jsonArray;
    }

    //阿里三项验证(身份证、手机、银行卡）
    private JSONObject toCheck(@NotNull Integer reportId) {
        Customer customer = customerService.findByReportId(reportId);
        IdCard idCard = aliService.findIdCard(reportId);

        CellCheck cellCheck = aliService.findCellCheck(reportId);
        JSONObject check = new JSONObject();

        JSONObject idCardJson = new JSONObject();
        idCardJson.put("msg", stringToMsg(idCard.getMessage()));
        idCardJson.put("color", stringColor(idCard.getMessage()));
        JSONArray one = new JSONArray();
        one.add(idCardJson);

        JSONObject cellJson = new JSONObject();
        cellJson.put("msg", stringToMsg(cellCheck.getMsg()));
        cellJson.put("color", stringColor(cellCheck.getMsg()));
        JSONArray two = new JSONArray();
        two.add(cellJson);

        check.put("idCard", one);
        check.put("cell", two);

        if (StringUtils.isNotBlank(customer.getBankId())) {
            BankCard bankCard = aliService.findBankCard(reportId);
            JSONObject bankCardJson = new JSONObject();
            bankCardJson.put("msg", stringToMsg(bankCard.getMessage()));
            bankCardJson.put("color", stringColor(bankCard.getMessage()));
            JSONArray three = new JSONArray();
            three.add(bankCardJson);
            check.put("bankCard", three);
        }

        check.put("photo", "data:image/png;base64,"+idCard.getIdCardPhoto());
        return check;
    }

    //阿里京东黑名单详细列表
    private List<BlackNameList> toBlackNameList(@NotNull Integer reportId) {
        BlackName blackName = jdService.findBlackName(reportId);
        List<BlackNameList> blackNameListList = jdService.findBlackNameList(blackName.getId());
        return blackNameListList;
    }

    //用于前端判断是否显示,默认值全部false
    private Map<String, Boolean> toShow() {
        Map<String, Boolean> map = new HashedMap();
        map.put("1", false);
        map.put("2", false);
        map.put("3", false);
        map.put("4", false);
        map.put("5", false);
        map.put("6", false);
        return map;
    }

    public List<Integer> stringToIntList(String serviceList) {
        List<String> stringList = Arrays.asList(serviceList.split(","));
        List<Integer> integerList = new ArrayList<>();
        for (String s : stringList) {
            integerList.add(Integer.parseInt(s));
        }
        return integerList;
    }

    public List<String> stringToStringList(String serviceList) {
        List<String> stringList = Arrays.asList(serviceList.split(","));
        return stringList;
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

    private Integer stringColor(String s) {
        Pattern p = Pattern.compile("^命中");
        Matcher m = p.matcher(s);
        if (StringUtils.isNotBlank(s)) {
            if (s.equals("未命中") || s.equals("验证成功") || s.equals("有效身份证") || s.equals("认证成功")
                    || s.equals("查询成功_无数据") || s.equals("一致")) {
                return 1;
            } else if (s.equals("验证失败") || m.find() || s.equals("不一致")) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private String stringToMsg(String s) {
        if (StringUtils.isNotBlank(s)) {
            if (s.equals("有效身份证") || s.equals("认证成功") || s.equals("验证成功")) {
                s = "一致";
            } else {
                s = "不一致";
            }
        } else {
            s = "未验证";
        }
        return s;
    }

    private String blackRecordToString(String s) {
        if (StringUtils.isNotBlank(s)) {
            if (s.equals("查询成功_无数据")) {
                s = "未命中";
                return s;
            } else {
                return s;
            }
        } else {
            s = "未验证";
            return s;
        }
    }

}
