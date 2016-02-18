package com.se.working.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority.UserAuthorityType;
import com.se.working.exception.SEWMException;
import com.se.working.invigilation.entity.Course;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.InvigilationStatusType.InviStatusType;
import com.se.working.invigilation.entity.SpecialInvigilationInfo;
import com.se.working.invigilation.entity.SpecialInvigilationType;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.invigilation.service.InviService;
import com.se.working.invigilation.service.SpecInviService;
import com.se.working.service.AdminService;
import com.se.working.service.UserService;
import com.se.working.util.StringUtils;


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
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private InviService inviService;
	@Autowired
	private SpecInviService specInviService;
	
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
		
		return redirect + adminBasePath + "adduser";
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
		return redirect + adminBasePath + "updateuser"; 
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
		return redirect + adminBasePath + "updateuser";
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
		return redirect + adminBasePath + "userauthsetting";
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
		return redirect + adminBasePath + "userinvisetting";
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
		return redirect + adminBasePath + "usernotifsetting";
	}
	
	/**
	 * 上传课表，
	 * @param uploadFile
	 * @param request
	 * @return
	 * @throws SEWMException 
	 * @throws Exception 
	 */
	@RequestMapping(path = "/importtimetable", method = RequestMethod.POST)
	public String importTimetable(MultipartFile uploadFile, HttpServletRequest request, RedirectAttributes vMap) throws SEWMException, Exception {
		if (uploadFile.isEmpty()) {
			throw new SEWMException("文件错误");
		}
		
		String fileName = uploadFile.getOriginalFilename();
		// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
		if (!(StringUtils.getFilenameExtension(fileName).equals("xls") || 
				StringUtils.getFilenameExtension(fileName).equals("xlsx"))) {
			throw new SEWMException("不是Excel表格文件");
		}
		
		String path = request.getServletContext().getRealPath("/");
		path = path + "\\WEB-INF\\JSP\\upload\\timetable\\";
		File directory = new File(path);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		try {
			File file = new File(path + fileName);
			uploadFile.transferTo(file);
			List<Course> courses = inviService.importTimetable(file);
			vMap.addFlashAttribute("courses", courses);
		} finally {
			uploadFile = null;
		}
		return redirect + adminBasePath + "importtimetable";
	}
	
	
	/**
	 * 导入监考信息
	 * @param uploadFile
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws SEWMException 
	 */
	@RequestMapping(path = "/importinvi", method = RequestMethod.POST)
	public String importInvigilation(MultipartFile uploadFile, HttpServletRequest request, RedirectAttributes vMap) throws SEWMException, Exception {

		if (uploadFile.isEmpty()) {
			throw new SEWMException("文件错误");
		}
		String fileName = uploadFile.getOriginalFilename();
		
		if (!(StringUtils.getFilenameExtension(fileName).equals("xls") || 
				StringUtils.getFilenameExtension(fileName).equals("xlsx"))) {
			throw new SEWMException("不是Excel表格文件");
		}
		String path = request.getServletContext().getRealPath("/");
		path = path + "\\WEB-INF\\JSP\\upload\\invi\\";
		File directory = new File(path);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		try {
			File file = new File(path + fileName);
			uploadFile.transferTo(file);
			List<InvigilationInfo> infos = inviService.importInvi(file);
			// 反序
			Collections.reverse(infos);
			vMap.addFlashAttribute("infos", infos);
		} finally {
			uploadFile = null;
		}
		
		return redirect + adminBasePath + "importinvi";
	}
	/**
	 * 基于选择列出所有监考信息
	 * @param vMap
	 */
	@RequestMapping(path = "/list/{invitype}", method = RequestMethod.GET)
	public String listUnassignInivs(@PathVariable String invitype, Map<String, Object> vMap) {
		List<InvigilationInfo> infos = new ArrayList<>();
		switch (invitype) {
		case "unassinvi":
			infos = inviService.findInviInfosByTypeId(InviStatusType.UNASSIGNED);
			break;
		case "assinvi":
			infos = inviService.findInviInfosByTypeId(InviStatusType.ASSIGNED);
			break;
		case "done":
			infos = inviService.findInviInfosByTypeId(InviStatusType.DONE);
			break;
		case "all":
			infos = inviService.findAllInviInfos();
			break;
		}
		
		// 反序
		Collections.reverse(infos);
		vMap.put("infos", infos);
		return adminBasePath + "listinviinfo";
	}
	
	
	/**
	 * 载入监考分配
	 * @param inviId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/assigninvi/{inviId}", method = RequestMethod.GET)
	public String assignInvi(@PathVariable long inviId, Map<String, Object> vMap) {
		
		// 分配监考详细信息
		InvigilationInfo inviInfo = inviService.findInviInfo(inviId);
		if (inviInfo == null) {
			return adminBasePath + "error";
		}
		// 冲突
		Map<Long, String> conflicts = inviService.findConflicts(inviId);
		// 推荐关闭
		Map<Long, String> urcds = inviService.findUnRCDs(inviId);
		// 推荐，所有非推荐关闭与非通知关闭，无时间冲突判断
		Map<Long, String> rcds = inviService.findRCDs(inviId);
		// 通知关闭
		List<User> disUsers = adminService.findDisabledUsers();
		
		
		
		// 从冲突中移除通知关闭
		for (User user : disUsers) {	
			if (conflicts.get(user.getId()) != null) {
				conflicts.remove(user.getId());
			}
			// 从推荐关闭中移除通知关闭
			if (urcds.get(user.getId()) != null) {
				urcds.remove(user.getId());
			}
		}
		
		// 推荐关闭中教师时间冲突，则将其放入冲突列表
		Set<Long> conflictsSet = conflicts.keySet();
		for (Long long1 : conflictsSet) {
			if (urcds.containsKey(long1)) {
				urcds.remove(long1);
			}
		}
		// 从推荐中剔除冲突
		for (Long long1 : conflictsSet) {
			if (rcds.containsKey(long1)) {
				rcds.remove(long1);
			}
		}
		
		// 监考信息的原分配，设置为默认推荐
		Map<Long, String> olders = null;
		if (inviInfo.getInvigilations().size() > 0) {
			olders = new LinkedHashMap<>();
			for (Invigilation i : inviInfo.getInvigilations()) {
				if (conflicts.get(i.getTeacher().getId()) != null) {
					olders.put(i.getTeacher().getId(), conflicts.get(i.getTeacher().getId()));
					conflicts.remove(i.getTeacher().getId());
				}
			}
		}

		vMap.put("olders", olders);
		vMap.put("inviInfo", inviInfo);
		vMap.put("conflicts", conflicts);
		vMap.put("urcds", urcds);
		vMap.put("rcds", rcds);
		vMap.put("disusers", disUsers);
		return adminBasePath + "assigninvi";
	}
	/**
	 * 提交监考安排
	 * @param inviInfoId
	 * @param checkeds
	 * @return
	 */
	@RequestMapping(path = "/assigninvi", method = RequestMethod.POST)
	public String assignInvi(long inviInfoId, long[] checkeds) {
		inviService.addinvi(inviInfoId, checkeds);
		return redirect + adminBasePath + "list/unassinvi";
	}
	
	/**
	 * 监考信息
	 * @param infoId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/updateinviinfo/{infoId}", method = RequestMethod.GET)
	public String updateInviInfo(@PathVariable long infoId, Map<String, Object> vMap) {
		InvigilationInfo info = inviService.findInviInfo(infoId);
		if (info == null) {
			return adminBasePath + "error";
		}
		vMap.put("info", info);
		return adminBasePath + "updateinviinfo";
	}
	
	/**
	 * 更新监考信息
	 * @param info
	 * @param date
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping(path = "/updateinviinfo", method = RequestMethod.POST)
	public String updateInviInfo(InvigilationInfo info, String date, String stime, String etime) {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		try {
			start.setTime(sf.parse(date + " " + stime));
			end.setTime(sf.parse(date + " " + etime));
			info.setStartTime(start);
			info.setEndTime(end);
			inviService.updateInviInfo(info);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return redirect + adminBasePath + "assigninvi/" + info.getId();
	}
	
	/**
	 * 载入添加特殊监考信息
	 * @param vMap
	 */
	@RequestMapping("/addspecinviinfo")
	public void addSpecInviInfo(Map<String, Object> vMap) {
		List<SpecialInvigilationType> specTypes = specInviService.findSpecTypes();
		List<TeacherInvigilation> teachers = specInviService.findTeacherOrderBySpecNumber();
		vMap.put("types", specTypes);
		vMap.put("teachers", teachers);
	}
	
	@RequestMapping(path = "/addspecinviinfo", method = RequestMethod.POST)
	public String addSpecInviInfo(long typeId, String date, String location, String comment, long[] checkeds) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar dateTime = Calendar.getInstance();
		SpecialInvigilationInfo info = new SpecialInvigilationInfo();
		try {
			dateTime.setTime(sf.parse(date));
			info.setDateTime(dateTime);
			info.setComment(comment);
			info.setLocation(location);
			info.setSpecType(new SpecialInvigilationType(typeId));
			
			specInviService.addSpecInfo(info, checkeds);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redirect + adminBasePath + "addspecinviinfo";
	}
	@RequestMapping(path = "/delinviinfo", method = RequestMethod.POST)
	public String delInviInfo(long infoinviid) {
		inviService.deleteInviInfo(infoinviid);
		return redirect + adminBasePath + "list/unassinvi";
	}
	
	@RequestMapping(path = "/addinviinfo", method = RequestMethod.POST)
	public String addInviInfo(String date,String stime, String etime, InvigilationInfo info) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		try {
			startTime.setTime(sf.parse(date + " " + stime));
			endTime.setTime(sf.parse(date + " " + etime));
			info.setStartTime(startTime);
			info.setEndTime(endTime);
			long inviInfoId = inviService.addInviInfo(info);
			return redirect + adminBasePath + "assigninvi/" + inviInfoId;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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
