package carSystem.com.bean.report;

import carSystem.com.bean.BaseBean;

import java.sql.Timestamp;

public class AdminLog extends BaseBean {

    public static final short INSERT = 1;
    public static final short UPDATE = 2;

    private Integer adminId;
    private Integer userId;
    private String adminName;
    private String userName;
    private Integer integral;
    private Short status;
    private String msg;
    private Timestamp created_at;

    public AdminLog() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
