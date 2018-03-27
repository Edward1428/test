package carSystem.com.service;

import carSystem.com.bean.User;
import carSystem.com.dao.UserDAO;
import carSystem.com.vo.ListQuery;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rico on 2017/6/23.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public boolean update(@NotNull User user) {
        return userDAO.update(user);
    }

    public User findUser(@NotNull String nickName, @NotNull String password) {
        String pass = User.sha512EncodePassword(password);
        return userDAO.find("email = ? and password = ?", nickName, User.sha512EncodePassword(password));
    }

    public User findSid(@NotNull String sid) {
        return userDAO.find("sid = ?", sid);
    }

    public boolean logout(@NotNull User user, @NotNull HttpServletResponse response) {
        String sid = UUID.randomUUID().toString();
        user.setSid(sid);
        user.setLast_logined_at(new Timestamp(System.currentTimeMillis()));
        userDAO.update(user);
        return true;
    }

    public User findById(Integer userId) {
        return userDAO.findById(userId);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public Integer insertUser(User user) {
        return userDAO.insert(user);
    }

    public User findByEmail(String email) {
        return userDAO.find(" email = ? ", email);
    }

    //分页查找user
    public List<User> userQuery(ListQuery query) {
        String sql = " 1=1 ";
        String nameSql = "";
        String orderBySql = " order by created_at desc ";
        String limitSql = "";

        if (StringUtils.isNotBlank(query.getName())) {
            nameSql = " and nickName like '%" +query.getName() +"%' ";
            sql = sql + nameSql;
        }

        sql = sql + orderBySql;
        Integer page = query.getPage();
        Integer limit = query.getLimit();
        if (page > 0 && limit > 0) {
            Integer start = (page - 1) * limit;
            limitSql = " limit "+start.toString()+","+limit.toString();
            sql = sql + limitSql;
        }

        return userDAO.findAll(sql);
    }
}
