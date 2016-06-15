package com.se.working.controller.student;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.entity.Student;
import com.se.working.project.entity.Evaluation;
import com.se.working.project.entity.ProjectFileDetail;
import com.se.working.project.entity.ProjectFileType.FileTypes;
import com.se.working.project.entity.SelectedTitleDetail;
import com.se.working.project.entity.TeacherProject;
import com.se.working.project.service.ProjectService;
import com.se.working.util.EnumConstant;

@Controller
@RequestMapping("/student/project/")
public class StudentProjectController {

	private String USER = "user";
	private String redirect = "redirect:";
	private String basePath = "/student/project/";
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(path = "/evaluation/{type}")
	public String getEvaluation(@PathVariable String type, Map<String, Object> vMap, HttpSession session){
		Student student = (Student) session.getAttribute("user");
		Evaluation evaluation = null;
		String typeZH = null;
		switch (type) {
		case "opening":
			evaluation = projectService.findEvaluation(student.getId(),FileTypes.OPENINGREPORT);
			typeZH = "开题";
			break;
		case "interim":
			evaluation = projectService.findEvaluation(student.getId(),FileTypes.INTERIMREPORT);
			typeZH = "中期";
			break;
		case "paper":
			evaluation = projectService.findEvaluation(student.getId(),FileTypes.PAPER);
			typeZH = "终期";
			break;
		default:
			break;
		}
		vMap.put("evaluation", evaluation);
		vMap.put("type", type);
		vMap.put("typeZH", typeZH);
		return basePath + "evaluation";
	}
	
