package com.se.working.project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.project.dao.GuideRecordDao;
import com.se.working.project.dao.ProjectFileDetailDao;
import com.se.working.project.dao.ProjectFileTypeDao;
import com.se.working.project.dao.ProjectTitleDao;
import com.se.working.project.dao.SelectedTitleDetailDao;
import com.se.working.project.dao.StudentProjectDao;
import com.se.working.project.dao.TeacherProjectDao;
import com.se.working.project.entity.GuideRecord;
import com.se.working.project.entity.ProjectFileDetail;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.ProjectFileType.ProjectFileTypes;
import com.se.working.project.entity.ProjectTitle;
import com.se.working.project.entity.SelectedTitleDetail;
import com.se.working.project.entity.StudentProject;
import com.se.working.project.entity.TeacherProject;
import com.se.working.service.GenericService;
import com.se.working.util.ProjectFileUtil;
import com.se.working.util.StringUtils;

@Service
@Transactional
public class ProjectService extends GenericService<ProjectTitle, Long> {

	@Autowired
	private ProjectTitleDao projectTitleDao;
	@Autowired
	private ProjectFileDetailDao projectFileDetailDao;
	@Autowired
	private TeacherProjectDao teacherProjectDao;
	@Autowired
	private ProjectFileTypeDao projectFileTypeDao;
	@Autowired
	private SelectedTitleDetailDao selectedTitleDetailDao;
	@Autowired
	private StudentProjectDao studentProjectDao;
	@Autowired
	private GuideRecordDao guideRecordDao;
	
	/**
	 * 查询所有文件类型
	 * @return
	 */
	public List<ProjectFileType> findAllProjectFileType(){
		return projectFileTypeDao.list();
	}
	
	/**
	 * 根据userid查询是否已开题
	 * @param userId
	 * @return
	 */
	public boolean findStudentProjectOpened(long userId){
		return studentProjectDao.get(userId).isOpened();
	}
	
	/**
	 * 根据userid和filetypeid获取指导记录
	 * @param userId
	 * @param fileTypeId
	 * @return
	 */
	public List<GuideRecord> findByUserIdAndFileTypeId(long userId, long fileTypeId){
		ProjectTitle projectTitle = studentProjectDao.get(userId).getTitle();
		return findByFileTypeIdAndTitleId(fileTypeId, projectTitle.getId());
	}
	
	public void addGuideRecord(long fileTypeId, long titleId, String comment, boolean opened, MultipartFile uploadfile){
		
		//初始化guiderecord的基本信息
		GuideRecord guideRecord = new GuideRecord();
		ProjectTitle projectTitle = projectTitleDao.get(titleId);
		ProjectFileType projectFileType = projectFileTypeDao.get(fileTypeId);
		StudentProject studentProject = projectTitle.getStudent();
		ProjectFileDetail projectFileDetail = projectFileDetailDao.getByStudentIdAndFileTypeId(studentProject.getId(), fileTypeId);
		guideRecord.setComment(comment);
		guideRecord.setTitle(projectTitle);
		guideRecord.setStudent(studentProject);
		guideRecord.setProjectFileType(projectFileType);
		guideRecord.setProjectFileDetail(projectFileDetail);
		
		//判断是否有修改文件上传
		if (opened) {
			if (!uploadfile.isEmpty()) {
				// 创建文件文件夹，同时返回文件夹名称
				guideRecord.setDirectory(ProjectFileUtil.getOrCreateProjectDirectory("指导记录"));
				
				String ext = StringUtils.getFilenameExtension(uploadfile.getOriginalFilename());
				String fileName = null;
				fileName = ProjectFileUtil.getGuideRecordName(projectFileType.getName(), studentProject.getUser().getName(), ext);
				guideRecord.setFileName(fileName);		
				File file = ProjectFileUtil.getOrCreateProjectFile("指导记录", fileName);
				ProjectFileUtil.transferTo(uploadfile, file);
			}
		}
		
		guideRecordDao.persist(guideRecord);
	}
	
	/**
	 * 指定毕业设计阶段和题目查找指导记录
	 * @param fileTypeId
	 * @param titleId
	 * @return
	 */
	public List<GuideRecord> findByFileTypeIdAndTitleId(long fileTypeId, long titleId){
		List<GuideRecord> guideRecords = new ArrayList<>();
		ProjectFileDetail projectFileDetail = projectFileDetailDao.getByStudentIdAndFileTypeId(projectTitleDao.get(titleId).getStudent().getUser().getId(), fileTypeId);
		if (projectFileDetail!=null) {
			for (GuideRecord guideRecord : projectFileDetail.getGuideRecords()) {
				guideRecords.add(guideRecord);
			}
		}
		
		return guideRecords;
	}
	
