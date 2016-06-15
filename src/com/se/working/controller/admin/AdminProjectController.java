package com.se.working.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.exception.SEWMException;
import com.se.working.project.entity.Evaluation;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.ProjectFileType.FileTypes;
import com.se.working.project.entity.StudentProject;
import com.se.working.project.entity.TeacherProject;
import com.se.working.project.service.ProjectService;
import com.se.working.service.AdminService;
import com.se.working.util.EnumConstant;
import com.se.working.util.FileTaskUtils;
import com.se.working.util.StringUtils;

@Controller
@RequestMapping("/admin/project")
public class AdminProjectController {

	/**
	 *  管理员根目录
	 *  所有管理员权限操作以此为根
	 *  单态，无需设为常量
	 */
	private String basePath = "/admin/project/";
	private String redirect = "redirect:";
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(path = "/updateevaluation", method = RequestMethod.POST)
	public String updateEvaluation(long[] studentIds, String type){
		if (studentIds !=null) {
			switch (type) {
			case "opening":
				projectService.updateEvaluation(studentIds,FileTypes.OPENINGREPORT);
				break;
			case "interim":
				projectService.updateEvaluation(studentIds,FileTypes.INTERIMREPORT);
				break;
			case "paper":
				projectService.updateEvaluation(studentIds,FileTypes.PAPER);
				break;
			default:
				break;
			}
		}
		return redirect + "listevaluation/" + type + "/1";
	}
	/**
	 * 评审结果
	 * @param type
	 * @param page
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/listevaluation/{type}/{page}")
	public String listEvaluation(@PathVariable String type, @PathVariable int page, Map<String, Object> vMap){
		List<Evaluation> evaluations = null;
		List<StudentProject> studentProjects = new ArrayList<>();
		List<StudentProject> notPassTeacherStus = null;
		List<StudentProject> notOpenedStus = projectService.findStudentsNotOpend();
		int count = 0;
		String typeZH = null;
		switch (type) {
		case "opening":
			//获取评审的学生信息
			studentProjects = projectService.findNotPassManagerByTypeId(FileTypes.OPENINGREPORT);
			notPassTeacherStus = projectService.findNotPassTeacherByTypeId(FileTypes.OPENINGREPORT);
			//获取已评审的评审结果
			evaluations = projectService.findByTypeId(FileTypes.OPENINGREPORT, page);
			count = projectService.getEvalCountByTypeId(FileTypes.OPENINGREPORT);
			typeZH = "开题";
			break;
		case "interim":
			studentProjects = projectService.findNotPassManagerByTypeId(FileTypes.INTERIMREPORT);
			notPassTeacherStus = projectService.findNotPassTeacherByTypeId(FileTypes.INTERIMREPORT);
			evaluations = projectService.findByTypeId(FileTypes.INTERIMREPORT, page);
			count = projectService.getEvalCountByTypeId(FileTypes.INTERIMREPORT);
			typeZH = "中期";
			break;
		case "paper":
			studentProjects = projectService.findNotPassManagerByTypeId(FileTypes.PAPER);
			notPassTeacherStus = projectService.findNotPassTeacherByTypeId(FileTypes.PAPER);
			evaluations = projectService.findByTypeId(FileTypes.PAPER, page);
			count = projectService.getEvalCountByTypeId(FileTypes.PAPER);
			typeZH = "终期";
			break;
		default:
			break;
		}
		studentProjects.removeAll(notOpenedStus);
		studentProjects.removeAll(notPassTeacherStus);
		notPassTeacherStus.removeAll(notOpenedStus);
		vMap.put("studentProjects", studentProjects);
		vMap.put("notPassTeacherStus", notPassTeacherStus);
		vMap.put("notOpenedStus", notOpenedStus);
		vMap.put("evaluations", evaluations);
		vMap.put("type", type);
		vMap.put("typeZH", typeZH);
		vMap.put("count", count);
		vMap.put("currentPage", page);
		vMap.put("location", "importstuinfo");
		vMap.put("countPage", count%EnumConstant.values()[0].getPageCount()==0
				?count/EnumConstant.values()[0].getPageCount():count/EnumConstant.values()[0].getPageCount()+1);
		return basePath + "evaluation";
	}
	
	/**
	 * 清除所有学生信息（仅限于导入信息有误）
	 * @return
	 */
	@RequestMapping(path = "/clearStudents")
	public String clearStudents(){
		adminService.clearStudents();
		return redirect + "studentmanagement/students/1";
	}
	
