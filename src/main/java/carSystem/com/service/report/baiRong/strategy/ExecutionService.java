package carSystem.com.service.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.Execution;
import carSystem.com.dao.report.baiRong.strategy.ExecutionDAO;
import carSystem.com.service.report.baiRong.BaiRongService;
import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService extends BaiRongService{

    @Autowired
    private ExecutionDAO executionDAO;

    public Execution findByReportId(Integer reportId) {
        return executionDAO.findByReportId(reportId);
    }

    public Execution getExecution(JSONObject jsonObject, Integer reportId) {
        Execution execution = new Execution();
        execution.setReportId(reportId);
        Integer flag = analyzeFlag(jsonObject.getString("flag_execution"));
        execution.setFlag(flag);
        if (flag == 1) {
            execution.setEx_bad1_name(jsonObject.getString("ex_bad1_name"));
            execution.setEx_bad1_cid(jsonObject.getString("ex_bad1_cid"));
            execution.setEx_bad1_cidtype(jsonObject.getString("ex_bad1_cidtype"));
            execution.setEx_bad1_datatime(jsonObject.getString("ex_bad1_datatime"));
            execution.setEx_bad1_datatypeid(jsonObject.getString("ex_bad1_datatypeid"));
            execution.setEx_bad1_datatype(jsonObject.getString("ex_bad1_datatype"));
            execution.setEx_bad1_leader(jsonObject.getString("ex_bad1_leader"));
            execution.setEx_bad1_address(jsonObject.getString("ex_bad1_address"));
            execution.setEx_bad1_court(jsonObject.getString("ex_bad1_court"));
            execution.setEx_bad1_time(jsonObject.getString("ex_bad1_time"));
            execution.setEx_bad1_casenum(jsonObject.getString("ex_bad1_casenum"));
            execution.setEx_bad1_money(jsonObject.getString("ex_bad1_money"));
            execution.setEx_bad1_base(jsonObject.getString("ex_bad1_base"));
            execution.setEx_bad1_basecompany(jsonObject.getString("ex_bad1_basecompany"));
            execution.setEx_bad1_obligation(jsonObject.getString("ex_bad1_obligation"));
            execution.setEx_bad1_lasttime(jsonObject.getString("ex_bad1_lasttime"));
            execution.setEx_bad1_performance(jsonObject.getString("ex_bad1_performance"));
            execution.setEx_bad1_concretesituation(jsonObject.getString("ex_bad1_concretesituation"));
            execution.setEx_bad1_breaktime(jsonObject.getString("ex_bad1_breaktime"));
            execution.setEx_bad1_posttime(jsonObject.getString("ex_bad1_posttime"));
            execution.setEx_bad1_performedpart(jsonObject.getString("ex_bad1_performedpart"));
            execution.setEx_bad1_unperformpart(jsonObject.getString("ex_bad1_unperformpart"));
            execution.setEx_execut1_name(jsonObject.getString("ex_execut1_name"));
            execution.setEx_execut1_cid(jsonObject.getString("ex_execut1_cid"));
            execution.setEx_execut1_cidtype(jsonObject.getString("ex_execut1_cidtype"));
            execution.setEx_execut1_datatime(jsonObject.getString("ex_execut1_datatime"));
            execution.setEx_execut1_datatypeid(jsonObject.getString("ex_execut1_datatypeid"));
            execution.setEx_execut1_datatype(jsonObject.getString("ex_execut1_datatype"));
            execution.setEx_execut1_court(jsonObject.getString("ex_execut1_court"));
            execution.setEx_execut1_time(jsonObject.getString("ex_execut1_time"));
            execution.setEx_execut1_casenum(jsonObject.getString("ex_execut1_casenum"));
            execution.setEx_execut1_money(jsonObject.getString("ex_execut1_money"));
            execution.setEx_execut1_statute(jsonObject.getString("ex_execut1_statute"));
            execution.setEx_execut1_basic(jsonObject.getString("ex_execut1_basic"));
            execution.setEx_execut1_basiccourt(jsonObject.getString("ex_execut1_basiccourt"));
            execution.setEx_bad2_name(jsonObject.getString("ex_bad2_name"));
            execution.setEx_bad2_cid(jsonObject.getString("ex_bad2_cid"));
            execution.setEx_bad2_cidtype(jsonObject.getString("ex_bad2_cidtype"));
            execution.setEx_bad2_datatime(jsonObject.getString("ex_bad2_datatime"));
            execution.setEx_bad2_datatypeid(jsonObject.getString("ex_bad2_datatypeid"));
            execution.setEx_bad2_datatype(jsonObject.getString("ex_bad2_datatype"));
            execution.setEx_bad2_leader(jsonObject.getString("ex_bad2_leader"));
            execution.setEx_bad2_address(jsonObject.getString("ex_bad2_address"));
            execution.setEx_bad2_court(jsonObject.getString("ex_bad2_court"));
            execution.setEx_bad2_time(jsonObject.getString("ex_bad2_time"));
            execution.setEx_bad2_casenum(jsonObject.getString("ex_bad2_casenum"));
            execution.setEx_bad2_money(jsonObject.getString("ex_bad2_money"));
            execution.setEx_bad2_base(jsonObject.getString("ex_bad2_base"));
            execution.setEx_bad2_basecompany(jsonObject.getString("ex_bad2_basecompany"));
            execution.setEx_bad2_obligation(jsonObject.getString("ex_bad2_obligation"));
            execution.setEx_bad2_lasttime(jsonObject.getString("ex_bad2_lasttime"));
            execution.setEx_bad2_performance(jsonObject.getString("ex_bad2_performance"));
            execution.setEx_bad2_concretesituation(jsonObject.getString("ex_bad2_concretesituation"));
            execution.setEx_bad2_breaktime(jsonObject.getString("ex_bad2_breaktime"));
            execution.setEx_bad2_posttime(jsonObject.getString("ex_bad2_posttime"));
            execution.setEx_bad2_performedpart(jsonObject.getString("ex_bad2_performedpart"));
            execution.setEx_bad2_unperformpart(jsonObject.getString("ex_bad2_unperformpart"));
            execution.setEx_execut2_name(jsonObject.getString("ex_execut2_name"));
            execution.setEx_execut2_cid(jsonObject.getString("ex_execut2_cid"));
            execution.setEx_execut2_cidtype(jsonObject.getString("ex_execut2_cidtype"));
            execution.setEx_execut2_datatime(jsonObject.getString("ex_execut2_datatime"));
            execution.setEx_execut2_datatypeid(jsonObject.getString("ex_execut2_datatypeid"));
            execution.setEx_execut2_datatype(jsonObject.getString("ex_execut2_datatype"));
            execution.setEx_execut2_court(jsonObject.getString("ex_execut2_court"));
            execution.setEx_execut2_time(jsonObject.getString("ex_execut2_time"));
            execution.setEx_execut2_casenum(jsonObject.getString("ex_execut2_casenum"));
            execution.setEx_execut2_money(jsonObject.getString("ex_execut2_money"));
            execution.setEx_execut2_statute(jsonObject.getString("ex_execut2_statute"));
            execution.setEx_execut2_basic(jsonObject.getString("ex_execut2_basic"));
            execution.setEx_execut2_basiccourt(jsonObject.getString("ex_execut2_basiccourt"));
            execution.setEx_execut3_name(jsonObject.getString("ex_execut3_name"));
            execution.setEx_execut3_cid(jsonObject.getString("ex_execut3_cid"));
            execution.setEx_execut3_cidtype(jsonObject.getString("ex_execut3_cidtype"));
            execution.setEx_execut3_datatime(jsonObject.getString("ex_execut3_datatime"));
            execution.setEx_execut3_datatypeid(jsonObject.getString("ex_execut3_datatypeid"));
            execution.setEx_execut3_datatype(jsonObject.getString("ex_execut3_datatype"));
            execution.setEx_execut3_court(jsonObject.getString("ex_execut3_court"));
            execution.setEx_execut3_time(jsonObject.getString("ex_execut3_time"));
            execution.setEx_execut3_casenum(jsonObject.getString("ex_execut3_casenum"));
            execution.setEx_execut3_money(jsonObject.getString("ex_execut3_money"));
            execution.setEx_execut3_statute(jsonObject.getString("ex_execut3_statute"));
            execution.setEx_execut3_basic(jsonObject.getString("ex_execut3_basic"));
            execution.setEx_execut3_basiccourt(jsonObject.getString("ex_execut3_basiccourt"));
        }
        return execution;
    }

    public Integer insert(@NotNull Execution execution) {
        return executionDAO.insert(execution);
    }
}
