package com.se.working.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.se.working.exception.SEWMException;
import com.se.working.invigilation.entity.InvigilationInfo;

public class InviExcelUtil {

	private static String REGEX_NUMBER = "软件(.+)人";
	// 匹配地址
	private static String REGEX_LOCATION = "(丹青|锦绣|成栋)";
	// 仅匹配日期，不会匹配班级
	private static String REGEX_DATE = "(^\\d{4}-\\d{1,2}-\\d{1,2})";
	// 匹配时间，较模糊，有待修正
	private static String REGEX_TIME = "(.+)~(.+)";

	/**
	 * 从表格中提取专业监考信息集
	 * 
	 * @param excelPath
	 * @return 专业监考信息集
	 * @throws SEWMException 
	 */

	public static List<InvigilationInfo> getExcel(File excelFile) {
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

	private static List<InvigilationInfo> getRow(Sheet sheet) throws ParseException {
		List<InvigilationInfo> infos = new ArrayList<>();
		Pattern pNum = Pattern.compile(REGEX_NUMBER);
		Matcher mNum = null;
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				for (int cellIndex = row.getLastCellNum(); cellIndex >= 0; cellIndex--) {
					Cell cell = row.getCell(cellIndex);
					if (cell != null) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						if (!StringUtils.isEmpty(StringUtils.trimAllWhitespace(cell.getStringCellValue()))) {
							// 判断是否为专业监考信息
							mNum = pNum.matcher(cell.getStringCellValue());
							if (mNum.find()) {
								infos.add(getRowInfos(row));
								break;
							}
						}
					}
				}
			}
		}
		return infos;
	}

	/**
	 * 提取专业监考信息
	 * 
	 * @param row
	 * @return
	 * @throws ParseException
	 */
	private static InvigilationInfo getRowInfos(Row row) throws ParseException {
		InvigilationInfo info = new InvigilationInfo();
		Pattern pNum = Pattern.compile(REGEX_NUMBER);
		Pattern pLocation = Pattern.compile(REGEX_LOCATION);
		Pattern pDate = Pattern.compile(REGEX_DATE);
		Pattern pTime = Pattern.compile(REGEX_TIME);
		Matcher mNum = null;
		Matcher mLocation = null;
		Matcher mDate = null;
		Matcher mTime = null;

		String sNumber = null;
		String sLocation = null;
		String sDate = null;
		String sStartTime = null;
		String sEndTime = null;
		for (int cellIndex = row.getLastCellNum(); cellIndex >= 0; cellIndex--) {
			Cell cell = row.getCell(cellIndex);
			if (cell != null && !StringUtils.isEmpty(StringUtils.trimAllWhitespace(cell.getStringCellValue()))) {

				String cellInfo = cell.getStringCellValue().trim();
				mNum = pNum.matcher(cellInfo);
				// 获取监考人数
				if (mNum.find()) {
					sNumber = mNum.group(1);
					// 判断人数是否为中文数字，是则转为整型字符串
					for (EnumZhDigital e : EnumZhDigital.values()) {
						if (e.getZh().equals(sNumber)) {
							sNumber = e.getDigital();
						}
					}
					continue;
				}
				// 获取监考地点
				mLocation = pLocation.matcher(cellInfo);
				// 无需完整匹配，包含即可
				if (mLocation.find()) {
					sLocation = cellInfo;
					continue;
				}

				// 获取监考日期，先不处理，与监考时间整合后封装
				cellInfo = cellInfo.replace(".", "-");
				mDate = pDate.matcher(cellInfo);
				if (mDate.find()) {
					sDate = mDate.group(1);
					continue;
				}

				// 获取监考时间
				// 如果是中文，转换
				cellInfo = cellInfo.replace("～", "~");
				cellInfo = cellInfo.replace("：", ":");
				mTime = pTime.matcher(cellInfo);
				if (mTime.find()) {
					sStartTime = mTime.group(1);
					sEndTime = mTime.group(2);
					continue;
				}
			}
		}
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		sStartTime = sDate + " " + sStartTime;
		sEndTime = sDate + " " + sEndTime;
		start = DateUtils.getCalendar(sStartTime);
		end = DateUtils.getCalendar(sEndTime);
		info.setRequiredNumber(Integer.valueOf(sNumber));
		info.setLocation(sLocation);
		info.setStartTime(start);
		info.setEndTime(end);
		return info;
	}
}
