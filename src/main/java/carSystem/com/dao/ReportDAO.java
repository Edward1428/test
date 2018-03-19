package carSystem.com.dao;

import carSystem.com.bean.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportDAO extends BaseDAO<Report> {

    public List<Report> findAllByUserId(Integer userId) {
        return findAll("userId = ?", userId);
    }

}
