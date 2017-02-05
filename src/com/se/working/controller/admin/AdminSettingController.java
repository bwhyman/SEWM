package com.se.working.controller.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.se.working.controller.ControllerMap;
import com.se.working.controller.ControllerMap.AdminSettingRequestMap;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.interceptor.MyAuthorize;
import com.se.working.interceptor.MyAuthorize.Authorize;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.invigilation.service.InviService;
import com.se.working.service.AdminService;
import com.se.working.service.UserService;


/**
 * 管理员操作
 * @author BO
 *
 */
@Controller
@SessionAttributes(value = ControllerMap.USER)
@MyAuthorize(value = {Authorize.SUPERADMIN, Authorize.ADMIN})
public class AdminSettingController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private InviService inviService;
	
	
	/**
	 * 加载职称，默认选择值为讲师
	 * @param model
	 */
	@RequestMapping(path = AdminSettingRequestMap.ADD_USER)
	public void adduser(Model model) {
		model.addAttribute("titles", userService.findTeacherTitles());
	}
	
	/**
	 * 添加用户
	 * @param newUser
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMap.ADD_USER, method = RequestMethod.POST)
	public String addUser(@ModelAttribute(ControllerMap.USER) User user, User newUser, long titleId) {
		newUser.setTitle(new TeacherTitle(titleId));
		newUser.setGroups(user.getGroups());
		adminService.add(newUser);
		
		return ControllerMap.REDIRECT + AdminSettingRequestMap.ADD_USER;
	}
	/**
	 * 加载所有用户
	 * @param model
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMap.UPDATE_USER)
	public void updateUser(@ModelAttribute(ControllerMap.USER) User user, Model model) {
		model.addAttribute("users", adminService.findUsers(user.getGroups().getId()));
		
	}
	/**
	 * 密码重置
	 * @param userId
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMap.SET_DEFAULTPWD ,method =RequestMethod.POST)
	public String setdefaultPassword(long userId) {
		adminService.updateDefaultPassword(userId);
		return ControllerMap.REDIRECT + AdminSettingRequestMap.UPDATE_USER; 
	}
	
	/**
	 * 更新用户基本信息
	 * @param userId 需更新用户ID
	 * @param model 封装V层所需用户当前信息，所有职称
	 */
	@RequestMapping(path = AdminSettingRequestMap.UPDATE_USER_AJAX)
	public void selectUserForUpdate(Model model, long userId) {
		User user = userService.findById(userId);
		model.addAttribute("user", user);
		model.addAttribute("titles", userService.findTeacherTitles());
	}
	
	/**
	 * 
	 * @param userTitleContro 封装用户所选职称信息
	 * @param user 更新用户信息
	 * @return
	 */
	@RequestMapping(path=AdminSettingRequestMap.UPDATE_USER, method=RequestMethod.POST)
	public String updateUser(User user, long titleId) {
		user.setTitle(new TeacherTitle(titleId));
		userService.update(user);
		return ControllerMap.REDIRECT + AdminSettingRequestMap.UPDATE_USER;
	}
	
	/**
	 * 修改用户权限，前端为多选框，新增管理员，删除管理员使用相同操作
	 * @param model 封装所有用户，管理员用户，前端比较匹配选中管理员用户
	 */
	@RequestMapping(path = AdminSettingRequestMap.UPDATE_AUTH, method = RequestMethod.GET)
	public void getPermission(@ModelAttribute(ControllerMap.USER) User user, Model model) {
		// 全部用户
		List<User> users = adminService.findUsers(user.getGroups().getId());
		// 管理员权限ID
		long adminId = UserAuthority.ADMIN;
		model.addAttribute("users", users);
		model.addAttribute("adminId", adminId);
	}
	
	@RequestMapping(path=AdminSettingRequestMap.UPDATE_AUTH, method=RequestMethod.POST)
	public String setPermission(@ModelAttribute(ControllerMap.USER) User user, long[] newAdmins) {
		if (newAdmins != null) {
			adminService.updateAdmins(newAdmins, user.getGroups().getId());
		}
		return ControllerMap.REDIRECT + AdminSettingRequestMap.UPDATE_AUTH;
	}
	/**
	 * 加载教师监考信息
	 * @param model
	 */
	@RequestMapping(path = AdminSettingRequestMap.UPDATE_INVI)
	public void getUserInvi(@ModelAttribute(ControllerMap.USER) User user, Model model) {
		List<TeacherInvigilation> teacherInvigilations = inviService.findTeacherInvigilations(user.getGroups().getId());
		model.addAttribute("inviusers", teacherInvigilations);
	}
	/**
	 * 
	 * @param ids 列表用户
	 * @param invqs 特殊监考次数
	 * @param checkeds 开启推荐用户IDs
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMap.UPDATE_INVI, method = RequestMethod.POST)
	public String setUserInviSetting(@ModelAttribute(ControllerMap.USER) User user, int[] invqs, long[] checkeds) {
		// 防止全部关闭，没有参数报空指针
		if (checkeds == null) {
			checkeds = new long[] {0L};
		}
		adminService.updateTeacherInviSetting(invqs, checkeds, user.getGroups().getId());
		return ControllerMap.REDIRECT + AdminSettingRequestMap.UPDATE_INVI;
	}
	
	/**
	 * 
	 * @param user
	 * @param model
	 */
	@RequestMapping(path = AdminSettingRequestMap.UPDATE_NOTIF)
	public  void getUserNotif(@ModelAttribute(ControllerMap.USER) User user, Model model) {
		model.addAttribute("notifusers", adminService.findUsers(user.getGroups().getId()));
	}
	/**
	 * 所有接收通知用户
	 * @param checkeds 
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMap.UPDATE_NOTIF, method = RequestMethod.POST)
	public String setUserNotifSetting(@ModelAttribute(ControllerMap.USER) User user, long[] checkeds) {
		// 防止全部关闭，没有参数报空指针
		if (checkeds == null) {
			checkeds = new long[] {0L};
		}
		adminService.updateUserNotifSetting(checkeds, user.getGroups().getId());
		return ControllerMap.REDIRECT + AdminSettingRequestMap.UPDATE_NOTIF;
	}
	/**
	 * 静态页面
	 * =====================
	 */
	@RequestMapping(path = AdminSettingRequestMap.USER_MANAGERMENT)
	public void userManagement() {
		
	}
}
