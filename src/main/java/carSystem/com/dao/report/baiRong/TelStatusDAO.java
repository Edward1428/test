package carSystem.com.dao.report.baiRong;

import carSystem.com.bean.report.baiRong.TelStatus;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class TelStatusDAO extends BaseDAO<TelStatus>{
    public TelStatus findByReportId(Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
