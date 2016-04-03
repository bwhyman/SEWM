package com.se.working.project.dao;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.project.entity.StudentProject;

@Repository
public class StudentProjectDao extends GenericDao<StudentProject, Long> {
	
	/**
	 * 清空StudentProject表数据
	 * @return
	 */
	public int deleteAll(){
		String HQL = "DELETE FROM StudentProject WHERE 1=1";
		return getSessionFactory().getCurrentSession().createQuery(HQL).executeUpdate();
	}
}
