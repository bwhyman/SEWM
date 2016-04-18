package com.se.working.invigilation.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.CourseSection;

@Repository
public class CourseSectionDao extends GenericDao<CourseSection, Long> {

	/**
	 * 基于日期查找相应授课片段<br>
	 * 查询条件：开始时间在授课时间内，或，结束时间在授课时间内，或，开始时间在授课时间前同时结束时间在授课时间后
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CourseSection> listSections(Calendar startTime, Calendar endTime) {
		String HQL = "FROM CourseSection c WHERE (:st>=c.startTime AND :st<=c.endTime) "
				+ "OR (:et>=c.startTime AND :et<=c.endTime) "
				+ "OR(:st<=c.startTime AND :et>=c.endTime)";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("st", startTime);
		query.setCalendar("et", endTime);
		
		return query.list();
	}
	/**
	 * 返回当前全部课程片段
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CourseSection> listSectionsByDate(Calendar date) {
		Calendar cDate = Calendar.getInstance();
		cDate.setTime(date.getTime());
		cDate.set(Calendar.HOUR_OF_DAY, 0);
		cDate.set(Calendar.MINUTE, 0);
		Calendar nDate = Calendar.getInstance();
		nDate.setTime(cDate.getTime());
		nDate.add(Calendar.DATE, 1);
		
		String HQL = "FROM CourseSection c WHERE c.startTime >= :date AND c.endTime<=:nextDate";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("date", cDate);
		query.setCalendar("nextDate", nDate);
		return query.list();
	}
	
}
