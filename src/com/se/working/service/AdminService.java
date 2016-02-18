package com.se.working.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.entity.UserAuthority.UserAuthorityLevel;
import com.se.working.entity.UserAuthority.UserAuthorityType;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.TeacherInvigilation;



/**
 * 用户相关业务逻辑处理
 * 
 * @author BO
 *
 */
@Service
@Transactional
public class AdminService extends GenericService<User, Long> {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TeacherInviDao teacherInviDao;
	@Autowired
	private TeacherTitleDao teacherTitleDao;

	/**
	 * 添加用户，需重写 单向关系，没有级联，需手动创建关联对象
	 */
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		user.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
		// user.setPassword(MD5.generateMD5(user.getEmployeeNumber()));
		userDao.persist(user);
		TeacherInvigilation teacherInvigilation = new TeacherInvigilation();
		teacherInvigilation.setUser(user);
		teacherInviDao.persist(teacherInvigilation);

	}

	/**
	 * 初始密码重置为用户名，即员工号
	 * 
	 * @param userId
	 */
	public void updateDefaultPassword(long userId) {
		User user = userDao.get(userId);
		// user.setPassword(MD5.generateMD5(user.getEmployeeNumber()));
	}

	/**
	 * 除超级管理员全部修改为普通权限<br>
	 * 再将新管理员修改为管理员权限
	 * 
	 * @param newAdmins
	 */
	public void updateAdmins(long[] newAdmins) {
		for (User teacher : userDao.list()) {
			if (teacher.getUserAuthority().getLevel() <= UserAuthorityLevel.ADAMIN) {
				teacher.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			}

		}
		for (int i = 0; i < newAdmins.length; i++) {
			User user = userDao.get(newAdmins[i]);
			if (user.getUserAuthority().getLevel() < UserAuthorityLevel.SUPERADMIN) {
				user.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
			}
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
	 * @param invqs 特殊监考次数
	 * @param checkeds 开启推荐用户IDs
	 */
	public void updateTeacherInviSetting(int[] invqs, long[] checkeds) {
		for (int i = 0; i < invqs.length; i++) {
			teacherInviDao.list().get(i).setSqecQuantity((invqs[i]));
		}
		for (TeacherInvigilation t : teacherInviDao.list()) {
			boolean checked = false;
			for (int i = 0; i < checkeds.length; i++) {
				if (t.getId() == checkeds[i]) {
					checked = true;
					break;
				}
			}
			t.setEnabledRecommend(checked);
		}
	}

	/**
	 * 修改用户通知设置，同时修改用户监考推荐设置
	 * @param checkeds 开启通知用户IDs
	 */
	public void updateUserNotifSetting(long[] checkeds) {
		for (User u : userDao.list()) {
			boolean checked = false;
			for (int i = 0; i < checkeds.length; i++) {
				if (u.getId() == checkeds[i]) {
					checked = true;
					break;
				}
			}
			u.setEnabledMessage(checked);
		}
		// 修改监考推荐设置
		for (TeacherInvigilation t : teacherInviDao.list()) {
			boolean checked = false;
			for (int i = 0; i < checkeds.length; i++) {
				if (t.getId() == checkeds[i]) {
					checked = true;
					break;
				}
			}
			t.setEnabledRecommend(checked);
		}
	}
	/**
	 * 所有通知关闭用户
	 * @return
	 */
	public List<User> findDisabledUsers() {
		return userDao.listDisableds();
	}
}
