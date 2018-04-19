package carSystem.com.service.report.xinshu;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.ali.BankCard;
import carSystem.com.bean.report.ali.Cell;
import carSystem.com.bean.report.ali.CellCheck;
import carSystem.com.bean.report.ali.IdCard;
import carSystem.com.bean.report.jd.*;
import carSystem.com.dao.report.ali.BankCardDAO;
import carSystem.com.dao.report.ali.CellCheckDAO;
import carSystem.com.dao.report.ali.CellDAO;
import carSystem.com.dao.report.ali.IdCardDAO;
import carSystem.com.dao.report.jd.*;
import carSystem.com.service.report.ali.AliService;
import carSystem.com.service.report.jd.JdService;
import carSystem.com.utils.Cryptography;
import carSystem.com.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class XinShuService {
    @Autowired
    private BankCardDAO bankCardDAO;

    @Autowired
    private CellDAO cellDAO;

    @Autowired
    private IdCardDAO idCardDAO;

    @Autowired
    private CellCheckDAO cellCheckDAO;

    @Autowired
    private BadRecordDAO badRecordDAO;

    @Autowired
    private JdService jdService;

    @Autowired
    private AliService aliService;

    @Autowired
    private BlackNameDAO blackNameDAO;

    @Autowired
    private BlackNameListDAO blackNameListDAO;

    @Autowired
    private CellLongDAO cellLongDAO;


    @Autowired
    private CarshieldDAO carshieldDAO;

    private static final String apikey = "6E088C13952A418AAA745E777EC13081";
    private static final String JDAppKey = "96a7b3b4812e581a287fc77834a6c159";
    private static final String sign = "nr1zz";


    public void xinShuApi(Customer customer, Integer reportId) {
        idCardPhoto(customer, reportId);
        cellCheck(customer, reportId);
        if (StringUtils.isNotBlank(customer.getBankId())) {
            bankCheck(customer, reportId);
        }
        badRecord(customer, reportId);
        BlackName(customer, reportId);
        cellLong(customer, reportId);

        aliService.cellApi(customer, reportId);
        jdService.carshieldApi(customer, reportId);
    }

    private String generatedSign(String sign) {
        DateTime dateTime = new DateTime();
        String time = dateTime.toString("yyyyMMdd");
        String s = sign+time;

        String generated_sign = Cryptography.md5Encode(s);
        return generated_sign;
    }

    private void idCardPhoto(Customer customer, Integer reportId) {
        String url = "http://123.59.76.144/ws/person/idCardPhoto";
        String s = url+"?apikey="+apikey+"&sign="+generatedSign(sign)+"&idCardName="+customer.getName()+"&idCardCode="+customer.getIdNum();
        IdCard idCard = new IdCard();
        idCard.setReportId(reportId);
        JSONObject jsonObject = HttpUtils.httpPostJsonObj(s);

        String rc = jsonObject.getString("rc");
        if (rc.equals("0000")) {
            idCard.setOrderNo(jsonObject.getString("orderNo"));
            JSONObject data = jsonObject.getJSONObject("data");
            idCard.setMessage(data.getString("message"));
            idCard.setIdCardPhoto(data.getString("idCardPhoto"));
            idCard.setFlag(1);
        } else {
            idCard.setFlag(-1);
        }
        idCardDAO.insert(idCard);
    }

    private void cellCheck(Customer customer, Integer reportId) {
        String url = "http://123.59.76.144/ws/person/authMobile";
        String s = url+"?apikey="+apikey+"&sign="+generatedSign(sign)+"&name="+customer.getName()+"&idCard="+customer.getIdNum()+"&mobile="+customer.getCell();
        CellCheck cellCheck = new CellCheck();
        cellCheck.setReportId(reportId);

        JSONObject jsonObject = HttpUtils.httpPostJsonObj(s);

        String rc = jsonObject.getString("rc");
        if (rc.equals("0000")) {
            cellCheck.setOrderNo(jsonObject.getString("orderNo"));
            JSONObject data = jsonObject.getJSONObject("data");
            cellCheck.setMsg(data.getString("result").equals("T") ? "一致" : "不一致");
            cellCheck.setFlag(1);
        } else {
            cellCheck.setFlag(-1);
            cellCheck.setMsg(jsonObject.getString("msg"));
        }
        cellCheckDAO.insert(cellCheck);
    }

    private void bankCheck(Customer customer, Integer reportId) {
        String url = "http://123.59.76.144/ws/personAuth/unionPay34Element";
        String s = url+"?apikey="+apikey+"&sign="+generatedSign(sign)+"&name="+customer.getName()+"&idCard="+customer.getIdNum()
                + "&accountNO="+customer.getBankId()+"&bankPreMobile="+customer.getCell();
        BankCard bankCard = new BankCard();
        bankCard.setReportId(reportId);

        JSONObject jsonObject = HttpUtils.httpPostJsonObj(s);

        String rc = jsonObject.getString("rc");
        if (rc.equals("0000")) {
            bankCard.setFlag(1);
            bankCard.setOrderNo(jsonObject.getString("orderNo"));
            JSONObject data = jsonObject.getJSONObject("data");
            bankCard.setMessage(data.getString("result"));

        } else {
            bankCard.setFlag(-1);
            bankCard.setMessage(jsonObject.getString("msg"));
        }
        bankCardDAO.insert(bankCard);
    }

    private void badRecord(Customer customer, Integer reportId) {
        String url = "http://123.59.76.144/ws/person/personBadInfo";
        String s = url+"?apikey="+apikey+"&sign="+generatedSign(sign)+"&name="+customer.getName()+"&idCard="+customer.getIdNum();
        BadRecord badRecord = new BadRecord();
        badRecord.setReportId(reportId);

        JSONObject jsonObject = HttpUtils.httpPostJsonObj(s);

        String rc = jsonObject.getString("rc");
        if (rc.equals("0000")) {
            JSONObject data = jsonObject.getJSONObject("data");
            badRecord.setOrderNo(jsonObject.getString("orderNo"));
            badRecord.setDescription(data.getString("checkMsg"));
            badRecord.setFlag(1);
        } else if (rc.equals("0001")) {
            badRecord.setFlag(1);
            badRecord.setDescription(jsonObject.getString("msg"));
        } else {
            badRecord.setFlag(-1);
            badRecord.setDescription(jsonObject.getString("msg"));
        }
        badRecordDAO.insert(badRecord);
    }

    private void BlackName(Customer customer, Integer reportId) {
        String url = "http://123.59.76.144/ws/black/compCQuery";
        String s = url+"?apikey="+apikey+"&sign="+generatedSign(sign)+"&name="+customer.getName()+"&idCard="+customer.getIdNum()
                +"&mobile="+customer.getCell();
        JSONObject jsonObject = HttpUtils.httpPostJsonObj(s);
        BlackName blackName = new BlackName();
        blackName.setReportId(reportId);
        String rc = jsonObject.getString("rc");

        if (rc.equals("0000")) {
            JSONObject data = jsonObject.getJSONObject("data");
            blackName.setOrderNo(jsonObject.getString("orderNo"));
            JSONArray totalCounts = data.getJSONArray("totalCounts");
            if (totalCounts.size() >= 5) {
                blackName.setFlag(1);
                blackName.setBlackType1(jdService.typeToString(totalCounts.getJSONObject(0).getString("blackType")));
                blackName.setBlackCount1(jdService.countToString(totalCounts.getJSONObject(0).getInteger("blackCount").toString()));

                blackName.setBlackType2(jdService.typeToString(totalCounts.getJSONObject(1).getString("blackType")));
                blackName.setBlackCount2(jdService.countToString(totalCounts.getJSONObject(1).getInteger("blackCount").toString()));

                blackName.setBlackType3(jdService.typeToString(totalCounts.getJSONObject(2).getString("blackType")));
                blackName.setBlackCount3(jdService.countToString(totalCounts.getJSONObject(2).getInteger("blackCount").toString()));

                blackName.setBlackType4(jdService.typeToString(totalCounts.getJSONObject(3).getString("blackType")));
                blackName.setBlackCount4(jdService.countToString(totalCounts.getJSONObject(3).getInteger("blackCount").toString()));

                blackName.setBlackType5(jdService.typeToString(totalCounts.getJSONObject(4).getString("blackType")));
                blackName.setBlackCount5(jdService.countToString(totalCounts.getJSONObject(4).getInteger("blackCount").toString()));
                Integer blackNameId = blackNameDAO.insert(blackName);
                jdService.insertSelfBlackName(blackNameId, customer.getIdNum());
                JSONArray list = data.getJSONArray("list");
                Integer i = list.size();
                for (int a = 0; a < i; a++) {
                    JSONObject object = list.getJSONObject(a);
                    BlackNameList blackNameList = new BlackNameList();
                    blackNameList.setBlackRiskType(jdService.typeToString(object.getString("blackRiskType")));
                    blackNameList.setBlackFactsType(jdService.typeToString(object.getString("blackFactsType")));
                    blackNameList.setBlackFacts(object.getString("blackFacts"));
                    blackNameList.setBlackPublishSource(object.getString("blackPublishSource"));
                    blackNameList.setBlackHappenDate(object.getString("blackHappenDate"));
                    blackNameList.setBlackAmt(object.getString("blackAmt"));
                    blackNameList.setBlackCount(object.getString("blackCount"));
                    blackNameList.setBlackNameId(blackNameId);
                    blackNameListDAO.insert(blackNameList);
                    }
                }
        } else if (rc.equals("0001")) {
            blackName.setFlag(1);
            blackName.setBlackType1(jdService.typeToString("A"));
            blackName.setBlackCount1(jdService.countToString("0"));

            blackName.setBlackType2(jdService.typeToString("B"));
            blackName.setBlackCount2(jdService.countToString("0"));

            blackName.setBlackType3(jdService.typeToString("C"));
            blackName.setBlackCount3(jdService.countToString("0"));

            blackName.setBlackType4(jdService.typeToString("D"));
            blackName.setBlackCount4(jdService.countToString("0"));

            blackName.setBlackType5(jdService.typeToString("E"));
            blackName.setBlackCount5(jdService.countToString("0"));
            blackName.setMsg(jsonObject.getString("msg"));
            Integer blackNameId = blackNameDAO.insert(blackName);
            jdService.insertSelfBlackName(blackNameId, customer.getIdNum());
        } else {
            blackName.setFlag(-1);
            blackNameDAO.insert(blackName);
        }
    }


    private void cellLong(Customer customer, Integer reportId) {
        String url = "http://api.xinshucredit.com/ws/person/timePhoneLength";
        String s = url+"?apikey="+apikey+"&sign="+generatedSign(sign)+"&mobilePhone="+customer.getCell();
        JSONObject jsonObject = HttpUtils.httpPostJsonObj(s);

        CellLong cellLong = new CellLong();
        cellLong.setReportId(reportId);
        String rc = jsonObject.getString("rc");

        if (rc.equals("0000")) {
            cellLong.setOrderNo(jsonObject.getString("orderNo"));
            cellLong.setFlag(1);
            JSONObject data = jsonObject.getJSONObject("data");
            cellLong.setDescription(data.getString("message"));
        } else if (rc.equals("0001")) {
            cellLong.setFlag(1);
            cellLong.setDescription(jsonObject.getString("msg"));
            cellLong.setMsg(jsonObject.getString("msg"));
        } else {
            cellLong.setFlag(-1);
            cellLong.setMsg(jsonObject.getString("msg"));
        }
        cellLongDAO.insert(cellLong);
    }
}
