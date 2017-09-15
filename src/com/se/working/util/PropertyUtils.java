package com.se.working.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.se.working.exception.SEWMException;

/**
 * 
 * @author BO
 *
 */
public class PropertyUtils {
	/**
	 * property文件名称
	 */
	private static String PROPERTY_FILE = "property.properties";
	
	private static String BASEDATE_KEY = "basedate";
	private static String INVI_REGEX_LOCATION_KEY = "invi_regex_location";
	private static String COOKIE_NAME_KEY = "cookie_name";
	private static String BASEDATE_VALUE = getProperty(BASEDATE_KEY);
	private static String COOKIE_NAME_VALUE = getProperty(COOKIE_NAME_KEY);
	private static String INVI_REGEX_LOCATION_VALUE = getProperty(INVI_REGEX_LOCATION_KEY);
	
	
	/**
	 * 获取property中的basedate属性值
	 * 
	 * @return
	 */
	public static String getBaseDate() {
		
		return BASEDATE_VALUE;
	}
	/**
	 * 获取cookie name属性值
	 * @return
	 */
	public static String getCookieName() {
		return COOKIE_NAME_VALUE;
	}
	
	public static String getInviRegexLocation() {
		return INVI_REGEX_LOCATION_VALUE;
	}
	
	/**
	 * 设置property中的basedate，同时更新内存中的basedate值<br />
	 * Spring不提供封装好的保存property方法？
	 * 
	 * @param baseDate
	 */
	public static void setBaseDate(String baseDate) {
		setProperty(BASEDATE_KEY, baseDate);
		// 更新当前basedate
		BASEDATE_VALUE = baseDate;
	}

	/**
	 * 抽象获取属性值方法
	 * @param propKey
	 * @return
	 */
	private static String getProperty(String propKey) {
		return getproperties().getProperty(propKey);
	}
	

	/**
	 * 
	 * @param key
	 * @param value
	 */
	private static synchronized void setProperty(String key, String value) {
		Properties properties = getproperties();
		EncodedResource resource = new EncodedResource(new ClassPathResource(PROPERTY_FILE), "UTF-8");
		try (FileOutputStream os = new FileOutputStream(resource.getResource().getFile())) {
			properties.setProperty(key, value);
			properties.store(new OutputStreamWriter(os, "UTF-8"), null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("写入Peoperties错误！" + e.getMessage());
		}
	}

	private static Properties getproperties() {
		Properties properties = null;
		try {
			EncodedResource resource = new EncodedResource(new ClassPathResource(PROPERTY_FILE), "UTF-8");
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("读取Peoperties错误！" + e.getMessage());
		}
		return properties;
	}
}
