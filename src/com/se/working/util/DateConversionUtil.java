package com.se.working.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConversionUtil {
	
	public static String BASE_DATE = "2016-03-07";
	public static int BASE_WEEK_OF_YEAR = getBaseWeek();
	
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
		Date dateTime = simpleDateFormat.parse(BASE_DATE + " " + time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.add(Calendar.WEEK_OF_YEAR, week - 1);
		calendar.add(Calendar.DAY_OF_WEEK,dayOfWeek - 1);
		
		return calendar;
	}
	
	/**
	 * 返回标准Calendar。yyyy-MM-dd HH:mm
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getCalendar(String dateTime) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(simpleDateFormat.parse(dateTime));
		return calendar;
	}
	
	
	private static int getBaseWeek() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sf.parse(BASE_DATE));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
}
