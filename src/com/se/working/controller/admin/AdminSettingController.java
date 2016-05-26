package com.se.working.controller.admin;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority.UserAuthorityType;
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
@RequestMapping("/admin/setting/")
public class AdminSettingController {
	/**
	 *  管理员根目录
	 *  所有管理员权限操作以此为根
	 *  单态，无需设为常量
	 */
	private String basePath = "/admin/setting/";
	private String redirect = "redirect:";
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private InviService inviService;
	
	
	/**
	 * 加载职称，默认选择值为讲师
	 * @param vMap
	 */
	@RequestMapping("/adduser")
	public void adduser(Map<String, Object> vMap) {
		vMap.put("titles", userService.findTeacherTitles());
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = "/adduser", method = RequestMethod.POST)
	public String addUser(User user, long titleId) {
		user.setTitle(new TeacherTitle(titleId));
		adminService.add(user);
		
		return redirect + "adduser";
	}
	/**
	 * 加载所有用户
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/updateuser")
	public void updateUser(Map<String, Object> vMap) {
		vMap.put("users", adminService.findAll());
	}
	/**
	 * 密码重置
	 * @param userId
	 * @return
	 */
	@RequestMapping(path = "/setdefaultpwd")
	public String setdefaultPassword(long userId) {
		adminService.updateDefaultPassword(userId);
		return redirect + "updateuser"; 
	}
	
	/**
	 * 更新用户基本信息
	 * @param userId 需更新用户ID
	 * @param vMap 封装V层所需用户当前信息，所有职称
	 */
	@RequestMapping(path = "/updateuserajax")
	public void selectUserForUpdate(Map<String, Object> vMap, long userId) {
		User user = userService.findById(userId);
		vMap.put("user", user);
		vMap.put("titles", userService.findTeacherTitles());
	}
	
	/**
	 * 
	 * @param userTitleContro 封装用户所选职称信息
	 * @param user 更新用户信息
	 * @return
	 */
	@RequestMapping(path="/updateuser", method=RequestMethod.POST)
	public String updateUser(User user, long titleId) {
		user.setTitle(new TeacherTitle(titleId));
		userService.update(user);
		return redirect + "updateuser";
	}
	
	/**
	 * 修改用户权限，前端为多选框，新增管理员，删除管理员使用相同操作
	 * @param vMap 封装所有用户，管理员用户，前端比较匹配选中管理员用户
	 */
	@RequestMapping(path = "/userauthsetting", method = RequestMethod.GET)
	public void getPermission(Map<String, Object> vMap) {
		// 全部用户
		List<User> users = adminService.findAll();
		// 管理员权限ID
		long adminId = UserAuthorityType.ADAMIN;
		vMap.put("users", users);
		vMap.put("adminId", adminId);
	}
	
	
	@RequestMapping(path="/userauthsetting", method=RequestMethod.POST)
	public String setPermission(long[] newAdmins) {
		if (newAdmins != null) {
			adminService.updateAdmins(newAdmins);
		}
		return redirect + "userauthsetting";
	}
	/**
	 * 加载教师监考信息
	 * @param vMap
	 */
	@RequestMapping("/userinvisetting")
	public void getUserInvi(Map<String, Object> vMap) {
		List<TeacherInvigilation> teacherInvigilations = inviService.findTeacherInvigilations();
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
	public String setUserInviSetting(int[] invqs, long[] checkeds) {
		// 防止全部关闭，没有参数报空指针
		if (checkeds == null) {
			checkeds = new long[] {0L};
		}
		adminService.updateTeacherInviSetting(invqs, checkeds);
		return redirect + "userinvisetting";
	}
	
	@RequestMapping("/usernotifsetting")
	public  void getUserNotif(Map<String, Object> vMap) {
		vMap.put("notifusers", adminService.findAll());
	}
	/**
	 * 所有接收通知用户
	 * @param checkeds 
	 * @return
	 */
	@RequestMapping(path = "/usernotifsetting", method = RequestMethod.POST)
	public String setUserNotifSetting(long[] checkeds) {
		// 防止全部关闭，没有参数报空指针
		if (checkeds == null) {
			checkeds = new long[] {0L};
		}
		adminService.updateUserNotifSetting(checkeds);
		return redirect + "usernotifsetting";
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
		
		return basePath + viewpath;
	}
	
	@RequestMapping(path = "/{root}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String viewpath) {
		
		return basePath + root + "/" + viewpath;
	}
	
}
