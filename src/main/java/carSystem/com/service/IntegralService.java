package carSystem.com.service;

import carSystem.com.bean.Goods;
import carSystem.com.bean.User;
import carSystem.com.bean.report.AdminLog;
import carSystem.com.dao.AdminLogDAO;
import carSystem.com.dao.GoodsDAO;
import carSystem.com.dao.ReportDAO;
import carSystem.com.dao.UserDAO;
import carSystem.com.utils.SqlBuilder;
import carSystem.com.vo.ListQuery;
import carSystem.com.vo.UserTableVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IntegralService {

    @Autowired
    private GoodsDAO goodsDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportDAO reportDAO;

    @Autowired
    private AdminLogDAO adminLogDAO;

    @Autowired
    private UserDAO userDAO;


    //计算需要消耗积分数
    public Integer sumPoint(List<Integer> serviceList) {
        Integer total = 0;
        for (Integer id : serviceList) {
            Goods goods = goodsDAO.findById(id);
            if (goods != null) {
                total = total + goods.getPrice();
            }
        }
        return total;
    }


    //列出所有用户报告统计
    public List<UserTableVO> toUserTableVO(List<User> userList) {
        DateTime end = new DateTime();
        DateTime one = end.minusDays(1);
        DateTime seven = end.minusDays(7);
        DateTime thirty = end.minusDays(30);
        List<UserTableVO> userTableVOList = new ArrayList<>();
        for (User user : userList) {
            UserTableVO userTableVO = new UserTableVO();
            int countAll = reportDAO.countALlByUserId(user.getId());
            long sumAll = reportDAO.sumAllPayoutByUserId(user.getId());
            int countOne = reportDAO.countByUserId(user.getId(), one, end);
            long sumOne = reportDAO.sumPayOutByUserId(user.getId(), one, end);
            int countSeven = reportDAO.countByUserId(user.getId(), seven, end);
            long sumSeven = reportDAO.sumPayOutByUserId(user.getId(), seven, end);
            int countThirty = reportDAO.countByUserId(user.getId(), thirty, end);
            long sumThirty =reportDAO.sumPayOutByUserId(user.getId(), thirty, end);

            userTableVO.setUser(user);
            userTableVO.setCountAll(countAll);
            userTableVO.setSumAll(sumAll);
            userTableVO.setCountSeven(countSeven);
            userTableVO.setSumSeven(sumSeven);
            userTableVO.setCountOne(countOne);
            userTableVO.setSumOne(sumOne);
            userTableVO.setCountThirty(countThirty);
            userTableVO.setSumThirty(sumThirty);
            userTableVOList.add(userTableVO);

        }

        return userTableVOList;
    }


    //插入log
    public Integer insertLog(AdminLog adminLog) {
        return adminLogDAO.insert(adminLog);
    }

    //查找所有log
    public List<AdminLog> findAllLog() {
        return adminLogDAO.findAll("1=1 order by created_at desc " );
    }

    //分页查找log
    public List<AdminLog> logQuery(ListQuery query) {
        SqlBuilder sql = new SqlBuilder();
        sql.appendSql("1=1");
        String orderBySql = " order by created_at desc ";
        sql.appendSql(orderBySql);

        Integer page = query.getPage();
        Integer limit = query.getLimit();
        if (page > 0 && limit > 0) {
            Integer start = (page - 1) * limit;
            sql.appendSql(" limit " + start.toString() + "," + limit.toString());
        }

        return adminLogDAO.findAll(sql.getSql(), sql.getValues());
    }



}
