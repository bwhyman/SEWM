package com.se.working.util;

import java.util.HashMap;
import java.util.Map;

public class KeyValueUtil {
	/**
	 * 星期对应的值
	 */
	public static Map<String, Integer> week = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put("星期一", 1);
			put("星期二", 2);
			put("星期三", 3);
			put("星期四", 4);
			put("星期五", 5);
			put("星期六", 6);
			put("星期日", 7);
		}
	};

	/**
	 * 汉字数据转成数字
	 */
	public static Map<String, Integer> chineseToNumber = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put("一", 1);
			put("二", 2);
			put("三", 3);
			put("四", 4);
			put("五", 5);
			put("六", 6);
			put("七", 7);
			put("八", 8);
			put("九", 9);
			put("十", 10);
			put("十一", 11);
			put("十二", 12);
			put("十三", 13);
			put("十四", 14);
			put("十五", 15);
			put("十六", 16);
			put("十七", 17);
			put("十八", 18);
			put("十九", 19);
			put("二十", 20);
		}
	};
	
	/**
	 * 第几节课的开始时间
	 */
	public static Map<String, String> courseStartTime = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("第一二节", "08:00");
			put("第三四节", "10:05");
			put("第五六节", "13:40");
			put("第七八节", "15:35");
			put("第九十节", "18:00");
			put("第十一十二节", "19:40");
		}
	};
	
	/**
	 * 第几节课的结束时间
	 */
	public static Map<String, String> courseEndTime = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("第一二节", "09:35");
			put("第三四节", "11:40");
			put("第五六节", "15:15");
			put("第七八节", "17:10");
			put("第九十节", "19:35");
			put("第十一十二节", "21:15");
		}
	};
}
