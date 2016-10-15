package com.se.working.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
	/**
	 * basetime key
	 */
	private static String BASEDATE_KEY = "basedate";
	/**
	 * basetime value
	 */
	private static String BASEDATE_VALUE = getBaseDateProperty();

	/**
	 * 获取property中的basedate属性值
	 * @return
	 */
	public static String getBaseDate() {
		return BASEDATE_VALUE;
	}
	
	/**
	 * 设置property中的basedate，同时更新内存中的basedate值<br />
	 * Spring不提供封装好的保存property方法？
	 * @param baseDate
	 */
	public static void setBaseDate(String baseDate) {
		Resource resource = new ClassPathResource(PROPERTY_FILE);
		try(FileOutputStream os = new FileOutputStream(resource.getFile())) {
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			properties.setProperty(BASEDATE_KEY, baseDate);
			properties.store(os, BASEDATE_KEY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new SEWMException("写入Property文件错误！" + e.getMessage());
		}
		
		// 更新当前basedate
		BASEDATE_VALUE = baseDate;
	}
	
	/**
	 * 将basedate加载到内存，减少IO操作
	 * @return
	 */
	private static String getBaseDateProperty() {
		String basedate = null; 
		try {
			Properties props = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE);
			basedate = props.getProperty(BASEDATE_KEY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new SEWMException("读取Property文件错误！" + e.getMessage());
		}
		return basedate;
	}
	
}
