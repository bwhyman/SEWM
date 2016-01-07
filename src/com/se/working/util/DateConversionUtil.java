package com.se.working.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConversionUtil {
	
	/**
	 * 指定起始周的年月日、第几周、周几、几点返回具体时间Date类型
	 * @param baseDateString
	 * @param myWeek
	 * @param myDayOfWeek
	 * @param hours
	 * @param minutes
	 * @return
	 * @throws ParseException
	 */
	public static Calendar courseTimeToDate(String baseDateString, int myWeek, int myDayOfWeek, int hours, int minutes) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date baseDate = simpleDateFormat.parse(baseDateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDate);
		calendar.add(Calendar.WEEK_OF_YEAR, myWeek - 1);
		calendar.add(Calendar.DAY_OF_WEEK,myDayOfWeek - 1);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
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
