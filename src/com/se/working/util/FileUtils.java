package com.se.working.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.exception.SEWMException;

public class FileUtils {
	private static Logger logger = LogManager.getLogger(FileUtils.class);
	private static String ROOT = setUploadDirectory();
	private static String TIMETABLE = setTimetableDirectory();
	private static String INVIGILATION = setInviDirectory();

	/**
	 * 空文件，在课表文件夹下创建课表文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static File getTimetableFile(String fileName) {

		return new File(TIMETABLE + "\\" + fileName);
	}

	/**
	 * 空文件，在监考信息文件夹下创建监考文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static File getInviFile(String fileName) {
		return new File(INVIGILATION + "\\" + fileName);
	}

	/**
	 * 创建课表文件夹
	 * 
	 * @return
	 */
	private static String setTimetableDirectory() {
		String timetableDirectory = ROOT + "\\Timetables";
		File directory = new File(timetableDirectory);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		return timetableDirectory;
	}

	/**
	 * 创建监考信息文件夹
	 * 
	 * @return
	 */
	private static String setInviDirectory() {
		String inviDirectory = ROOT + "\\Invigilations";
		File directory = new File(inviDirectory);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		return inviDirectory;
	}

	/**
	 * 1-课表 <br>
	 * 创建任务文件夹，返回任务文件夹名称，及相对路径
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public static String getOrCreateTaskDirectory(long id, String name) {
		String taskName = String.valueOf(id) + "-" + name;
		String taskPath = ROOT + taskName;
		File taskDirectory = new File(taskPath);
		if (!taskDirectory.exists() && !taskDirectory.isDirectory()) {
			taskDirectory.mkdirs();
		}
		return taskName;
	}

	/**
	 * 课表_王波.xls<br>
	 * 基于任务名称，用户名，扩展名，返回文件名称，无路径
	 * 
	 * @param taskName
	 * @param userName
	 * @param Extension
	 * @return
	 */
	public static String getFileTaskName(String taskName, String userName, String Extension) {
		return taskName + "_" + userName + "." + Extension;
	}

	/**
	 * 课表_模板.xls <br>
	 * 基于任务名称，扩展名，返回 模板 文件名称，无路径
	 * 
	 * @param name
	 * @param userName
	 * @param Extension
	 * @return
	 */
	public static String getFileTaskTemplateName(String taskName, String Extension) {
		return taskName + "_" + "模板" + "." + Extension;
	}

	/**
	 * 课表_模板_20160301215846761.xls<br>
	 * 用于单一文件任务模板文件命名，3位毫秒，用于版本控制
	 * 
	 * @param taskName
	 * @param Extension
	 * @return
	 */
	public static String getSingalFileTaskTemplateName(String taskName, String Extension) {
		String patten = "yyyyMMddHHmmssSSS";
		SimpleDateFormat sf = new SimpleDateFormat(patten);
		return taskName + "_" + "模板" + "_" + sf.format(new Date()) + "." + Extension;
	}

