package com.se.working.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.se.working.entity.Classes;
import com.se.working.entity.Student;
import com.se.working.exception.SEWMException;

public class StudentExcelUtil {

	public static String REGEX_STUDENTID = "^[0-9]{8,}$";
	public static String REGEX_ClASSES = "软件(一|二)班";
	public static String REGEX_SEX = "(男|女)";
	public static List<Student> getExcel(File excelFile) {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(excelFile);
			return getRow(workbook.getSheetAt(0));
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("文件操作错误", e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new SEWMException("文件操作错误", e);
				}
				workbook = null;
			}
		}
		
	}
	
	private static List<Student> getRow(Sheet sheet) throws ParseException {
		List<Student> students = new ArrayList<>();
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				students.add(getRowInfos(row));
			}
		}
		return students;
	}
	
	private static Student getRowInfos(Row row){
		Pattern pID = Pattern.compile(REGEX_STUDENTID);
		Pattern pClass = Pattern.compile(REGEX_ClASSES);
		Pattern pSex = Pattern.compile(REGEX_SEX);
		Matcher mID = null;
		Matcher mClass = null;
		Matcher mSex = null;
		Student student = new Student();
		for (int cellIndex = 0; cellIndex <= row.getLastCellNum(); cellIndex++) {
			Cell cell = row.getCell(cellIndex);
			if (cell != null){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (!StringUtils.isEmpty(StringUtils.trimAllWhitespace(cell.getStringCellValue()))) {
					String cellInfo = cell.getStringCellValue().trim();
					mID = pID.matcher(cellInfo);
					mClass = pClass.matcher(cellInfo);
					mSex = pSex.matcher(cellInfo);
					if (mID.find()) {
						student.setStudentId(cellInfo);
					}else if (mClass.find()) {
						Classes classes = new Classes();
						classes.setName(cellInfo);
						student.setClasses(classes);
					}else if (mSex.find()) {
						student.setSex(cellInfo);
					}else {
						student.setName(cellInfo);
					}
				}
			}
		}
		return student;
	}
	public StudentExcelUtil() {
		// TODO Auto-generated constructor stub
	}

}
