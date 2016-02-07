package com.se.working.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.working.controller.pojo.UserAdminContro;
import com.se.working.controller.pojo.UserTitleContro;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.service.AdminService;


/**
 * 管理员操作
 * @author BO
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	/**
	 *  管理员根目录
	 *  所有管理员权限操作以此为根
	 *  单态，无需设为常量
	 */
	private String adminBasePath = "/admin/";
	private String redirect = "redirect:";
	@Autowired
	private AdminService userService;
	
	/**
	 * 加载职称，默认选择值为讲师
	 * @param vMap
	 */
	@RequestMapping("/adduser")
	public void adduser(Map<String, Object> vMap) {
		String userTitleId = String.valueOf(TeacherTitle.TeacherTitleType.LECTURER);
		System.out.println(userTitleId);
		UserTitleContro userTitleContro = new UserTitleContro(userService.findTeacherTitles(), userTitleId);
		vMap.put("titles", userTitleContro);
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = "/adduser", method = RequestMethod.POST)
	public String addUser(User user, UserTitleContro userTitleContro) {
		user.setTitle(new TeacherTitle(Long.parseLong(userTitleContro.getUserTitleId())));
		userService.add(user);
		return redirect + adminBasePath + "adduser";
	}
	/**
	 * 加载所有用户
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/updateuser")
	public void updateUser(Map<String, Object> vMap) {
		vMap.put("users", userService.findAll());
	}
	/**
	 * 密码重置
	 * @param userId
	 * @return
	 */
	@RequestMapping(path = "/setdefaultpwd")
	public String setdefaultPassword(long userId) {
		userService.setDefaultPassword(userId);
		return redirect + adminBasePath + "updateuser"; 
	}
	
	/**
	 * 更新用户基本信息
	 * @param userId 需更新用户ID
	 * @param vMap 封装V层所需用户当前信息，所有职称，用户当前职称
	 */
	@RequestMapping(path = "/updateuserajax")
	public void selectUserForUpdate(long userId, Map<String, Object> vMap) {
		User user = userService.findById(userId);
		String userTitleId = String.valueOf(user.getTitle().getId());
		UserTitleContro userTitle = new UserTitleContro(user, userService.findTeacherTitles(), userTitleId);
		vMap.put("selectuser", userTitle);
	}
	
	/**
	 * 
	 * @param userTitleContro 封装用户所选职称信息
	 * @param user 更新用户信息
	 * @return
	 */
	@RequestMapping(path="/updateuser", method=RequestMethod.POST)
	public String updateUser(UserTitleContro userTitleContro, User user) {
		System.out.println(user.getId());
		System.out.println(user.getPhoneNumber());
		user.setTitle(new TeacherTitle(Long.parseLong(userTitleContro.getUserTitleId())));
		userService.update(user);
		return redirect + adminBasePath + "updateuser";
	}
	
	/**
	 * 修改用户权限，前端为多选框，新增管理员，删除管理员使用相同操作
	 * @param vMap 封装所有用户，管理员用户，前端比较匹配选中管理员用户
	 */
	@RequestMapping(path = "/userauthsetting", method = RequestMethod.GET)
	public void getPermission(Map<String, Object> vMap) {
		// 全部用户
		List<User> users = userService.findAll();
		// 当前管理员
		List<User> adminsList = userService.findAdmins();
		// 将管理员ID转为字符串数组共V层比较
		String[] olderAdminIds = new String[adminsList.size()];
		for (int i = 0; i < olderAdminIds.length; i++) {
			olderAdminIds[i] = String.valueOf(adminsList.get(i).getId());
		}
		UserAdminContro userAdmin = new UserAdminContro(users, olderAdminIds, olderAdminIds);
		vMap.put("useradmin", userAdmin);
	}
	
	
	@RequestMapping(path="/userauthsetting", method=RequestMethod.POST)
	public String setPermission(UserAdminContro userAdmin) {
		// 最新管理员
		List<User> admins = new ArrayList<>();
		for (int i = 0; i < userAdmin.getNewAdminIds().length; i++) {
			User user = new User(Long.parseLong(userAdmin.getNewAdminIds()[i]));
			admins.add(user);
		}
		// 原管理员
		List<User> teachers = new ArrayList<>();
		for (int i = 0; i < userAdmin.getOlderAdminIds().length; i++) {
			User user = new User(Long.parseLong(userAdmin.getOlderAdminIds()[i]));
			teachers.add(user);
		}
		userService.updateAdmins(admins, teachers);
		return redirect + adminBasePath + "userauthsetting";
	}
	/**
	 * 加载教师监考信息
	 * @param vMap
	 */
	@RequestMapping("/userinvisetting")
	public void getUserInvi(Map<String, Object> vMap) {
		List<TeacherInvigilation> teacherInvigilations = userService.findTeacherInvigilations();
		vMap.put("inviusers", teacherInvigilations);
	}
	/**
	 * 
	 * @param ids 列表用户
	 * @param invqs 特殊监考次数
	 * @param checkeds 开启推荐用户IDs
	 * @return
	 */
	@RequestMapping(path = "/userinvisetting", method = RequestMethod.POST)
	public String setUserInviSetting(long[] ids, int[] invqs, Long[] checkeds) {
		List<TeacherInvigilation> teachers = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			TeacherInvigilation teacher = new TeacherInvigilation();
			teacher.setId(ids[i]);
			teacher.setSqecQuantity(invqs[i]);
			// 全部变为false，后面在刷回来
			teacher.setEnabledRecommend(false);
			teachers.add(teacher);
		}
		// 防止checkbox全部没有选中时报空指针
		if (checkeds != null && checkeds.length > 0) {
			boolean enabled = true;
			for (int i = 0; i < ids.length; i++) {
				for (int j = 0; j < checkeds.length; j++) {
					if (ids[i] == checkeds[j]) {
						enabled = true;
						break;
					} else {
						enabled = false;
					}
				}
				if (enabled) {
					teachers.get(i).setEnabledRecommend(true);
				}
			}
		}
		userService.updateTeacherInviSetting(teachers);
		return redirect + adminBasePath + "userinvisetting";
	}
	
	@RequestMapping("/usernotifsetting")
	public  void getUserNotif(Map<String, Object> vMap) {
		vMap.put("notifusers", userService.findAll());
	}
	@RequestMapping(path = "/usernotifsetting", method = RequestMethod.POST)
	public String setUserNotifSetting(long[] ids, Long[] checkeds) {
		List<User> users = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			User user = new User();
			user.setId(ids[i]);
			user.setEnabledMessage(false);
			users.add(user);
		}
		if (checkeds != null && checkeds.length > 0) {
			boolean enabled = true;
			for (int i = 0; i < ids.length; i++) {
				for (int j = 0; j < checkeds.length; j++) {
					if (ids[i] == checkeds[j]) {
						enabled = true;
						break;
					} else {
						enabled = false;
					}
				}
				if (enabled) {
					users.get(i).setEnabledMessage(true);
				}
			}
		}
		
		userService.updateUserNotifSetting(users);
		return redirect + adminBasePath + "usernotifsetting";
	}
	
	/**
	 * 直接加载页面时的通配方法
	 * 不会覆盖显式声明的请求
	 * 仅对一级目录有效
	 * @param viewpath
	 * @return 视图路径
	 */
	@RequestMapping(path = "/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String viewpath) {
		
		return adminBasePath + viewpath;
	}
	
	@RequestMapping(path = "/{root}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String viewpath) {
		
		return adminBasePath + root + "/" + viewpath;
	}
	
}
