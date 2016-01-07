package com.se.working.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.UserDao;
import com.se.working.entity.User;

/**
 * 教师相关业务逻辑处理
 * @author BO
 *
 */
@Service
@Transactional
public class UserService extends GenericService<User, Long>{
	@Autowired
	private UserDao userDao;
	
	/**
	 * 添加用户，需重写
	 * 单向关系，没有级联，需手动创建关联对象
	 */
	@Override
	public void add(User entity) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 登录验证
	 * @param userName
	 * @param password
	 * @return
	 */
	public User findByPassword(String userName, String password) {
		
		return userDao.getBypassword(userName, password);
	}

	/**
	 * 初始密码重置为用户名，即员工号
	 * @param user
	 */
	public void setDefaultPassword(User user) {
		
	}
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
