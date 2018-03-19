package carSystem.com.service.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.*;
import com.alibaba.fastjson.JSONObject;

public interface StrategyInterface {
    public ApplyLoan getApplyLoan(JSONObject jsonObject, Integer reportId);

    public Execution getExecution(JSONObject jsonObject, Integer reportId);

    public InfoRelation getInfoRelation(JSONObject jsonObject, Integer reportId);

    public RiskStrategy getRiskStrategy(JSONObject jsonObject, Integer reportId);

    public SpecialList getSpecialList(JSONObject jsonObject, Integer reportId);
}
