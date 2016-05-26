package com.se.working.controller.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.entity.User;
import com.se.working.exception.SEWMException;
import com.se.working.service.UserService;
import com.se.working.task.entity.FileTask;
import com.se.working.task.entity.FileType;
import com.se.working.task.entity.Notification;
import com.se.working.task.entity.TeacherTask;
import com.se.working.task.service.TaskService;
import com.se.working.util.DateUtils;

@Controller
@RequestMapping("/admin/task/")
public class AdminTaskController {
	private String basePath = "/admin/task/";
	private String redirect = "redirect:";
	private String USER = "user";

	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	/**
	 * 加载创建任务
	 * 
	 * @param vMap
	 * @return
	 */
	@RequestMapping("/addfiletask")
	public String addTask(Map<String, Object> vMap) {
		List<User> users = userService.findAbledUsers();
		List<FileType> fileTypes = taskService.findFileTypes();
		vMap.put("filetypes", fileTypes);
		vMap.put("users", users);
		return basePath + "addfiletask";
	}

	/**
	 * 创建任务
	 * 
	 * @param fileTask
	 * @param datetime
	 * @param teachers
	 * @return
	 * @throws SEWMException 
	 * @throws Exception
	 */
	@RequestMapping(path = "/addfiletask", method = RequestMethod.POST)
	public String addTask(FileTask fileTask, long filetypeid, String datetime, long[] teachers,
			MultipartFile uploadFile, Boolean mytask, Boolean notice, HttpSession session) {
		Calendar endTime = DateUtils.getCalendar(datetime);
		if (endTime.getTime().before(new Date())) {
			// throw new SEWMException("截止时间在当前时间之前");
		}
		if (teachers == null) {
			throw new SEWMException("任务相关人员不能为空");
		}
		// 开始操作
		fileTask.setEndTime(endTime);
		User user = (User) session.getAttribute(USER);
		
		// 创建文件任务，基于单文件、普通文件有不同实现
		long fileTaskId = taskService.addFileTask(fileTask, filetypeid, teachers, uploadFile, user.getId());
			
		if (mytask != null) {
			taskService.implementFileTask(user.getId(), fileTaskId, uploadFile);
		}
		uploadFile = null;
		return redirect +"/task/list/started";

	}
	
	/**
	 * 列出文件任务更新内容
	 * @param id
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/updatefiletask/{id}", method = RequestMethod.GET)
	public String updateFileTask(@PathVariable long id, Map<String, Object> vMap) {
		FileTask task = taskService.findById(id);
		List<User> users = userService.findAbledUsers();
		List<FileType> fileTypes = taskService.findFileTypes();
		List<TeacherTask> teachers = taskService.findTeachersByFileTaskId(id);
		vMap.put("task", task);
		vMap.put("filetypes", fileTypes);
		vMap.put("users", users);
		vMap.put("teachers", teachers);
		return basePath + "updatefiletask";
	}
	/**
	 * 更新文件任务
	 * @param fileTask
	 * @param filetypeid
	 * @param datetime
	 * @param teachers
	 * @param uploadFile
	 * @param mytask
	 * @param notice
	 * @param session
	 * @return
	 * @throws SEWMException
	 */
	@RequestMapping(path = "/updatefiletask", method = RequestMethod.POST)
	public String updateFileTask(FileTask fileTask, long filetypeid, String datetime, long[] teachers,
			MultipartFile uploadFile, Boolean mytask, Boolean notice, HttpSession session) {
		Calendar endTime = DateUtils.getCalendar(datetime);
		if (endTime.getTime().before(new Date())) {
			throw new SEWMException("截止时间在当前时间之前");
		}
		if (teachers == null) {
			throw new SEWMException("任务相关人员不能为空");
		}
		
		fileTask.setEndTime(endTime);
		User user = (User) session.getAttribute(USER);
		taskService.updateFileTask(fileTask, filetypeid, teachers, uploadFile, user.getId());
		return redirect +"/task/filetaskdetail/" + fileTask.getId(); 
	}
	
	/**
	 * 删除文件任务
	 * @param id
	 * @return
	 * @throws SEWMException
	 */
	@RequestMapping(path = "/delfiletask", method = RequestMethod.POST)
	public String delFileTask(long id) throws SEWMException {
		taskService.deleteFileTask(id);
		return redirect + "/task/list/started";
	}
	
	
	/**
	 * 关闭任务文件
	 * @param filetaskid
	 * @param undoneusers
	 */
	@RequestMapping(path = "/closefiletask", method = RequestMethod.POST)
	public String closeFileTask(long filetaskid, long[] undoneusers) {
		if (undoneusers == null) {
			undoneusers = new long[]{};
		}
		taskService.closeFileTask(filetaskid, undoneusers);
		
		return redirect + "/task/filetaskdetail/" + filetaskid;
	}
	
	/**
	 * 加载创建通知
	 * @param vMap
	 * @return
	 */
	@RequestMapping("/addnotification")
	public String addNotifcation(Map<String, Object> vMap) {
		vMap.put("users", userService.findAbledUsers());
		return basePath + "addnotification";
	}
	
	/**
	 * 
	 * @param point
	 * @param advanced
	 * @return
	 */
	@RequestMapping(path = "/addnotification", method = RequestMethod.POST)
	public String addNotifcation(@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date datetime, 
			String comment, boolean advanced, int point, long[] teachers,  HttpSession session) {
		Notification notification = new Notification();
		notification.setComment(comment);
		if (datetime != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(datetime);
			notification.setEndTime(calendar);
		}
		if (advanced) {
			notification.setPoint(point);
		}
		User user = (User) session.getAttribute(USER);
		notification.setCreateUser(new TeacherTask(user.getId()));
		taskService.addNotification(notification, teachers);
		
		return redirect + "addnotification";
		
	}
	
	
/*
 * ============================================
 */
	@RequestMapping(path = "/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String viewpath) {
		return basePath + viewpath;
	}

	@RequestMapping(path = "/{root}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String viewpath) {
		return basePath + root + "/" + viewpath;
	}

	@RequestMapping(path = "/{root}/{root1}/{viewpath}", method = RequestMethod.GET)
	public String getView(@PathVariable String root, @PathVariable String root1, @PathVariable String viewpath) {
		return basePath + root + "/" + root1 + "/" + viewpath;
	}

	public AdminTaskController() {
		// TODO Auto-generated constructor stub
	}

}
