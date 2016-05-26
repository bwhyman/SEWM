package com.se.working.util;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.se.working.entity.User;

public class CollegeTimetableUtils {
	/**
	 * 教师姓名，课程名称
	 */
	private static final String TEACHER_COURSE = "(.*)\\s(.*)";

	/**
	 * 学院理论课课表 每课程，4个一组，忽略第一个\n和最后一个\n，可过滤中间的空行，最后是否有空行 
	 * 1.教师,课程名称；2.专业年级；3.周次；4.教室
	 */
	private static final String COURSE_SECTION = "([^\n].*)\n(.*)\n(.*)\n(.*[^\n])";
	
	@SuppressWarnings("unused")
	private static void getTimetable(File excelFile, List<User> users) {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(excelFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(1);
		
		
		
		
		String cellString = cell.getStringCellValue();
		Matcher matcher = Pattern.compile(COURSE_SECTION).matcher(cellString);
		Pattern pattern = Pattern.compile(TEACHER_COURSE);
		Matcher matcher2 = null;
		while (matcher.find()) {
			String string = matcher.group(1);
			matcher2 = pattern.matcher(string);
			matcher2.find();
			if (matcher2.group(1).equals("王健")) {
				System.out.println(matcher.group(1));
				System.out.println(matcher.group(2));
				System.out.println(matcher.group(3));
				System.out.println(matcher.group(4));
			}
		}
	}
	public CollegeTimetableUtils() {
		// TODO Auto-generated constructor stub
	}

}
