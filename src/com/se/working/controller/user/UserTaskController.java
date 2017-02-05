package com.se.working.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.entity.User;
import com.se.working.exception.SEWMException;
import com.se.working.task.entity.FileTask;
import com.se.working.task.entity.FileTaskDetail;
import com.se.working.task.entity.FileTaskStatus;
import com.se.working.task.service.TaskService;
import com.se.working.util.FileUtils;

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
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/list/{tasktype}", method = RequestMethod.GET)
	public String listTasks(@PathVariable String tasktype, Model model, HttpSession session) {

		List<FileTask> fileTasks = new ArrayList<>();
		switch (tasktype) {
		case "started":
			fileTasks = taskService.findFileTasksByStatusId(FileTaskStatus.STARTED);
			break;
		case "expired":
			fileTasks = taskService.findFileTasksByStatusId(FileTaskStatus.EXPIRED);
			break;
		case "closed":
			fileTasks = taskService.findFileTasksByStatusId(FileTaskStatus.CLOSED);
			break;
		case "all":
			fileTasks = taskService.findFileTasks();
			break;
		default:
			return basePath + "error";

		}
		Collections.reverse(fileTasks);
		model.addAttribute("tasks", fileTasks);
		model.addAttribute("type", tasktype);
		return basePath + "listtask";
	}

	/**
	 * 
	 * @param tasktype
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/listmytask/{tasktype}", method = RequestMethod.GET)
	public String listMyTasks(@PathVariable String tasktype, Model model, HttpSession session) {
		User user = (User) session.getAttribute(USER);

		List<FileTaskDetail> details = new ArrayList<>();
		switch (tasktype) {
		case "undone":
			// 开启状态，未完成
			details = taskService.findFileTaskDetails(user.getId(), false, FileTaskStatus.STARTED);
			// 过期状态，未完成
			details.addAll(taskService.findFileTaskDetails(user.getId(), false, FileTaskStatus.EXPIRED));
			break;
		case "done":
			// 开启状态，已完成
			details = taskService.findFileTaskDetails(user.getId(), true, FileTaskStatus.STARTED);
			// 过期状态，已完成
			details.addAll(taskService.findFileTaskDetails(user.getId(), true, FileTaskStatus.EXPIRED));
			break;
		}
		Collections.reverse(details);
		model.addAttribute("details", details);
		model.addAttribute("type", tasktype);
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
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/filetaskdetail/{filetaskId}", method = RequestMethod.GET)
	public String getFileTaskDetail(@PathVariable long filetaskId, Model model) {
		FileTask task = taskService.findById(filetaskId);
		model.addAttribute("task", task);
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
		
		ResponseEntity<byte[]> entity = FileUtils.toResponseEntity(directory + ".zip", FileUtils.zipDirectory(directory));
	
		return entity;
	}
}
