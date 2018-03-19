package carSystem.com.dao.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.Execution;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ExecutionDAO extends BaseDAO<Execution> {
    public Execution findByReportId(Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
