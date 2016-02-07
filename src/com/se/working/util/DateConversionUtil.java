package com.se.working.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConversionUtil {
	
	public static String BASEDATE = "2016-03-07";
	
	/**
	 * 指定周、星期、时间，基于基点返回日历
	 * @param week 指定周
	 * @param dayOfWeek 指定星期
	 * @param time 指定时间
	 * @return
	 * @throws ParseException
	 */
	public static Calendar courseTimeToDate(int week, int dayOfWeek, String time) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dateTime = simpleDateFormat.parse(BASEDATE + " " + time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.add(Calendar.WEEK_OF_YEAR, week - 1);
		calendar.add(Calendar.DAY_OF_WEEK,dayOfWeek - 1);
		
		return calendar;
	}
	
	/**
	 * 字符串连接生成时间Date类型
	 * @param yyyyMMDD
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date dateFormat(String yyyyMMDD, String time) throws ParseException{
		if (yyyyMMDD.contains(".")) {
			yyyyMMDD = yyyyMMDD.replace('.', '-');
		}
		// 替换中文引号
		if (time.contains("：")) {
			time = time.replace('：', ':');
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(simpleDateFormat.parse(yyyyMMDD));
		String[] timeArray = time.split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeArray[0]));
		cal.set(Calendar.MINUTE, Integer.valueOf(timeArray[1]));
		return cal.getTime();
	}
	
}
