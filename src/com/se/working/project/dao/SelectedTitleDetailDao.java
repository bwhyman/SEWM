package com.se.working.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.project.entity.SelectedTitleDetail;

@Repository
public class SelectedTitleDetailDao extends GenericDao<SelectedTitleDetail, Long> {
	
	/**
	 * 根据教师id查询选题信息
	 * @param teacherId
	 * @param confirmed
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SelectedTitleDetail> listByTeacherIdAndconfirmed(long teacherId, boolean confirmed){
		String HQL = "FROM SelectedTitleDetail s WHERE s.confirmed =:confirmed AND s.title.teacher.id =:teacherId";
		return getSessionFactory().getCurrentSession().createQuery(HQL).setBoolean("confirmed", confirmed).setLong("teacherId", teacherId).list();
	}
	
	/**
	 * 根据titleId查询选题成功的选题信息
	 * @param titleId
	 * @return
	 */
	public SelectedTitleDetail getByTitleId(long titleId){
		String HQL = "FROM SelectedTitleDetail s WHERE s.confirmed = true AND s.title.id =:titleId";
		return (SelectedTitleDetail) getSessionFactory().getCurrentSession().createQuery(HQL).setLong("titleId", titleId).uniqueResult();
	}
	
	/**
	 * 查询选题成功的学生信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SelectedTitleDetail> listSuccess(){
		String HQL = "FROM SelectedTitleDetail s WHERE s.confirmed = true";
		return getSessionFactory().getCurrentSession().createQuery(HQL).list();
	}
}
