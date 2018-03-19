package carSystem.com.service.report.baiRong;

import com.bfd.facade.MerchantServer;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BaiRongService {

    @Value("${baiRong_userName}")
    private String baiRong_userName;

    @Value("${baiRong_password}")
    private String baiRong_password;

    @Value("${baiRong_apiCode}")
    protected String baiRong_apiCode;

    @Value("${baiRong_strategyId}")
    protected String baiRong_strategyId;

    protected String getApiToken() throws Exception {
        MerchantServer ms = new MerchantServer();
        String login_result=ms.login(baiRong_userName, baiRong_password, "LoginApi", baiRong_apiCode);
        JSONObject json=JSONObject.fromObject(login_result);
        return json.getString("tokenid");
    }

    protected String getApiData(String jsonData) {
        MerchantServer ms = new MerchantServer();
        return ms.getApiData(jsonData, baiRong_apiCode);
    }

    //用于获取运营商
    protected String getOperation(String operation) {
        String s = null;
        if (operation != null) {
            switch (operation) {
                case "1" : s = "电信";
                    break;
                case "2" : s = "联通";
                    break;
                case "3" : s = "移动";
                    break;
                case "4" : s = "其他，如170号段等";
            }
        }

        return s;
    }

    //用于解析String输出标识
    protected Integer analyzeFlag(String flag) {
        Integer s = -1;
        if (flag != null) {
            switch (flag) {
                case "1" : s = 1;
                    break;
                case "0" : s = 0;
                    break;
                case "98" : s = 98;
                    break;
                case "99" : s = 99;
            }
        }

        return s;
    }
}
