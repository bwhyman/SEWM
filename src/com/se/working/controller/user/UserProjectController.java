package com.se.working.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.se.working.project.entity.ProjectFileType.FileTypes;
import com.se.working.project.entity.ProjectTitle;
import com.se.working.project.entity.TeacherProject;
import com.se.working.project.service.ProjectService;
import com.se.working.service.UserService;
import com.se.working.util.StringUtils;

@Controller
@RequestMapping(path = "/project")
public class UserProjectController {

	private String redirect = "redirect:";
	private String basePath = "/user/project/";
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/exportSelectResult")
	public ResponseEntity<byte[]> downloadSelectResult(){
		return projectService.exportSelectSuccess();
	}
	
	@RequestMapping(path = "/uploadfiles",method = RequestMethod.POST)
	public String uploadFiles(long fileDetailId, MultipartFile uploadfile){
		if (uploadfile.isEmpty()) {
			throw new SEWMException("文件错误");
		}
		String fileName = uploadfile.getOriginalFilename();
		// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
		if (!(StringUtils.getFilenameExtension(fileName).equals("doc")
				|| StringUtils.getFilenameExtension(fileName).equals("docx"))) {
			throw new SEWMException("不是Word文件");
		}
		
		projectService.updateDemonFile(fileDetailId, uploadfile);
		return redirect + "uploadfiles";
	}
	
	/**
	 * 跳转页面上传论证报告
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/uploadfiles")
	public String uploadFiles(Map<String, Object> vMap, HttpSession session){
		User user = (User) session.getAttribute("user");
		vMap.put("demonFileDetails", projectService.findFileDetailsByTeacherIdAndTypeId(user.getId(), FileTypes.DEMONSTRATIONREPORT));
		return basePath + "uploadfile";
	}
	
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
		String typeCH = projectService.findFileTypeById(fileTypeId).getName();
		
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
		List<GuideRecord> guideRecords = projectService.findByTypeIdAndTitleId(titleId, fileTypeId);
		String typeCH = projectService.findFileTypeById(fileTypeId).getName();
		String type = null;
		switch ((int)fileTypeId) {
		case (int) FileTypes.OPENINGREPORT:
			type = "openreport";
			break;
		case (int) FileTypes.INTERIMREPORT:
			type = "interimreport";
			break;
		case (int) FileTypes.PAPER:
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
		List<ProjectFileDetail> fileDetails = new ArrayList<>();
		switch (type) {
		case "openreport":
			fileDetails = projectService.findByTeacherIdAndTypeId(user.getId(), FileTypes.OPENINGREPORT);
			vMap.put("typeCH", "开题报告");
			break;
		case "interimreport":
			fileDetails = projectService.findByTeacherIdAndTypeId(user.getId(), FileTypes.INTERIMREPORT);
			vMap.put("typeCH", "中期报告");
			break;
		case "paperreport":
			fileDetails = projectService.findByTeacherIdAndTypeId(user.getId(), FileTypes.PAPER);
			vMap.put("typeCH", "论文");
			break;

		default:
			break;
		}
		vMap.put("fileDetails", fileDetails);
		return basePath + "listreport";
	}
	

	
	/**
	 * 教师确认学生选题
	 * @param detailid
	 * @param studentId
	 * @return
	 */
	@RequestMapping(path = "/confirmselectproject",method = RequestMethod.POST)
	public String confirmSelectProject(String studentId, HttpSession session){
		String []strs = studentId.split(",");
		long []stIds = new long[strs.length] ;
		for (int i = 0; i < strs.length; i++) {
			stIds[i] = Long.valueOf(strs[i]);
		}
		
		
		User user = (User)session.getAttribute("user");
		projectService.updateSelectTitle(user.getId(), stIds);
		user = userService.findById(user.getId());
		return redirect + "selecttitles/" + user.getId();
	}
	