	@RequestMapping(path = "/delstudent", method = RequestMethod.POST)
	public String delStudent(long studentId){
		adminService.delStudent(studentId);
		return redirect + "studentmanagement/students/1";
	}
	
	@RequestMapping(path = "/resetpassword", method = RequestMethod.POST)
	public @ResponseBody String resetPassword(long studentId){
		adminService.updateStudentDefaultPassword(studentId);
		return "success";
	}
	
	/**
	 * 导入学生信息
	 * @param uploadFile
	 * @param request
	 * @param vMap
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(path = "/importstu", method = RequestMethod.POST)
	public String importStudentInfo(MultipartFile uploadFile,HttpServletRequest request, RedirectAttributes vMap)throws IllegalStateException, IOException{
		if (uploadFile.isEmpty()) {
			throw new SEWMException("文件错误");
		}
		String fileName = uploadFile.getOriginalFilename();

		if (!(StringUtils.getFilenameExtension(fileName).equals("xls")
				|| StringUtils.getFilenameExtension(fileName).equals("xlsx"))) {
			throw new SEWMException("不是Excel表格文件");
		}
		try {
			InputStream is = uploadFile.getInputStream(); 
			adminService.importStudent(is);
			
			vMap.addFlashAttribute("students", adminService.findByPage(1));
			long count = adminService.findStudents().size();
			vMap.addFlashAttribute("count", count);
			vMap.addFlashAttribute("currentPage", 1);
			vMap.addFlashAttribute("location", "importstuinfo");
			vMap.addFlashAttribute("countPage", count%EnumConstant.values()[0].getPageCount()==0
					?count/EnumConstant.values()[0].getPageCount():count/EnumConstant.values()[0].getPageCount()+1);
		} finally {
			uploadFile = null;
		}

		return redirect + "importstuinfo";
	}
	
	@RequestMapping(path = "/{location}/students/{page}")
	public String listStudentsByPage(@PathVariable String location, @PathVariable int page, Map<String, Object> vMap){
		vMap.put("students", adminService.findByPage(page));
		long count = adminService.findStudents().size();
		vMap.put("count", count);
		vMap.put("currentPage", page);
		vMap.put("countPage", count%EnumConstant.values()[0].getPageCount()==0
				?count/EnumConstant.values()[0].getPageCount():count/EnumConstant.values()[0].getPageCount()+1);
		vMap.put("location", location);
		return basePath + location;
	}
	
	/**
	 * 教师毕业设计所带人数设置
	 * @param leadNum
	 * @return
	 */
	@RequestMapping(path = "/divide", method = RequestMethod.POST)
	public String divide(int[] leadNum){
		projectService.divideLeadNum(leadNum);
		return redirect + "divide";
	}
	
	@RequestMapping(path = "/divide")
	public String divide(Map<String, Object> vMap){
		List<TeacherProject> teachers = projectService.findAllTeacherProjects();
		vMap.put("teachers", teachers);
		return basePath + "divide";
	}
	
	@RequestMapping(path = "/downloadzip/{directory}/")
	public ResponseEntity<byte[]> getFileZip(@PathVariable String directory) {
		// 基于任务文件夹相对路径，生成相同名称的zip压缩文件
		ResponseEntity<byte[]> entity = FileTaskUtils.toResponseEntity(directory + ".zip", FileTaskUtils.zipDirectory(directory));
		// 压缩文件已转为字节数组，可以删除压缩文件
		return entity;
	}
	
	@RequestMapping(path = "/downloadzip")
	public String downloadZip(Map<String, Object> vMap){
		List<ProjectFileType> fileTypes = projectService.findAllFileType();
		vMap.put("fileTypes", fileTypes);
		return basePath + "downloadzip";
	}
	
