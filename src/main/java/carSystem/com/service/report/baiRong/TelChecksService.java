package carSystem.com.service.report.baiRong;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.baiRong.TelChecks;
import carSystem.com.dao.report.baiRong.TelChecksDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelChecksService extends BaiRongService {

    @Autowired
    private TelChecksDAO telChecksDAO;

    public TelChecks findByReportId(Integer reportId) {
        return telChecksDAO.findByReportId(reportId);
    }

    public TelChecks telChecksApi(Customer customer, Integer reportId) {
        TelChecks telChecks = new TelChecks();
        try {
            String apiToken = getApiToken();
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", "TrinityForceAPI");
            jso.put("tokenid", apiToken);
            reqData.put("meal", "TelCheck_s");
            reqData.put("id", customer.getIdNum());
            reqData.put("cell", customer.getCell());
            reqData.put("bank_id", customer.getBankId());

            reqData.put("name", customer.getName());
            jso.put("reqData", reqData);

            String result = getApiData(jso.toString());
            JSONObject jsonObject = JSON.parseObject(result);
            telChecks.setResult(resultMsg(jsonObject.getJSONObject("product").getString("result")));
            telChecks.setFlag(jsonObject.getJSONObject("flag").getInteger("flag_telcheck"));
            telChecks.setOperation(getOperation(jsonObject.getJSONObject("product").getString("operation")));
            telChecks.setReportId(reportId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        telChecksDAO.insert(telChecks);
        return telChecks;
    }

    //解析result查询结果
    private String resultMsg(String result) {
        String s = "";
        switch (result) {
            case "0" : s = "查无此号";
                break;
            case "1" : s = "一致";
                break;
            case "2" : s = "不一致";
                break;
            default: s = "无法查询虚拟号段";
        }
        return s;
    }

}
