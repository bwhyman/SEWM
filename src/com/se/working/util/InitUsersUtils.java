package com.se.working.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.se.working.entity.Groups;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.exception.SEWMException;

public class InitUsersUtils {
	public static List<User> getUsers(InputStream is) {
		try {
			Workbook workbook = WorkbookFactory.create(is);
			return getRow(workbook.getSheetAt(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("文件操作错误", e);
		}
	}

	private static List<User> getRow(Sheet sheet) {
		List<User> users = new ArrayList<>();
		// 从第2行
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row == null) {
				break;
			}
			Cell nameCell = row.getCell(1);
			nameCell.setCellType(Cell.CELL_TYPE_STRING);
			Cell emCell = row.getCell(2);
			emCell.setCellType(Cell.CELL_TYPE_STRING);
			Cell phoneCell = row.getCell(3);
			phoneCell.setCellType(Cell.CELL_TYPE_STRING);
			Cell titleCell = row.getCell(4);
			titleCell.setCellType(Cell.CELL_TYPE_STRING);
			Cell groupCell = row.getCell(5);
			groupCell.setCellType(Cell.CELL_TYPE_STRING);
			Cell authCell = row.getCell(6);
			authCell.setCellType(Cell.CELL_TYPE_STRING);
			User user = new User();
			user.setName(nameCell.getStringCellValue());
			user.setEmployeeNumber(emCell.getStringCellValue());
			user.setPhoneNumber(phoneCell.getStringCellValue());
			user.setTitle(new TeacherTitle(Long.valueOf(titleCell.getStringCellValue())));
			user.setGroups(new Groups(Long.valueOf(groupCell.getStringCellValue())));
			user.setUserAuthority(new UserAuthority(Long.valueOf(authCell.getStringCellValue())));
			users.add(user);
		}
		return users;
	}
}
