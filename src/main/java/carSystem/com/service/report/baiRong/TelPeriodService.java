package carSystem.com.service.report.baiRong;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.baiRong.TelPeriod;
import carSystem.com.dao.report.baiRong.TelPeriodDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelPeriodService extends BaiRongService{

    @Autowired
    private TelPeriodDAO telPeriodDAO;

    public TelPeriod findByReportId(Integer reportId) {
        return telPeriodDAO.findByReportId(reportId);
    }

    public TelPeriod telPeriodApi(Customer customer, Integer reportId) {
        TelPeriod telPeriod = new TelPeriod();
        try {
            String apiToken = getApiToken();
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", "TrinityForceAPI");
            jso.put("tokenid", apiToken);
            reqData.put("meal", "TelPeriod");
            reqData.put("id", customer.getIdNum());
            reqData.put("cell", customer.getCell());
            reqData.put("bank_id", customer.getBankId());

            reqData.put("name", customer.getName());
            jso.put("reqData", reqData);

            String result = getApiData(jso.toString());
            JSONObject jsonObject = JSON.parseObject(result);
            telPeriod.setResult(resultMsg(jsonObject.getJSONObject("product").getString("result")));
            telPeriod.setFlag(jsonObject.getJSONObject("flag").getInteger("flag_telperiod"));
            telPeriod.setOperation(getOperation(jsonObject.getJSONObject("product").getString("operation")));
            telPeriod.setValue(valueMsg(jsonObject.getJSONObject("product").getJSONObject("data").getString("value")));
            telPeriod.setReportId(reportId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        telPeriodDAO.insert(telPeriod);
        return telPeriod;
    }

    private String resultMsg(String result) {
        String s = "";
        switch (result) {
            case "0" : s = "查无结果";
                break;
            case "1" : s = "查询成功";
                break;
            case "2" : s = "请求超时";
                break;
            case "3" : s = "系统异常";
        }
        return s;
    }

    private String valueMsg(String value) {
        String s = "";
        switch (value) {
            case "1" : s = "0~6个月";
                break;
            case "2" : s = "6~12个月";
                break;
            case "3" : s = "12~24个月";
                break;
            case "4" : s = "24个月以上";
                break;
            default: s = "无结果";
        }
        return s;
    }
}
