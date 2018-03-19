package carSystem.com.dao.report.baiRong;

import carSystem.com.bean.report.baiRong.TelPeriod;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TelPeriodDAO extends BaseDAO<TelPeriod>{
    public TelPeriod findByReportId(Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
