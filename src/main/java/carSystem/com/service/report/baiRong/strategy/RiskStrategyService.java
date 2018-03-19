package carSystem.com.service.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.RiskStrategy;
import carSystem.com.dao.report.baiRong.strategy.RiskStrategyDAO;
import carSystem.com.service.report.baiRong.BaiRongService;
import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class RiskStrategyService extends BaiRongService{

    @Autowired
    private RiskStrategyDAO riskStrategyDAO;

    public RiskStrategy getRiskStrategy(JSONObject jsonObject, Integer reportId) {
        RiskStrategy riskStrategy = new RiskStrategy();
        riskStrategy.setReportId(reportId);
        Integer flag = analyzeFlag(jsonObject.getString("flag_riskstrategy"));
        riskStrategy.setFlag(flag);
        if (flag == 1) {
            riskStrategy.setRs_final_decision(decision(jsonObject.getString("rs_final_decision")));
            riskStrategy.setRs_Rule_decision(decision(jsonObject.getString("rs_Rule_decision")));
            riskStrategy.setRs_ScoreAf_decision(decision(jsonObject.getString("rs_ScoreAf_decision")));
            riskStrategy.setRs_Socre_decision(decision(jsonObject.getString("rs_Socre_decision")));
            riskStrategy.setRule_final_decision(decision(jsonObject.getString("Rule_final_decision")));

            riskStrategy.setRs_strategy_id	(jsonObject.getString("rs_strategy_id"));
            riskStrategy.setRs_strategy_version	(jsonObject.getString("rs_strategy_version"));
            riskStrategy.setRs_platform	(jsonObject.getString("rs_platform"));
            riskStrategy.setRs_scene	(jsonObject.getString("rs_scene"));
            riskStrategy.setRs_product_name	(jsonObject.getString("rs_product_name"));
            riskStrategy.setRs_product_type	(jsonObject.getString("rs_product_type"));
            riskStrategy.setRs_Rule_weight	(jsonObject.getString("rs_Rule_weight"));
            riskStrategy.setRs_ScoreAf_scoreafcust	(jsonObject.getString("rs_ScoreAf_scoreafcust"));
            riskStrategy.setRs_Score_scorebankv2	(jsonObject.getString("rs_Score_scorebankv2"));
            riskStrategy.setRule_final_weight	(jsonObject.getString("Rule_final_weight"));
            riskStrategy.setRule_weight_QJE010	(jsonObject.getString("rule_weight_QJE010"));
            riskStrategy.setRule_weight_QJE020	(jsonObject.getString("rule_weight_QJE020"));
            riskStrategy.setRule_weight_QJE040	(jsonObject.getString("rule_weight_QJE040"));
            riskStrategy.setRule_weight_QJE050	(jsonObject.getString("rule_weight_QJE050"));
            riskStrategy.setRule_weight_QCD010	(jsonObject.getString("rule_weight_QCD010"));
            riskStrategy.setRule_weight_QCD020	(jsonObject.getString("rule_weight_QCD020"));
            riskStrategy.setRule_weight_QCD030	(jsonObject.getString("rule_weight_QCD030"));
            riskStrategy.setRule_weight_QCD040	(jsonObject.getString("rule_weight_QCD040"));
            riskStrategy.setRule_weight_QCD050	(jsonObject.getString("rule_weight_QCD050"));
            riskStrategy.setRule_weight_QCD060	(jsonObject.getString("rule_weight_QCD060"));
            riskStrategy.setRule_weight_QCD070	(jsonObject.getString("rule_weight_QCD070"));
            riskStrategy.setRule_weight_QCD080	(jsonObject.getString("rule_weight_QCD080"));
            riskStrategy.setRule_weight_QJS150	(jsonObject.getString("rule_weight_QJS150"));
            riskStrategy.setRule_weight_QJS151	(jsonObject.getString("rule_weight_QJS151"));
            riskStrategy.setRule_weight_QJS152	(jsonObject.getString("rule_weight_QJS152"));
            riskStrategy.setRule_weight_QJS153	(jsonObject.getString("rule_weight_QJS153"));
            riskStrategy.setRule_weight_QJS154	(jsonObject.getString("rule_weight_QJS154"));
            riskStrategy.setRule_weight_QJS155	(jsonObject.getString("rule_weight_QJS155"));
            riskStrategy.setRule_weight_QJS156	(jsonObject.getString("rule_weight_QJS156"));
            riskStrategy.setRule_weight_QJS157	(jsonObject.getString("rule_weight_QJS157"));
            riskStrategy.setRule_weight_QJS158	(jsonObject.getString("rule_weight_QJS158"));
            riskStrategy.setRule_weight_QJS159	(jsonObject.getString("rule_weight_QJS159"));
            riskStrategy.setRule_weight_QJS160	(jsonObject.getString("rule_weight_QJS160"));
            riskStrategy.setRule_weight_QJS161	(jsonObject.getString("rule_weight_QJS161"));
            riskStrategy.setRule_weight_QJS162	(jsonObject.getString("rule_weight_QJS162"));
            riskStrategy.setRule_weight_QJS163	(jsonObject.getString("rule_weight_QJS163"));
            riskStrategy.setRule_weight_QJS164	(jsonObject.getString("rule_weight_QJS164"));
            riskStrategy.setRule_weight_QJS165	(jsonObject.getString("rule_weight_QJS165"));
            riskStrategy.setRule_weight_QJS166	(jsonObject.getString("rule_weight_QJS166"));
            riskStrategy.setRule_weight_QJS167	(jsonObject.getString("rule_weight_QJS167"));
            riskStrategy.setRule_weight_CBC001	(jsonObject.getString("rule_weight_CBC001"));
            riskStrategy.setRule_weight_CBC002	(jsonObject.getString("rule_weight_CBC002"));
            riskStrategy.setRule_weight_CBC003	(jsonObject.getString("rule_weight_CBC003"));
            riskStrategy.setRule_weight_CBC004	(jsonObject.getString("rule_weight_CBC004"));
            riskStrategy.setRule_weight_CAC001	(jsonObject.getString("rule_weight_CAC001"));
            riskStrategy.setRule_weight_CAC002	(jsonObject.getString("rule_weight_CAC002"));
            riskStrategy.setRule_weight_CAC003	(jsonObject.getString("rule_weight_CAC003"));
            riskStrategy.setRule_weight_CAC004	(jsonObject.getString("rule_weight_CAC004"));

            riskStrategy.setRule_name_QJE010(jsonObject.getString("Rule_name_QJE010"));
            riskStrategy.setRule_name_QJE020(jsonObject.getString("Rule_name_QJE020"));
            riskStrategy.setRule_name_QJE040(jsonObject.getString("Rule_name_QJE040"));
            riskStrategy.setRule_name_QJE050(jsonObject.getString("Rule_name_QJE050"));
            riskStrategy.setRule_name_QCD010(jsonObject.getString("Rule_name_QCD010"));
            riskStrategy.setRule_name_QCD020(jsonObject.getString("Rule_name_QCD020"));
            riskStrategy.setRule_name_QCD030(jsonObject.getString("Rule_name_QCD030"));
            riskStrategy.setRule_name_QCD040(jsonObject.getString("Rule_name_QCD040"));
            riskStrategy.setRule_name_QCD050(jsonObject.getString("Rule_name_QCD050"));
            riskStrategy.setRule_name_QCD060(jsonObject.getString("Rule_name_QCD060"));
            riskStrategy.setRule_name_QCD070(jsonObject.getString("Rule_name_QCD070"));
            riskStrategy.setRule_name_QCD080(jsonObject.getString("Rule_name_QCD080"));
            riskStrategy.setRule_name_QJS150(jsonObject.getString("Rule_name_QJS150"));
            riskStrategy.setRule_name_QJS151(jsonObject.getString("Rule_name_QJS151"));
            riskStrategy.setRule_name_QJS152(jsonObject.getString("Rule_name_QJS152"));
            riskStrategy.setRule_name_QJS153(jsonObject.getString("Rule_name_QJS153"));
            riskStrategy.setRule_name_QJS154(jsonObject.getString("Rule_name_QJS154"));
            riskStrategy.setRule_name_QJS155(jsonObject.getString("Rule_name_QJS155"));
            riskStrategy.setRule_name_QJS156(jsonObject.getString("Rule_name_QJS156"));
            riskStrategy.setRule_name_QJS157(jsonObject.getString("Rule_name_QJS157"));
            riskStrategy.setRule_name_QJS158(jsonObject.getString("Rule_name_QJS158"));
            riskStrategy.setRule_name_QJS159(jsonObject.getString("Rule_name_QJS159"));
            riskStrategy.setRule_name_QJS160(jsonObject.getString("Rule_name_QJS160"));
            riskStrategy.setRule_name_QJS161(jsonObject.getString("Rule_name_QJS161"));
            riskStrategy.setRule_name_QJS162(jsonObject.getString("Rule_name_QJS162"));
            riskStrategy.setRule_name_QJS163(jsonObject.getString("Rule_name_QJS163"));
            riskStrategy.setRule_name_QJS164(jsonObject.getString("Rule_name_QJS164"));
            riskStrategy.setRule_name_QJS165(jsonObject.getString("Rule_name_QJS165"));
            riskStrategy.setRule_name_QJS166(jsonObject.getString("Rule_name_QJS166"));
            riskStrategy.setRule_name_QJS167(jsonObject.getString("Rule_name_QJS167"));
            riskStrategy.setRule_name_CBC001(jsonObject.getString("Rule_name_CBC001"));
            riskStrategy.setRule_name_CBC002(jsonObject.getString("Rule_name_CBC002"));
            riskStrategy.setRule_name_CBC003(jsonObject.getString("Rule_name_CBC003"));
            riskStrategy.setRule_name_CBC004(jsonObject.getString("Rule_name_CBC004"));
            riskStrategy.setRule_name_CAC001(jsonObject.getString("Rule_name_CAC001"));
            riskStrategy.setRule_name_CAC002(jsonObject.getString("Rule_name_CAC002"));
            riskStrategy.setRule_name_CAC003(jsonObject.getString("Rule_name_CAC003"));
            riskStrategy.setRule_name_CAC004(jsonObject.getString("Rule_name_CAC004"));
        }
        return riskStrategy;
    }

    public Integer insert(@NotNull RiskStrategy riskStrategy) {
        return riskStrategyDAO.insert(riskStrategy);
    }

    public RiskStrategy findByReportId(Integer reportId) {
        return riskStrategyDAO.findByReportId(reportId);
    }

    private String decision(String value) {
        String s = null;
        if (value != null) {
            switch (value) {
                case "Accept": s= "通过";
                    break;
                case "Reject": s= "拒绝";
                    break;
                case "Review": s= "复议";
            }
        }
        return s;
    }
}
