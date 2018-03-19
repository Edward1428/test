package carSystem.com.service.report;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.ali.AliApi;
import carSystem.com.bean.report.baiRong.*;
import carSystem.com.bean.report.baiRong.strategy.Strategy;
import carSystem.com.bean.report.jd.JdApi;

public interface ApiInterface {
    //银行卡四要素验证
    public BankFourPro bankFourProApi(Customer customer, Integer reportId);
    //策略引擎
    public Strategy strategyApi(Customer customer, Integer reportId);
    //手机三要素简版—移动联通电信
    public TelChecks telChecksApi(Customer customer, Integer reportId);
    //手机在网时长—移动联通电信
    public TelPeriod telPeriodApi(Customer customer, Integer reportId);
    //手机在网状态—移动联通电信
    public TelStatus telStatusApi(Customer customer, Integer reportId);
    //阿里市场api
    public AliApi aliApi(Customer customer, Integer reportId);
    //京东市场api
    public JdApi jdApi(Customer customer, Integer reportId);
}

