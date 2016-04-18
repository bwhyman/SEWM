package com.se.working.controller.user;

import java.util.ArrayList;
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

import com.se.working.entity.User;
import com.se.working.exception.SEWMException;
import com.se.working.project.entity.GuideRecord;
import com.se.working.project.entity.ProjectFileDetail;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.ProjectFileType.ProjectFileTypes;
import com.se.working.project.entity.ProjectTitle;
import com.se.working.project.service.ProjectService;
import com.se.working.util.StringUtils;

@Controller
@RequestMapping(path = "/project")
public class UserProjectController {

	private String redirect = "redirect:";
	private String basePath = "/user/project/";
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 添加指导记录
	 * @param fileTyeId
	 * @param titleId
	 * @return
	 */
	@RequestMapping(path = "/addguiderecord", method = RequestMethod.POST)
	public String addGuideRecord(long fileTypeId, long titleId,String comment, boolean opened, MultipartFile uploadfile){
		
		if (opened) {
			if (uploadfile.isEmpty()) {
				throw new SEWMException("文件错误");
			}
			String fileName = uploadfile.getOriginalFilename();
			// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
			if (!(StringUtils.getFilenameExtension(fileName).equals("doc")
					|| StringUtils.getFilenameExtension(fileName).equals("docx"))) {
				throw new SEWMException("不是Word文件");
			}
		}
		
		projectService.addGuideRecord(fileTypeId, titleId, comment, opened, uploadfile);
		return redirect + "listguiderecord/" + fileTypeId + "/" + titleId;
	}
	
	/**
	 * 跳转页面
	 * @param fileTyeId
	 * @param titleId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/addguiderecord/{fileTypeId}/{titleId}")
	public String addGuideRecord(@PathVariable long fileTypeId, @PathVariable long titleId, Map<String, Object> vMap){
		String typeCH = projectService.findProjectFileTypeById(fileTypeId).getName();
		
		vMap.put("typeCH", typeCH);
		vMap.put("fileTypeId", fileTypeId);
		vMap.put("titleId", titleId);
		return basePath + "addguiderecord";
	}
	
	/**
	 * 指定毕业设计阶段和题目查找指导记录
	 * @param fileTypeId
	 * @param titleId
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/listguiderecord/{fileTypeId}/{titleId}")
	public String listGuideRecord(@PathVariable long fileTypeId, @PathVariable long titleId, Map<String, Object> vMap){
		List<GuideRecord> guideRecords = projectService.findByFileTypeIdAndTitleId(fileTypeId, titleId);
		String typeCH = projectService.findProjectFileTypeById(fileTypeId).getName();
		String type = null;
		switch ((int)fileTypeId) {
		case (int) ProjectFileTypes.OPENINGREPORT:
			type = "openreport";
			break;
		case (int) ProjectFileTypes.INTERIMREPORT:
			type = "interimreport";
			break;
		case (int) ProjectFileTypes.PAPER:
			type = "paperreport";
			break;

		default:
			break;
		}
		vMap.put("typeCH", typeCH);
		vMap.put("guideRecords", guideRecords);
		vMap.put("fileTypeId", fileTypeId);
		vMap.put("titleId", titleId);
		vMap.put("type", type);
		
		return basePath + "listrecord";
	}
	
	/**
	 * 指定阶段跳转到相应的指导记录界面
	 * @param type
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/listguiderecord/{type}")
	public String listGuideRecordByType(@PathVariable String type, Map<String, Object> vMap, HttpSession session){
		User user = (User) session.getAttribute("user");
		List<ProjectFileDetail> projectFileDetails = new ArrayList<>();
		switch (type) {
		case "openreport":
			projectFileDetails = projectService.findByTeacherIdAndFileTypeId(user.getId(), ProjectFileTypes.OPENINGREPORT);
			vMap.put("typeCH", "开题报告");
			break;
		case "interimreport":
			projectFileDetails = projectService.findByTeacherIdAndFileTypeId(user.getId(), ProjectFileTypes.INTERIMREPORT);
			vMap.put("typeCH", "中期报告");
			break;
		case "paperreport":
			projectFileDetails = projectService.findByTeacherIdAndFileTypeId(user.getId(), ProjectFileTypes.PAPER);
			vMap.put("typeCH", "论文");
			break;

		default:
			break;
		}
		
		vMap.put("projectFileDetails", projectFileDetails);
		
		return basePath + "listreport";
	}
	

	
	/**
	 * 教师确认学生选题
	 * @param detailid
	 * @param studentId
	 * @return
	 */
	@RequestMapping(path = "/confirmselectproject",method = RequestMethod.POST)
	public String confirmSelectProject(long detailid, long studentId){
		projectService.updateSelectTitle(detailid, studentId);
		return redirect + "selecttitles";
	}
	
