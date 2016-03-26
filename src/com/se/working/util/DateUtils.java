package com.se.working.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
	
	private static String BASE_DATE = "2016-03-07";
	private static Pattern pattern = Pattern.compile("(\\d+):(\\d+)");
	private static Matcher matcher = null;
	/**
	 * 指定授课周、星期、时间，基于基点返回日历
	 * @param week 指定周
	 * @param dayOfWeek 指定星期
	 * @param time 指定时间
	 * @return
	 * @throws ParseException
	 */
	public static Calendar courseTimeToDate(int week, int dayOfWeek, String time) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getBaseCalender().getTime());
		calendar.add(Calendar.WEEK_OF_YEAR, week - 1);
		calendar.add(Calendar.DAY_OF_WEEK,dayOfWeek - 1);
		
		matcher = pattern.matcher(time);
		while (matcher.find()) {
			calendar.add(Calendar.HOUR_OF_DAY, Integer.valueOf(matcher.group(1)));
			calendar.add(Calendar.MINUTE, Integer.valueOf(matcher.group(2)));
		}
	
		return calendar;
	}
	
	/**
	 * 返回标准Calendar。yyyy-MM-dd HH:mm
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getCalendar(String dateTime)  {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(dateTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}
	/**
	 * 返回基点日历
	 * @return
	 */
	public static Calendar getBaseCalender() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(BASE_DATE));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}
}