	/**
	 * 需修改，分离创建与获取<br>
	 * 基于任务文件夹创建或返回文件，可以创建模板文件和上传文件
	 * 
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public static File getOrCreateFileTaskFile(String directory, String fileName) {
		File file = new File(ROOT + directory + "\\" + fileName);
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
	 * 删除目录，及目录下的所有文件
	 * 
	 * @param directory
	 */
	public static void deleteDirectory(String directory) {
		try {
			File file = new File(ROOT + directory);
			if (file.exists() && file.isDirectory()) {
				org.apache.commons.io.FileUtils.deleteDirectory(file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件删除错误");
		}
	}

	/**
	 * 基于文件目录，文件名称，删除文件
	 * 
	 * @param directory
	 * @param FileName
	 */
	public static void deleteFileTaskFile(String directory, String FileName) {
		File file = new File(ROOT + directory + "\\" + FileName);
		if (file.exists() && file.isFile()) {
			file.delete();
		} else {
			throw new SEWMException("文件已不存在");
		}
	}

	/**
	 * 20-课表.zip<br>
	 * 基于文件任务文件夹压缩全部文件，并在文件夹下生成压缩文件<br>
	 * 返回以文件夹名命名的压缩文件
	 * 
	 * @param directoryPath
	 * @return
	 */
	/*
	 * public static File zipDirectory(String directoryPath) { File directory =
	 * new File(ROOT + directoryPath); File[] files = directory.listFiles(); if
	 * (files == null || files.length == 0) { throw new
	 * SEWMException("没有任务文件，无法打包下载"); } File zipFile = new File(ROOT +
	 * directoryPath + "\\" + directoryPath + ".zip"); try { InputStream input =
	 * null; ZipOutputStream zipOut = new ZipOutputStream(new
	 * FileOutputStream(zipFile)); for (int i = 0; i < files.length; ++i) {
	 * input = new FileInputStream(files[i]); zipOut.putNextEntry(new
	 * ZipEntry(files[i].getName())); int temp = 0; while ((temp = input.read())
	 * != -1) { zipOut.write(temp); } } zipOut.closeEntry(); zipOut.close(); }
	 * catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return zipFile; }
	 */

	/**
	 * 20-课表.zip<br>
	 * 基于文件任务文件夹压缩全部文件<br>
	 * 返回z压缩文件字节数组
	 * 
	 * @param directoryPath
	 * @return
	 */
	public static byte[] zipDirectory(String directoryPath) {
		byte[] datas = null;
		File directory = new File(ROOT + directoryPath);
		File[] files = directory.listFiles();
		if (files == null || files.length == 0) {
			throw new SEWMException("没有任务文件，无法打包下载");
		}
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			InputStream input = null;
			ZipOutputStream zipOut = new ZipOutputStream(os);
			for (int i = 0; i < files.length; ++i) {
				input = new FileInputStream(files[i]);
				zipOut.putNextEntry(new ZipEntry(files[i].getName()));
				int temp = 0;
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
			}
			zipOut.closeEntry();
			zipOut.close();
			datas = os.toByteArray();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;

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

	/**
	 * 抽象上传文件保存至本地，用于同一异常处理即简化开发
	 * 
	 * @param uploadFile
	 * @param file
	 */
	public static void transferTo(MultipartFile uploadFile, File file) {
		try {
			uploadFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件保存至本地时失败；" + e.getMessage());
		}
	}

	/**
	 * 全局File封装为ResponseEntity<byte[]>，抽象下载实现
	 * 
	 * @param file
	 * @return
	 */
	public static ResponseEntity<byte[]> toResponseEntity(File file) {
		ResponseEntity<byte[]> entity = null;
		try {
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);
			entity = new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(file), headers,
					HttpStatus.OK);
			return entity;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件加载失败；" + e.getMessage());
		}
	}

	/**
	 * 全局基于文件名称封装为ResponseEntity<byte[]>，抽象下载实现
	 * 
	 * @param fileName
	 * @param datas
	 * @return
	 */
	public static ResponseEntity<byte[]> toResponseEntity(String fileName, byte[] datas) {
		ResponseEntity<byte[]> entity = null;
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);
			entity = new ResponseEntity<byte[]>(datas, headers, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件下载失败；" + e.getMessage());
		}
		return entity;
	}

	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public static InputStream getInitUsersExcel() throws IOException {
		InputStream inputStream = null;
		File file = new File(ROOT + "initusers.xlsx");
		if (!file.exists()) {
			logger.debug(ROOT + "initusers.xlsx");
			file = new File(ROOT + "initusers.xls");
		}
		if (!file.exists()) {
			throw new SEWMException("初始化用户表格不存在!" + "initusers.xlsx");
		}
		inputStream = org.apache.commons.io.FileUtils.openInputStream(file);
		return inputStream;
	}

}
