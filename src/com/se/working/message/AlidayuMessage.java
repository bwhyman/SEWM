package com.se.working.message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.util.DateConversionUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Service
@Transactional
public class AlidayuMessage {

	/**
	 * Alidayu标准：
	 * 必须是标准JSON，数字类型必须包含引号，因此使用string较合适
	 * 不能包含[]，因此不能使用集合、数组
	 * 所有参数必须赋值，否则报参数缺失，因此即使为空也要声明
	 * 
	 * 手机关机，属于正常发送，即，与正常发送短信效果相同，
	 * 当超过运营商限制(不知具体数据)，营运商返回发送失败给dayu，dayu返回发送错误数据，但需单写监听器实现
	 * 手机欠费没有测试
	 */
	/**
	 * 监考通知模板代码: SMS_5082963
	 * 监考通知模板: 监考通知:时间:${t};地点:${l};人员:${ns};备注:${c};${n}老师，当前已分配${f}次监考
	 * 监考通知例子：监考通知:时间: 第15周星期二 06-06 08:00-10:00; 地点: 丹青楼101; 监考教师: 王波，孙哲; 王波老师，当前已分配15次监考
	 * 
	 */
	private static final String url = "http://gw.api.taobao.com/router/rest";
	private static final String appkey = "23292991";
	private static final String secret = "a80a71d6f7b73752a00c4203eb21f36e";
	// 签名
	private static final String SMS_SIGN_NAME = "东林软件";
	// 监考通知模板
	private static final String INVI_NOTICE = "SMS_5082963";
	
	
	/**
	 * 按监考信息发送，而不是监考安排，因为需要为每一位监考教师提供所有监考人员名单
	 * @param info
	 */
	public void sendInviNotice(InvigilationInfo info) {
		Calendar startDate = info.getStartTime();
		Calendar endDate = info.getEndTime();
		// 相对于基点的周数，+1，为第1周
		int iWeek = startDate.get(Calendar.WEEK_OF_YEAR) - DateConversionUtil.BASE_WEEK_OF_YEAR + 1;
		StringBuffer sTime = new StringBuffer("第" + iWeek + "周");
		// 星期 日期
		SimpleDateFormat sfDate = new SimpleDateFormat("E MM-dd");
		SimpleDateFormat sfTime = new SimpleDateFormat("HH:mm");
		sTime.append(sfDate.format(startDate.getTime()) + " ");
		sTime.append(sfTime.format(startDate.getTime()) + "-");
		sTime.append(sfTime.format(endDate.getTime()));
		String sLocation = info.getLocation();
		// 获取本次监考全部人员
		String ns = null;
		int count = 0;
		for (Invigilation i : info.getInvigilations()) {
			if (count == 0) {
				ns = i.getTeacher().getUser().getName();
			} else {
				ns = ns + "," + i.getTeacher().getUser().getName();
			}
			count++;
		}
		Gson gson = new Gson();
		for (Invigilation i : info.getInvigilations()) {
			AlidayuInvi invi = new AlidayuInvi();
			invi.setT(sTime.toString());
			invi.setL(sLocation);
			invi.setNs(ns);
			invi.setN(i.getTeacher().getUser().getName());
			invi.setF(String.valueOf(i.getTeacher().getInvigilations().size()));
			try {
				sendSMS(gson.toJson(invi), i.getTeacher().getUser().getPhoneNumber(), INVI_NOTICE);
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Date date = new Date();
				invi.setT("ERROR:" + date.toString());
				invi.setNs("ERROR");
				sendError(gson.toJson(invi), INVI_NOTICE);
			} finally {
				gson = null;
			}
		}
	}
	
	/**
	 * 抽象发送SMS短信方法，recNum可以是多号码，以英文逗号分开，无空格
	 * @param smsParamString
	 * @param RecNum
	 * @param smsTemplateCode
	 * @throws ApiException 
	 */
	private void sendSMS(String smsParamString, String recNum, String smsTemplateCode) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		// 必须使用注册的签名
		req.setSmsFreeSignName(SMS_SIGN_NAME);
		req.setSmsParamString(smsParamString);
		req.setRecNum(recNum);
		// 必须使用注册的短信模板
		req.setSmsTemplateCode(smsTemplateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		System.out.println(smsParamString);
		System.out.println(recNum);
		/*rsp = client.execute(req);

		if (rsp.getBody().contains("success")) {
			throw new ApiException("error");
		}*/
		client = null;
		req = null;
	}
	
	private void sendError(String smsParamString, String smsTemplateCode) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		// 必须使用注册的签名
		req.setSmsFreeSignName(SMS_SIGN_NAME);
		req.setSmsParamString(smsParamString);
		req.setRecNum("15104548299");
		// 必须使用注册的短信模板
		req.setSmsTemplateCode(smsTemplateCode);
		try {
			client.execute(req);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			client = null;
			req = null;
		}
		
	}
	
	public AlidayuMessage() {
		// TODO Auto-generated constructor stub
	}

}
