package com.se.working.controller.admin;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.entity.User;
import com.se.working.exception.SEWMException;
import com.se.working.invigilation.entity.Course;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.SpecialInvigilationInfo;
import com.se.working.invigilation.entity.SpecialInvigilationType;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.invigilation.service.InviService;
import com.se.working.invigilation.service.SpecInviService;
import com.se.working.service.AdminService;
import com.se.working.task.entity.FileTaskStatus;
import com.se.working.task.service.TaskService;
import com.se.working.util.DateUtils;
import com.se.working.util.StringUtils;

@Controller
@RequestMapping("/admin/invi/")
@SessionAttributes("simportinviinfos")
public class AdminInviController {

	private String basePath = "/admin/invi/";
	private String redirect = "redirect:";

	@Autowired
	private AdminService adminService;
	@Autowired
	private InviService inviService;
	@Autowired
	private SpecInviService specInviService;
	@Autowired
	private TaskService taskService;

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
	@RequestMapping(path = "/importtimetable", method = RequestMethod.POST)
	public String importTimetable(MultipartFile uploadFile, RedirectAttributes vMap) throws IOException {
		if (uploadFile.isEmpty()) {
			throw new SEWMException("上传文件为空");
		}
		String fileName = uploadFile.getOriginalFilename();
		// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
		if (!(StringUtils.getFilenameExtension(fileName).equals("xls")
				|| StringUtils.getFilenameExtension(fileName).equals("xlsx"))) {
			throw new SEWMException("不是Excel表格文件");
		}

		List<Course> courses = inviService.importTimetable(uploadFile);
		uploadFile = null;
		vMap.addFlashAttribute("courses", courses);
		return redirect + "importtimetable";
	}

	/**
	 * 导入监考信息
	 * 
	 * @param uploadFile
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/importinviinfos", method = RequestMethod.POST)
	public String importInvigilation(MultipartFile uploadFile, boolean checked, RedirectAttributes vMap) {
		if (uploadFile.isEmpty()) {
			throw new SEWMException("上传文件为空");
		}
		String fileName = uploadFile.getOriginalFilename();

		if (!(StringUtils.getFilenameExtension(fileName).equals("xls")
				|| StringUtils.getFilenameExtension(fileName).equals("xlsx"))) {
			throw new SEWMException("不是Excel表格文件");
		}
		List<InvigilationInfo> infos = inviService.importInviInfos(uploadFile, checked);

		vMap.addFlashAttribute("infos", infos);
		// 置入@SessionAttributes
		vMap.addFlashAttribute("simportinviinfos", infos);
		uploadFile = null;
		return redirect + "importinvi";
	}
	@RequestMapping(path = "/saveinviinfos", method = RequestMethod.POST)
	public String saveInviInfos(@ModelAttribute("simportinviinfos") List<InvigilationInfo> infos, SessionStatus status) {
		if (infos != null) {
			inviService.addInviInfos(infos);
		}
		
		// 清空
		status.setComplete();
		return redirect + "/invi/listinviinfo/unassinvi";
	}

	/**
	 * 载入监考分配
	 * 
	 * @param inviId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/assigninvi/{inviId}", method = RequestMethod.GET)
	public String assignInvi(@PathVariable long inviId, Map<String, Object> vMap) {

		// 分配监考详细信息
		InvigilationInfo inviInfo = inviService.findInviInfo(inviId);
		if (inviInfo == null) {
			return basePath + "error";
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
		// 返回周数
		int week = DateUtils.getWeekRelativeBaseDate(inviInfo.getStartTime());
		vMap.put("olders", olders);
		vMap.put("inviInfo", inviInfo);
		vMap.put("conflicts", conflicts);
		vMap.put("urcds", urcds);
		vMap.put("rcds", rcds);
		vMap.put("disusers", disUsers);
		vMap.put("week", week);
		return basePath + "assigninvi";
	}

	/**
	 * 提交监考安排，返回至发送监考通知
	 * 
	 * @param inviInfoId
	 * @param checkeds
	 * @return
	 */
	@RequestMapping(path = "/assigninvi", method = RequestMethod.POST)
	public String assignInvi(long inviInfoId, long[] checkeds) {
		if (checkeds == null) {
			throw new SEWMException("没有分配监考教师");
		}
		InvigilationInfo info = inviService.addinvi(inviInfoId, checkeds);

		return redirect + basePath + "sendinvimessage/" + info.getId();
	}

