package carSystem.com.controller;

import carSystem.com.bean.Customer;
import carSystem.com.bean.User;
import carSystem.com.bean.report.SelfBlackName;
import carSystem.com.bean.report.baiRong.*;
import carSystem.com.bean.report.baiRong.strategy.Strategy;
import carSystem.com.dao.report.SelfBlackNameDAO;
import carSystem.com.service.report.ApiService;
import carSystem.com.service.report.baiRong.BankFourProService;
import carSystem.com.service.report.baiRong.TelChecksService;
import carSystem.com.service.report.baiRong.TelPeriodService;
import carSystem.com.service.report.baiRong.TelStatusService;
import carSystem.com.service.report.baiRong.strategy.StrategyService;
import carSystem.com.utils.FileUtils;
import carSystem.com.vo.ReportVO;
import carSystem.com.vo.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private BankFourProService bankFourProService;

    @Autowired
    private TelChecksService telChecksService;

    @Autowired
    private TelPeriodService telPeriodService;

    @Autowired
    private TelStatusService telStatusService;

    @Autowired
    private SelfBlackNameDAO selfBlackNameDAO;

    @RequestMapping(value = "/bbb/aaa", method = RequestMethod.GET)
    public @ResponseBody
    Result test() {
        Customer customer = new Customer();
//        customer.setBankId("623020009513587222222");
//        customer.setCell("18789256992");
//        customer.setName("江臻");
//        customer.setIdNum("230405199404240527");

//		customer.setBankId("6212263602006556948");
//		customer.setCell("15876538217");
//		customer.setName("李林海");
//		customer.setIdNum("445122199109085935");

//        customer.setBankId("6228480128258726271");
//        customer.setCell("13686273795");
//        customer.setName("杨静");
//        customer.setIdNum("439004198208276138");

//        customer.setBankId("6228480086711954577");
//        customer.setCell("18684145618");
//        customer.setName("饶波");
//        customer.setIdNum("522129198810061015");


        customer.setBankId("6228480086711954577");
        customer.setCell("13956129955");
        customer.setName("汪德奎");
        customer.setIdNum("342427196402084817");

//        BankFourPro bankFourPro = apiService.bankFourProApi(customer, 1);
//
//        TelChecks telChecks = apiService.telChecksApi(customer, 1);
//
//        TelPeriod telPeriod = apiService.telPeriodApi(customer, 1);
//
//        TelStatus telStatus = apiService.telStatusApi(customer, 1);

        Strategy strategy = strategyService.strategyApi(customer, 1);
        BankFourPro bankFourPro = bankFourProService.bankFourProApi(customer, 1);
        TelChecks telChecks = telChecksService.telChecksApi(customer, 1);
        TelPeriod telPeriod = telPeriodService.telPeriodApi(customer, 1);
        TelStatus telStatus = telStatusService.telStatusApi(customer, 1);

        ReportVO reportVO = new ReportVO();
        reportVO.setBankFourPro(bankFourPro);
        reportVO.setTelChecks(telChecks);
        reportVO.setTelPeriod(telPeriod);
        reportVO.setTelStatus(telStatus);
        reportVO.setStrategy(strategy);
//        JSONObject jsonObject = strategyService.getJson(customer, 1);
//        ApplyLoan applyLoan = applyLoanService.getApplyloan(jsonObject, 1);
//        Execution execution = executionService.getExecution(jsonObject, 1);
//        SpecialList specialList = specialListService.getSpecialList(jsonObject, 1);
//        Strategy strategy = new Strategy();
//        strategy.setApplyLoan(applyLoan);
//        strategy.setExecution(execution);
//        strategy.setSpecialList(specialList);
        return Result.success(reportVO);

    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public @ResponseBody
    Result getPassword(@RequestParam String password) {
        return Result.success(User.sha512EncodePassword(password));
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public @ResponseBody
    Result setExcel() {
        String file = FileUtils.readResource("/document/selfBlackName.md");
        JSONArray jsonArray = JSON.parseArray(file);
        List<SelfBlackName> selfblackNameList = jsonArray.toJavaList(SelfBlackName.class);
        selfBlackNameDAO.batchInsert(selfblackNameList);
        return Result.success(selfblackNameList);
    }
}