	/**
	 * 指定毕业设计阶段和题目查找指导记录
	 * @param fileTypeId
	 * @param titleId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/listguiderecord/{type}")
	public String listGuideRecord(@PathVariable String type , Map<String, Object> vMap, HttpSession session){
		String typeZH = null;
		long typeId = 0;
		switch (type) {
		case "opening":
			typeZH = "开题";
			typeId = FileTypes.OPENINGREPORT;
			break;
		case "interim":
			typeZH = "中期";
			typeId = FileTypes.INTERIMREPORT;
			break;
		case "paper":
			typeZH = "结题";
			typeId = FileTypes.PAPER;
			break;
		default:
			break;
		}
		vMap.put("typeZH", typeZH);
		vMap.put("type", type);
		vMap.put("typeId", typeId);
		vMap.put("guideRecords", projectService.findByStudentIdAndTypeId(((Student)session.getAttribute(USER)).getId(), typeId));
		
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
			vMap.put("typeZH", "开题报告");
			vMap.put("stageTypeZH", "开题");
			vMap.put("stageType", "opening");
			vMap.put("typeId", FileTypes.OPENINGREPORT);
			vMap.put("openReport", projectService.findFileTypeById(FileTypes.OPENINGREPORT));
			break;
		case "openrecord":
			vMap.put("typeZH", "开题答辩记录");
			vMap.put("stageTypeZH", "开题");
			vMap.put("stageType", "opening");
			vMap.put("typeId", FileTypes.OPENDEFENSERECORD);
			vMap.put("openRecord", projectService.findFileTypeById(FileTypes.OPENDEFENSERECORD));
			break;
		case "interimreport":
			vMap.put("typeZH", "中期报告");
			vMap.put("stageTypeZH", "中期");
			vMap.put("stageType", "interim");
			vMap.put("typeId", FileTypes.INTERIMREPORT);
			vMap.put("interimReport", projectService.findFileTypeById(FileTypes.INTERIMREPORT));
			break;
		case "interimrecord":
			vMap.put("typeZH", "中期答辩记录");
			vMap.put("stageTypeZH", "中期");
			vMap.put("stageType", "interim");
			vMap.put("typeId", FileTypes.INTERIMDEFENSERECORD);
			vMap.put("interimRecord", projectService.findFileTypeById(FileTypes.INTERIMDEFENSERECORD));
			break;
		case "paperreport":
			vMap.put("typeZH", "论文");
			vMap.put("stageTypeZH", "结题");
			vMap.put("stageType", "paper");
			vMap.put("typeId", FileTypes.PAPER);
			vMap.put("paperReport", projectService.findFileTypeById(FileTypes.PAPER));
			break;
		case "paperrecord":
			vMap.put("typeZH", "论文答辩记录");
			vMap.put("stageTypeZH", "结题");
			vMap.put("stageType", "paper");
			vMap.put("typeId", FileTypes.PAPERDEFENSERECORD);
			vMap.put("paperRecord", projectService.findFileTypeById(FileTypes.PAPERDEFENSERECORD));
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
	public String uploadFile(@PathVariable String type,long typeId, String stageType, MultipartFile uploadfile, HttpSession session){
		projectService.uploadProjectFile(((Student)session.getAttribute(USER)).getId(), typeId, uploadfile);
		return redirect + basePath + "projectmanagement/" + stageType;
	}
	
	/**
	 * 跳转页面到毕设管理页面
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/projectmanagement/{type}")
	public String projectManagement(@PathVariable String type, Map<String, Object> vMap, HttpSession session){
		Student student = (Student) session.getAttribute(USER);
		String typeZH = null;
		switch (type) {
		case "title":
			typeZH = "题目";
			vMap.put("openedProject", projectService.findStudentProjectOpened(student.getId()));
			break;
		case "opening":
			typeZH = "开题";
			vMap.put("openEval", projectService.findByStudentIdTypeId(student.getId(), FileTypes.OPENINGREPORT));
			break;
		case "interim":
			typeZH = "中期";
			vMap.put("interimEval", projectService.findByStudentIdTypeId(student.getId(), FileTypes.INTERIMREPORT));
			break;
		case "paper":
			typeZH = "结题";
			break;
		default:
			break;
		}
		vMap.put("typeZH", typeZH);
		vMap.put("type", type);
		
		return basePath + "projectmanagement";
	}
	
	@RequestMapping(path = "/selecttitle", method = RequestMethod.POST)
	public @ResponseBody String selectTitle(long titleId, long teacherId, HttpSession session){
		projectService.addSelectedTitleDetail(((Student)session.getAttribute("user")).getId(), titleId);
		return "success";
	}
	@RequestMapping(path = "/mytitle")
	public String getMyTitle(HttpSession session, Map<String, Object> vMap){
		Student student = (Student)session.getAttribute("user");
		ProjectFileDetail fileDetail = projectService.findFileDetailByStudentId(student.getId(), FileTypes.DEMONSTRATIONREPORT);
		vMap.put("fileDetail", fileDetail);
		return basePath + "mytitle";
	}
	/**
	 * 查询所有题目信息
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/listtitles/{type}/{page}")
	public String listTitles(@PathVariable long type, @PathVariable int page, Map<String, Object> vMap, HttpSession session){
		//判断所选题目是否已被导师确认
		List<TeacherProject> teachers = projectService.findAllTeacherProjects();
		List<ProjectFileDetail> fileDetails = null;
		if (type == -1) {
			fileDetails = projectService.findFileDetailsByTypeId(FileTypes.DEMONSTRATIONREPORT, page);
		}else{
			fileDetails = projectService.findByTeacherIdAndTypeId(type, FileTypes.DEMONSTRATIONREPORT);
		}
		vMap.put("teachers", teachers);
		vMap.put("fileDetails", fileDetails);
			
		//查看当前选择题目
		SelectedTitleDetail selectedTitleDetail = projectService.findSelectedTitleDetailByStudentId(((Student)session.getAttribute(USER)).getId());
		vMap.put("selectedTitleDetail", selectedTitleDetail);
		long count = projectService.getCountByTypeId(FileTypes.DEMONSTRATIONREPORT);
		vMap.put("count", count);
		vMap.put("currentPage", page);
		vMap.put("countPage", count%EnumConstant.values()[0].getPageCount()==0
				?count/EnumConstant.values()[0].getPageCount():count/EnumConstant.values()[0].getPageCount()+1);
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
