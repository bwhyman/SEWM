package com.se.working.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.exception.SEWMException;

public class FileTaskUtils {

	private static String ROOT = setUploadDirectory();
	private static String TIMETABLE = setTimetableDirectory();
	private static String INVIGILATION = setInviDirectory();
	
	/**
	 * 空文件，在课表文件夹下创建课表文件
	 * @param fileName
	 * @return
	 */
	public static File getTimetableFile(String fileName) {
	
		return new File(TIMETABLE + "\\" + fileName);
	}
	
	/**
	 * 空文件，在监考信息文件夹下创建监考文件
	 * @param fileName
	 * @return
	 */
	public static File getInviFile(String fileName) {
		return new File(INVIGILATION + "\\" + fileName);
	}
	/**
	 * 创建课表文件夹
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
	 * @param taskName
	 * @param Extension
	 * @return
	 */
	public static String getSingalFileTaskTemplateName(String taskName, String Extension) {
		String patten = "yyyyMMddHHmmssSSS";
		SimpleDateFormat sf  =  new SimpleDateFormat(patten);
		return taskName + "_" + "模板" + "_" + sf.format(new Date())  + "." + Extension;
	}
	
	/**
	 * 需修改，分离创建与获取<br>
	 * 基于任务文件夹创建或返回文件，可以创建模板文件和上传文件
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
				FileUtils.deleteDirectory(file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件删除错误");
		}
	}
	
	/**
	 * 基于文件目录，文件名称，删除文件
	 * @param directory
	 * @param FileName
	 */
	public static void deleteFileTaskFile(String directory, String FileName) {
		File file = new File(ROOT +directory + "\\" + FileName);
		if (file.exists() && file.isFile()) {
			file.delete();
		}else {
			throw new SEWMException("文件已不存在");
		}
	}
	
	/**
	 * 20-课表.zip<br>
	 * 基于文件任务文件夹压缩全部文件，并在文件夹下生成压缩文件<br>
	 * 返回以文件夹名命名的压缩文件
	 * @param directoryPath
	 * @return
	 */
	public static File zipDirectory(String directoryPath) {
		File directory = new File(ROOT + directoryPath);
		File[] files = directory.listFiles();
		if (files == null || files.length == 0) {
			throw new SEWMException("没有任务文件，无法打包下载");
		}
		File zipFile = new File(ROOT + directoryPath + "\\" + directoryPath + ".zip");
		InputStream input = null;
		ZipOutputStream zipOut = null;
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			for (int i = 0; i < files.length; ++i) {
				input = new FileInputStream(files[i]);
				zipOut.putNextEntry(new ZipEntry(files[i].getName()));
				int temp = 0;
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
			}
			input.close();
			zipOut.closeEntry();
			zipOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return zipFile;
	}
	

	/**
	 * 获取工程upload绝对路径地址
	 * 
	 * @return
	 */
	private static String setUploadDirectory() {
		String webapp = System.getProperty("webapp.root");
		String uploadDirectory = webapp + "\\WEB-INF\\jsp\\upload\\";
		File directory = new File(uploadDirectory);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		return uploadDirectory;
	}
	
	/**
	 * 抽象上传文件保存至本地，用于同一异常处理即简化开发
	 * @param uploadFile
	 * @param file
	 */
	public static void transferTo(MultipartFile uploadFile, File file) {
		try {
			uploadFile.transferTo(file);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件保存至本地时失败；" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件保存至本地时失败；" + e.getMessage());
		}
	}

	/**
	 * 全局文件转换为ResponseEntity<byte[]>，抽象下载实现
	 * @param file
	 * @return
	 * @throws SEWMException
	 */
	public static ResponseEntity<byte[]> downloadFile(File file) {
		ResponseEntity<byte[]> entity = null;
		String fileName = null;
		try {
			fileName = URLEncoder.encode(file.getName(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		try {
			entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件加载失败；"+ e.getMessage());
		}
		return entity;
	}
	
	public FileTaskUtils() {
		// TODO Auto-generated constructor stub
	}

}
