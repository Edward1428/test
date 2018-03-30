package carSystem.com.dao;

import carSystem.com.bean.Customer;
import carSystem.com.dbmanager.QueryHelper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAO extends BaseDAO<Customer> {

    @Autowired
    private QueryHelper queryHelper;

    public Integer threeDaysCount(String idNum) {

        DateTime dateTime = new DateTime();
        DateTime threeDaysAgo = dateTime.minusDays(3);
        Integer i = count(" idNum = ? and created_at between timestamp(?) and timestamp(?)", idNum,
                threeDaysAgo.toString("yyyy-MM-dd HH:mm:ss"), dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        return i > 0 ? i-1 : i;

    }

    public Integer countCustomer(String idNum) {
        Integer i = count(" idNum = ? ", idNum);
        return i > 0 ? i-1 : i;
    }

    public List<Customer> findAllByUserId(Integer userId) {
        String sql = " select customer.* from customer, report where customerId = customer.id and userId = ? and status = 0 order by customer.created_at desc ";
        return queryHelper.queryAll(Customer.class, sql, userId);
    }
}
