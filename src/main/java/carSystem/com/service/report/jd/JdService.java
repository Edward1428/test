package carSystem.com.service.report.jd;

import carSystem.com.bean.Customer;
import carSystem.com.bean.report.jd.*;
import carSystem.com.dao.report.jd.*;
import carSystem.com.utils.HttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class JdService {

    @Autowired
    private BadRecordDAO badRecordDAO;

    @Autowired
    private CellLongDAO cellLongDAO;

    @Autowired
    private BlackNameDAO blackNameDAO;

    @Autowired
    private BlackNameListDAO blackNameListDAO;

    @Autowired
    private CarshieldDAO carshieldDAO;

    private static final String JDAppKey = "96a7b3b4812e581a287fc77834a6c159";

    public JdApi jdApi(Customer customer, Integer reportId) {
        JdApi jdApi = new JdApi();
        CellLong cellLong = cellLongApi(customer, reportId);
        BadRecord badRecord = badRecordApi(customer, reportId);
        BlackName blackName = blackNameApi(customer, reportId);
        Carshield carshield = carshieldApi(customer, reportId);
        List<BlackNameList> blackNameLists = findBlackNameList(blackName.getId());
        jdApi.setCellLong(cellLong);
        jdApi.setBadRecord(badRecord);
        jdApi.setCarshield(carshield);
        jdApi.setBlackNameLists(blackNameLists);
        return jdApi;
    }

    private CellLong cellLongApi(Customer customer, Integer reportId) {
        String host = "https://way.jd.com";
        String path = "/hangzhoushumaikeji/mobile_get_online_interval";
        String url = host + path + "?name=" + customer.getName() + "&idcard=" + customer.getIdNum() + "&mobile="
                + customer.getCell() + "&appkey=" + JDAppKey;

        CellLong cellLong = new CellLong();
        cellLong.setReportId(reportId);

        try {
            JSONObject json = HttpUtils.httpGetJsonObj(url);
            String code = json.getString("code");
            String msg = json.getString("msg");
            if (code.equals("10000")) {
                JSONObject result = json.getJSONObject("result");
                if (result.getString("code").equals("0")) {
                    JSONObject result2 = result.getJSONObject("result");
                    String name = result2.getString("name");
                    String mobile = result2.getString("mobile");
                    String idcard = result2.getString("idcard");
                    String res = result2.getString("res");
                    String description = month(res);
                    cellLong.setFlag(1);
                    cellLong.setCode(code);
                    cellLong.setMsg(msg);
                    cellLong.setMobile(mobile);
                    cellLong.setIdcard(idcard);
                    cellLong.setName(name);
                    cellLong.setDescription(description);
                } else {
                    cellLong.setFlag(-1);
                }
            } else {
                cellLong.setFlag(-1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cellLongDAO.insert(cellLong);
        return cellLong;
    }

    private BadRecord badRecordApi(Customer customer, Integer reportId) {
        String host = "https://way.jd.com";
        String path = "/hangzhoushumaikeji/illegal_records_check";

        String url = host + path + "?name=" + customer.getName() + "&idcard=" + customer.getIdNum() + "&appkey=" + JDAppKey;

        BadRecord badRecord = new BadRecord();
        badRecord.setReportId(reportId);

        try {
            JSONObject json = HttpUtils.httpGetJsonObj(url);
            String code = json.getString("code");
            String msg = json.getString("msg");
            badRecord.setMsg(msg);
            badRecord.setCode(code);
            if (code.equals("10000")) {
                JSONObject result = json.getJSONObject("result");
                String code2 = result.getString("code");
                String message = result.getString("message");
                badRecord.setCode2(code2);
                badRecord.setMessage(message);
                if (code2.equals("0")) {
                    badRecord.setFlag(1);
                    JSONObject result2 = result.getJSONObject("result");
                    String name = result2.getString("name");
                    String idcard = result2.getString("idcard");
                    String res = result2.getString("res");
                    String case_time = result2.getString("case_time");
                    String description = result2.getString("description");
                    badRecord.setName(name);
                    badRecord.setIdcard(idcard);
                    badRecord.setRes(res);
                    badRecord.setCase_time(case_time);
                    badRecord.setDescription(description);
                } else {
                    badRecord.setFlag(-1);
                }
            } else {
                badRecord.setFlag(-1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        badRecordDAO.insert(badRecord);
        return badRecord;
    }

    private BlackName blackNameApi(Customer customer, Integer reportId) {
        String host = "https://way.jd.com";
        String path = "/XinShu/compBQuery";
        String url = host + path + "?name=" + customer.getName() + "&idCard=" + customer.getIdNum() +"&mobile=" + customer.getCell()
                + "&bankCardNo" + customer.getBankId() + "&appkey=" + JDAppKey;

        BlackName blackName = new BlackName();
        blackName.setReportId(reportId);
        try {
            JSONObject json = HttpUtils.httpGetJsonObj(url);
            String code = json.getString("code");
            if (code.equals("10000")) {
                blackName.setMsg(json.getString("msg"));
                blackName.setFlag(1);
                JSONObject result = json.getJSONObject("result");
                if (result.getString("rc").equals("0000")) {
                    String orderNo = result.getString("orderNo");
                    JSONObject data = result.getJSONObject("data");
                    JSONArray totalCounts = data.getJSONArray("totalCounts");
                    if (totalCounts.size() >= 5) {
                        blackName.setBlackType1(typeToString(totalCounts.getJSONObject(0).getString("blackType")));
                        blackName.setBlackCount1(countToString(totalCounts.getJSONObject(0).getInteger("blackCount").toString()));

                        blackName.setBlackType2(typeToString(totalCounts.getJSONObject(1).getString("blackType")));
                        blackName.setBlackCount2(countToString(totalCounts.getJSONObject(1).getInteger("blackCount").toString()));

                        blackName.setBlackType3(typeToString(totalCounts.getJSONObject(2).getString("blackType")));
                        blackName.setBlackCount3(countToString(totalCounts.getJSONObject(2).getInteger("blackCount").toString()));

                        blackName.setBlackType4(typeToString(totalCounts.getJSONObject(3).getString("blackType")));
                        blackName.setBlackCount4(countToString(totalCounts.getJSONObject(3).getInteger("blackCount").toString()));

                        blackName.setBlackType5(typeToString(totalCounts.getJSONObject(4).getString("blackType")));
                        blackName.setBlackCount5(countToString(totalCounts.getJSONObject(4).getInteger("blackCount").toString()));
                        Integer blackNameId = blackNameDAO.insert(blackName);
                        JSONArray list = data.getJSONArray("list");
                        Integer i = list.size();
                        for (int a = 0; a < i; a++) {
                            JSONObject object = list.getJSONObject(a);
                            BlackNameList blackNameList = new BlackNameList();
                            blackNameList.setBlackRiskType(typeToString(object.getString("blackRiskType")));
                            blackNameList.setBlackFactsType(typeToString(object.getString("blackFactsType")));
                            blackNameList.setBlackFacts(object.getString("blackFacts"));
                            blackNameList.setBlackPublishSource(object.getString("blackPublishSource"));
                            blackNameList.setBlackHappenDate(object.getString("blackHappenDate"));
                            blackNameList.setBlackNameId(blackNameId);
                            blackNameListDAO.insert(blackNameList);
                        }
                    }
                } else if (result.getString("rc").equals("0001")){
                    blackName.setFlag(1);
                    blackName.setBlackType1(typeToString("A"));
                    blackName.setBlackCount1(countToString("0"));

                    blackName.setBlackType2(typeToString("B"));
                    blackName.setBlackCount2(countToString("0"));

                    blackName.setBlackType3(typeToString("C"));
                    blackName.setBlackCount3(countToString("0"));

                    blackName.setBlackType4(typeToString("D"));
                    blackName.setBlackCount4(countToString("0"));

                    blackName.setBlackType5(typeToString("E"));
                    blackName.setBlackCount5(countToString("0"));
                    blackName.setMsg(result.getString("msg"));
                    blackNameDAO.insert(blackName);
                }
            } else {
                blackName.setFlag(-1);
                blackName.setMsg(json.getString("msg"));
                blackNameDAO.insert(blackName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blackName;
    }

    private Carshield carshieldApi(Customer customer, Integer reportId) {
        String host = "https://way.jd.com";
        String path = "/Carshield/Blacklist";

        String url = host + path + "?Name=" + customer.getName() + "&IdCard=" + customer.getIdNum() + "&appkey=" + JDAppKey;

        Carshield carshield = new Carshield();
        carshield.setReportId(reportId);

        try {
            JSONObject json = HttpUtils.httpGetJsonObj(url);
            String code = json.getString("code");
            carshield.setCode(code);
            carshield.setCharge(json.getString("charge"));
            carshield.setMsg(json.getString("msg"));

            if (code.equals("10000")) {
                JSONObject result = json.getJSONObject("result");
                String code2 = result.getString("Code");
                carshield.setCode2(code2);
                carshield.setMessage(result.getString("Message"));
                if (code2.equals("200")) {
                    carshield.setFlag(1);
                    JSONObject data = result.getJSONObject("Data");
                    Integer count = data.getInteger("Count");
                    if (count == 0) {
                        carshield.setValue("未命中");
                    } else {
                        JSONArray Desc = data.getJSONArray("Desc");
                        String s = Desc.getJSONObject(0).getString("value");
                        for (int i = 1; i < Desc.size(); i++) {
                            s = s + "; " +  Desc.getJSONObject(i).getString("value");
                        }
                        carshield.setValue(s);
                    }
                    carshield.setCount(count.toString());
                } else {
                    carshield.setFlag(-1);
                }
            } else {
                carshield.setFlag(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        carshieldDAO.insert(carshield);
        return carshield;
    }

    private String typeToString(String s) {
        switch (s) {
            case "A": s ="严重违法"; break;
            case "B": s = "信贷逾期"; break;
            case "C": s = "法院涉诉"; break;
            case "D": s = "潜在风险"; break;
            case "E": s = "多头借贷"; break;
            case"A02": s ="名下公司税务重大违法"; break;
            case"B01": s ="失联"; break;
            case"B02": s ="贷款不良"; break;
            case"B03": s ="短时逾期"; break;
            case"B04": s ="逾期"; break;
            case"C01": s ="失信被执行人"; break;
            case"C02": s ="被执行人"; break;
            case"C03": s ="裁判文书"; break;
            case"D01": s ="疑似催收风险"; break;
            case"D02": s ="名下公司存在违规行为"; break;
            case"D03": s ="来自信贷高风险区域"; break;
            case"D04": s ="其他潜在风险"; break;
            case"E01": s ="7天内多头借贷"; break;
            case"E02": s ="1月内多头借贷"; break;
            case"E03": s ="3月内多头借贷"; break;
            case"E04": s ="疑似多头借贷"; break;
        }
        return s;
    }

    private String countToString(String count) {
        String s = "";
        if (StringUtils.isNotBlank(count)) {

            if (count.equals("0")) {
                s = "未命中";
            } else {
                s = "命中" + count + "条";
            }
        } else {
            s = "未命中";
        }
        return s;
    }

    private String month(String s) {
        switch (s) {
            case "1" : s = "0到3个月";break;
            case "2" : s = "3到6个月";break;
            case "3" : s = "6到12个月";break;
            case "4" : s = "12到24个月";break;
            case "5" : s = "24个月以上";break;
            case "6" : s = "系统无记录";break;
            default : s="没有匹配月份";
        }
        return s;
    }

    private String descriptionToString(String s) {
        if (StringUtils.isNotBlank(s)) {
            String string = "";
            boolean a = Pattern.matches("A", s);
            boolean b = Pattern.matches("B", s);
            boolean c = Pattern.matches("C", s);
            boolean d = Pattern.matches("D", s);

            if (a) {
                string = string + "在逃；";
            }
            if (b) {
                string = string + "前科；";
            }
            if (c) {
                string = string + "吸毒；";
            }
            if (d) {
                string = string + "涉毒;";
            }

            if (a || b || c || d) {
                return "没有匹配的不良记录";
            } else {
                return string;
            }

        } else {
            return "没有匹配的不良记录";
        }
    }

    public List<BlackNameList> findBlackNameList(Integer blackNameId) {
        return blackNameListDAO.findAll("blackNameId = ?", blackNameId);
    }

    public BlackName findBlackName(Integer reportId) {
        return blackNameDAO.find("reportId = ?", reportId);
    }

    public BadRecord findBadRecord(Integer reportId) {
        return badRecordDAO.find("reportId = ?", reportId);
    }

    public CellLong findCellLong(Integer reportId) {
        return cellLongDAO.find("reportId = ?", reportId);
    }

    public Carshield findCarshield(Integer reportId) {
        return carshieldDAO.find("reportId = ?", reportId);
    }
}
