package com.se.working.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.GroupsDao;
import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.Groups;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.util.MD5;



/**
 * 用户相关业务逻辑处理
 * 
 * @author BO
 *
 */
@Service
@Transactional
public class AdminService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupsDao groupsDao;
	@Autowired
	private TeacherInviDao teacherInviDao;
	@Autowired
	private TeacherTitleDao teacherTitleDao;

	/**
	 * 添加用户，需重写 单向关系，没有级联，需手动创建关联对象
	 */
	public void add(User user) {
		// TODO Auto-generated method stub
		user.setUserAuthority(new UserAuthority(UserAuthority.TEACHER));
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
		user.setPassword(MD5.generateMD5(user.getEmployeeNumber()));
	}

	/**
	 * 原管理员全部修改为普通权限<br>
	 * 再将新管理员修改为管理员权限
	 * @param newAdmins
	 * @param groupId
	 */
	public void updateAdmins(long[] newAdmins, long groupId) {
		List<User> oldAdmins = userDao.list(UserAuthority.ADMIN, groupId);
		for (User u : oldAdmins) {
			u.setUserAuthority(new UserAuthority(UserAuthority.TEACHER));
		}
		for (int i = 0; i < newAdmins.length; i++) {
			 userDao.get(newAdmins[i]).setUserAuthority(new UserAuthority(UserAuthority.ADMIN));
		}
	}

	/**
	 * 
	 * @return 所有职称
	 */
	public List<TeacherTitle> findTeacherTitles() {
		return teacherTitleDao.list();
	}

	public List<User> findUsers(long groupId) {
		Groups groups = groupsDao.get(groupId);
		return new ArrayList<>(groups.getUsers());
	}

	/**
	 * 
	 * @param invqs 特殊监考次数
	 * @param checkeds 开启推荐用户IDs
	 * @param groupId
	 */
	public void updateTeacherInviSetting(int[] invqs, long[] checkeds, long groupId) {
		for (int i = 0; i < invqs.length; i++) {
			teacherInviDao.list(groupId).get(i).setSqecQuantity((invqs[i]));
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
	 * @param enabled
	 * @param groupId
	 */
	public void updateUserNotifSetting(long[] enabled, long groupId) {
		
		for (User u : userDao.list(groupId)) {
			boolean message = false;
			for (int i = 0; i < enabled.length; i++) {
				if (u.getId() == enabled[i]) {
					message = true;
				}
			}
			// 如果在开启中
			if (message) {
				u.setEnabledMessage(true);
			} else {
				// 不在则关闭，并置推荐为关闭
				u.setEnabledMessage(false);
				teacherInviDao.get(u.getId()).setEnabledRecommend(false);
			}
		}
		
	}
	/**
	 * 所有通知设置用户
	 * @return
	 */
	public List<User> findUsers(boolean enabledMessage, long groupId) {
		return userDao.list(enabledMessage, groupId);
	}
}
