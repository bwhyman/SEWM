package com.se.working.invigilation.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.CourseSection;

@Repository
public class CourseSectionDao extends GenericDao<CourseSection> {

	/**
	 * 基于日期查找相应授课片段<br>
	 * 查询条件：开始时间在授课时间内，或，结束时间在授课时间内，或，开始时间在授课时间前同时结束时间在授课时间后
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CourseSection> find(Calendar startTime, Calendar endTime, long groupId) {
		String HQL = "FROM CourseSection c WHERE (c.course.teacher.user.groups.id=:groupId) AND ((:st>=c.startTime AND :st<=c.endTime) "
				+ "OR (:et>=c.startTime AND :et<=c.endTime) "
				+ "OR(:st<=c.startTime AND :et>=c.endTime))";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("st", startTime);
		query.setParameter("et", endTime);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
	/**
	 * 返回当前全部课程片段
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CourseSection> find(Calendar date, long groupId) {
		Calendar cDate = Calendar.getInstance();
		cDate.setTime(date.getTime());
		cDate.set(Calendar.HOUR_OF_DAY, 0);
		cDate.set(Calendar.MINUTE, 0);
		Calendar nDate = Calendar.getInstance();
		nDate.setTime(cDate.getTime());
		nDate.add(Calendar.DATE, 1);
		
		String HQL = "FROM CourseSection c WHERE c.course.teacher.user.groups.id=:groupId AND c.startTime >= :date AND c.endTime<=:nextDate";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("date", cDate);
		query.setParameter("nextDate", nDate);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
	
}
