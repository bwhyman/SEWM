package com.se.working.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.se.working.entity.User;

@Repository
public class UserDao extends GenericDao<User> {

	public User find(String employeeNumber, String password) {
		String HQL = "FROM User u WHERE u.employeeNumber=:employeeNumber AND u.password=:password";
		Query query = getCurrentSession().createQuery(HQL)
				.setParameter("employeeNumber", employeeNumber)
				.setParameter("password", password);
		return (User) query.getSingleResult();
	}

	/**
	 * 基于通知设置，组，查找用户
	 * 
	 * @param enabledMessage
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> find(boolean enabledMessage, long groupId) {
		String HQL = "FROM User u WHERE u.enabledMessage=:enabledMessage AND u.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("enabledMessage", enabledMessage);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}

	/**
	 * 
	 * @param authorityId
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUsers(long authorityId, long groupId) {
		String HQL = "FROM User u WHERE u.userAuthority.id=:authorityId AND u.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("authorityId", authorityId);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findUsers(long groupId) {
		String HQL = "FROM User u WHERE u.groups.id=:groupId";
		Query query =getCurrentSession().createQuery(HQL);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}
}
