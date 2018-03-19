package carSystem.com.dao.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.RiskStrategy;
import carSystem.com.dao.BaseDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class RiskStrategyDAO extends BaseDAO<RiskStrategy>{

    public RiskStrategy findByReportId(@NotNull Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
