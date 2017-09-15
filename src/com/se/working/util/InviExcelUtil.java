package com.se.working.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.se.working.exception.SEWMException;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.InvigilationStatusType;
import com.se.working.invigilation.entity.TeacherInvigilation;

/**
 * 监考表格分析 2016.05.11，添加监考起止时间在2个不同段的匹配方法
 * 
 * @author BO
 *
 */
public class InviExcelUtil {
	private static Logger logger = LogManager.getLogger(InviExcelUtil.class);
	private static final String REGEX_INVI_SUFFIX = "(\\d+)";
	// 匹配地址
	// private static final String REGEX_LOCATION = "(丹青楼.\\d+|锦绣楼.\\d+|成栋楼.\\d+|研究生楼.\\d+)";
	// 独立的监考开始结束时间，日期与时间分别获取
	/**
	 * 独立的监考开始结束时间，日期与时间分别获取<br>
	 */
	private static final String REGEX_IND_DATE = "(^\\d{4}-\\d{1,2}-\\d{1,2})\\s+(\\d{2}:\\d{2})";
	// 仅匹配日期，不会匹配班级
	/**
	 * 仅匹配日期，不会匹配班级<br>
	 */
	private static final String REGEX_DATE = "(^\\d{4}-\\d{1,2}-\\d{1,2})";
	// 匹配时间，较模糊，有待修正
	private static final String REGEX_TIME = "(.+)~(.+)";
	// 匹配课程名称，去掉[]
	private static final String REGEX_COURSE = "(.*)\\[";
	

	/**
	 * 从表格中提取专业监考信息集<br>
	 * 2016.04.05，强制将cell类型转为string
	 * 
	 * @param is
	 * @return 专业监考信息集
	 */
	public static List<InvigilationInfo> getExcel(InputStream is, String inviRegex) {
		List<InvigilationInfo> info;
		try {
			Workbook workbook = WorkbookFactory.create(is);
			info = getRow(workbook.getSheetAt(0), inviRegex);
			return info;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("文件操作错误", e);
		}
	}

