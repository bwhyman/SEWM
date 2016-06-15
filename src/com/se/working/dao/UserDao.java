package com.se.working.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.entity.User;
@Repository
public class UserDao extends GenericDao<User, Long>{
	
	public User getBypassword(String employeeNumber, String password) {
		String HQL = "FROM User u WHERE u.employeeNumber=:employeeNumber AND u.password=:password";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL)
				.setString("employeeNumber", employeeNumber)
				.setString("password", password);
		return (User) query.uniqueResult();
	}
	/**
	 * 查找通知关闭用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> listDisableds() {
		String HQL = "FROM User u WHERE u.enabledMessage = false";
		return getSessionFactory().getCurrentSession().createQuery(HQL).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> listAbleds() {
		String HQL = "FROM User u WHERE u.enabledMessage = true AND u.userAuthority.level > 5";
		return getSessionFactory().getCurrentSession().createQuery(HQL).list();
	}
	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}

}
