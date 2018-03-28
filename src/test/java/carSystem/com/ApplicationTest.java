package carSystem.com;

import carSystem.com.bean.Customer;
import carSystem.com.bean.User;
import carSystem.com.bean.report.baiRong.*;
import carSystem.com.service.report.ApiService;
import carSystem.com.utils.IdcardValidatorUtil;
import carSystem.com.utils.SqlBuilder;
import ch.qos.logback.classic.db.SQLBuilder;
import com.bfd.facade.MerchantServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private ApiService apiService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void login() throws Exception {
//		MerchantServer ms = new MerchantServer();
//
//		String login_result = ms.login("mogeCL", "mogeCL", "LoginApi", "3002021");
//
//		System.out.println(login_result);
//		String tokenId = JSONObject.fromObject(login_result).getString("tokenid");
//		System.out.println(tokenId);
//		JSONObject jsonObject = new JSONObject();
//		JSONObject reqData = new JSONObject();
//		JSONArray cells = new JSONArray();
//		jsonObject.put("apiName", "strategyApi");
//		jsonObject.put("tokenid", tokenId);
//		reqData.put("strategy_id","STR0000189");
//		cells.add("15876538217");
//		reqData.put("cell", cells);
//		reqData.put("name", "李林海");
//		reqData.put("id","445122199109085935");
//		jsonObject.put("reqData",reqData);
//		String portrait_result=ms.getApiData(jsonObject.toString(), "3002021");
//		JSONObject jsonObject1 = JSONObject.fromObject(portrait_result);
//
//		System.out.println(jsonObject1);
	}

	@Test
	public void login2() throws Exception {
//		MerchantServer ms=new MerchantServer();
//		//登陆
//		String login_result=ms.login("mogeCL","mogeCL", "LoginApi", "3002021");
//		System.out.println(login_result);
//		JSONObject json=JSONObject.fromObject(login_result);
//
//		String tokenid=json.getString("tokenid");
//
//		JSONObject jso = new JSONObject();
//		JSONObject reqData = new JSONObject();
//		jso.put("apiName", "TrinityForceAPI");
//		jso.put("tokenid", tokenid);
//		reqData.put("meal", "BankFourPro");//不传默认调用所有打包产品。
//		reqData.put("id","230405199404240527");//610602199202191221  //606021199202191221
//		reqData.put("cell","18789256992");
//		reqData.put("bank_id","623020009513587222222");
//		//	reqData.put("searchkey","北京小米支付技术有限公司 雷军");
//		reqData.put("name","江臻");
//		jso.put("reqData",reqData);
//
//		String portrait_result=ms.getApiData(jso.toString(), "3002021");
//		JSONObject jsonObject1 = JSONObject.fromObject(portrait_result);
//		System.out.println(jsonObject1);
	}

	@Test
	public void bank4() throws Exception {
//		Customer customer = new Customer();
//		customer.setBankId("623020009513587222222");
//		customer.setCell("18789256992");
//		customer.setName("江臻");
//		customer.setIdNum("230405199404240527");
////		customer.setBankId("6212263602006556948");
////		customer.setCell("15876538217");
////		customer.setName("李林海");
////		customer.setIdNum("445122199109085935");
//
//		BankFourPro bankFourPro = apiService.bankFourProApi(customer, 1);
//
//		TelChecks telChecks = apiService.telChecksApi(customer, 1);
//
//		TelPeriod telPeriod = apiService.telPeriodApi(customer, 1);
//
//		TelStatus telStatus = apiService.telStatusApi(customer, 1);
//
//		Strategy strategy = apiService.strategyApi(customer, 1);
//
//		System.out.println("结束");
	}

	@Test
	public void  md5() throws Exception {
//		Format format = new SimpleDateFormat("yyyyMMdd");
//		String data = format.format(new Date());
//		System.out.println(data);
//		String encryptStr = "6tj4u"+ data;
//		MessageDigest md5;
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//			byte[] md5Bytes = md5.digest(encryptStr.getBytes());
//			StringBuffer hexValue = new StringBuffer();
//			for (int i = 0; i < md5Bytes.length; i++) {
//				int val = ((int) md5Bytes[i]) & 0xff;
//				if (val < 16)
//					hexValue.append("0");
//				hexValue.append(Integer.toHexString(val));
//			}
//			encryptStr = hexValue.toString();
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//
//		System.out.println(encryptStr);
	}

	@Test
	public void sh512() {
//		String s = "123456";
//		System.out.println(User.sha512EncodePassword(s));
	}

	@Test
	public void idCard() {
//		String s = "133029107501015478";
//		boolean b = IdcardValidatorUtil.isValidatedAllIdcard(s);
//		System.out.println(b);
	}

	@Test
	public void zz() {
//		String s = "查询成功_有数据,B";
//		Pattern p = Pattern.compile("^查询成功_有数据,");
//		Matcher m = p.matcher(s);
//		if (StringUtils.isNotBlank(s)) {
//			if (s.equals("查询成功_无数据")) {
//				s = "未命中";
//
//			} else if (m.find()) {
//				String string = "命中：";
//				Pattern A = Pattern.compile("[A]");
//				Matcher a = A.matcher(s);
//				Pattern B = Pattern.compile("[B]");
//				Matcher b = B.matcher(s);
//				Pattern C = Pattern.compile("[C]");
//				Matcher c = C.matcher(s);
//				Pattern D = Pattern.compile("[D]");
//				Matcher d = D.matcher(s);
//
//				if (a.find()) {
//					string = string + "在逃;";
//				}
//				if (b.find()) {
//					string = string + "前科;";
//				}
//				if (c.find()) {
//					string = string + "吸毒;";
//				}
//
//				if (d.find()) {
//					string = string + "涉毒;";
//				}
//				s = string;
//			} else {
//				s = "未命中";
//			}
//
//		} else {
//			s = "未验证";
//		}
//		System.out.println(s);
	}

	@Test
	public void test() {
		Customer newCustomer = new Customer();
		newCustomer.setIdNum("123123123");
		newCustomer.setBankId("银行卡");
		newCustomer.setName("llh");
		DateTime end = new DateTime();
		DateTime start = end.minusDays(1);

		SqlBuilder sqlBuilder = new SqlBuilder();
		sqlBuilder.appendSql(" created_at between timestamp(" + end.toString("yyyy-MM-dd HH:mm:ss") +
				") and timestamp(" + start.toString("yyyy-MM-dd HH:mm:ss") + ") ");

		if (StringUtils.isNotBlank(newCustomer.getName())) {
			sqlBuilder.appendSql(" and nickName = ");
			sqlBuilder.appendValue(newCustomer.getName());
		}

		if (StringUtils.isNotBlank(newCustomer.getCell())) {
			sqlBuilder.appendSql(" and cell = ");
			sqlBuilder.appendValue(newCustomer.getCell());
		}

		if (StringUtils.isNotBlank(newCustomer.getBankId())) {
			sqlBuilder.appendSql(" and bankId = ");
			sqlBuilder.appendValue(newCustomer.getBankId());
		}

		if (StringUtils.isNotBlank(newCustomer.getIdNum())) {
			sqlBuilder.appendSql(" and idNum =  ");
			sqlBuilder.appendValue(newCustomer.getIdNum());
		}
		System.out.println(sqlBuilder.getSql());
	}
}