	private static List<InvigilationInfo> getRow(Sheet sheet, String inviRegex) throws ParseException {
		List<InvigilationInfo> infos = new ArrayList<>();
		Pattern pNum = Pattern.compile(inviRegex + REGEX_INVI_SUFFIX);
		
		Matcher mNum = null;
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				for (int cellIndex = row.getLastCellNum(); cellIndex >= 0; cellIndex--) {
					Cell cell = row.getCell(cellIndex);

					if (cell == null) {
						continue;
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if (!StringUtils.isEmpty(StringUtils.trimAllWhitespace(cell.getStringCellValue()))) {
						// 判断是否为专业监考信息
						mNum = pNum.matcher(cell.getStringCellValue());
						if (mNum.find()) {
							// 传递人数匹配
							infos.add(getRowInfos(row, pNum));
							break;
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
	private static InvigilationInfo getRowInfos(Row row, Pattern pNum) throws ParseException {
		InvigilationInfo info = new InvigilationInfo();
		
		Pattern pLocation = Pattern.compile(PropertyUtils.getInviRegexLocation());
		Pattern pDate = Pattern.compile(REGEX_DATE);
		Pattern pTime = Pattern.compile(REGEX_TIME);
		Pattern pDtime = Pattern.compile(REGEX_IND_DATE);
		Pattern pCourse = Pattern.compile(REGEX_COURSE);
		Matcher mNum = null;
		Matcher mLocation = null;
		Matcher mDate = null;
		Matcher mTime = null;
		Matcher mDTime = null;
		Matcher mCourse = null;
		boolean indTime = false;

		String sNumber = "";
		String sLocation = "";
		String sDate = "";
		String sStartTime = "";
		String sEndTime = "";
		String sCourse = "";
		for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
			Cell cell = row.getCell(cellIndex);
			if (cell == null) {
				continue;
			}
			
			String cellInfo = "";
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
				cellInfo = DateUtils.transformDateTimetoString(date);
				break;
			default:
				// 强制设置全局cell类型为string
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellInfo = cell.getStringCellValue();
				break;
			}
			if (!StringUtils.isEmpty(StringUtils.trimAllWhitespace(cellInfo))) {
				cellInfo = StringUtils.getStandardDate(cellInfo);
				
				// 默认第1列为考试课程
				if (cellIndex == 0) {
					mCourse = pCourse.matcher(cellInfo);
					// 是否包含[]，有则去掉
					if (mCourse.find()) {
						cellInfo = mCourse.group(1);
					}
					sCourse = cellInfo;
					continue;
				}
				
				mNum = pNum.matcher(cellInfo);
				// 获取监考人数
				if (mNum.find()) {
					sNumber = mNum.group(1);
					// 判断人数是否为中文数字，是则转为整型字符串
					/*for (EnumZhDigital e : EnumZhDigital.values()) {
						if (e.getZh().equals(sNumber)) {
							sNumber = e.getDigital();
						}
					}*/
					continue;
				}
				// 获取监考地点
				mLocation = pLocation.matcher(cellInfo);
				if (mLocation.find()) {
					sLocation = mLocation.group(1);
					continue;
				}
		
				/**
				 * 开始起止时间分别在2个段的获取方法，在日期，时间段前必配
				 */
				mDTime = pDtime.matcher(cellInfo);
				
				if (mDTime.find()) {
					if (!indTime) {
						sDate = mDTime.group(1);
						sStartTime = mDTime.group(2);
					} else {
						sEndTime = mDTime.group(2);
					}
					indTime = true;
					continue;
				}
				
				// 监考日期
				mDate = pDate.matcher(cellInfo);
				if (mDate.find()) {
					sDate = mDate.group(1);
					continue;
				}
				// 监考时间
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

		if (start.after(end)) {
			Calendar temp = Calendar.getInstance();
			temp = start;
			start = end;
			end = temp;
		}

		info.setRequiredNumber(Integer.valueOf(sNumber));
		info.setLocation(sLocation);
		info.setStartTime(start);
		info.setEndTime(end);
		info.setComment(sCourse);
		return info;
	}

	/**
	 * 创建监考信息表格 ========================================
	 */

	/**
	 * 创建监考详细信息表格
	 * 
	 * @param infos
	 * @return
	 */
	public static byte[] createInviDetailExcel(List<InvigilationInfo> infos, List<TeacherInvigilation> teachers, String title) {
		byte[] datas = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			sheet.setColumnWidth(0, 1800);
			sheet.setColumnWidth(1, 7300);
			sheet.setColumnWidth(2, 3600);
			sheet.setColumnWidth(3, 3600);
			sheet.setColumnWidth(4, 3600);
			sheet.setColumnWidth(5, 2400);
			sheet.setColumnWidth(6, 4600);
			sheet.setColumnWidth(7, 2400);

			// 创建列标题
			createDetailColTitle(workbook);
			// 创建标题，基于列标题合并第一行列，因此必须在创建列标题后创建
			createDetailTitle(workbook, title);
			// 创建内容
			createDetailBody(infos, workbook);
			createCount(workbook, teachers);
			// 创建文档属性
			createProperties(workbook, title);
			datas = toFile(workbook);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;

	}

	/**
	 * 创建详细监考信息内容
	 * 
	 * @param infos
	 * @param workbook
	 */
	private static void createDetailBody(List<InvigilationInfo> infos, Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		for (int i = 0; i < infos.size(); i++) {
			Row row = sheet.createRow(1 + 1 + i);
			row.createCell(0).setCellValue(i + 1);

			row.createCell(1).setCellValue(infos.get(i).getComment());

			if (infos.get(i).getStartTime() != null) {
				row.createCell(2).setCellValue(date.format(infos.get(i).getStartTime().getTime()));
				row.createCell(3).setCellValue(time.format(infos.get(i).getStartTime().getTime()) + "~"
						+ time.format(infos.get(i).getEndTime().getTime()));
			}
			row.createCell(4).setCellValue(infos.get(i).getLocation());
			row.createCell(5).setCellValue(infos.get(i).getRequiredNumber());
			String names = "";
			if (infos.get(i).getInvigilations() != null) {
				int count = 0;
				for (Invigilation invi : infos.get(i).getInvigilations()) {
					if (count == 0) {
						names = invi.getTeacher().getUser().getName();
					} else {
						names = names + " " + invi.getTeacher().getUser().getName();
					}
					count++;
				}
			}
			row.createCell(6).setCellValue(names);
			Cell cell7 = row.createCell(7);
			CellStyle style = getCellStyle(workbook);
			Font font = workbook.createFont();
			short color = 0;
			if (infos.get(i).getCurrentStatusType().getId() == InvigilationStatusType.UNASSIGNED) {
				color = IndexedColors.RED.index;
			} else if (infos.get(i).getCurrentStatusType().getId() == InvigilationStatusType.ASSIGNED) {
				color = IndexedColors.GREEN.index;
			} else if (infos.get(i).getCurrentStatusType().getId() == InvigilationStatusType.DONE) {
				color = IndexedColors.SKY_BLUE.index;
			}
			font.setColor(color);
			style.setFont(font);
			cell7.setCellStyle(style);
			cell7.setCellValue(infos.get(i).getCurrentStatusType().getName());
			/**
			 * 最后一项需要颜色，所有需要越过，否则会覆盖原设置
			 */
			for (int j = 0; j < row.getLastCellNum() - 1; j++) {
				row.getCell(j).setCellStyle(getCellStyle(workbook));
			}
		}
	}

	/**
	 * 基于列标题的数量合并第一行列，必须在创建列标题后创建 创建标题，前3行，列标题的宽度，字体：22px，垂直居中<br>
	 * 
	 * 
	 * @param workbook
	 */
	private static void createDetailTitle(Workbook workbook, String title) {
		
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		cell.setCellValue(title + "  " + date.format(new Date()));
		CellStyle style = workbook.createCellStyle();
		// 设置居中
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置垂直
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置字体
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 22);
		style.setFont(font);
		cell.setCellStyle(style);
		CellRangeAddress range = new CellRangeAddress(0, 0, 0, sheet.getRow(1).getLastCellNum() - 1);
		sheet.addMergedRegion(range);
	}

	/**
	 * 创建文档属性信息
	 * 
	 * @param workbook
	 */
	private static void createProperties(XSSFWorkbook workbook, String title) {
		POIXMLProperties xmlProps = workbook.getProperties();
		POIXMLProperties.CoreProperties coreProps = xmlProps.getCoreProperties();
		coreProps.setCreator("王波");
		coreProps.setTitle(title);
	}

	/**
	 * 创建列标题，第4行
	 * 
	 * @param workbook
	 */
	private static void createDetailColTitle(Workbook workbook) {
		String cols[] = { "#", "课程备注", "考试日期", "考试时间", "考试地点", "监考人数", "分配人员", "状态" };
		Row row = workbook.getSheetAt(0).createRow(1);
		for (int i = 0; i < cols.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(cols[i]);
			cell.setCellStyle(getCellStyle(workbook));
		}

	}

	/**
	 * 创建cell默认样式
	 * 
	 * @param workbook
	 * @return
	 */
	private static CellStyle getCellStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(CellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(CellStyle.BORDER_THIN);// 右边框
		// 设置居中
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置垂直
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return style;
	}

	private static void createCount(Workbook workbook, List<TeacherInvigilation> teachers) {
		Sheet sheet = workbook.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		String counts[] = { "教师", "次数" };
		// 空2行
		Row countRow = sheet.createRow(lastRowNum + 3);
		// 创建次数标题列，从第二列开始
		for (int i = 0; i < counts.length; i++) {
			countRow.createCell(i + 1).setCellValue(counts[i]);
		}
		// 空3行
		for (int i = 0; i < teachers.size(); i++) {
			Row row = sheet.createRow(lastRowNum + 4 + i);
			row.createCell(1).setCellValue(teachers.get(i).getUser().getName());
			row.createCell(2).setCellValue(teachers.get(i).getInvigilations().size());
		}
	}

	/**
	 * 将workbook转为excel文档
	 * 
	 * @param workbook
	 * @return 字节数组流
	 */
	private static byte[] toFile(Workbook workbook) {
		try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			workbook.write(os);
			return os.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("生成Excel表格文件是发生错误！" + e.getMessage());
		}

	}
}
