package carSystem.com.bean.report.ali;

import carSystem.com.bean.BaseBean;

public class BankCard extends BaseBean {
    private Integer flag;
    private Integer reportId;
    private String message;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
