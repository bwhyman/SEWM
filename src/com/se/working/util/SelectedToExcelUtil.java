package com.se.working.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.se.working.exception.SEWMException;
import com.se.working.project.entity.StudentProject;

public class SelectedToExcelUtil {

	static String[] excelHeader = {"学号","姓名","题目", "指导老师"};
	
	/**
	 * 导出选题信息
	 * @param students
	 * @return
	 */
	public static ResponseEntity<byte[]> export(List<StudentProject> students){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("选题信息");
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		HSSFFont fontStyle = wb.createFont();    
		fontStyle.setFontName("宋体");    
		fontStyle.setFontHeightInPoints((short)12); 
		style.setFont(fontStyle);
		
		for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 20000);
            sheet.setColumnWidth(3, 5000);
        } 
		
		for (int i = 0; i < students.size(); i++) {    
            row = sheet.createRow(i + 1);    
            StudentProject student = students.get(i); 
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(student.getUser().getEmployeeNumber());  
            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue(student.getUser().getName()); 
            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(student.getSelectedTitleDetail().getTitle().getName()); 
            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(student.getSelectedTitleDetail().getTitle().getTeacher().getUser().getName()); 
        }  
		
		return downloadFile(getFile(wb));
	}
	
	/**
	 * 将HSSFWorkbook写入file
	 * @param wb
	 * @return
	 */
	private static File getFile(HSSFWorkbook wb){
		File file = new File(getExportExcelDirectory()+"选题信息.xls");
		try {
			wb.write(new FileOutputStream(file));
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件加载失败；"+ e.getMessage());
		}
		return file;
	}
	
	/**
	 * 全局文件转换为ResponseEntity<byte[]>，抽象下载实现
	 * @param file
	 * @return
	 * @throws SEWMException
	 */
	private static ResponseEntity<byte[]> downloadFile(File file) {
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
			file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new SEWMException("文件加载失败；"+ e.getMessage());
		}
		return entity;
	}
	
	
	
	/**
	 * 获取工程upload绝对路径地址
	 * 
	 * @return
	 */
	private static String getExportExcelDirectory() {
		String webapp = System.getProperty("webapp.root");
		String exportExcelDirectory = webapp + "\\WEB-INF\\jsp\\excel\\";
		File directory = new File(exportExcelDirectory);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		return exportExcelDirectory;
	}
	
	public SelectedToExcelUtil() {
		// TODO Auto-generated constructor stub
	}

}