	/**
	 * 根据id查看选题信息，包括确认选题学生
	 * @param id
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/findprojectdetail/{id}")
	public String findProjectDetailById(@PathVariable long id, Map<String, Object> vMap){
		ProjectFileDetail projectFileDetail = projectService.findProjectFileDetailById(id);
		vMap.put("projectFileDetail", projectFileDetail);
		return basePath + "selectprojectdetail";
	}
	
	/**
	 * 列出所有学生选题信息
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/selecttitles")
	public String selectProjects(Map<String, Object> vMap, HttpSession session){
		List<ProjectFileDetail> projectFileDetails = new ArrayList<>();
		User user = (User) session.getAttribute("user");
		projectFileDetails = projectService.findProjectFileDetailsByTeacherIdAndFileTypeId(user.getId(), ProjectFileTypes.DEMONSTRATIONREPORT);
		vMap.put("projectFileDetails", projectFileDetails);
		return basePath + "selectproject";
	}
	
	/**
	 * 根据题目id查看详细信息
	 * @param id
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/projecttitle/{id}")
	public String getProjectTitleById(@PathVariable long id, Map<String, Object> vMap){
		ProjectFileDetail projectFileDetail = projectService.findProjectFileDetailById(id);
		vMap.put("projectFileDetail", projectFileDetail);
		return basePath + "projectdetail";
	}
	
	
	/**
	 * 查看个人、全部教师的题目信息
	 * @param type
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/listtitles/{type}")
	public String listProjectTitles(@PathVariable String type, Map<String, Object> vMap, HttpSession session){
		List<ProjectFileDetail> projectFileDetails = new ArrayList<>();
		User user = (User) session.getAttribute("user");
		switch (type) {
		case "mytitles":
			projectFileDetails = projectService.findProjectFileDetailsByTeacherIdAndFileTypeId(user.getId(), ProjectFileTypes.DEMONSTRATIONREPORT);
			break;
		case "all":
			projectFileDetails = projectService.findProjectFileDetailsByFileTypeId(ProjectFileTypes.DEMONSTRATIONREPORT);
			break;	
		default:
			break;
		}
		
		vMap.put("projectFileDetails", projectFileDetails);
		vMap.put("type", type);
		return basePath + "listprojects";
	}
	
	/**
	 * 页面跳转
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/projectmanagement")
	public String projectManagement(Map<String, Object> vMap){
		ProjectFileType demonstration = projectService.findProjectFileTypeById(ProjectFileTypes.DEMONSTRATIONREPORT);
		vMap.put("demonstration", demonstration);
		return basePath + "projectmanagement";
	}
	
	/**
	 * 检查是否已有相同题目
	 * @param name
	 * @return
	 */
	@RequestMapping(path = "/checkProjectTitle", method = RequestMethod.POST)
	public @ResponseBody boolean checkProjectTitle(String name){
		return projectService.isEmptyProjectTitleByName(name);
	}
	
	/**
	 * 添加毕业设计题目
	 * @param title
	 * @param uploadFile
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/addproject", method = RequestMethod.POST)
	public String addProject(ProjectTitle title, MultipartFile uploadFile, HttpSession session){
		if (uploadFile.isEmpty()) {
			throw new SEWMException("文件错误");
		}
		String fileName = uploadFile.getOriginalFilename();
		// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
		if (!(StringUtils.getFilenameExtension(fileName).equals("doc")
				|| StringUtils.getFilenameExtension(fileName).equals("docx"))) {
			throw new SEWMException("不是Word文件");
		}
		
		User user = (User) session.getAttribute("user");
		projectService.addProjectTitle(user.getId(), title, uploadFile);
		
		return redirect + "projectmanagement";
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
	
	public UserProjectController() {
		// TODO Auto-generated constructor stub
	}

}
