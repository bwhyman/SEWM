package com.se.working.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.entity.User;
@Repository
public class UserDao extends GenericDao<User, Long>{

	public User getBypassword(String userName, String password) {
		String HQL = "FROM User u WHERE u.userName=:userName AND u.password=:password";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL)
				.setString("userName", userName)
				.setString("password", password);
		return (User) query.uniqueResult();
	}
	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}

}
