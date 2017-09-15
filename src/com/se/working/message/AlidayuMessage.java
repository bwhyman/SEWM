package com.se.working.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.se.working.exception.SEWMException;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.message.pojo.AlidayuInvi;
import com.se.working.message.pojo.AlidayuInviRemind;
import com.se.working.message.pojo.AlidayuNotification;
import com.se.working.task.entity.Notification;
import com.se.working.task.entity.TeacherTask;
import com.se.working.util.DateUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Service
@Transactional
public class AlidayuMessage {

	/**
	 * Alidayu标准： 必须是标准JSON，数字类型必须包含引号，因此使用string较合适 不能包含[]，因此不能使用集合、数组
	 * 所有参数必须赋值，否则报参数缺失，因此即使为空也要声明
	 * 
	 * 手机关机，属于正常发送，即，与正常发送短信效果相同，
	 * 当超过运营商限制(不知具体数据)，营运商返回发送失败给dayu，dayu返回发送错误数据，但需单写监听器实现 手机欠费没有测试
	 * 
	 * 短信字数<=70个字数，按照70个字数一条短信计算
	 * 短信字数>70个字数，即为长短信，按照67个字数记为一条短信计算
	 * 
	 * 每个参数变量值不能多于15个字符
	 */

	private static final String url = "http://gw.api.taobao.com/router/rest";
	private static final String appkey = "23292991";
	private static final String secret = "a80a71d6f7b73752a00c4203eb21f36e";
	// 签名
	private static final String SIGN_NAME = "东林软件";
	// 软件专业监考通知模板
	private static final String SMS_INVI_NOTICE = "SMS_10395391";
	// 专业监考提醒模板
	private static final String SMS_INVI_REMIND = "SMS_7816430";
	// 专业通知模板
	private static final String SMS_NOTICE = "SMS_6105454";

	/**
	 * 按监考信息发送，而不是监考安排，因为需要为每一位监考教师提供所有监考人员名单，用于在紧急情况下联络相关教师
	 * 
	 * 监考通知模板代码: 第N次申请，为减少短信字符压缩删除参数前的声明，结果已全参数模板被拒 改为自己拼接监考通知字符串
	 * 
	 * 监考:${invi};备注:${comment};地点:${location};人员:${names};分配${freq}次 
	 * 例子: 65字符 
	 * [东林软件]监考:15周二06-06 08:00;备注:软件构件与中间件阶段;地点:丹青楼101;人员:王波波,孙哲波;分配15次
	 * 
	 * @param info
	 */
	public List<String> sendInviNotice(List<Invigilation> invigilations) {
		List<String> results = new ArrayList<>();
		InvigilationInfo info = invigilations.get(0).getInvInfo();
		Calendar startDate = info.getStartTime();
		int iWeek = DateUtils.getWeekRelativeBaseDate(startDate);
		
		StringBuffer sTimeBuffer = new StringBuffer(iWeek + "周");
		// 将星期转为中文数字，星期二转为二
		sTimeBuffer.append(DateUtils.weeksZh.get(startDate.get(Calendar.DAY_OF_WEEK)));
		// 星期 日期
		SimpleDateFormat sfDate = new SimpleDateFormat("MM-dd HH:mm");
		sTimeBuffer.append(sfDate.format(startDate.getTime()));
		
		// 时间
		String invi = sTimeBuffer.toString();
		
		String comment = info.getComment();
		
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
		
		// 地点
		String Location = info.getLocation();
		
		Gson gson = new Gson();
		for (Invigilation i : invigilations) {
			AlidayuInvi inviP = new AlidayuInvi();
			inviP.setInvi(invi);
			if (comment != null) {
				inviP.setComment(comment);
			}
			inviP.setLocation(Location);
			inviP.setNames(ns);
			inviP.setFreq(String.valueOf(i.getTeacher().getInvigilations().size()));
			sendSMS(gson.toJson(inviP), i.getTeacher().getUser().getPhoneNumber(), SMS_INVI_NOTICE);
			results.add(getMessage(inviP));
		}
		return results;
	}
	/**
	 * 监考:${invi};备注:${comment};地点:${location};人员:${names};分配${freq}次 
	 * @param invi
	 * @return
	 */
	private String getMessage(AlidayuInvi invi) {
		String message = "【东林软件】监考:" + invi.getInvi() + ";" + "备注:" + invi.getComment() + "地点:" + invi.getLocation()
				+ ";" + "人员:" + invi.getNames() + ";" + "已分配" + invi.getFreq() + "次";

		return message;
	}
	
