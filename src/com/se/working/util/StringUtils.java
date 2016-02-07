package com.se.working.util;

public class StringUtils {
	
	/**
	 * 判断某个字符串是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string){
		if (string == null || string.length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 移除字符串全部空格
	 * @param string
	 * @return
	 */
	public static String trimAllWhitespace(String string){
		return string.replaceAll("\\s{1,}", "");
	}
	
	/**
	 * 是否包含软件*人
	 * @param string
	 * @return
	 */
	public static boolean isContainSoftWareNum(String string){
		if (string.contains("软件") && string.contains("人")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否包含监考地址：丹青楼、锦绣楼
	 * @param string
	 * @return
	 */
	public static boolean isContainAddress(String string){
		if (string.contains("丹青楼") || string.contains("锦绣楼") || string.contains("成栋楼")) {
			return true;
		} 
		return false;
	}
	
	/**
	 * 是否包含年月日 yyyy-mm-dd或者yyyy.mm.rr
	 * @param string
	 * @return
	 */
	public static boolean isContainYYYYMMDD(String string){
		//System.out.println(string);
		if (string.matches("(^\\d{4}-\\d{1,2}-\\d{1,2}$)")||string.matches("(^\\d{4}.\\d{1,2}.\\d{1,2}$)")) {
			return true;
		} 
		return false;
	}
	
	/**
	 * 是否包含~或者～、:或者：，即监考的具体时间hh:mm
	 * @param string
	 * @return
	 */
	public static boolean isContainHHMM(String string){
		if ((string.contains("~")||string.contains("～"))&&(string.contains(":")||string.contains("："))) {
			return true;
		} 
		return false;
	}
	
	/**
	 * 将考试时间分离hh:mm~hh:mm,以~号分离出起始时间、结束时间
	 * @param string
	 * @return
	 */
	public static String[] hhMMSplit(String string){
		if (string.contains("~")) {
			return string.split("~");
		}else if (string.contains("～")) {
			return string.split("～");
		}
		return null;
	}
	/**
	 * 
	 * @param path 文件路径
	 * @return 文件扩展名
	 */
	public static String getFilenameExtension(String path) {
		return path.substring(path.lastIndexOf(".")+1);
	}
}
