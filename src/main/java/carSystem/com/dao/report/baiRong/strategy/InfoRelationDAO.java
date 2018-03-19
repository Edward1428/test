package carSystem.com.dao.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.InfoRelation;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class InfoRelationDAO extends BaseDAO<InfoRelation>{
    public InfoRelation findByReportId(Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
