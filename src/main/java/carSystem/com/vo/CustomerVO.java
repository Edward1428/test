package carSystem.com.vo;

import carSystem.com.bean.Customer;

import java.util.List;

public class CustomerVO {
    private Customer customer;
    private List<Integer> serviceList;

    public static String listToString(List<Integer> list) {
        String s = "";
        if (list.size() > 0) {
            s = list.get(0).toString();
            int i = 1;
            while (i < list.size()) {
                s = s + "," + list.get(i).toString();
                i++;
            }
        }
        return s;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Integer> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Integer> serviceList) {
        this.serviceList = serviceList;
    }
}
