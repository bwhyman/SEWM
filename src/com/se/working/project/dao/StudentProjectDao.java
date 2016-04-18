package com.se.working.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.project.entity.StudentProject;
import com.se.working.util.EnumConstant;

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
	
	/**
	 * 查选题未成功的学生
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StudentProject> listSelectFailByPage(int page){
		String HQL = "FROM StudentProject s WHERE s.student.id not in(SELECT p.student.id FROM StudentProject p WHERE p.selectedTitleDetail.confirmed=true)";
		return getSessionFactory().getCurrentSession().createQuery(HQL)
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
}