	/**
	 * 通知:内容:${c};时间:${t};地点:${l};发布:${n}
	 * @param notification
	 */
	public void sendNotification(Notification notification) {
		StringBuffer phoneNumberbuffer = new StringBuffer();
		int count = 0;
		// 拼接电话号码
		for (TeacherTask task : notification.getTeachers()) {
			if (count == 0) {
				phoneNumberbuffer = phoneNumberbuffer.append(task.getUser().getPhoneNumber());
			} else {
				phoneNumberbuffer = phoneNumberbuffer.append("," + task.getUser().getPhoneNumber());
			}
			count++;
		}
		
		AlidayuNotification aNotification = new AlidayuNotification();
		aNotification.setC(notification.getComment());
		aNotification.setN(notification.getCreateUser().getUser().getName());
		Gson gson = new Gson();
		sendSMS(gson.toJson(aNotification), phoneNumberbuffer.toString(), SMS_NOTICE);
	
	}
	/**
	 * 监考提醒:时间:${time};地点:${location};人员:${names}
	 * @param info
	 */
	public void sendInviRemind(InvigilationInfo info) {
		SimpleDateFormat sfDate = new SimpleDateFormat("HH:mm");
		//时间
		String time = sfDate.format(info.getStartTime().getTime());
		
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		// 与当前时间比较，判断监考是今日还是明日
		if ((info.getStartTime().get(Calendar.YEAR) - today.get(Calendar.YEAR) > 0) ||
				(info.getStartTime().get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 0)) {
			time = "明日 " + time;
		} else {
			time = "今日 " + time;
		}
		
		
		String location = info.getLocation();
		// 获取本次监考全部人员姓名
		String names = null;
		// 获取本次监考全部人员手机号
		String phoneNumbers = null;
		int count = 0;
		for (Invigilation i : info.getInvigilations()) {
			if (count == 0) {
				names = i.getTeacher().getUser().getName();
				phoneNumbers = i.getTeacher().getUser().getPhoneNumber();
			} else {
				names = names + "," + i.getTeacher().getUser().getName();
				phoneNumbers = phoneNumbers + "," + i.getTeacher().getUser().getPhoneNumber();
			}
			count++;
		}
	
		Gson gson = new Gson();
		AlidayuInviRemind r = new AlidayuInviRemind();
		r.setTime(time);
		r.setLocation(location);
		r.setNames(names);
		sendSMS(gson.toJson(r), phoneNumbers, SMS_INVI_REMIND);
	}
	

	/**
	 * 抽象发送SMS短信方法，recNum可以是多号码，以英文逗号分开，无空格
	 * @param smsParamString
	 * @param recNum
	 * @param smsTemplateCode
	 */
	private boolean sendSMS(String smsParamString, String recNum, String smsTemplateCode) {
		boolean result = false;
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		// 必须使用注册的签名
		req.setSmsFreeSignName(SIGN_NAME);
		req.setSmsParamString(smsParamString);
		req.setRecNum(recNum);
		// 必须使用注册的短信模板
		req.setSmsTemplateCode(smsTemplateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		System.out.println(smsParamString);
		System.out.println(recNum);
		try {
			rsp = client.execute(req);
			String body = rsp.getBody();
			System.out.println(body);
			if (body.contains("true")) {
				result = true;
			} else {
				throw new SEWMException("短信发送失败；" + recNum + "; " + smsParamString);
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("短信发送失败；" + recNum + "; " + smsParamString);
		} finally {
			client = null;
			req = null;
			rsp = null;
		}
		
		return true;
	}

	private void sendError(String smsParamString, String smsTemplateCode) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		// 必须使用注册的签名
		req.setSmsFreeSignName(SIGN_NAME);
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
