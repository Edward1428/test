package carSystem.com.service.report.ali;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.ali.*;
import carSystem.com.bean.report.jd.BlackName;
import carSystem.com.bean.report.jd.BlackNameList;
import carSystem.com.dao.report.ali.*;
import carSystem.com.utils.HttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AliService {
    @Autowired
    private BankCardDAO bankCardDAO;

    @Autowired
    private CellDAO cellDAO;

    @Autowired
    private IdCardDAO idCardDAO;

    @Autowired
    private CellCheckDAO cellCheckDAO;

    private static final String HeaderKey = "Authorization";
    private static final String APPCODE = "APPCODE 2de6da0d76bc41209219fd0887244118";

    public AliApi aliApi(Customer customer, Integer reportId) {
        AliApi aliApi = new AliApi();
        IdCard  idCard = idCardApi(customer, reportId);
        BankCard bankCard = bankCardApi(customer, reportId);
        CellCheck cellCheck = cellCheck(customer, reportId);
        Cell cell = cellApi(customer, reportId);
        aliApi.setIdCard(idCard);
        aliApi.setBankCard(bankCard);
        aliApi.setCell(cell);
        aliApi.setCellCheck(cellCheck);
        return aliApi;
    }

    private IdCard idCardApi(Customer customer, Integer reportId) {
        String host = "http://idcardpho.market.alicloudapi.com";
        String path = "/idcardinfo/photo";
        String url = host + path + "?idcard=" + customer.getIdNum() + "&realname=" + customer.getName();
        IdCard idCard = new IdCard();
        idCard.setReportId(reportId);
        try {
            JSONObject json  = HttpUtils.httpGetHeaderJsonObj(url, HeaderKey, APPCODE);
            JSONObject data = json.getJSONObject("data");
            String code = data.getString("code");

            if (code.equals("1000")) {
                idCard.setFlag(1);
                idCard.setIdCardPhoto(data.getString("idCardPhoto"));
                idCard.setMessage(data.getString("message"));
                JSONObject idCardInfo = json.getJSONObject("idCardInfo");
                idCard.setArea(idCardInfo.getString("area"));
                idCard.setBirthday(idCardInfo.getString("birthday"));
                idCard.setSex(idCardInfo.getString("sex"));
            } else {
                idCard.setFlag(-1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        idCardDAO.insert(idCard);
        return idCard;
    }

    private BankCard bankCardApi(Customer customer, Integer reportId) {
        String host = "http://card3.market.alicloudapi.com";
        String path = "/card3/server";
        String url = host + path + "?cardnum=" + customer.getBankId() + "&idcard=" + customer.getIdNum() + "&realname="
                + customer.getName();

        BankCard bankCard = new BankCard();
        bankCard.setReportId(reportId);
        try {
            JSONObject json = HttpUtils.httpGetHeaderJsonObj(url, HeaderKey, APPCODE);
            JSONObject data = json.getJSONObject("data");
            String code = data.getString("code");
            if (code.equals("1000") || code.equals("1001")) {
                bankCard.setFlag(1);
                bankCard.setMessage(data.getString("message"));
            } else {
                bankCard.setFlag(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bankCardDAO.insert(bankCard);
        return bankCard;
    }

    private CellCheck cellCheck(Customer customer, Integer reportId) {
        String host = "https://auditphone.showapi.com";
        String path = "/phoneAudit";
        String url =  host + path + "?idCard=" + customer.getIdNum() + "&name=" + customer.getName() +
                 "&phone=" + customer.getCell();
        CellCheck cellCheck = new CellCheck();
        cellCheck.setReportId(reportId);
        try {
            JSONObject json = HttpUtils.httpGetHeaderJsonObj(url, HeaderKey, APPCODE);
            JSONObject showapi_res_body = json.getJSONObject("showapi_res_body");
            Integer ret_code = showapi_res_body.getInteger("ret_code");
            if (ret_code == 0) {
                cellCheck.setFlag(1);
            } else {
                cellCheck.setFlag(-1);
            }
            String code = showapi_res_body.getInteger("code").toString();
            cellCheck.setCode(code);
            cellCheck.setMsg(showapi_res_body.getString("msg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cellCheckDAO.insert(cellCheck);
        return cellCheck;
    }

    private Cell cellApi (Customer customer, Integer reportId) {
        String host = "http://showphone.market.alicloudapi.com";
        String path = "/6-1";
        String url = host + path + "?num=" + customer.getCell();

        Cell cell = new Cell();
        cell.setReportId(reportId);
        try {
            JSONObject json = HttpUtils.httpGetHeaderJsonObj(url, HeaderKey, APPCODE);
            JSONObject showapi_res_body = json.getJSONObject("showapi_res_body");
            Integer ret_code = showapi_res_body.getInteger("ret_code");
            if (ret_code == 0) {

                cell.setFlag(1);
                cell.setCity(showapi_res_body.getString("city"));
                cell.setProv(showapi_res_body.getString("prov"));
                cell.setName(showapi_res_body.getString("name"));
            } else {
                cell.setFlag(-1);
                cell.setCode(codeToString(ret_code));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cellDAO.insert(cell);
        return cell;
    }

    private String getDateToString() {
        Format format = new SimpleDateFormat("yyyyMMdd");
        String data = format.format(new Date());
        return data;
    }

    //32 位MD5
    private String md5(@NotNull String s) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(s.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            s = hexValue.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return s;
    }


    private String codeToString(@NotNull Integer code) {
        String s = "";
        switch (code) {
            case 0:
                s = "认证成功";
                break;
            case 1:
                s = "认证失败";
                break;
            case 2:
                s = "无该手机号记录";
                break;
            case 11:
                s = "手机号、身份证或者姓名为空";
                break;
            case 12:
                s = "身份证校验错误";
                break;
            case 13:
                s = "手机号校验错误";
                break;
            case 21:
                s = "渠道升级暂停服务";
                break;
            case 22:
                s = "渠道维护暂停服务";
                break;
        }
        return s;
    }

    public Cell findCell(Integer reportId) {
        return cellDAO.find("reportId = ?", reportId);
    }

    public BankCard findBankCard(Integer reportId) {
        return bankCardDAO.find("reportId = ?", reportId);
    }

    public CellCheck findCellCheck(Integer reportId) {
        return cellCheckDAO.find("reportId = ?", reportId);
    }

    public IdCard findIdCard(Integer reportId) {
        return idCardDAO.find("reportId = ?", reportId);
    }



}
