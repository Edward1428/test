package carSystem.com.service.report.baiRong;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.baiRong.BankFourPro;
import carSystem.com.dao.report.baiRong.BankFourProDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankFourProService extends BaiRongService{

    @Autowired
    private BankFourProDAO bankFourProDAO;

    public BankFourPro findByReportId(Integer reportId) {
        return bankFourProDAO.findByReportId(reportId);
    }

    public BankFourPro bankFourProApi(Customer customer, Integer reportId) {
        BankFourPro bankFourPro = new BankFourPro();
        try {
            String apiToken = getApiToken();
            JSONObject jso = new JSONObject();
            JSONObject reqData = new JSONObject();
            jso.put("apiName", "TrinityForceAPI");
            jso.put("tokenid", apiToken);
            reqData.put("meal", "BankFourPro");
            reqData.put("id", customer.getIdNum());
            reqData.put("cell", customer.getCell());
            reqData.put("bank_id", customer.getBankId());
            reqData.put("name", customer.getName());
            jso.put("reqData", reqData);

            String result = getApiData(jso.toString());
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.get("code").equals("600000")) {
                bankFourPro.setResult(jsonObject.getJSONObject("product").getString("result"));
                bankFourPro.setMsg(jsonObject.getJSONObject("product").getString("msg"));
                bankFourPro.setFlag(jsonObject.getJSONObject("flag").getInteger("flag_bankfour"));
            } else {
                bankFourPro.setMsg("错误代码:"+jsonObject.get("code")+"，请联系管理员");
            }
            bankFourPro.setReportId(reportId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        bankFourProDAO.insert(bankFourPro);
        return bankFourPro;
    }

}
