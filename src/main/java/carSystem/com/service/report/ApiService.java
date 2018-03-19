package carSystem.com.service.report;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.ali.AliApi;
import carSystem.com.bean.report.baiRong.*;
import carSystem.com.bean.report.baiRong.strategy.Strategy;
import carSystem.com.bean.report.jd.JdApi;
import carSystem.com.service.report.ali.AliService;
import carSystem.com.service.report.baiRong.*;
import carSystem.com.service.report.baiRong.strategy.StrategyService;
import carSystem.com.service.report.jd.JdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService implements ApiInterface{

    @Autowired
    private BankFourProService bankFourProService;

    @Autowired
    private TelChecksService telChecksService;

    @Autowired
    private TelPeriodService telPeriodService;

    @Autowired
    private TelStatusService telStatusService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private AliService aliService;

    @Autowired
    private JdService jdService;

    //银行卡四要素验证
    @Override
    public BankFourPro bankFourProApi(Customer customer, Integer reportId) {
        return bankFourProService.bankFourProApi(customer, reportId);
    }

    //策略引擎
    @Override
    public Strategy strategyApi(Customer customer, Integer reportId) {
        return strategyService.strategyApi(customer, reportId);
    }

    //手机三要素简版—移动联通电信
    @Override
    public TelChecks telChecksApi(Customer customer, Integer reportId) {
        return telChecksService.telChecksApi(customer, reportId);
    }

    //手机在网时长—移动联通电信
    @Override
    public TelPeriod telPeriodApi(Customer customer, Integer reportId) {
        return telPeriodService.telPeriodApi(customer, reportId);
    }

    //手机在网状态—移动联通电信
    @Override
    public TelStatus telStatusApi(Customer customer, Integer reportId) {
        return telStatusService.telStatusApi(customer, reportId);
    }

    //阿里接口
    @Override
    public AliApi aliApi(Customer customer, Integer reportId) {
        return aliService.aliApi(customer, reportId);
    }

    //京东接口
    @Override
    public JdApi jdApi(Customer customer, Integer reportId) {
        return jdService.jdApi(customer, reportId);
    }
}
