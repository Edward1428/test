package carSystem.com.dao;

import carSystem.com.bean.Report;
import carSystem.com.dbmanager.QueryHelper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportDAO extends BaseDAO<Report> {

    @Autowired
    private QueryHelper queryHelper;

    public List<Report> findAllByUserId(Integer userId) {
        return findAll("userId = ? and status = 0 ", userId);
    }


    public long sumAllPayoutByUserId(Integer userId) {
        return queryHelper.stateLong(" select sum(payout) from report where userId = ? and status = 0 ", userId);
    }

    public long sumPayOutByUserId(Integer userId, DateTime start, DateTime end) {
        return queryHelper.stateLong( " select sum(payout) from report where userId = ? " +
                " and created_at between timestamp(?) and timestamp(?) and status = 0 ",
                userId, start.toString("yyyy-MM-dd HH:mm:ss"), end.toString("yyyy-MM-dd HH:mm:ss"));
    }

    public Integer countALlByUserId(Integer userId) {
        return count(" userId = ? and status = 0 ", userId);
    }

    public Integer countByUserId(Integer userId, DateTime start, DateTime end) {
        return count(" userId = ? and created_at between timestamp(?) and timestamp(?) and status = 0 ",
                userId, start.toString("yyyy-MM-dd HH:mm:ss"), end.toString("yyyy-MM-dd HH:mm:ss"));
    }

    public Report findByCustomerId(Integer customerId) {
        return find(" customerId = ? ", customerId);
    }

}
