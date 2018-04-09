package carSystem.com.vo;

import carSystem.com.bean.User;

import java.util.List;

public class UserCountVO {
    private User user;
    private List<ReportDayCount> list;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ReportDayCount> getList() {
        return list;
    }

    public void setList(List<ReportDayCount> list) {
        this.list = list;
    }
}
