package com.se.working.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.entity.User;

@Repository
public class UserDao extends GenericDao<User> {

	public User getBypassword(String employeeNumber, String password) {
		String HQL = "FROM User u WHERE u.employeeNumber=:employeeNumber AND u.password=:password";
		Query query = getCurrentSession().createQuery(HQL).setString("employeeNumber", employeeNumber)
				.setString("password", password);
		return (User) query.uniqueResult();
	}

	/**
	 * 基于通知设置，组，查找用户
	 * 
	 * @param enabledMessage
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> list(boolean enabledMessage, long groupId) {
		String HQL = "FROM User u WHERE u.enabledMessage=:enabledMessage AND u.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setBoolean("enabledMessage", enabledMessage);
		query.setLong("groupId", groupId);
		return query.list();
	}

	/**
	 * 
	 * @param authorityId
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> list(long authorityId, long groupId) {
		String HQL = "FROM User u WHERE u.userAuthority.id=:authorityId AND u.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setLong("authorityId", authorityId);
		query.setLong("groupId", groupId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> list(long groupId) {
		String HQL = "FROM User u WHERE u.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setLong("groupId", groupId);
		return query.list();
	}
}
