package com.se.working.controller.admin;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.controller.ControllerMapping;
import com.se.working.controller.ControllerMapping.AdminInviRequestMapping;
import com.se.working.controller.ControllerMapping.AdminInviViewMapping;
import com.se.working.controller.ControllerMapping.UserInviRequestMapping;
import com.se.working.entity.User;
import com.se.working.exception.SEWMException;
import com.se.working.interceptor.MyAuthorize;
import com.se.working.interceptor.MyAuthorize.Authorize;
import com.se.working.invigilation.entity.Course;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.SpecialInvigilationInfo;
import com.se.working.invigilation.entity.SpecialInvigilationType;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.invigilation.service.InviService;
import com.se.working.invigilation.service.SpecInviService;
import com.se.working.service.AdminService;
import com.se.working.util.DateUtils;
import com.se.working.util.StringUtils;

@Controller
@SessionAttributes(value = ControllerMapping.USER)
@MyAuthorize(value = {Authorize.SUPERADMIN, Authorize.ADMIN})
public class AdminInviController {
	private static Logger logger = LogManager.getLogger(AdminInviController.class);
	@Autowired
	private AdminService adminService;
	@Autowired
	private InviService inviService;
	@Autowired
	private SpecInviService specInviService;

	/**
	 * 上传课表，
	 * 
	 * @param uploadFile
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws SEWMException
	 * @throws Exception
	 */
	@RequestMapping(path = AdminInviRequestMapping.IMPORT_TIMETABLE, method = RequestMethod.POST)
	public String importTimetable(@ModelAttribute(ControllerMapping.USER) User user, MultipartFile uploadFile,
			RedirectAttributes vMap) throws IOException {
		if (uploadFile.isEmpty()) {
			throw new SEWMException("上传文件为空");
		}
		String fileName = uploadFile.getOriginalFilename();
		// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
		if (!(StringUtils.getFilenameExtension(fileName).equals("xls")
				|| StringUtils.getFilenameExtension(fileName).equals("xlsx"))) {
			throw new SEWMException("不是Excel表格文件");
		}

		List<Course> courses = inviService.addTimetable(uploadFile, user.getGroups().getId());
		uploadFile = null;
		vMap.addFlashAttribute("courses", courses);
		return ControllerMapping.REDIRECT + AdminInviRequestMapping.IMPORT_TIMETABLE;
	}

