package com.se.working.invigilation.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.TeacherInvigilation;

@Repository
public class TeacherInviDao extends GenericDao<TeacherInvigilation, Long>{

	/**
	 * 获取推荐关闭教师
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeacherInvigilation> listUnRDCs() {
		String HQL = "FROM TeacherInvigilation t WHERE t.enabledRecommend = false";
		return getSessionFactory().getCurrentSession().createQuery(HQL).list();
	}
	/**
	 * 获取所有可监考教师，按监考次数最少的正序，ID倒序 SIZE(t.invigilations)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TeacherInvigilation> listRDCs() {
		String HQL = "FROM TeacherInvigilation t WHERE t.enabledRecommend = true "
				+ "AND t.user.enabledMessage = true ORDER BY SIZE(t.invigilations) ASC, id DESC";
		return getSessionFactory().getCurrentSession().createQuery(HQL).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeacherInvigilation> listOrderBySpecNumber() {
		String HQL = "FROM TeacherInvigilation t ORDER BY t.sqecQuantity ASC";
		return getSessionFactory().getCurrentSession().createQuery(HQL).list();
	}
	
	public TeacherInviDao() {
		// TODO Auto-generated constructor stub
	}

}
