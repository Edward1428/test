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

        Integer start = (page - 1) * limit;

        List<RCIVO> list = queryHelper.queryAll(RCIVO.class, "select c.*, i.* from customer as c, report as r, idCard as i "+
                "where r.customerId = c.id and r.id = i.reportId limit ?,? ", start, limit);
        return list;
    }

    public List<ExcelVO> export(Integer userId) {
        String sql = " select a.*, bc.message as bankcardCheck from "+
                " (select re.id, re.created_at, cu.name, cu.cell, cu.idNum, cu.bankId,cc.msg as cellCheck, ic.message as idcardCheck,  ca.value as carCheck, " +
                " cl.description as cellLong, cell.prov, cell.city, cell.name as cellName, bl.blackCount1, bl.blackCount2, bl.blackCount3, bl.blackCount4, bl.blackCount5, br.description as bad "+
                " from report as re, customer as cu, idcard as ic, cellcheck as cc,  carshield as ca, celllong as cl, cell, blackname as bl, badrecord as br "+
                " where cu.id = re.customerId and re.id = ic.reportId and re.id = cc.reportId  and re.id = ca.reportId and re.id = cl.reportId "+
                " and re.id = cell.reportId and re.id = bl.reportId and re.id = br.reportId and re.status = 0 and re.userId = ?)as a "+
                " left join bankcard as bc on a.id = bc.reportId ";
        return queryHelper.queryAll(ExcelVO.class, sql, userId);
    }
}
