package com.se.working.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.invigilation.dao.CourseDao;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.Course;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.util.MD5;
@Service
@Transactional
public class UserService extends GenericService<User, Long>{

	@Autowired
	private TeacherInviDao teacherInviDao;
	@Autowired
	private CourseDao courseDao;
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
	public User findByPassword(String employeeNumber, String password) {
		
		return userDao.getBypassword(employeeNumber, MD5.generateMD5(password));
	}
	
	/**
	 * 
	 * @return 所有职称
	 */
	public List<TeacherTitle> findTeacherTitles() {
		return teacherTitleDao.list();
	}
	/**
	 * 重写update方法，重新封装
	 */
	@Override
	public void update(User newUser) {
		// TODO Auto-generated method stub
		User user = userDao.get(newUser.getId());
		user.setName(newUser.getName());
		user.setEmployeeNumber(newUser.getEmployeeNumber());
		user.setPhoneNumber(newUser.getPhoneNumber());
		user.setTitle(newUser.getTitle());
		user.setIntroduction(newUser.getIntroduction());
	}
	
	public void updatePassword(long userId, String newPwd) {
		User user = userDao.get(userId);
		user.setPassword(MD5.generateMD5(newPwd));
	}
	
	/**
	 * 删除的课程数
	 * @param userId
	 * @return
	 */
	public int deleteCourseByUserId(long userId) {
		TeacherInvigilation teacher = teacherInviDao.get(userId);
		int count = teacher.getCourses().size();
		for (Course c : teacher.getCourses()) {
			 courseDao.delete(c);
		}
		return count;
	}
	
	/**
	 * 所有通知开启用户
	 * @return
	 */
	public List<User> findAbledUsers() {
		return userDao.listAbleds();
	}
	
	public UserService() {
		// TODO Auto-generated constructor stub
	}

}
