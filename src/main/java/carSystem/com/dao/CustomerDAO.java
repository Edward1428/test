package carSystem.com.dao;

import carSystem.com.bean.Customer;
import carSystem.com.dbmanager.QueryHelper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO extends BaseDAO<Customer> {


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
}