	/**
	 * 导入监考信息
	 * 
	 * @param uploadFile
	 * @param request
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.IMPORT_INVIINFOS, method = RequestMethod.POST)
	public String importInvigilation(@ModelAttribute(ControllerMapping.USER) User user, MultipartFile uploadFile,
			boolean checked, RedirectAttributes vMap, HttpSession session) {
		if (uploadFile.isEmpty()) {
			throw new SEWMException("上传文件为空");
		}
		String fileName = uploadFile.getOriginalFilename();

		if (!(StringUtils.getFilenameExtension(fileName).equals("xls")
				|| StringUtils.getFilenameExtension(fileName).equals("xlsx"))) {
			throw new SEWMException("不是Excel表格文件");
		}
		long groupId = user.getGroups().getId();
		List<InvigilationInfo> infos = inviService.addInviInfos(uploadFile, checked, groupId);
		vMap.addFlashAttribute("infos", infos);
		// 置入@SessionAttributes
		session.setAttribute("saveinfostemp", infos);
		uploadFile = null;
		return ControllerMapping.REDIRECT + AdminInviRequestMapping.IMPORT_INVIINFOS;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(path = AdminInviRequestMapping.SAVE_INVIINFOS, method = RequestMethod.POST)
	public String saveInviInfos(@ModelAttribute(ControllerMapping.USER) User user, HttpSession session) {
		List<InvigilationInfo> infos = (List<InvigilationInfo>) session.getAttribute("saveinfostemp");
		if (infos != null) {
			inviService.addInviInfos(infos, user.getGroups().getId());
		}
		// 清空
		session.removeAttribute("saveinfostemp");
		return ControllerMapping.REDIRECT + UserInviRequestMapping.LIST_INVIINFO_UNASSINVI;
	}

	/**
	 * 载入监考分配
	 * 
	 * @param inviId
	 * @param model
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.ASSIGN_INVI_INVIID, method = RequestMethod.GET)
	public String assignInvi(@ModelAttribute(ControllerMapping.USER) User user, @PathVariable long inviid, Model model) {
		long start = System.nanoTime();
		long groupId = user.getGroups().getId();
		// 分配监考详细信息
		InvigilationInfo inviInfo = inviService.getInviInfo(inviid, user.getGroups().getId());
		if (inviInfo == null) {
			return ControllerMapping.ERROR;
		}
		// 冲突
		Map<Long, String> conflicts = inviService.getConflicts(inviid, groupId);
		// 推荐关闭
		Map<Long, String> urcds = inviService.getUnRCDs(inviid, groupId);
		// 推荐，所有非推荐关闭与非通知关闭，无时间冲突判断
		Map<Long, String> rcds = inviService.getRCDs(inviid, groupId);
		// 通知关闭
		List<User> disUsers = adminService.getUsers(false, groupId);

		// 从冲突中移除通知关闭
		for (User u : disUsers) {
			if (conflicts.get(u.getId()) != null) {
				conflicts.remove(u.getId());
			}
			// 从推荐关闭中移除通知关闭
			if (urcds.get(u.getId()) != null) {
				urcds.remove(u.getId());
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
		// 返回周数
		int week = DateUtils.getWeekRelativeBaseDate(inviInfo.getStartTime());
		model.addAttribute("olders", olders);
		model.addAttribute("inviInfo", inviInfo);
		model.addAttribute("conflicts", conflicts);
		model.addAttribute("urcds", urcds);
		model.addAttribute("rcds", rcds);
		model.addAttribute("disusers", disUsers);
		model.addAttribute("week", week);
		
		logger.info(System.nanoTime() - start);
		
		return AdminInviViewMapping.ASSIGN_INVI;
	}

	/**
	 * 提交监考安排，返回至发送监考通知
	 * 
	 * @param inviInfoId
	 * @param checkeds
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.ASSIGN_INVI, method = RequestMethod.POST)
	public String assignInvi(long inviInfoId, long[] checkeds) {
		if (checkeds == null) {
			throw new SEWMException("没有分配监考教师");
		}
		InvigilationInfo info = inviService.addinvi(inviInfoId, checkeds);

		return ControllerMapping.REDIRECT + AdminInviRequestMapping.SEND_INVI_MESSAGE + "/" + info.getId();
	}

	/**
	 * 加载发送短信通知监考信息
	 * 
	 * @param user
	 * @param inviinfoid
	 * @param model
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.SEND_INVI_MESSAGE_INVIID, method = RequestMethod.GET)
	public String sendInviMessage(@ModelAttribute(ControllerMapping.USER) User user, @PathVariable long inviinfoid,
			Model model) {
		InvigilationInfo info = inviService.getInviInfo(inviinfoid, user.getGroups().getId());
		if (info == null) {
			return ControllerMapping.ERROR;
		}
		model.addAttribute("invis", info.getInvigilations());
		model.addAttribute("inviinfoid", inviinfoid);
		return AdminInviViewMapping.SEND_INVI_MESSAGE;
	}

	/**
	 * 发送监考短信通知
	 * 
	 * @param inviids
	 * @param inviinfoid
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.SEND_INVI_MESSAGE, method = RequestMethod.POST)
	public String sendIviMessage(long[] inviids, long inviinfoid, RedirectAttributes vMap) {
		if (inviids != null) {
			List<String> results = inviService.sendInviNoticeMessages(inviids);
			vMap.addFlashAttribute("results", results);
		} else {
			throw new SEWMException("没有选择发送短信教师！");
		}

		return ControllerMapping.REDIRECT + AdminInviRequestMapping.SEND_INVI_MESSAGE + "/" + inviinfoid;
	}

	/**
	 * 加载监考信息更新
	 * 
	 * @param user
	 * @param infoid
	 * @param model
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.UPDATE_INVI_INFO_INVIID, method = RequestMethod.GET)
	public String updateInviInfo(@ModelAttribute(ControllerMapping.USER) User user, @PathVariable long infoid,
			Model model) {
		InvigilationInfo info = inviService.getInviInfo(infoid, user.getGroups().getId());
		if (info == null) {
			return ControllerMapping.ERROR;
		}
		model.addAttribute("info", info);
		return AdminInviViewMapping.UPDATE_INVI_INFO;
	}

	/**
	 * 更新监考信息
	 * 
	 * @param info
	 * @param date
	 * @param stime
	 * @param etime
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.UPDATE_INVI_INFO, method = RequestMethod.POST)
	public String updateInviInfo(InvigilationInfo info, String date, String stime, String etime) {

		Calendar start = DateUtils.getCalendar(date + " " + stime);
		Calendar end = DateUtils.getCalendar(date + " " + etime);
		info.setStartTime(start);
		info.setEndTime(end);
		inviService.updateInviInfo(info);
		return ControllerMapping.REDIRECT + AdminInviRequestMapping.ASSIGN_INVI + "/" + info.getId();
	}

	/**
	 * 为便于与家属等共同监考，提供将单一监考信息分解功能，允许同一监考，1人分配2次
	 * 
	 * @param inviinfoid
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.SPLIT_INVIINFO, method = RequestMethod.POST)
	public String splitInviInfo(long inviinfoid) {
		inviService.splitInviInfo(inviinfoid);
		return ControllerMapping.REDIRECT + UserInviRequestMapping.LIST_INVIINFO_UNASSINVI;
	}

	/**
	 * 删除监考信息
	 * 
	 * @param infoinviid
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.DEL_INVIINFO, method = RequestMethod.POST)
	public String delInviInfo(long infoinviid) {
		inviService.deleteInviInfo(infoinviid);
		return ControllerMapping.REDIRECT + UserInviRequestMapping.LIST_INVIINFO_UNASSINVI;
	}

	/**
	 * 添加监考信息
	 * 
	 * @param date
	 * @param stime
	 * @param etime
	 * @param info
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.ADD_INVIINFO, method = RequestMethod.POST)
	public String addInviInfo(@ModelAttribute(ControllerMapping.USER) User user, String date, String stime, String etime,
			InvigilationInfo info) {
		Calendar startTime = DateUtils.getCalendar(date + " " + stime);
		Calendar endTime = DateUtils.getCalendar(date + " " + etime);
		info.setStartTime(startTime);
		info.setEndTime(endTime);
		info.setGroups(user.getGroups());
		long inviInfoId = inviService.addInviInfo(info);
		return ControllerMapping.REDIRECT + AdminInviRequestMapping.ASSIGN_INVI + "/" + inviInfoId;

	}

	/**
	 * 载入添加特殊监考信息
	 * 
	 * @param model
	 */
	@RequestMapping("/addspecinviinfo")
	public void addSpecInviInfo(Model model) {
		List<SpecialInvigilationType> specTypes = specInviService.findSpecTypes();
		List<TeacherInvigilation> teachers = specInviService.findTeacherOrderBySpecNumber();
		model.addAttribute("types", specTypes);
		model.addAttribute("teachers", teachers);
	}

