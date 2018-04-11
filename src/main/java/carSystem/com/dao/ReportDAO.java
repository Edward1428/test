package carSystem.com.dao;

import carSystem.com.bean.Report;
import carSystem.com.dbmanager.QueryHelper;
import carSystem.com.vo.ExcelVO;
import carSystem.com.vo.RCIVO;
import carSystem.com.vo.ReportDayCount;
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

    public List<ReportDayCount> countDayReportByUserId(Integer userId, String start, String end) {
        return queryHelper.queryAll(ReportDayCount.class, " select DATE_FORMAT(created_at,'%Y-%m-%d') as days, sum(payout) as sum, count(1) "
                + " count from report where userId = ? and status = 0 and created_at between timestamp(?) and timestamp(?) group by days "
                , userId, start, end);
    }

    public List<RCIVO> findDetail(Integer page, Integer limit) {

        if (page > 0 && limit > 0) {
            Integer start = (page - 1) * limit;

            List<RCIVO> list = queryHelper.queryAll(RCIVO.class, "select c.*, i.* from customer as c, report as r, idCard as i " +
                    "where r.customerId = c.id and r.id = i.reportId limit ?,? ", start, limit);
            return list;
        } else {
            return null;
        }

    }

    public List<ExcelVO> export(Integer userId) {
        String sql =
                "select distinct "+
                    "a.nickName, a.id, a.created_at, cu.name, cu.cell, cu.idNum, cu.bankId, cs.value as carCheck, ic.message as idcardCheck, " +
                    "cc.msg as cellCheck, bc.message as bankcardCheck, cl.description as cellLong, cell.prov, cell.city, "+
                    "cell.name as cellName, bl.blackCount1, bl.blackCount2, bl.blackCount3, bl.blackCount4, bl.blackCount5, "+
                    "br.description as bad "+
                "from "+
                    "(select u.nickName, r.id, r.customerId, r.created_at from report as r, user as u where r.status = 0 and r.userId = u.id and u.id = ?) as a "+
                "left join customer as cu on a.customerId = cu.id "+
                "left join carshield as cs on a.id = cs.reportId "+
                "left join idcard as ic on a.id = ic.reportId "+
                "left join cellcheck as cc on a.id = cc.reportId "+
                "left join bankcard as bc on a.id = bc.reportId "+
                "left join celllong as cl on a.id = cl.reportId "+
                "left join cell on a.id = cell.reportId "+
                "left join blackname as bl on a.id = bl.reportId "+
                "left join badrecord as br on a.id = br.reportId "+
                "order by nickName, created_at desc ";
        return queryHelper.queryAll(ExcelVO.class, sql, userId);
    }

    public List<ExcelVO> exportAll() {
        String sql =
                "select distinct "+
                        "a.nickName, a.id, a.created_at, cu.name, cu.cell, cu.idNum, cu.bankId, cs.value as carCheck, ic.message as idcardCheck, " +
                        "cc.msg as cellCheck, bc.message as bankcardCheck, cl.description as cellLong, cell.prov, cell.city, "+
                        "cell.name as cellName, bl.blackCount1, bl.blackCount2, bl.blackCount3, bl.blackCount4, bl.blackCount5, "+
                        "br.description as bad "+
                        "from "+
                        "(select u.nickName, r.id, r.customerId, r.created_at from report as r, user as u where r.status = 0 and r.userId = u.id) as a "+
                        "left join customer as cu on a.customerId = cu.id "+
                        "left join carshield as cs on a.id = cs.reportId "+
                        "left join idcard as ic on a.id = ic.reportId "+
                        "left join cellcheck as cc on a.id = cc.reportId "+
                        "left join bankcard as bc on a.id = bc.reportId "+
                        "left join celllong as cl on a.id = cl.reportId "+
                        "left join cell on a.id = cell.reportId "+
                        "left join blackname as bl on a.id = bl.reportId "+
                        "left join badrecord as br on a.id = br.reportId "+
                        "order by nickName, created_at desc";
        return queryHelper.queryAll(ExcelVO.class, sql);
    }
}
