package carSystem.com.dao.report;

import carSystem.com.bean.report.SelfBlackName;
import carSystem.com.dao.BaseDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SelfBlackNameDAO extends BaseDAO<SelfBlackName> {

    public List<SelfBlackName> findByIdNum(String idNum) {
        return findAll("idNum = ?", idNum);
    }
}
