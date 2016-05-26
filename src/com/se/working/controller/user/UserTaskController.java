package com.se.working.controller.user;

import java.util.ArrayList;
import java.util.Collections;
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
	 */
	@RequestMapping(path = "/downloadzip/{directory}/", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFileZip(@PathVariable String directory) {
		
		ResponseEntity<byte[]> entity = FileTaskUtils.toResponseEntity(directory, FileTaskUtils.zipDirectory(directory));
	
		return entity;
	}

	public UserTaskController() {
		// TODO Auto-generated constructor stub
	}

}
