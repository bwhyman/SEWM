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

import com.se.working.entity.Student;
import com.se.working.project.entity.GuideRecord;
import com.se.working.project.entity.ProjectFileDetail;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.ProjectFileType.FileTypes;
import com.se.working.project.entity.SelectedTitleDetail;
import com.se.working.project.entity.TeacherProject;
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
	@RequestMapping(path = "/listguiderecord/{typeId}")
	public String listGuideRecord(@PathVariable long typeId , Map<String, Object> vMap, HttpSession session){
		List<GuideRecord> guideRecords = projectService.findByStudentIdAndTypeId(((Student)session.getAttribute("user")).getId(), typeId);
		String typeCH = projectService.findFileTypeById(typeId).getName();
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
			vMap.put("typeId", FileTypes.OPENINGREPORT);
			break;
		case "openrecord":
			vMap.put("typeCH", "开题答辩记录");
			vMap.put("typeId", FileTypes.OPENDEFENSERECORD);
			break;
		case "interimreport":
			vMap.put("typeCH", "中期报告");
			vMap.put("typeId", FileTypes.INTERIMREPORT);
			break;
		case "interimrecord":
			vMap.put("typeCH", "中期答辩记录");
			vMap.put("typeId", FileTypes.INTERIMDEFENSERECORD);
			break;
		case "paperreport":
			vMap.put("typeCH", "论文");
			vMap.put("typeId", FileTypes.PAPER);
			break;
		case "paperrecord":
			vMap.put("typeCH", "论文答辩记录");
			vMap.put("typeId", FileTypes.PAPERDEFENSERECORD);
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
		projectService.uploadProjectFile(((Student)session.getAttribute("user")).getId(), typeId, uploadfile);
		return redirect + basePath + "projectmanagement";
	}
	
	/**
	 * 跳转页面到毕设管理页面
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/projectmanagement")
	public String projectManagement(Map<String, Object> vMap, HttpSession session){

		boolean openedProject = projectService.findStudentProjectOpened(((Student)session.getAttribute("user")).getId()); 
		
		ProjectFileType openReport = projectService.findFileTypeById(FileTypes.OPENINGREPORT);
		ProjectFileType openRecord = projectService.findFileTypeById(FileTypes.OPENDEFENSERECORD);
		ProjectFileType interimReport = projectService.findFileTypeById(FileTypes.INTERIMREPORT);
		ProjectFileType interimRecord = projectService.findFileTypeById(FileTypes.INTERIMDEFENSERECORD);
		ProjectFileType paperReport = projectService.findFileTypeById(FileTypes.PAPER);
		ProjectFileType paperRecord = projectService.findFileTypeById(FileTypes.PAPERDEFENSERECORD);
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
	public String selectTitle(long titleId, long teacherId, HttpSession session){
		projectService.addSelectedTitleDetail(((Student)session.getAttribute("user")).getId(), titleId);
		return redirect + "listtitles/" + teacherId;
	}
	
	/**
	 * 查询所有题目信息
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/listtitles/{type}")
	public String listTitles(@PathVariable long type, Map<String, Object> vMap, HttpSession session){
		//判断所选题目是否已被导师确认
		Student student = (Student)session.getAttribute("user");
		ProjectFileDetail fileDetail = projectService.findFileDetailByStudentId(student.getId(), FileTypes.DEMONSTRATIONREPORT);
		if (fileDetail !=null) {
			vMap.put("fileDetail", fileDetail);
		}else{
			List<TeacherProject> teachers = projectService.findAllTeacherProjects();
			List<ProjectFileDetail> fileDetails = null;
			if (type == -1) {
				fileDetails = projectService.findFileDetailsByTypeId(FileTypes.DEMONSTRATIONREPORT);
			}else{
				fileDetails = projectService.findByTeacherIdAndTypeId(type, FileTypes.DEMONSTRATIONREPORT);
			}
			vMap.put("teachers", teachers);
			vMap.put("fileDetails", fileDetails);
			
			//查看当前选择题目
			SelectedTitleDetail selectedTitleDetail = projectService.findSelectedTitleDetailByStudentId(student.getId());
			vMap.put("selectedTitleDetail", selectedTitleDetail);
		}
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
