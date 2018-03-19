package carSystem.com.dao.report.baiRong.strategy;

import carSystem.com.bean.report.baiRong.strategy.SpecialList;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialListDAO extends BaseDAO<SpecialList>{

    public SpecialList findByReportId(Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
