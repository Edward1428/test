package carSystem.com.bean.report.baiRong.strategy;

import carSystem.com.bean.BaseBean;
import carSystem.com.bean.report.baiRong.strategy.*;

//百融策略引擎
//策略引擎
public class Strategy{
    public static final Integer DISABLE = -1;

    private ApplyLoan applyLoan;
    private Execution execution;
    private InfoRelation infoRelation;
    private RiskStrategy riskStrategy;
    private SpecialList specialList;

    public ApplyLoan getApplyLoan() {
        return applyLoan;
    }

    public void setApplyLoan(ApplyLoan applyLoan) {
        this.applyLoan = applyLoan;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public InfoRelation getInfoRelation() {
        return infoRelation;
    }

    public void setInfoRelation(InfoRelation infoRelation) {
        this.infoRelation = infoRelation;
    }

    public RiskStrategy getRiskStrategy() {
        return riskStrategy;
    }

    public void setRiskStrategy(RiskStrategy riskStrategy) {
        this.riskStrategy = riskStrategy;
    }

    public SpecialList getSpecialList() {
        return specialList;
    }

    public void setSpecialList(SpecialList specialList) {
        this.specialList = specialList;
    }

}