	/**
	 * 添加特殊监考信息及分配
	 * 
	 * @param typeId
	 * @param date
	 * @param location
	 * @param comment
	 * @param checkeds
	 * @return
	 */
	@RequestMapping(path = "/addspecinviinfo", method = RequestMethod.POST)
	public String addSpecInviInfo(long typeId, String date, String location, String comment, long[] checkeds) {

		SpecialInvigilationInfo info = new SpecialInvigilationInfo();

		info.setDateTime(DateUtils.getCalendar(date));
		info.setComment(comment);
		info.setLocation(location);
		info.setSpecType(new SpecialInvigilationType(typeId));

		specInviService.addSpecInfo(info, checkeds);

		return ControllerMapping.REDIRECT + "addspecinviinfo";
	}

	/**
	 * 加载导入课程任务页面
	 * 
	 * @param vMap
	 */
	/*
	 * @RequestMapping("/importtimetabletask") public void
	 * importTimetableTask(Map<String, Object> vMap) { // 当前所有课程信息
	 * vMap.put("teachers", inviService.findTeacherInvigilations()); //
	 * 所有已关闭文件任务 vMap.put("tasks",
	 * taskService.findFileTasksByStatusId(FileTaskStatus.CLOSED)); }
	 */
	/**
	 * 使用课表任务导入课表
	 * 
	 * @param filetaskId
	 * @throws SEWMException
	 */
	/*
	 * @RequestMapping(path = "/importtimetabletask", method =
	 * RequestMethod.POST) public String importTimetableTask(long filetaskId,
	 * RedirectAttributes vMap) { inviService.importTimetableTask(filetaskId);
	 * 
	 * vMap.addFlashAttribute("teachers",
	 * inviService.findTeacherInvigilations()); return redirect + basePath +
	 * "importtimetabletask"; }
	 */

	/**
	 * 发送监考提醒
	 * 
	 * @return
	 */
	/*
	 * @RequestMapping(path = "/sendinviremind", method = RequestMethod.POST)
	 * public String sendInviRemind() { inviService.sendInviRemind(); return
	 * redirect + basePath + "invimanagement"; }
	 */

	/**
	 * 将从学期初至今的已分配监考置为完成状态
	 * 
	 * @return
	 */
	@RequestMapping(path = AdminInviRequestMapping.SET_INVIINFO_DONE, method = RequestMethod.POST)
	public String setInviInfoDone() {
		inviService.setInviInfoDone();
		return ControllerMapping.REDIRECT + AdminInviViewMapping.INVI_MANAGEMENT;
	}
	
	/**
	 * 
	 * ==============================
	 */
	
	@RequestMapping(value = AdminInviRequestMapping.ADD_INVIINFO)
	public void addInviInfo() {
		
	}
	@RequestMapping(value = AdminInviRequestMapping.ADD_SPECINVIINFO)
	public void addSpecInviInfo() {}
	
	@RequestMapping(value = AdminInviRequestMapping.IMPORT_INVIINFOS)
	public void importInviInfos() {}
	
	@RequestMapping(value = AdminInviRequestMapping.IMPORT_TIMETABLE)
	public void importTimetable() {}
	
	@RequestMapping(path = AdminInviRequestMapping.INVI_MANAGEMENT)
	public void getInviManagement() {
		
	}
}
