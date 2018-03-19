package carSystem.com.service.report.baiRong;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.baiRong.TelStatus;
import carSystem.com.dao.report.baiRong.TelStatusDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelStatusService extends BaiRongService{

    @Autowired
    private TelStatusDAO telStatusDAO;

    public TelStatus findByReportId(Integer reportId) {
        return telStatusDAO.findByReportId(reportId);
    }

    public TelStatus telStatusApi(Customer customer, Integer reportId) {
        TelStatus telStatus = new TelStatus();
        try {
            String apiToken = getApiToken();
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", "TrinityForceAPI");
            jso.put("tokenid", apiToken);
            reqData.put("meal", "TelStatus");
            reqData.put("id", customer.getIdNum());
            reqData.put("cell", customer.getCell());
            reqData.put("bank_id", customer.getBankId());

            reqData.put("name", customer.getName());
            jso.put("reqData", reqData);

            String result = getApiData(jso.toString());
            JSONObject jsonObject = JSON.parseObject(result);
            telStatus.setResult(resultMsg(jsonObject.getJSONObject("product").getString("result")));
            telStatus.setFlag(jsonObject.getJSONObject("flag").getInteger("flag_telstatus"));
            telStatus.setOperation(getOperation(jsonObject.getJSONObject("product").getString("operation")));
            telStatus.setValue(valueMsg(jsonObject.getJSONObject("product").getJSONObject("data").getString("value")));
            telStatus.setReportId(reportId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        telStatusDAO.insert(telStatus);
        return telStatus;
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
            case "1" : s = "正常";
                break;
            case "2" : s = "停机";
                break;
            case "3" : s = "销号";
                break;
            case "4" : s = "异常（包括预销号、不在网；联通手机号非正常状态均返回此值）";
                break;
            default: s = "无结果";
        }
        return s;
    }
}