	/**
	 * 加载发送短信通知监考信息
	 * 
	 * @param inviinfoid
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/sendinvimessage/{inviinfoid}", method = RequestMethod.GET)
	public String sendInviMessage(@PathVariable long inviinfoid, Map<String, Object> vMap) {
		InvigilationInfo info = inviService.findInviInfo(inviinfoid);
		vMap.put("invis", info.getInvigilations());
		vMap.put("inviinfoid", inviinfoid);
		return basePath + "sendinvimessage";
	}

	/**
	 * 发送监考短信通知
	 * 
	 * @param inviids
	 * @param inviinfoid
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/sendinvimessage", method = RequestMethod.POST)
	public String sendIviMessage(long[] inviids, long inviinfoid, RedirectAttributes vMap) {
		if (inviids != null) {
			List<String> results = inviService.sendInviNoticeMessage(inviids);
			vMap.addFlashAttribute("results", results);
		} else {
			throw new SEWMException("没有选择发送短信教师！");
		}

		return redirect + basePath + "sendinvimessage/" + inviinfoid;
	}

	/**
	 * 加载监考信息更新
	 * 
	 * @param infoId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/updateinviinfo/{infoId}", method = RequestMethod.GET)
	public String updateInviInfo(@PathVariable long infoId, Map<String, Object> vMap) {
		InvigilationInfo info = inviService.findInviInfo(infoId);
		if (info == null) {
			return basePath + "error";
		}
		vMap.put("info", info);
		return basePath + "updateinviinfo";
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
	@RequestMapping(path = "/updateinviinfo", method = RequestMethod.POST)
	public String updateInviInfo(InvigilationInfo info, String date, String stime, String etime) {

		Calendar start = DateUtils.getCalendar(date + " " + stime);
		Calendar end = DateUtils.getCalendar(date + " " + etime);
		info.setStartTime(start);
		info.setEndTime(end);
		inviService.updateInviInfo(info);
		return redirect + "assigninvi/" + info.getId();
	}

	/**
	 * 为便于与家属等共同监考，提供将单一监考信息分解功能，允许同一监考，1人分配2次
	 * @param inviinfoid
	 * @return
	 */
	@RequestMapping(path = "/splitinviinfo", method = RequestMethod.POST)
	public String splitInviInfo(long inviinfoid) {
		inviService.splitInviInfo(inviinfoid);
		return redirect + "/invi/listinviinfo/unassinvi";
		}

	/**
	 * 载入添加特殊监考信息
	 * 
	 * @param vMap
	 */
	@RequestMapping("/addspecinviinfo")
	public void addSpecInviInfo(Map<String, Object> vMap) {
		List<SpecialInvigilationType> specTypes = specInviService.findSpecTypes();
		List<TeacherInvigilation> teachers = specInviService.findTeacherOrderBySpecNumber();
		vMap.put("types", specTypes);
		vMap.put("teachers", teachers);
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

		return redirect + "addspecinviinfo";
	}

	/**
	 * 删除监考信息
	 * 
	 * @param infoinviid
	 * @return
	 */
	@RequestMapping(path = "/delinviinfo", method = RequestMethod.POST)
	public String delInviInfo(long infoinviid) {
		inviService.deleteInviInfo(infoinviid);
		return redirect + "/invi/listinviinfo/unassinvi";
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
	@RequestMapping(path = "/addinviinfo", method = RequestMethod.POST)
	public String addInviInfo(String date, String stime, String etime, InvigilationInfo info) {

		Calendar startTime = DateUtils.getCalendar(date + " " + stime);
		Calendar endTime = DateUtils.getCalendar(date + " " + etime);
		info.setStartTime(startTime);
		info.setEndTime(endTime);
		long inviInfoId = inviService.addInviInfo(info);
		return redirect + basePath + "assigninvi/" + inviInfoId;
	}

	/**
	 * 加载导入课程任务页面
	 * 
	 * @param vMap
	 */
	@RequestMapping("/importtimetabletask")
	public void importTimetableTask(Map<String, Object> vMap) {
		// 当前所有课程信息
		vMap.put("teachers", inviService.findTeacherInvigilations());
		// 所有已关闭文件任务
		vMap.put("tasks", taskService.findFileTasksByStatusId(FileTaskStatus.CLOSED));
	}
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
	@RequestMapping(path = "/sendinviremind", method = RequestMethod.POST)
	public String sendInviRemind() {
		inviService.sendInviRemind();
		return redirect + basePath + "invimanagement";
	}

	/**
	 * 将从学期初至今的已分配监考置为完成状态
	 * 
	 * @return
	 */
	@RequestMapping(path = "/setinviinfodone", method = RequestMethod.POST)
	public String setInviInfoDone() {
		inviService.setInviInfoDone();
		return redirect + basePath + "invimanagement";
	}

	/**
	 * ====================================
	 */

	/**
	 * 直接加载页面时的通配方法 不会覆盖显式声明的请求 仅对一级目录有效
	 * 
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

	public AdminInviController() {
		// TODO Auto-generated constructor stub
	}

}
