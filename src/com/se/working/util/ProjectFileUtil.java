package com.se.working.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.se.working.exception.SEWMException;

public class ProjectFileUtil {

	private static String ROOT = setUploadDirectory();
	
	public static String getUploadDirectory() {
		return ROOT;
	}
	
	/**
	 * 论文_姓名_20160301215846761.doc<br>
	 * 用于指导修改文件命名，3位毫秒，用于版本控制
	 * @param guideRecordName
	 * @param Extension
	 * @return
	 */
	public static String getGuideRecordName(String top, String studentName, String Extension) {
		String patten = "yyyyMMddHHmmssSSS";
		SimpleDateFormat sf  =  new SimpleDateFormat(patten);
		return top + "_" + studentName + "_" + sf.format(new Date())  + "." + Extension;
	}
	
	public static String getFileName(String top, String titleName, String Extension) {
		return top + "_" + titleName + "." + Extension;
	}
	
	/**
	 * 抽象上传文件保存至本地，用于同一异常处理即简化开发
	 * @param uploadFile
	 * @param file
	 * @throws SEWMException
	 */
	public static void transferTo(MultipartFile uploadFile, File file) {
		try {
			uploadFile.transferTo(file);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件上传失败；" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件上传失败；" + e.getMessage());
		}
	}
	
	/**
	 * 基于文件夹创建或返回文件
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public static File getOrCreateProjectFile(String directory, String fileName) {
		File file = new File(getUploadDirectory() + directory + "\\" + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new SEWMException("文件处理错误！" + e.getMessage());
			}
		}
		return file;
	}
	
	/**
	 * 论证报告 <br>
	 * 创建任务文件夹，返回任务文件夹名称，及相对路径
	 * @param name
	 * @return
	 */
	public static String getOrCreateProjectDirectory(String name) {
		String projectPath = getUploadDirectory() + name;
		File taskDirectory = new File(projectPath);
		if (!taskDirectory.exists() && !taskDirectory.isDirectory()) {
			taskDirectory.mkdirs();
		}
		return name;
	}
	
	/**
	 * 获取工程upload绝对路径地址
	 * 
	 * @return
	 */
	private static String setUploadDirectory() {
		String webapp = System.getProperty("SEWM.root");
		String uploadDirectory = webapp + "\\WEB-INF\\jsp\\upload\\";
		File directory = new File(uploadDirectory);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		return uploadDirectory;
	}
	
	public ProjectFileUtil() {
		// TODO Auto-generated constructor stub
	}

}
