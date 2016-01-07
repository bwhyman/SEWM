package com.se.working.invigilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.invigilation.dao.CourseSectionDao;
import com.se.working.invigilation.entity.CourseSection;
import com.se.working.service.GenericService;

/**
 * 教师课表信息逻辑处理
 * @author BO
 *
 */
@Service
@Transactional
public class CourseSectionService extends GenericService<CourseSection, Long>{
	@Autowired
	private CourseSectionDao courseSectionDao;
	/**
	 * 删除教师课表
	 * @param userId
	 * @return
	 */
	public int deleteSectionsByUserId(long userId) {
		return courseSectionDao.deleteSectionsByUserId(userId);
	}
	
	
}
