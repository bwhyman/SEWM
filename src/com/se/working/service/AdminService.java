package com.se.working.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.controller.ReturnMessage;
import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserAuthorityDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.entity.UserAuthority.UserAuthorityType;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.util.MD5;

/**
 * 用户相关业务逻辑处理
 * @author BO
 *
 */
@Service
@Transactional
public class AdminService extends GenericService<User, Long>{
	@Autowired
	private UserDao userDao;
	@Autowired
	private TeacherInviDao teacherInvigilationDao;
	@Autowired
	private UserAuthorityDao userAuthorityDao;
	@Autowired
	private TeacherTitleDao teacherTitleDao;
	
	/**
	 * 添加用户，需重写
	 * 单向关系，没有级联，需手动创建关联对象
	 */
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		user.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
		// user.setPassword(MD5.generateMD5(user.getEmployeeNumber()));
		userDao.persist(user);
		TeacherInvigilation teacherInvigilation = new TeacherInvigilation();
		teacherInvigilation.setUser(user);
		teacherInvigilationDao.persist(teacherInvigilation);
		
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
	 * @param userId
	 */
	public void setDefaultPassword(long userId) {
		User user = userDao.get(userId);
		// user.setPassword(MD5.generateMD5(user.getEmployeeNumber()));
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
	/**
	 * 查找全部管理员
	 * @return 全部管理员
	 */
	public List<User> findAdmins() {
		return new ArrayList<>(userAuthorityDao.get(UserAuthorityType.ADAMIN).getUsers());
	}

	/**
	 * 将原管理员全部修改为普通权限，再将新管理员修改为管理员权限
	 * @param newAdmins 需更新为管理员权限用户
	 * @param olderAdmins 需更新为普通用户权限用户
	 */
	public void updateAdmins(List<User> newAdmins, List<User> olderAdmins) {
		for (User teacher : olderAdmins) {
			User user = userDao.get(teacher.getId());
			user.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
		}
		for (User admin : newAdmins) {
			User user = userDao.get(admin.getId());
			user.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
		}
	}
	
	/**
	 * 
	 * @return 所有职称
	 */
	public List<TeacherTitle> findTeacherTitles() {
		return teacherTitleDao.list();
	}
	/**
	 * 
	 * @return 用户监考对象
	 */
	public List<TeacherInvigilation> findTeacherInvigilations() {
		return teacherInvigilationDao.list();
	}
	
	/**
	 * 修改用户监考设置
	 * @param teacherInvigilations
	 * @return
	 */
	public String updateTeacherInviSetting(List<TeacherInvigilation> teacherInvigilations) {
		for (TeacherInvigilation t : teacherInvigilations) {
			TeacherInvigilation teacher = teacherInvigilationDao.get(t.getId());
			teacher.setSqecQuantity(t.getSqecQuantity());
			teacher.setEnabledRecommend(t.isEnabledRecommend());
		}
		
		return ReturnMessage.NONE;
	}
	
	public String updateUserNotifSetting(List<User> users) {
		for (User u : users) {
			User user = userDao.get(u.getId());
			user.setEnabledMessage(u.isEnabledMessage());
		}
		return ReturnMessage.NONE;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