	/**
	 * 根据teacherid和文件类型id查看某阶段学生毕设详细信息
	 * @param teacherId
	 * @param fileTypeId
	 * @return
	 */
	public List<ProjectFileDetail> findByTeacherIdAndFileTypeId(long teacherId, long fileTypeId){
		return projectFileDetailDao.listByTeacherIdAndFileTypeId(teacherId, fileTypeId);
	}
	
	/**
	 * 上传毕业设计相关文档（学生）
	 * @param userId
	 * @param fileTypeId
	 * @param uploadFile
	 */
	public void uploadProjectFile(long userId, long fileTypeId, MultipartFile uploadFile){
		StudentProject studentProject = studentProjectDao.get(userId);
		studentProject.setOpened(true);
		ProjectFileType projectFileType = projectFileTypeDao.get(fileTypeId);
		ProjectTitle projectTitle = studentProject.getTitle();
		ProjectFileDetail projectFileDetail = projectFileDetailDao.getByStudentIdAndFileTypeId(userId, fileTypeId);
		
		boolean isExist = true;
		//判断是否已上传过该文档，存在则创建新的对象
		if (projectFileDetail== null) {
			isExist = false;
			projectFileDetail = new ProjectFileDetail();
		}
		
		projectFileDetail.setStudent(studentProject);
		projectFileDetail.setTitle(projectTitle);
		projectFileDetail.setProjectFileType(projectFileType);
		
		if (!uploadFile.isEmpty()) {
			// 创建文件文件夹，同时返回文件夹名称
			String directory = ProjectFileUtil.getOrCreateProjectDirectory(projectFileType.getName());
			projectFileDetail.setDirectory(directory);
			String ext = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
			String fileName = null;
			fileName = ProjectFileUtil.getFileName(studentProject.getUser().getEmployeeNumber() + "_" + studentProject.getUser().getName(), projectFileType.getName(), ext);
			projectFileDetail.setFileName(fileName);
					
			File file = ProjectFileUtil.getOrCreateProjectFile(directory, fileName);
			ProjectFileUtil.transferTo(uploadFile, file);
			
			if (isExist) {
				projectFileDetailDao.update(projectFileDetail);
			} else {
				projectFileDetailDao.persist(projectFileDetail);
			}
			
		}
	}
	
	/**
	 * 用于已被导师确认选题后根据studentid查看题目信息
	 * @param studentId
	 * @return
	 */
	public ProjectFileDetail findProjectFileDetailByStudentIdAndFileTypeId(long studentId, long fileTypeId){
		return null;//studentProjectDao.get(studentId).getTitle();
	}
	
	/**
	 * 确认学生选题信息，删除未被确认的学生选题信息
	 * @param detailid
	 * @param studentId
	 */
	public void updateSelectTitle(long detailid, long studentId){
		ProjectFileDetail projectFileDetail = projectFileDetailDao.get(detailid);
		StudentProject studentProject = studentProjectDao.get(studentId);
		projectFileDetail.setStudent(studentProject);
		projectFileDetailDao.update(projectFileDetail);
		
		//删除已确认选题的选题信息
		for (SelectedTitleDetail selectedTitleDetail : projectFileDetail.getTitle().getSelectedTitleDetails()) {
			selectedTitleDetailDao.delete(selectedTitleDetail);
		}
	}
	
	/**
	 * 学生选题
	 * @param studentId
	 * @param titleId
	 * @return
	 */
	public SelectedTitleDetail addSelectedTitleDetail(long studentId, long titleId){
		
		//设置题目所对应学生
		StudentProject studentProject = studentProjectDao.get(studentId);
		ProjectTitle projectTitle = projectTitleDao.get(titleId);
		projectTitle.setStudent(studentProject);
		projectTitleDao.update(projectTitle);
		projectTitleDao.flush();
		projectTitleDao.refresh(projectTitle);
		
		//如果已有选择题目，则删除已选择的题目记录
		SelectedTitleDetail exist = studentProject.getSelectedTitleDetail();
		if (exist != null) {
			selectedTitleDetailDao.delete(exist);
		}
		SelectedTitleDetail selectedTitleDetail = new SelectedTitleDetail();
		selectedTitleDetail.setStudent(studentProject);
		selectedTitleDetail.setTitle(projectTitle);
		selectedTitleDetailDao.persist(selectedTitleDetail);
		selectedTitleDetailDao.flush();
		selectedTitleDetailDao.refresh(selectedTitleDetail);
		
		return selectedTitleDetail;
	}
	
	public SelectedTitleDetail findSelectedTitleDetailByStudentId(long studentId){
		return studentProjectDao.get(studentId).getSelectedTitleDetail();
	}
	
	public ProjectFileDetail findProjectFileDetailById(long id){
		return projectFileDetailDao.get(id);
	}
	
