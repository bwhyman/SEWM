package com.se.working.controller.admin;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.working.exception.SEWMException;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.ProjectFileType.ProjectFileTypes;
import com.se.working.project.service.ProjectService;
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
	
	@RequestMapping(path = "/downloadzip/{directory}/")
	public ResponseEntity<byte[]> getFileZip(@PathVariable String directory) {
		// 基于任务文件夹相对路径，生成相同名称的zip压缩文件
		File file = FileTaskUtils.zipDirectory(directory);
		// 以字节流返回
		ResponseEntity<byte[]> entity = FileTaskUtils.downloadFile(file);
		// 压缩文件已转为字节数组，可以删除压缩文件
		file.delete();
		return entity;
	}
	
	@RequestMapping(path = "/downloadzip")
	public String downloadZip(Map<String, Object> vMap){
		List<ProjectFileType> fileTypes = projectService.findAllProjectFileType();
		vMap.put("fileTypes", fileTypes);
		return basePath + "downloadzip";
	}
	
	@RequestMapping(path = "/uploadfile/{type}", method = RequestMethod.POST)
	public String openProjected(@PathVariable String type, long reportid, long recordid, boolean opened,@RequestParam MultipartFile[] uploadFiles, RedirectAttributes vMap){
		projectService.updateProjectFileTypeOpened(reportid, opened);
		projectService.updateProjectFileTypeOpened(recordid, opened);
		
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
			
			projectService.openProjectType(reportid, opened, uploadFiles[0]);
			projectService.openProjectType(recordid, opened, uploadFiles[1]);
		}
		
		return redirect + basePath + "projectmanagement";
		
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
			reportType = projectService.findProjectFileTypeById(ProjectFileTypes.OPENINGREPORT);
			recordType = projectService.findProjectFileTypeById(ProjectFileTypes.OPENDEFENSERECORD);
			break;
		case "interimreport":
			vMap.put("typeReportCH", "中期报告模板");
			vMap.put("typeRecodeCH", "中期答辩记录模板");
			vMap.put("typeCH", "中期管理");
			reportType = projectService.findProjectFileTypeById(ProjectFileTypes.INTERIMREPORT);
			recordType = projectService.findProjectFileTypeById(ProjectFileTypes.INTERIMDEFENSERECORD);
			break;
		case "paperreport":
			vMap.put("typeReportCH", "论文模板");
			vMap.put("typeRecodeCH", "论文答辩记录模板");
			vMap.put("typeCH", "终期管理");
			reportType = projectService.findProjectFileTypeById(ProjectFileTypes.PAPER);
			recordType = projectService.findProjectFileTypeById(ProjectFileTypes.PAPERDEFENSERECORD);
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
	@RequestMapping(path = "/startproject", method = RequestMethod.POST)
	public String startProjected(long id, boolean opened, MultipartFile uploadFile, RedirectAttributes vMap){
		projectService.updateProjectFileTypeOpened(id, opened);
		if (opened) {
			if (uploadFile.isEmpty()) {
				throw new SEWMException("文件错误");
			}
			String fileName = uploadFile.getOriginalFilename();
			// 前端已经通过属性控制上传文件类型，再一次判断文件扩展名，但并不保证文件一定为可读excel文件
			if (!(StringUtils.getFilenameExtension(fileName).equals("doc")
					|| StringUtils.getFilenameExtension(fileName).equals("docx"))) {
				throw new SEWMException("不是Word文件");
			}
			projectService.openProjectType(id, opened, uploadFile);
		}
		
		return redirect + "projectmanagement";
	}
	
	/**
	 * 跳转开启上传题目功能页面
	 * @param vMap
	 * @return
	 */
	@RequestMapping(path = "/startproject")
	public String startProject(Map<String, Object> vMap){
		ProjectFileType startProject = projectService.findProjectFileTypeById(ProjectFileTypes.DEMONSTRATIONREPORT);
		vMap.put("startProject", startProject);
		return basePath + "startproject";
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
