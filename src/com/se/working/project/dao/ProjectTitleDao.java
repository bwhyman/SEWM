package com.se.working.project.dao;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.project.entity.ProjectTitle;

@Repository
public class ProjectTitleDao extends GenericDao<ProjectTitle, Long> {

	/**
	 * 根据题目名称查找毕设题目信息
	 * @param name
	 * @return
	 */
	public ProjectTitle getProjectTitleByName(String name){
		String HQL = "FROM ProjectTitle pt WHERE pt.name =:name";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setString("name", name);
		return (ProjectTitle) query.uniqueResult();
	}
}
