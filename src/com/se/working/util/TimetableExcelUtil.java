package com.se.working.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.se.working.invigilation.entity.Course;
import com.se.working.invigilation.entity.CourseSection;
import com.se.working.invigilation.entity.TeacherInvigilation;


public class TimetableExcelUtil {
	/**
	 * 从表格中提取教师
	 * 
	 * @param excelPath
	 * @return 教师授课时间
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public static List<CourseSection> getTeacherCourseSectionUtil(String excelPath,
			TeacherInvigilation teacherInvigilation) throws NumberFormatException, ParseException {
		List<CourseSection> courseSections = new ArrayList<>();
		HSSFWorkbook wb = null;
		try {
			FileInputStream fis = new FileInputStream(excelPath);
			wb = new HSSFWorkbook(fis);
			HSSFSheet hssfSheet = wb.getSheetAt(0);
			// 获取第一个sheet页
			if (hssfSheet != null) {
				courseSections = getSheetInfo(hssfSheet, teacherInvigilation);
			}
			hssfSheet = null;
			wb.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		wb = null;

		return courseSections;
	}

	/**
	 * sheet信息处理
	 * 
	 * @param cell
	 * @param teacherInvigilation
	 * @return
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	private static List<CourseSection> getSheetInfo(HSSFSheet sheet, TeacherInvigilation teacherInvigilation)
			throws NumberFormatException, ParseException {
		List<CourseSection> courseSections = new ArrayList<>();
		// 获取教师信息
		HSSFRow row = sheet.getRow(0);
		if (row != null) {
			HSSFCell cell = row.getCell(0);
			if (!StringConversionUtil.isEmpty(StringConversionUtil.moveBlankSpace(cell.toString()))) {
				String teacherName = cell.toString().split(" ")[1];
				if (teacherInvigilation.getUser().getName().equals(teacherName)) {
					// 获取课程信息
					Map<String, Course> courseMap = new HashMap<>();
					Course course = null;
					for (int rowIndex = 3; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
						row = sheet.getRow(rowIndex);
						if (row != null) {
							for (int cellIndex = 1; cellIndex < row.getLastCellNum(); cellIndex++) {
								cell = row.getCell(cellIndex);
								if (cell != null && !StringConversionUtil
										.isEmpty(StringConversionUtil.moveBlankSpace(cell.toString()))) {
									// 获取单个课程信息
									String[] cellArray = cell.toString().split("\n");
									for (int i = 0; i < cellArray.length / 5; i++) {
										if (courseMap.get(cellArray[i * 5 + 1]) == null) {
											course = new Course();
											course.setName(cellArray[i * 5 + 1]);
											courseMap.put(cellArray[i * 5 + 1], course);
										}

										course.setTeacher(teacherInvigilation);
										course.setTeachingClass(cellArray[i * 5 + 4]);
										course.setInsertTime(new Date());

										// 获取上课周，并进行，分离
										String[] weekArray = cellArray[i * 5 + 2]
												.substring(0, cellArray[i * 5 + 2].length() - 1).split(",");
										for (int j = 0; j < weekArray.length; j++) {
											// 进行-分离
											String[] weekNum = weekArray[j].split("-");
											int start;
											int end;
											for (int k = 0; k < weekNum.length; k += 2) {
												start = Integer.valueOf(weekNum[k]);
												if (k + 1 >= weekNum.length) {
													end = Integer.valueOf(weekNum[k]);
												} else {
													end = Integer.valueOf(weekNum[k + 1]);
												}

												// 遍历获取开课周
												for (int k2 = start; k2 <= end; k2++) {
													// 根据第几周，周几，第几节课获取上课时间、下课时间
													CourseSection courseSection = new CourseSection();
													courseSection.setCourse(courseMap.get(cellArray[i * 5 + 1]));
													String[] startTimeArray = KeyValueUtil.courseStartTime
															.get(row.getCell(0).toString()).split(":");
													courseSection.setStartTime(
															DateConversionUtil.courseTimeToDate("2015-09-07", k2,
																	KeyValueUtil.week.get(sheet.getRow(2)
																			.getCell(cellIndex).toString()),
															Integer.valueOf(startTimeArray[0]),
															Integer.valueOf(startTimeArray[1])));

													String[] endTimeArray = KeyValueUtil.courseEndTime
															.get(row.getCell(0).toString()).split(":");
													courseSection.setEndTime(
															DateConversionUtil.courseTimeToDate("2015-09-07", k2,
																	KeyValueUtil.week.get(sheet.getRow(2)
																			.getCell(cellIndex).toString()),
															Integer.valueOf(endTimeArray[0]),
															Integer.valueOf(endTimeArray[1])));
													courseSection.setTeacher(teacherInvigilation);
													courseSections.add(courseSection);

												}

											}
											weekNum = null;
										}
										weekArray = null;
									}
									cellArray = null;
									cell = null;
								}
							}
							row = null;
						}

					}
				}
			}
		} else {
			return null;
		}

		return courseSections;
	}
}
