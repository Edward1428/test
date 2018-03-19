package carSystem.com.service;

import carSystem.com.bean.Customer;
import carSystem.com.bean.Report;
import carSystem.com.bean.User;
import carSystem.com.dao.CustomerDAO;
import carSystem.com.dao.ReportDAO;
import carSystem.com.dbmanager.QueryHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ReportDAO reportDAO;


    public Integer insert(@NotNull Customer customer) {
        return customerDAO.insert(customer);
    }

    public Customer findById(Integer customerId) {
        return customerDAO.findById(customerId);
    }

    public Customer findByReportId(Integer reportId) {
        Report report = reportDAO.findById(reportId);
        Customer customer = findById(report.getCustomerId());
        return customer;
    }
}