	/**
	 * 查看教师选题信息，包括确认选题学生
	 * @param id
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/myselecttitles")
	public String listselecttitlesByTeacher( Map<String, Object> vMap, HttpSession session){
		User user = (User) session.getAttribute("user");
		List<ProjectTitle> titles = projectService.findUncomfirmedByTeacherId(user.getId());
		vMap.put("titles", titles);
		vMap.put("leadNum", projectService.findLeadNumById(user.getId()));
		return basePath + "selectprojectdetail";
	}
	
	@RequestMapping(path = "/selectresult/{type}")
	public String selectResult(@PathVariable String type, Map<String, Object> vMap){
		switch (type) {
		case "selected":
			vMap.put("students", projectService.findSelectSuccess());
			break;
		case "unselect":
			vMap.put("students", projectService.findSelectfail());
			break;
		default:
			break;
		}
		
		vMap.put("type", type);
		return basePath + "selectresult";
	}
	
	/**
	 * 列出所有学生选题信息
	 * @param vMap
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/selecttitles/{type}")
	public String selectProjects(@PathVariable long type, Map<String, Object> vMap, HttpSession session){
		List<TeacherProject> teachers = projectService.findAllTeacherProjects();
		List<ProjectFileDetail> fileDetails = new ArrayList<>();
		if (type == -1) {
			fileDetails = projectService.findFileDetailsByTypeId(FileTypes.DEMONSTRATIONREPORT);
		} else {
			fileDetails = projectService.findFileDetailsByTeacherIdAndTypeId(type, FileTypes.DEMONSTRATIONREPORT);
		}
		vMap.put("teachers", teachers);
		vMap.put("fileDetails", fileDetails);
		vMap.put("type", type);
		return basePath + "selectproject";
	}
	
	/**
	 * 修改题目
	 * @return
	 */
	@RequestMapping(path = "/updatetitle", method = RequestMethod.POST)
	public String updateProject(ProjectTitle title, boolean isExist, MultipartFile uploadfile,  HttpSession session){
		if (isExist && uploadfile.isEmpty()) {
			throw new SEWMException("文件错误");
		}
		String fileName = uploadfile.getOriginalFilename();
		// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
		if (isExist && !(StringUtils.getFilenameExtension(fileName).equals("doc")
				|| StringUtils.getFilenameExtension(fileName).equals("docx"))) {
			throw new SEWMException("不是Word文件");
		}
		projectService.updateProject(title, uploadfile);
		
		
		return redirect + "listtitles/" + ((User)session.getAttribute("user")).getId();
	}
	
	/**
	 * 跳转页面，修改题目信息
	 * @param id
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/updatetitle/{id}")
	public String getUpdateTitleById(@PathVariable long id, Map<String, Object> vMap){
		vMap.put("title", projectService.findById(id));
		return basePath + "updatetitle";
	}
	
	/**
	 * 根据题目id查看详细信息
	 * @param id
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/title/{id}")
	public String getTitleById(@PathVariable long id, Map<String, Object> vMap){
		vMap.put("title", projectService.findById(id));
		return basePath + "titledetail";
	}
	
	@RequestMapping(path = "/deltitle", method = RequestMethod.POST)
	public String delTitle(long id, HttpSession session){
		User user = (User) session.getAttribute("user");
		projectService.delTitle(id);
		return redirect + "/project/listtitles/" + user.getId();
	}
	
	/**
	 * 查看个人、全部教师的题目信息
	 * @param type
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/listtitles/{type}")
	public String listTitles(@PathVariable long type, Map<String, Object> vMap){
		List<TeacherProject> teachers = projectService.findAllTeacherProjects();
		List<ProjectFileDetail> fileDetails = new ArrayList<>();
		if (type == -1) {
			fileDetails = projectService.findFileDetailsByTypeId(FileTypes.DEMONSTRATIONREPORT);
		} else {
			fileDetails = projectService.findFileDetailsByTeacherIdAndTypeId(type, FileTypes.DEMONSTRATIONREPORT);
		}
		vMap.put("teachers", teachers);
		vMap.put("fileDetails", fileDetails);
		vMap.put("type", type);
		return basePath + "listtitles";
	}
	
	/**
	 * 页面跳转
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/projectmanagement")
	public String projectManagement(Map<String, Object> vMap){
		ProjectFileType demonstration = projectService.findFileTypeById(FileTypes.DEMONSTRATIONREPORT);
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
	 * 添加毕业设计题目页面跳转
	 * @param title
	 * @param uploadFile
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/addtitle")
	public String addTitle(Map<String, Object> vMap, HttpSession session){
		User user = (User) session.getAttribute("user");
		List<ProjectFileDetail> fileDetails = projectService.findByTeacherIdAndTypeId(user.getId(), FileTypes.DEMONSTRATIONREPORT);
		vMap.put("fileDetails", fileDetails);
		return basePath + "addtitle";
	}
	
	/**
	 * 添加毕业设计题目
	 * @param title
	 * @param uploadFile
	 * @param session
	 * @return
	 */
	@RequestMapping(path = "/addtitle", method = RequestMethod.POST)
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
		projectService.addTitle(user.getId(), title, uploadFile);
		
		return redirect + "addtitle";
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
