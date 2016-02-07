package com.se.working.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.InvigilationStatusType;
import com.se.working.invigilation.entity.InvigilationStatusType.InvStatusType;

public class InviExcleUtil {

	/**
	 * 从表格中提取专业监考信息集
	 * 
	 * @param excelPath
	 * @return 专业监考信息集
	 */
	public static List<InvigilationInfo> getInvigilationInfoUtil(String excelPath) {
		List<InvigilationInfo> invInfos = new ArrayList<>();
		HSSFWorkbook wb = null;
		try {
			FileInputStream fis = new FileInputStream(excelPath);
			wb = new HSSFWorkbook(fis);
			HSSFSheet hssfSheet = null;
			// 获取第一个sheet页
			for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
				hssfSheet = wb.getSheetAt(numSheet);
				if (hssfSheet != null) {
					List<String> invInfoString = null;
					// 获取考试信息
					if (hssfSheet != null) {
						// 专业监考信息
						invInfoString = new ArrayList<>();
						HSSFRow row = null;
						for (int rowIndex = 0; rowIndex <= hssfSheet.getLastRowNum(); rowIndex++) {
							row = hssfSheet.getRow(rowIndex);
							if (row == null) {
								continue;
							}

							boolean isUser = false;
							// 一列一列遍历
							String rowString = "";
							HSSFCell cell = null;
							for (int cellIndex = 0; cellIndex <= row.getLastCellNum(); cellIndex++) {
								cell = row.getCell(cellIndex);
								if (cell == null || StringUtils
										.isEmpty(StringUtils.trimAllWhitespace(cell.toString()))) {
									continue;
								} else {
									rowString += cell.toString() + "&";
								}
								// 找出包含软件*人的行
								if (StringUtils.isContainSoftWareNum(cell.toString())) {
									isUser = true;
								}
							}
							if (isUser) {
								invInfoString.add(rowString);
								rowString = "";
							}
							cell = null;
						}
						row = null;
					}
					if (invInfoString != null) {
						invInfos = getInfo(invInfoString);
						return invInfos;
					}
				}
			}

			wb.close();
			fis.close();
			wb = null;
			fis = null;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从专业监考信息中提取重要信息
	 * 
	 * @param invInfoString
	 * @return 专业监考信息
	 */
	private static List<InvigilationInfo> getInfo(List<String> invInfoString) {
		List<InvigilationInfo> invigilationInfos = new ArrayList<>();
		for (String string : invInfoString) {
			String[] invInfoArray = string.split("&");
			InvigilationInfo invInfo = new InvigilationInfo();
			String yyyyMMDD = "";
			String[] timeArray = null;
			for (String string2 : invInfoArray) {
				if (!string2.isEmpty()) {
					// 找出包含软件*人的行
					if (StringUtils.isContainSoftWareNum(string2)) {
						invInfo.setRequiredNumber(Integer.valueOf(string2.substring(2, 3)));
					} else if (StringUtils.isContainAddress(string2)) {
						invInfo.setLocation(string2);
					} else if (StringUtils.isContainYYYYMMDD(string2)) {
						yyyyMMDD = string2;
					} else if (StringUtils.isContainHHMM(string2)) {
						timeArray = StringUtils.hhMMSplit(string2);
					}
				}
			}

			if (timeArray != null && !StringUtils.isEmpty(yyyyMMDD)) {
				try {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(DateConversionUtil.dateFormat(yyyyMMDD, timeArray[0]));
					calendar.add(Calendar.MINUTE, -15);
					invInfo.setStartTime(calendar);
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(DateConversionUtil.dateFormat(yyyyMMDD, timeArray[1]));
					calendar2.add(Calendar.MINUTE, 15);
					invInfo.setEndTime(calendar2);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			timeArray = null;
			yyyyMMDD = null;

			InvigilationStatusType invigilationStatus = new InvigilationStatusType();
			invigilationStatus.setId(InvStatusType.UNASSIGNED);
			invInfo.setCurrentStatusType(invigilationStatus);
			invigilationInfos.add(invInfo);
		}
		return invigilationInfos;
	}
	public InviExcleUtil() {
	}
	
}
