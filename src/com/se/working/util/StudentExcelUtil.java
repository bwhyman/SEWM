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

import com.se.working.entity.User;
import com.se.working.exception.SEWMException;

public class StudentExcelUtil {

	public static String REGEX_STUDENTID = "^[0-9]{8,}$";
	public static List<User> getExcel(File excelFile) {
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
			excelFile.delete();
		}
		
	}
	
	private static List<User> getRow(Sheet sheet) throws ParseException {
		List<User> users = new ArrayList<>();
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				users.add(getRowInfos(row));
			}
		}
		return users;
	}
	
	private static User getRowInfos(Row row){
		Pattern pID = Pattern.compile(REGEX_STUDENTID);
		Matcher mID = null;
		User user = new User();
		for (int cellIndex = 0; cellIndex <= row.getLastCellNum(); cellIndex++) {
			Cell cell = row.getCell(cellIndex);
			if (cell != null){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (!StringUtils.isEmpty(StringUtils.trimAllWhitespace(cell.getStringCellValue()))) {
					String cellInfo = cell.getStringCellValue().trim();
					mID = pID.matcher(cellInfo);
					if (mID.find()) {
						user.setEmployeeNumber(cellInfo);
					}else {
						user.setName(cellInfo);
					}
				}
			}
		}
		return user;
	}
	public StudentExcelUtil() {
		// TODO Auto-generated constructor stub
	}

}
