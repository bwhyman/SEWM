package com.se.working.invigilation.dao;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.CourseSection;

@Repository
public class CourseSectionDao extends GenericDao<CourseSection, Long> {
	/**
	 * 基于ID删除教师课表数据信息
	 * @param userId
	 * @return 
	 */
	public int deleteSectionsByUserId(long userId) {
		String HQL = "DELETE FROM CourseSection c WHERE c.teacher.id = :userId";
		return getSessionFactory().getCurrentSession().createQuery(HQL).executeUpdate();
	}
	
	/**
	 * 
	 * @param userId
	 * @return 已包含记录
	 */
	public int listSectionByUserId(long userId) {
		String HQL = "SELECT COUNT(c.id) FROM CourseSection c WHERE c.teacher.id = :userId";
		return 0;
	}
}
