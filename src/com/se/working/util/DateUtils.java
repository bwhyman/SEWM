package com.se.working.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.se.working.exception.SEWMException;

public class DateUtils {
	private static Calendar BASEDATE = createBaseCalender();
	private static Pattern pattern = Pattern.compile("(\\d+):(\\d+)");
	private static Matcher matcher = null;

	/**
	 * 指定授课周、星期、时间，基于基点返回日历
	 * 
	 * @param week
	 *            指定周
	 * @param dayOfWeek
	 *            指定星期
	 * @param time
	 *            指定时间
	 * @return
	 * @throws ParseException
	 */
	public static Calendar courseTimeToDate(int week, int dayOfWeek, String time) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getBaseCalender().getTime());
		calendar.add(Calendar.WEEK_OF_YEAR, week - 1);
		calendar.add(Calendar.DAY_OF_WEEK, dayOfWeek - 1);

		matcher = pattern.matcher(time);
		while (matcher.find()) {
			calendar.add(Calendar.HOUR_OF_DAY, Integer.valueOf(matcher.group(1)));
			calendar.add(Calendar.MINUTE, Integer.valueOf(matcher.group(2)));
		}

		return calendar;
	}

	/**
	 * 返回标准Calendar。yyyy-MM-dd HH:mm
	 * 
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getCalendar(String dateTime) {
		dateTime = dateTime.replaceAll("/", "-");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(dateTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("类型转换错误");
		}
		return calendar;
	}

	/**
	 * 返回基点日历
	 * 
	 * @return
	 */
	public static Calendar getBaseCalender() {
		return BASEDATE;
	}

	/**
	 * 
	 * @return
	 */
	private static Calendar createBaseCalender() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(PropertyUtils.getBaseDate()));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}

	/**
	 * 基于日期返回相对基点时间的周数<br>
	 * 从第一周开始，没有第0周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekRelativeBaseDate(Calendar date) {
		// Calendar中，1月为0，1日为1
		LocalDate lDate = LocalDate.of(date.get(Calendar.YEAR), date.get(Calendar.MONTH ) + 1,
				date.get(Calendar.DAY_OF_MONTH));
		LocalDate baseDate = LocalDate.of(BASEDATE.get(Calendar.YEAR), BASEDATE.get(Calendar.MONTH) + 1,
				BASEDATE.get(Calendar.DAY_OF_MONTH));
		
		return (int) (baseDate.until(lDate, ChronoUnit.WEEKS) + 1);
		/*
		 * date.setFirstDayOfWeek(Calendar.MONDAY); int iWeek = 0; // 判断是否跨年 if
		 * (date.getWeekYear() > DateUtils.getBaseCalender().getWeekYear()) {
		 * iWeek = date.get(Calendar.WEEK_OF_YEAR) -
		 * getBaseCalender().get(Calendar.WEEK_OF_YEAR) +
		 * DateUtils.getBaseCalender().getWeeksInWeekYear() - 1; } else { iWeek
		 * = date.get(Calendar.WEEK_OF_YEAR) -
		 * DateUtils.getBaseCalender().get(Calendar.WEEK_OF_YEAR); } // 没有第0周
		 * iWeek = iWeek + 1; return iWeek;
		 */
	}

	/**
	 * 返回yyyy-MM-dd HH:mm字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String transformDateTimetoString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return simpleDateFormat.format(date);
	}

	public static Map<Integer, String> weeksZh = new HashMap<>();
	static {
		weeksZh.put(2, "一");
		weeksZh.put(3, "二");
		weeksZh.put(4, "三");
		weeksZh.put(5, "四");
		weeksZh.put(6, "五");
		weeksZh.put(7, "六");
		weeksZh.put(1, "日");
	}

}
