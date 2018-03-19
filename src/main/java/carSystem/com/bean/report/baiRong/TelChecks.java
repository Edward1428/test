package carSystem.com.bean.report.baiRong;

import carSystem.com.bean.BaseBean;

//百融风险罗盘，整合调用
//手机三要素简版—移动联通电信
public class TelChecks extends BaseBean {

    private Integer reportId;
    private Integer flag;
    private String operation;
    private String result;

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

}
