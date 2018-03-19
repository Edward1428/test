package carSystem.com.bean.report.baiRong;

import carSystem.com.bean.BaseBean;

//百融风险罗盘，整合调用
//手机在网时长—移动联通电信
public class TelPeriod extends BaseBean {

    private Integer reportId;
    private Integer flag;
    private String operation;
    private String result;
    private String value;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
