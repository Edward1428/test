package carSystem.com.bean;

import java.sql.Timestamp;
import java.util.List;

public class Customer extends BaseBean {

    private String name;
    private String idNum;
    private String cell;
    private String bankId;
    private String linkManCell;
    private String homeAddress;
    private Timestamp created_at;

    public Customer() {
        created_at = new Timestamp(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getLinkManCell() {
        return linkManCell;
    }

    public void setLinkManCell(String linkManCell) {
        this.linkManCell = linkManCell;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
