package com.se.working.util;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.se.working.exception.SEWMException;
import com.se.working.invigilation.entity.Course;
import com.se.working.invigilation.entity.CourseSection;
/**
 * 2016.05.08，修改资源操作
 * @author BO
 *
 */
public class TimetableExcelUtil {
	// 获取教师姓名表达式
	private static final String REGEX_TNAME = "大学(.*)教师";
	/**
	 * 获取课表表达式<br>
	 * 2016.04.11，修改表达式<br>
	 * 忽略第一个\n和最后一个\n，可过滤中间的空行，最后是否有空行
	 */
	private static final String REGEX_SECTION = "([^\n].*)\n(.*)\n(.*)\n(.*[^\n])";
	// 基于"-" 获取起止周
	private static final String REGEX_STARTEND_WEEKS = "(\\d+)-(\\d+)";
	// 单周
	private static final String REGEX_SINAGLE_WEEK = "(\\d+)";

	/**
	 * 仅依靠课程名称无法定位具体课程，因为即使课程名称相同，但授课地点，授课对象授课类型(实验)不同无法详细区分。
	 * 如进一步设计地点班级等实体类过于繁琐。<br>
	 * 每个cell为一个课程，如果cell中包含多个授课则拆分为多个课程；每个课程包含若干授课时间。
	 * 
	 * @param excelFile
	 * @return
	 */
	public static List<Course> getExcel(InputStream is) {
		try {
			Workbook workbook = WorkbookFactory.create(is);
			return getRow(workbook.getSheetAt(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("文件操作错误", e);
		}
	}

	/**
	 * 读取row
	 * 
	 * @param sheet
	 * @return
	 * @throws ParseException
	 */
	private static List<Course> getRow(Sheet sheet) throws ParseException {
		List<Course> courses = new ArrayList<>();

		// 从第4行读取
		for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row == null) {
				break;
			}
			// 从第2列读取
			for (int cellIndex = 1; cellIndex < row.getLastCellNum(); cellIndex++) {
				Cell cell = row.getCell(cellIndex);
				if (cell != null && !StringUtils.isEmpty(StringUtils.trimAllWhitespace(cell.getStringCellValue()))) {
					Pattern pattern = Pattern.compile(REGEX_SECTION);

					/**
					 * 当没有授课地点时无法提取长度为0的内容，使用*替代地点 否则后面循环错误
					 */
					Matcher matcher = pattern.matcher(cell.getStringCellValue().replace("周\n\n", "周\n*\n"));
					// 课程名称
					String courseName = null;
					// 授课地点
					String courseLocation = null;
					String courseClass = null;
					
					/**
					 * 每次获取的片段
					 */
					
					while (matcher.find()) {
						// 置于循环内，一个课程，便于统计
						List<CourseSection> cs = new ArrayList<>();
						// 课程名称
						courseName = matcher.group(1);
						// 课程起止
						String weekString = matcher.group(2);
						Pattern pWeek = Pattern.compile(REGEX_STARTEND_WEEKS);
						Matcher mWeek = pWeek.matcher(weekString);
						while (mWeek.find()) {
							int startWeek = Integer.valueOf(mWeek.group(1));
							int endWeek = Integer.valueOf(mWeek.group(2));
							
							for (int week = startWeek; week <= endWeek; week++) {
								CourseSection section = new CourseSection();
								// 开始时间。第3行为12节(从0行算)，列数为星期
								section.setStartTime(DateUtils.courseTimeToDate(week, cellIndex,
										EnumSession.values()[rowIndex - 3].getStartTime()));
								// 结束时间
								section.setEndTime(DateUtils.courseTimeToDate(week, cellIndex,
										EnumSession.values()[rowIndex - 3].getEndTime()));
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
							section.setStartTime(DateUtils.courseTimeToDate(singleWeek, cellIndex,
									EnumSession.values()[rowIndex - 3].getStartTime()));
							section.setEndTime(DateUtils.courseTimeToDate(singleWeek, cellIndex,
									EnumSession.values()[rowIndex - 3].getEndTime()));
							cs.add(section);
						}
						// 授课地点
						courseLocation = matcher.group(3);
						// 授课班级
						courseClass = matcher.group(4);
						Course course = new Course();
						course.setName(courseName);

						course.setLocation(courseLocation);
						course.setTeachingClass(courseClass);
						// 将本次课程片段关联至本课程
						course.setCourseSections(new LinkedHashSet<>(cs));
						// 将本次课程片段添加至整体课程片段
						courses.add(course);
					}
				}
			}
		}

		return courses;
	}

	/**
	 * 读取课表中的教师姓名
	 * 
	 * @param excelFile
	 * @return
	 * @throws Exception
	 * @throws SEWMException
	 */
	public static String getTimetableName(InputStream is) {
		try {
			Workbook workbook = WorkbookFactory.create(is);
			Row row = workbook.getSheetAt(0).getRow(0);
			Pattern pattern = Pattern.compile(REGEX_TNAME);
			Matcher matcher = pattern.matcher(row.getCell(0).getStringCellValue());
			String name = null;
			if (matcher.find()) {
				name = StringUtils.trimAllWhitespace(matcher.group(1));
			}
			return name;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SEWMException("文件操作错误", e);
		}
	}
}