package carSystem.com.vo;

import carSystem.com.bean.report.ali.AliApi;
import carSystem.com.bean.report.baiRong.BankFourPro;
import carSystem.com.bean.report.baiRong.TelChecks;
import carSystem.com.bean.report.baiRong.TelPeriod;
import carSystem.com.bean.report.baiRong.TelStatus;
import carSystem.com.bean.report.baiRong.strategy.Strategy;
import carSystem.com.bean.report.jd.JdApi;

public class ReportVO {
    private Strategy strategy;
    private BankFourPro bankFourPro;
    private TelChecks telChecks;
    private TelPeriod telPeriod;
    private TelStatus telStatus;
    private AliApi aliApi;
    private JdApi jdApi;

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public BankFourPro getBankFourPro() {
        return bankFourPro;
    }

    public void setBankFourPro(BankFourPro bankFourPro) {
        this.bankFourPro = bankFourPro;
    }

    public TelChecks getTelChecks() {
        return telChecks;
    }

    public void setTelChecks(TelChecks telChecks) {
        this.telChecks = telChecks;
    }

    public TelPeriod getTelPeriod() {
        return telPeriod;
    }

    public void setTelPeriod(TelPeriod telPeriod) {
        this.telPeriod = telPeriod;
    }

    public TelStatus getTelStatus() {
        return telStatus;
    }

    public void setTelStatus(TelStatus telStatus) {
        this.telStatus = telStatus;
    }

    public AliApi getAliApi() {
        return aliApi;
    }

    public void setAliApi(AliApi aliApi) {
        this.aliApi = aliApi;
    }

    public JdApi getJdApi() {
        return jdApi;
    }

    public void setJdApi(JdApi jdApi) {
        this.jdApi = jdApi;
    }
}