	/**
	 * 根据文件类型id查找毕设题目详细信息
	 * @param teacherId
	 * @param projectFileTypeId
	 * @return
	 */
	public List<ProjectFileDetail> findProjectFileDetailsByFileTypeId(long projectFileTypeId){
		return projectFileDetailDao.listByFileTypeId(projectFileTypeId);
	}
	
	/**
	 * 根据teacherid和文件类型id查找毕设题目详细信息
	 * @param teacherId
	 * @param projectFileTypeId
	 * @return
	 */
	public List<ProjectFileDetail> findProjectFileDetailsByTeacherIdAndFileTypeId(long teacherId, long projectFileTypeId){
		return projectFileDetailDao.listByTeacherIdAndFileTypeId(teacherId, projectFileTypeId);
	}
	
	/**
	 * 根据文件夹和文件名称查找文件详细信息
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public ProjectFileDetail findProjectFileDetailByDirectoryAndName(String directory, String fileName){
		return projectFileDetailDao.getProjectFileDetailByDirectoryAndName(directory, fileName);
	}
	
	/**
	 * 开启指定阶段，并上传模板文件
	 * @param typeId
	 * @param opened
	 * @param uploadFile
	 * @param directoryName
	 */
	public void openProjectType(long typeId,boolean opened, MultipartFile uploadFile){
		ProjectFileType projectFileType = projectFileTypeDao.get(typeId);
		if (opened!=false && !uploadFile.isEmpty()) {
			// 创建论证报告文件夹，同时返回文件夹名称
			String directory = ProjectFileUtil.getOrCreateProjectDirectory(projectFileType.getName());
			projectFileType.setDirectory(directory);
			String ext = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
			String fileName = null;
			fileName = ProjectFileUtil.getFileName(projectFileType.getName(), "模板", ext);
			projectFileType.setTempleteFile(fileName);
			
			File file = ProjectFileUtil.getOrCreateProjectFile(directory, fileName);
			ProjectFileUtil.transferTo(uploadFile, file);
			projectFileTypeDao.update(projectFileType);
		}
		updateProjectFileTypeOpened(typeId, opened);
	}
	
	/**
	 * 根据ProjectFileType的id修改是否开启状态
	 * @param checkeds
	 * @param opened
	 */
	public void updateProjectFileTypeOpened(long id, boolean opened){
		ProjectFileType projectFileType = projectFileTypeDao.get(id);
		projectFileType.setOpened(opened);
		projectFileTypeDao.update(projectFileType);
	}
	
	public ProjectFileType findProjectFileTypeById(long id){
		return projectFileTypeDao.get(id);
	}
	
	/**
	 * 根据教师id查询毕设题目
	 * @param teacherId
	 * @return
	 */
	public List<ProjectTitle> listByteacherId(long teacherId){
		List<ProjectTitle> titles = new ArrayList<>();
		for (ProjectTitle title: teacherProjectDao.get(teacherId).getTitles()) {
			titles.add(title);
		}
		return titles;
	}
	
	/**
	 * 判断是否已存在该题目
	 * @param name
	 * @return
	 */
	public boolean isEmptyProjectTitleByName(String name){
		if (projectTitleDao.getProjectTitleByName(name)!=null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 添加毕业设计题目
	 * @param teacherId
	 * @param title
	 * @param uploadFile
	 * @return
	 */
	public long addProjectTitle(long teacherId, ProjectTitle title, MultipartFile uploadFile){
		title.setTeacher(new TeacherProject(teacherId));
		projectTitleDao.persist(title);
		projectTitleDao.flush();
		projectTitleDao.refresh(title);
		
		ProjectFileDetail projectFileDetail = new ProjectFileDetail();
		projectFileDetail.setTitle(title);
		ProjectFileType projectFileType = projectFileTypeDao.get(ProjectFileTypes.DEMONSTRATIONREPORT);
		projectFileDetail.setProjectFileType(projectFileType);
		// 创建论证报告文件夹，同时返回文件夹名称
		projectFileDetail.setDirectory(ProjectFileUtil.getOrCreateProjectDirectory(projectFileType.getName()));
		if (!uploadFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
			String fileName = null;
			fileName = ProjectFileUtil.getFileName(projectFileType.getName(), title.getName(), ext);
			projectFileDetail.setFileName(fileName);
			
			File file = ProjectFileUtil.getOrCreateProjectFile(projectFileType.getName(), fileName);
			ProjectFileUtil.transferTo(uploadFile, file);
			projectFileDetailDao.persist(projectFileDetail);
		}
		
		return title.getId();
	}
	
	/**
	 * 根据studentid和文件类型id查询毕设文档详细信息
	 * @param titleId
	 * @param projectFileTypeId
	 * @return
	 */
	public ProjectFileDetail findProjectFileDetail(long studentId, long projectFileTypeId){
		return projectFileDetailDao.getByStudentIdAndFileTypeId(studentId, projectFileTypeId);
	}
}
