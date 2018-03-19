package carSystem.com.bean.report.baiRong;

import carSystem.com.bean.BaseBean;

//百融风险罗盘，整合调用
//银行卡四要素验证
public class BankFourPro extends BaseBean {

    private Integer reportId;
    //银行卡四要素验证产品计费标识 1计费，0不计费
    private Integer flag;
    private String result;
    private String msg;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
