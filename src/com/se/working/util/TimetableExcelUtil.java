package com.se.working.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.se.working.exception.SEWMException;
import com.se.working.invigilation.entity.Course;
import com.se.working.invigilation.entity.CourseSection;

public class TimetableExcelUtil {
	//获取教师姓名表达式
	private static final String REGEX_TNAME = "大学(.*)教师";
	// 获取课表表达式
	private static final String REGEX_SECTION = "([^\n].*)\n";
	// 基于"-" 获取起止周
	private static final String REGEX_STARTEND_WEEKS = "(\\d+)-(\\d+)";
	// 单周
	private static final String REGEX_SINAGLE_WEEK = "(\\d+)";
	

	public static List<CourseSection> getExcel(File excelFile) throws SEWMException, Exception {
		Workbook workbook = null;
		// 根据excel版本创建不同对象
		try {
			if (StringUtils.getFilenameExtension(excelFile.getName()).equals("xlsx")) {
				workbook = new XSSFWorkbook(excelFile);	
			} else if (StringUtils.getFilenameExtension(excelFile.getName()).equals("xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(excelFile));
			} else {
				throw new SEWMException("不是Excel格式文件");
			}
			Sheet sheet = workbook.getSheetAt(0);
			Row row = sheet.getRow(0);
			Pattern pattern = Pattern.compile(REGEX_TNAME);
			Matcher matcher = pattern.matcher(row.getCell(0).getStringCellValue());
			String name = null;
			if (matcher.find()) {
				name = StringUtils.trimAllWhitespace(matcher.group(1));
			}
			/*if (name == null || "BO".equals(name)) {
				throw new SEWMException("教师不存在，或姓名有误");
			}*/
			return getRow(sheet);
			
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}
	private static List<CourseSection> getRow(Sheet sheet) throws ParseException {
		List<CourseSection> courseSections = new ArrayList<>();
		/**
		 * 每次获取的片段
		 */
		List<CourseSection> cs =null;
		// 从第4行读取
		for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			// 从第2列读取
			for (int cellIndex = 1; cellIndex < row.getLastCellNum(); cellIndex++) {
				Cell cell = row.getCell(cellIndex);
				if (cell != null && !StringUtils
						.isEmpty(StringUtils.trimAllWhitespace(cell.getStringCellValue()))) {
					Pattern pattern = Pattern.compile(REGEX_SECTION);
					/**
					 * 当没有授课地点时无法提取长度为0的内容，使用*替代地点
					 * 否则后面循环错误
					 */
					Matcher matcher = pattern.matcher(cell.getStringCellValue().replace("周\n\n", "周\n*\n"));
					// 课程名称
					String courseName = null;
					// 授课地点
					String courseLocation = null;
					String courseClass = null;
					int  i = 0;	
					while(matcher.find()) {
						/**
						 * 当授课星期时间相同，但授课周数不同时，4个一组循环
						 * i = 0，课程名称；i = 1，课程周次；i = 2，授课地点；i = 3，班级
						 */
						if (i % 4 == 0) {
							/**
							 * 创建本次授课课程片段
							 */
							cs = new ArrayList<>();
							i = 0;
						}
						switch (i) {
						// 课程名称
						case 0:
							courseName = matcher.group(1);
							break;
						// 课程起止周	
						case 1:
							String weekString = matcher.group(1);
							Pattern pWeek = Pattern.compile(REGEX_STARTEND_WEEKS);
							Matcher mWeek = pWeek.matcher(weekString);
							int a = 0;
							while (mWeek.find()) {
								if (a % 2 == 0) {
									a = 0;
								}
								int startWeek = Integer.valueOf(mWeek.group(1));
								int endWeek =  Integer.valueOf(mWeek.group(2));
							
								for (int week = startWeek; week <= endWeek; week++) {
									CourseSection section = new CourseSection();
									// 开始时间。第3行为12节(从0行算)，列数为星期
									section.setStartTime(DateConversionUtil.courseTimeToDate(week, cellIndex, EnumSession.values()[rowIndex - 3].getStartTime()));
									// 结束时间
									section.setEndTime(DateConversionUtil.courseTimeToDate(week, cellIndex, EnumSession.values()[rowIndex - 3].getEndTime()));
									cs.add(section);
								}
							}
							// 将起止周的数字替换掉，从而在下面直接获取单一周数
							weekString = mWeek.replaceAll("*");
							Pattern pSingleWeek = Pattern.compile(REGEX_SINAGLE_WEEK);
							Matcher mSingleWeek = pSingleWeek.matcher(weekString);
							while (mSingleWeek.find()) {
								int singleWeek = Integer.valueOf(mSingleWeek.group(1));
								CourseSection section = new CourseSection();
								section.setStartTime(DateConversionUtil.courseTimeToDate(singleWeek, cellIndex, EnumSession.values()[rowIndex - 3].getStartTime()));
								section.setEndTime(DateConversionUtil.courseTimeToDate(singleWeek, cellIndex, EnumSession.values()[rowIndex - 3].getEndTime()));
								cs.add(section);
							}
							break;
						// 授课地点
						case 2:
							courseLocation =  matcher.group(1);
							break;
						// 授课班级
						case 3:
							courseClass = matcher.group(1);
							Course course = new Course();
							course.setName(courseName);
							course.setLocation(courseLocation);
							course.setTeachingClass(courseClass);
							// 将本次课程片段关联至本课程
							for (CourseSection s : cs) {
								s.setCourse(course);
							}
							// 将本次课程片段添加至整体课程片段
							courseSections.addAll(cs);
							break;
						}
						i++;
					}		
				}
			}	
		}
		return courseSections;
	}
	
	
	/**
	 * 
	 * @param excelPath
	 * @throws SEWMException
	 * @throws Exception
	 */
	public static List<CourseSection> getExcel(String excelPath) throws SEWMException, Exception {
		return getExcel(new File(excelPath));
	}
}
