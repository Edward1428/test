package carSystem.com.bean;

import org.hibernate.validator.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public class Report extends BaseBean {
    private String num;
    private String name;
    private Integer userId;
    private Integer customerId;
    private String serviceList;
    private Integer payout;
    private Short status;
    private Timestamp created_at;

    public Report() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    public Integer getPayout() {
        return payout;
    }

    public void setPayout(Integer payout) {
        this.payout = payout;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }


    public static String generateCode(int length) {
        StringBuilder sb=new StringBuilder();
        Random random = new Random();
        for (int i=0; i<length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
