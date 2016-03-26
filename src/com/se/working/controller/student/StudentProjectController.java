package com.se.working.controller.student;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.entity.User;
import com.se.working.project.entity.GuideRecord;
import com.se.working.project.entity.ProjectFileDetail;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.ProjectFileType.ProjectFileTypes;
import com.se.working.project.entity.SelectedTitleDetail;
import com.se.working.project.service.ProjectService;

@Controller
@RequestMapping("/student/project/")
public class StudentProjectController {

	private String redirect = "redirect:";
	private String basePath = "/student/project/";
	
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 指定毕业设计阶段和题目查找指导记录
	 * @param fileTypeId
	 * @param titleId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/listguiderecord/{fileTypeId}")
	public String listGuideRecord(@PathVariable long fileTypeId , Map<String, Object> vMap, HttpSession session){
		User user = (User) session.getAttribute("user");
		List<GuideRecord> guideRecords = projectService.findByUserIdAndFileTypeId(user.getId(), fileTypeId);
		String typeCH = projectService.findProjectFileTypeById(fileTypeId).getName();
		vMap.put("typeCH", typeCH);
		vMap.put("guideRecords", guideRecords);
		
		return basePath + "listrecord";
	}
	
	
	/**
	 * 根据上传文件类型跳转到相应的界面
	 * @param type
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/uploadfile/{type}")
	public String uploadFile(@PathVariable String type, Map<String, Object> vMap){
		switch (type) {
		case "openreport":
			vMap.put("typeCH", "开题报告");
			vMap.put("typeId", ProjectFileTypes.OPENINGREPORT);
			break;
		case "openrecord":
			vMap.put("typeCH", "开题答辩记录");
			vMap.put("typeId", ProjectFileTypes.OPENDEFENSERECORD);
			break;
		case "interimreport":
			vMap.put("typeCH", "中期报告");
			vMap.put("typeId", ProjectFileTypes.INTERIMREPORT);
			break;
		case "interimrecord":
			vMap.put("typeCH", "中期答辩记录");
			vMap.put("typeId", ProjectFileTypes.INTERIMDEFENSERECORD);
			break;
		case "paperreport":
			vMap.put("typeCH", "论文");
			vMap.put("typeId", ProjectFileTypes.PAPER);
			break;
		case "paperrecord":
			vMap.put("typeCH", "论文答辩记录");
			vMap.put("typeId", ProjectFileTypes.PAPERDEFENSERECORD);
			break;
		default:
			break;
		}
		vMap.put("type", type);
		return basePath + "uploadfile";
	}
	
	/**
	 * 学生上传毕设相关文档
	 * @param type
	 * @param typeId
	 * @param uploadfile
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/uploadfile/{type}", method = RequestMethod.POST)
	public String uploadFile(@PathVariable String type,long typeId, MultipartFile uploadfile, HttpSession session){
		User user = (User) session.getAttribute("user");
		projectService.uploadProjectFile(user.getId(), typeId, uploadfile);
		return redirect + basePath + "projectmanagement";
	}
	
	/**
	 * 跳转页面到毕设管理页面
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/projectmanagement")
	public String projectManagement(Map<String, Object> vMap, HttpSession session){
		
		User user = (User) session.getAttribute("user");
		boolean openedProject = projectService.findStudentProjectOpened(user.getId()); 
		
		ProjectFileType openReport = projectService.findProjectFileTypeById(ProjectFileTypes.OPENINGREPORT);
		ProjectFileType openRecord = projectService.findProjectFileTypeById(ProjectFileTypes.OPENDEFENSERECORD);
		ProjectFileType interimReport = projectService.findProjectFileTypeById(ProjectFileTypes.INTERIMREPORT);
		ProjectFileType interimRecord = projectService.findProjectFileTypeById(ProjectFileTypes.INTERIMDEFENSERECORD);
		ProjectFileType paperReport = projectService.findProjectFileTypeById(ProjectFileTypes.PAPER);
		ProjectFileType paperRecord = projectService.findProjectFileTypeById(ProjectFileTypes.PAPERDEFENSERECORD);
		vMap.put("openedProject", openedProject);
		vMap.put("openReport", openReport);
		vMap.put("openRecord", openRecord);
		vMap.put("interimReport", interimReport);
		vMap.put("interimRecord", interimRecord);
		vMap.put("paperReport", paperReport);
		vMap.put("paperRecord", paperRecord);
		return basePath + "projectmanagement";
	}
	
	@RequestMapping(path = "/selecttitle", method = RequestMethod.POST)
	public String selectTitle(long id, HttpSession session){
		User user = (User) session.getAttribute("user");
		projectService.addSelectedTitleDetail(user.getId(), id);
		
		return redirect + "listtitles";
	}
	
	/**
	 * 查询所有题目信息
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/listtitles")
	public String listProjectTitles(Map<String, Object> vMap, HttpSession session){
		List<ProjectFileDetail> projectFileDetails = projectService.findProjectFileDetailsByFileTypeId(ProjectFileTypes.DEMONSTRATIONREPORT);
		vMap.put("projectFileDetails", projectFileDetails);
		User user = (User) session.getAttribute("user");
		
		//判断所选题目是否已被导师确认
		ProjectFileDetail projectFileDetail = projectService.findProjectFileDetail(user.getId(), ProjectFileTypes.DEMONSTRATIONREPORT);
		vMap.put("projectFileDetail", projectFileDetail);
		//查看当前选择题目
		SelectedTitleDetail selectedTitleDetail = projectService.findSelectedTitleDetailByStudentId(user.getId());
		vMap.put("selectedTitleDetail", selectedTitleDetail);
		return basePath + "listprojects";
	}
	
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

	public StudentProjectController() {
		// TODO Auto-generated constructor stub
	}

}
