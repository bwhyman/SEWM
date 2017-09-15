package com.se.working.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.util.MD5;
@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private TeacherTitleDao teacherTitleDao;
	/**
	 * 登录验证
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getUser(String employeeNumber, String password) {
		
		return userDao.find(employeeNumber, MD5.generateMD5(password));
	}
	
	/**
	 * 
	 */
	public User getUser(long id) {
		return userDao.find(id);
	}
	
	/**
	 * 
	 * @return 所有职称
	 */
	public List<TeacherTitle> getTeacherTitles() {
		return teacherTitleDao.find();
	}
	
	public void updateUser(User newUser) {
		// TODO Auto-generated method stub
		User user = userDao.find(newUser.getId());
		user.setName(newUser.getName());
		user.setEmployeeNumber(newUser.getEmployeeNumber());
		user.setPhoneNumber(newUser.getPhoneNumber());
		user.setTitle(newUser.getTitle());
		user.setIntroduction(newUser.getIntroduction());
	}
	
	public void updatePassword(long userId, String newPwd) {
		User user = userDao.find(userId);
		user.setPassword(MD5.generateMD5(newPwd));
	}
	
}
