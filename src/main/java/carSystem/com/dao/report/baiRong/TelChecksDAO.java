package carSystem.com.dao.report.baiRong;

import carSystem.com.bean.report.baiRong.TelChecks;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TelChecksDAO extends BaseDAO<TelChecks>{
    public TelChecks findByReportId(Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
