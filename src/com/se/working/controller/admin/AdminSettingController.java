package com.se.working.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.se.working.controller.ControllerMapping;
import com.se.working.controller.ControllerMapping.AdminSettingRequestMapping;
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
 * 
 * @author BO
 *
 */
@Controller
@SessionAttributes(value = ControllerMapping.USER)
@MyAuthorize(value = { Authorize.SUPERADMIN, Authorize.ADMIN })
public class AdminSettingController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private InviService inviService;

	/**
	 * 加载职称，默认选择值为讲师
	 * 
	 * @param model
	 */
	@RequestMapping(path = AdminSettingRequestMapping.ADD_USER)
	public void adduser(Model model) {
		model.addAttribute("titles", userService.getTeacherTitles());
	}

	/**
	 * 添加用户，Spring bug？不能直接在方法注入newUser对象，与session中的user混淆？？
	 * 
	 * @param newUser
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMapping.ADD_USER, method = RequestMethod.POST)
	public String addUse(String name, String employeeNumber, String phoneNumber, String introduction, long titleId,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ControllerMapping.USER);
		User newUser = new User();
		newUser.setName(name);
		newUser.setEmployeeNumber(employeeNumber);
		newUser.setIntroduction(introduction);
		newUser.setTitle(new TeacherTitle(titleId));

		newUser.setGroups(user.getGroups());

		adminService.addUser(newUser);

		return ControllerMapping.REDIRECT + AdminSettingRequestMapping.ADD_USER;
	}

	/**
	 * 加载所有用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_USER)
	public void updateUser(@ModelAttribute(ControllerMapping.USER) User user, Model model) {
		model.addAttribute("users", adminService.getUsers(user.getGroups().getId()));

	}

	/**
	 * 密码重置
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMapping.SET_DEFAULTPWD, method = RequestMethod.POST)
	public String setdefaultPassword(long userId) {
		adminService.updateDefaultPassword(userId);
		return ControllerMapping.REDIRECT + AdminSettingRequestMapping.UPDATE_USER;
	}

	/**
	 * 更新用户基本信息
	 * 
	 * @param userId
	 *            需更新用户ID
	 * @param model
	 *            封装V层所需用户当前信息，所有职称
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_USER_AJAX)
	public void selectUserForUpdate(Model model, long userId) {
		User user = userService.getUser(userId);
		model.addAttribute("user", user);
		model.addAttribute("titles", userService.getTeacherTitles());
	}

	/**
	 * 
	 * @param userTitleContro
	 *            封装用户所选职称信息
	 * @param user
	 *            更新用户信息
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_USER, method = RequestMethod.POST)
	public String updateUser(User user, long titleId) {
		user.setTitle(new TeacherTitle(titleId));
		userService.updateUser(user);
		return ControllerMapping.REDIRECT + AdminSettingRequestMapping.UPDATE_USER;
	}

	/**
	 * 修改用户权限，前端为多选框，新增管理员，删除管理员使用相同操作
	 * 
	 * @param model
	 *            封装所有用户，管理员用户，前端比较匹配选中管理员用户
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_AUTH, method = RequestMethod.GET)
	public void getPermission(@ModelAttribute(ControllerMapping.USER) User user, Model model) {
		// 全部用户
		List<User> users = adminService.getUsers(user.getGroups().getId());
		// 管理员权限ID
		long adminId = UserAuthority.ADMIN;
		model.addAttribute("users", users);
		model.addAttribute("adminId", adminId);
	}

	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_AUTH, method = RequestMethod.POST)
	public String setPermission(@ModelAttribute(ControllerMapping.USER) User user, long[] newAdmins) {
		if (newAdmins != null) {
			adminService.updateAdmins(newAdmins, user.getGroups().getId());
		}
		return ControllerMapping.REDIRECT + AdminSettingRequestMapping.UPDATE_AUTH;
	}

	/**
	 * 加载教师监考信息
	 * 
	 * @param model
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_INVI)
	public void getUserInvi(@ModelAttribute(ControllerMapping.USER) User user, Model model) {
		List<TeacherInvigilation> teacherInvigilations = inviService.getTeacherInvigilations(user.getGroups().getId());
		model.addAttribute("inviusers", teacherInvigilations);
	}

	/**
	 * 
	 * @param ids
	 *            列表用户
	 * @param invqs
	 *            特殊监考次数
	 * @param checkeds
	 *            开启推荐用户IDs
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_INVI, method = RequestMethod.POST)
	public String setUserInviSetting(@ModelAttribute(ControllerMapping.USER) User user, int[] invqs, long[] checkeds) {
		// 防止全部关闭，没有参数报空指针
		if (checkeds == null) {
			checkeds = new long[] { 0L };
		}
		adminService.updateTeacherInviSetting(invqs, checkeds, user.getGroups().getId());
		return ControllerMapping.REDIRECT + AdminSettingRequestMapping.UPDATE_INVI;
	}

	/**
	 * 
	 * @param user
	 * @param model
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_NOTIF)
	public void getUserNotif(@ModelAttribute(ControllerMapping.USER) User user, Model model) {
		model.addAttribute("notifusers", adminService.getUsers(user.getGroups().getId()));
	}

	/**
	 * 所有接收通知用户
	 * 
	 * @param checkeds
	 * @return
	 */
	@RequestMapping(path = AdminSettingRequestMapping.UPDATE_NOTIF, method = RequestMethod.POST)
	public String setUserNotifSetting(@ModelAttribute(ControllerMapping.USER) User user, long[] checkeds) {
		// 防止全部关闭，没有参数报空指针
		if (checkeds == null) {
			checkeds = new long[] { 0L };
		}
		adminService.updateUserNotifSetting(checkeds, user.getGroups().getId());
		return ControllerMapping.REDIRECT + AdminSettingRequestMapping.UPDATE_NOTIF;
	}

	/**
	 * 静态页面 =====================
	 */
	@RequestMapping(path = AdminSettingRequestMapping.USER_MANAGERMENT)
	public void userManagement() {

	}
}
