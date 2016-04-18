package com.se.working.controller.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.entity.User;
import com.se.working.exception.SEWMException;
import com.se.working.task.entity.FileTask;
import com.se.working.task.entity.FileTaskDetail;
import com.se.working.task.entity.FileTaskStatus.FileTaskStatusType;
import com.se.working.task.entity.Notification;
import com.se.working.task.service.NotificationService;
import com.se.working.task.service.TaskService;
import com.se.working.util.FileTaskUtils;

@Controller
@RequestMapping("/task")
public class UserTaskController {
	private String USER = "user";
	private String redirect = "redirect:";
	private String basePath = "/user/task/";
	@Autowired
	private TaskService taskService;
	@Autowired
	private NotificationService notifiService;
	
	@RequestMapping(path = "/notificationdetail/{id}")
	public String getNotificationDetail(@PathVariable long id, Map<String, Object> vMap){
		Notification notification = notifiService.findById(id);
		boolean isExpired = false;
		if (notification.getEndTime().getTime().before(new Date())) {
			isExpired = true;
		} 
		vMap.put("notification", notification);
		vMap.put("isExpired", isExpired);
		return basePath + "notificationdetail";
	}
	
	/**
	 * 查询通知信息
	 * @param type
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/listnotification/{type}", method = RequestMethod.GET)
	public String listNotification(@PathVariable String type, Map<String, Object> vMap, HttpSession session){
		List<Notification> notifications = new ArrayList<>();
		
		switch (type) {
		case "expired":
		case "started":
			notifications = notifiService.findNotifiByEndTime(type);
			break;
		case "all":
			notifications = notifiService.findAll();
			break;
		default:
			return basePath + "error";
		}
		
		Collections.reverse(notifications);
		vMap.put("type", type);
		vMap.put("notifications", notifications);
		return basePath + "listnotification";
	}

	/**
	 * 列出任务
	 * 
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/list/{tasktype}", method = RequestMethod.GET)
	public String listTasks(@PathVariable String tasktype, Map<String, Object> vMap, HttpSession session) {

		List<FileTask> fileTasks = new ArrayList<>();
		switch (tasktype) {
		case "started":
			fileTasks = taskService.findFileTasksByStatusId(FileTaskStatusType.STARTED);
			break;
		case "expired":
			fileTasks = taskService.findFileTasksByStatusId(FileTaskStatusType.EXPIRED);
			break;
		case "closed":
			fileTasks = taskService.findFileTasksByStatusId(FileTaskStatusType.CLOSED);
			break;
		case "all":
			fileTasks = taskService.findFileTasks();
			break;
		default:
			return basePath + "error";

		}
		Collections.reverse(fileTasks);
		vMap.put("tasks", fileTasks);
		vMap.put("type", tasktype);
		return basePath + "listtask";
	}

	/**
	 * 
	 * @param tasktype
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/listmytask/{tasktype}", method = RequestMethod.GET)
	public String listMyTasks(@PathVariable String tasktype, Map<String, Object> vMap, HttpSession session) {
		User user = (User) session.getAttribute(USER);

		List<FileTaskDetail> details = new ArrayList<>();
		switch (tasktype) {
		case "undone":
			// 开启状态，未完成
			details = taskService.findFileTaskDetails(user.getId(), false, FileTaskStatusType.STARTED);
			// 过期状态，未完成
			details.addAll(taskService.findFileTaskDetails(user.getId(), false, FileTaskStatusType.EXPIRED));
			break;
		case "done":
			// 开启状态，已完成
			details = taskService.findFileTaskDetails(user.getId(), true, FileTaskStatusType.STARTED);
			// 过期状态，已完成
			details.addAll(taskService.findFileTaskDetails(user.getId(), true, FileTaskStatusType.EXPIRED));
			break;
		}
		Collections.reverse(details);
		vMap.put("details", details);
		vMap.put("type", tasktype);
		return basePath + "listmytask";
	}

	/**
	 * 完成任务
	 * 
	 * @param uploadFile
	 * @param taskid
	 * @param session
	 * @return
	 * @throws SEWMException
	 */
	@RequestMapping(path = "/implementfiletask", method = RequestMethod.POST)
	public String implementFileTask(MultipartFile uploadFile, long taskid, HttpSession session) {
		User user = (User) session.getAttribute(USER);

		if (!uploadFile.isEmpty()) {
			FileTask task = taskService.findById(taskid);
			if (task.isSingleFile() && task.getTempleteFile() != null) {
				if (!uploadFile.getOriginalFilename().equals(task.getTempleteFile())) {
					throw new SEWMException("版本错误，请基于最新模板文件，重新修改上传");
				}
			}
		}

		taskService.implementFileTask(user.getId(), taskid, uploadFile);
		return redirect + "listmytask/undone";
	}

	/**
	 * 列出任务详细信息
	 * 
	 * @param filetaskId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/filetaskdetail/{filetaskId}", method = RequestMethod.GET)
	public String getFileTaskDetail(@PathVariable long filetaskId, Map<String, Object> vMap) {
		FileTask task = taskService.findById(filetaskId);
		vMap.put("task", task);
		return basePath + "filetaskdetail";
	}

	/**
	 * 下载ZIP压缩包
	 * 
	 * @param directory
	 * @return
	 * @throws SEWMException
	 */
	@RequestMapping(path = "/downloadzip/{directory}/", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFileZip(@PathVariable String directory) {
		// 基于任务文件夹相对路径，生成相同名称的zip压缩文件
		File file = FileTaskUtils.zipDirectory(directory);
		// 以字节流返回
		ResponseEntity<byte[]> entity = FileTaskUtils.downloadFile(file);
		// 压缩文件已转为字节数组，可以删除压缩文件
		file.delete();
		return entity;
	}

	public UserTaskController() {
		// TODO Auto-generated constructor stub
	}

}
