package carSystem.com.service.report.baiRong.strategy;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.baiRong.strategy.Strategy;
import carSystem.com.bean.report.baiRong.strategy.*;
import carSystem.com.service.report.baiRong.BaiRongService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategyService extends BaiRongService implements StrategyInterface{

    @Autowired
    private SpecialListService specialListService;

    @Autowired
    private ExecutionService executionService;

    @Autowired
    private ApplyLoanService applyLoanService;

    @Autowired
    private InfoRelationService infoRelationService;

    @Autowired
    private RiskStrategyService riskStrategyService;

    @Override
    public ApplyLoan getApplyLoan(JSONObject jsonObject, Integer reportId) {
        return applyLoanService.getApplyloan(jsonObject, reportId);
    }

    @Override
    public Execution getExecution(JSONObject jsonObject, Integer reportId) {
        return executionService.getExecution(jsonObject, reportId);
    }

    @Override
    public InfoRelation getInfoRelation(JSONObject jsonObject, Integer reportId) {
        return infoRelationService.getInfoRelation(jsonObject, reportId);
    }

    @Override
    public RiskStrategy getRiskStrategy(JSONObject jsonObject, Integer reportId) {
        return riskStrategyService.getRiskStrategy(jsonObject, reportId);
    }

    @Override
    public SpecialList getSpecialList(JSONObject jsonObject, Integer reportId) {
        return specialListService.getSpecialList(jsonObject, reportId);
    }

    public Strategy strategyApi(Customer customer, Integer reportId) {
        JSONObject jsonObject = getJson(customer, reportId);

        ApplyLoan applyLoan = getApplyLoan(jsonObject, reportId);
        applyLoanService.insert(applyLoan);

        Execution execution = getExecution(jsonObject, reportId);
        executionService.insert(execution);

        InfoRelation infoRelation = getInfoRelation(jsonObject, reportId);
        infoRelationService.insert(infoRelation);

        RiskStrategy riskStrategy = getRiskStrategy(jsonObject, reportId);
        riskStrategyService.insert(riskStrategy);

        SpecialList specialList = getSpecialList(jsonObject, reportId);
        specialListService.insert(specialList);

        Strategy strategy = new Strategy();
        strategy.setApplyLoan(applyLoan);
        strategy.setExecution(execution);
        strategy.setInfoRelation(infoRelation);
        strategy.setRiskStrategy(riskStrategy);
        strategy.setSpecialList(specialList);
        return strategy;
    }

    public JSONObject getJson(Customer customer, Integer reportId) {
        JSONObject jsonObject = new JSONObject();
        try {
            String apiToken = getApiToken();
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", "strategyApi");
            jso.put("tokenid", apiToken);
            reqData.put("strategy_id",baiRong_strategyId);
            reqData.put("id", customer.getIdNum());
            JSONArray cells = new JSONArray();
            cells.add(customer.getCell());
            reqData.put("cell", cells);
            reqData.put("name", customer.getName());
            reqData.put("bank_id",customer.getBankId());
            jso.put("reqData", reqData);

            String result = getApiData(jso.toString());
            jsonObject = JSON.parseObject(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
