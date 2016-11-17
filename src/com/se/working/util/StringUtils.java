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
	 * 替换日期中的 / . 为-，替换中文引号为英文引号
	 * @param string
	 * @return
	 */
	public static String getStandardDate(String string) {
		string = string.replaceAll("/|\\.", "-");
		string = string.replaceAll("～", "~");
		string = string.replaceAll("：", ":");
		return string;
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
