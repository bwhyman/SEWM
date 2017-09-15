package com.se.working.invigilation.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.TeacherInvigilation;

@Repository
public class TeacherInviDao extends GenericDao<TeacherInvigilation>{

	public TeacherInvigilation find(String name, long groupId) {
		String HQL = "FROM TeacherInvigilation t WHERE t.user.name=:name AND t.user.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("name", name);
		query.setParameter("groupId", groupId);
		return (TeacherInvigilation) query.getSingleResult();
	}
	
	/**
	 * 基于group查询
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeacherInvigilation> findTeacher(long groupId) {
		String HQL = "FROM TeacherInvigilation t WHERE t.user.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
	
	/**
	 * 获取推荐关闭教师
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeacherInvigilation> findUnRDCs(long groupId) {
		String HQL = "FROM TeacherInvigilation t WHERE t.user.groups.id=:groupId AND t.enabledRecommend = false";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
	/**
	 * 获取所有可监考教师，按监考次数最少的正序，ID倒序 SIZE(t.invigilations)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeacherInvigilation> findRDCs(long groupId) {
		String HQL = "FROM TeacherInvigilation t WHERE t.user.groups.id=:groupId AND t.enabledRecommend = true "
				+ "AND t.user.enabledMessage = true ORDER BY SIZE(t.invigilations) ASC, id DESC";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeacherInvigilation> listOrderBySpecNumber() {
		String HQL = "FROM TeacherInvigilation t ORDER BY t.sqecQuantity ASC";
		return getCurrentSession().createQuery(HQL).getResultList();
	}
	
}
