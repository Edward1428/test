package carSystem.com.dao.report.baiRong;

import carSystem.com.bean.report.baiRong.BankFourPro;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class BankFourProDAO extends BaseDAO<BankFourPro>{
    public BankFourPro findByReportId(Integer reportId) {
        return find("reportId = ?", reportId);
    }
}