	/**
	 * 修改各阶段开启、关闭状态
	 * @param type
	 * @param reportid
	 * @param recordid
	 * @param opened
	 * @param uploadFiles
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/uploadfile/{type}", method = RequestMethod.POST)
	public String openProjected(@PathVariable String type, long reportid, long recordid, boolean opened,@RequestParam MultipartFile[] uploadFiles, RedirectAttributes vMap){
		projectService.updateFileTypeOpened(reportid, opened);
		projectService.updateFileTypeOpened(recordid, opened);
		
		if (opened) {
			for (MultipartFile multipartFile : uploadFiles) {
				if (multipartFile.isEmpty()) {
					throw new SEWMException("文件错误");
				}
				String fileName = multipartFile.getOriginalFilename();
				// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
				if (!(StringUtils.getFilenameExtension(fileName).equals("doc")
						|| StringUtils.getFilenameExtension(fileName).equals("docx"))) {
					throw new SEWMException("不是Word文件");
				}
			}
			
			if (!projectService.openProjectType(reportid, opened, uploadFiles[0])
					||!projectService.openProjectType(recordid, opened, uploadFiles[1])) {
				vMap.addFlashAttribute("exception", "操作失败！");
			}
		}
		
		return redirect + basePath + "uploadfile/" + type;
		
	}
	
	/**
	 * 跳转开启开题功能页面
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/uploadfile/{type}")
	public String openProject(@PathVariable String type, Map<String, Object> vMap){
		ProjectFileType reportType = null;
		ProjectFileType recordType = null;
		switch (type) {
		case "openreport":
			vMap.put("typeReportCH", "开题报告模板");
			vMap.put("typeRecodeCH", "开题答辩记录模板");
			vMap.put("typeCH", "开题管理");
			reportType = projectService.findFileTypeById(FileTypes.OPENINGREPORT);
			recordType = projectService.findFileTypeById(FileTypes.OPENDEFENSERECORD);
			break;
		case "interimreport":
			vMap.put("typeReportCH", "中期报告模板");
			vMap.put("typeRecodeCH", "中期答辩记录模板");
			vMap.put("typeCH", "中期管理");
			reportType = projectService.findFileTypeById(FileTypes.INTERIMREPORT);
			recordType = projectService.findFileTypeById(FileTypes.INTERIMDEFENSERECORD);
			break;
		case "paperreport":
			vMap.put("typeReportCH", "论文模板");
			vMap.put("typeRecodeCH", "论文答辩记录模板");
			vMap.put("typeCH", "终期管理");
			reportType = projectService.findFileTypeById(FileTypes.PAPER);
			recordType = projectService.findFileTypeById(FileTypes.PAPERDEFENSERECORD);
			break;
		default:
			break;
		}
		
		vMap.put("reportType", reportType);
		vMap.put("recordType", recordType);
		vMap.put("type", type);
		
		return basePath + "uploadfile";
	}
	
	/**
	 * 开启毕业设计、关机添加毕业设计题目
	 * @param id
	 * @param opened
	 * @param uploadFile
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/openaddtitle", method = RequestMethod.POST)
	public String startProjected(long id, boolean opened, MultipartFile uploadFile, RedirectAttributes vMap){
		projectService.updateFileTypeOpened(id, opened);
		if (opened) {
			if (uploadFile.isEmpty()) {
				throw new SEWMException("文件错误");
			}
			String fileName = uploadFile.getOriginalFilename();
			// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读文件
			if (!(StringUtils.getFilenameExtension(fileName).equals("doc")
					|| StringUtils.getFilenameExtension(fileName).equals("docx"))) {
				throw new SEWMException("不是Word文件");
			}
			if (!projectService.openProjectType(id, opened, uploadFile)) {
				vMap.addFlashAttribute("exception", "操作失败！");
			}
		}
		
		return redirect + basePath + "openaddtitle";
	}
	
	/**
	 * 跳转开启上传题目功能页面
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/openaddtitle")
	public String openAddTitle(Map<String, Object> vMap){
		ProjectFileType startProject = projectService.findFileTypeById(FileTypes.DEMONSTRATIONREPORT);
		vMap.put("startProject", startProject);
		return basePath + "openaddtitle";
	}
	
	/**
	 * 直接加载页面时的通配方法
	 * 不会覆盖显式声明的请求
	 * 仅对一级目录有效
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
	
	public AdminProjectController() {
		// TODO Auto-generated constructor stub
	}

}
