package com.se.working.project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.project.dao.EvaluationDao;
import com.se.working.project.dao.GuideRecordDao;
import com.se.working.project.dao.ProjectFileDetailDao;
import com.se.working.project.dao.ProjectFileTypeDao;
import com.se.working.project.dao.ProjectTitleDao;
import com.se.working.project.dao.SelectedTitleDetailDao;
import com.se.working.project.dao.StudentProjectDao;
import com.se.working.project.dao.TeacherProjectDao;
import com.se.working.project.entity.Evaluation;
import com.se.working.project.entity.GuideRecord;
import com.se.working.project.entity.ProjectFileDetail;
import com.se.working.project.entity.ProjectFileType;
import com.se.working.project.entity.ProjectFileType.FileTypes;
import com.se.working.project.entity.ProjectTitle;
import com.se.working.project.entity.SelectedTitleDetail;
import com.se.working.project.entity.StudentProject;
import com.se.working.project.entity.TeacherProject;
import com.se.working.service.GenericService;
import com.se.working.util.FileTaskUtils;
import com.se.working.util.ProjectFileUtil;
import com.se.working.util.SelectedToExcelUtil;
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
	@Autowired
	private EvaluationDao evalDao;
	
	/**
	 * 教师评审
	 * @param studentIds
	 * @param typeId
	 * @return
	 */
	public boolean updateEvaluationByUser(long[] studentIds, long typeId, long teacherId){
		//当前评审的学生
		List<StudentProject> studentProjects = findByTeatherIdTypeId(teacherId, typeId);
		for (StudentProject studentProject : studentProjects) {
			if (evalDao.getByStudentIdTypeId(studentProject.getId(), typeId)==null) {
				Evaluation evaluation = new Evaluation(studentProject, projectFileTypeDao.get(typeId));
				evaluation.setManagerEval(false);
				evaluation.setTeacherEval(false);
				evalDao.persist(evaluation);
				evalDao.flush();
			}
		}
		boolean isAll = true;
		for (long l : studentIds) {
			Evaluation evaluation = evalDao.getByStudentIdTypeId(l, typeId);
			if (evaluation!=null) {
				evaluation.setTeacherEval(true);
			}else {
				isAll = false;
			}
		}
		return isAll;
	}
	/**
	 * 管理员评审
	 * @param studentIds
	 * @param typeId
	 */
	public void updateEvaluation(long[] studentIds, long typeId){
		//所有学生
		List<StudentProject> studentProjects = studentProjectDao.list();
		//已通过的学生
		List<StudentProject> passEvalStudents = studentProjectDao.listPassByEval(typeId, false, true);
		//未通过的学生
		studentProjects.removeAll(passEvalStudents);
		
		//未选题、教师未评审学生教师评审置：否
		for (StudentProject studentProject : passEvalStudents) {
			//未评审创建评审记录
			Evaluation evaluation = evalDao.getByStudentIdTypeId(studentProject.getId(), typeId);
			if (evaluation==null) {
				evaluation = new Evaluation(studentProject, projectFileTypeDao.get(typeId));
				evaluation.setManagerEval(false);
				evaluation.setTeacherEval(false);
				evalDao.persist(evaluation);
				evalDao.flush();
			}
		}
		
		for (long l : studentIds) {
			evalDao.getByStudentIdTypeId(l, typeId).setManagerEval(true);
		}
	}
	
	/**
	 * 判断管理员是否已评审
	 * @return
	 */
	public boolean isManageEval(long typeId){
		return evalDao.getCountManagerEval(typeId)>0?true:false;
	}
	
	/**
	 * 查询教师评审的学生
	 * @param teacherId
	 * @param typeId
	 * @return
	 */
	public List<StudentProject> findByTeatherIdTypeId(long teacherId, long typeId){
		List<StudentProject> studentProjects = new ArrayList<>();
		for (SelectedTitleDetail selectedTitleDetail : selectedTitleDetailDao.listByTeacherIdAndconfirmed(teacherId, true)) {
			StudentProject studentProject = selectedTitleDetail.getStudent();
			Evaluation evaluation = evalDao.getByStudentIdTypeId(studentProject.getId(), typeId);
			if (evaluation == null || evaluation.isTeacherEval() == false) {
				studentProjects.add(studentProject);
			}
		}
		return studentProjects;
	}
	
	/**
	 * 根据teacherid和typeId返回Evaluation
	 * @param typeId
	 * @return
	 */
	public List<Evaluation> findEvalByTeatherIdTypeId(long teacherId, long typeId){
		List<Evaluation> evaluations = new ArrayList<>();
		for (SelectedTitleDetail stDetail : selectedTitleDetailDao.listByTeacherIdAndconfirmed(teacherId, true)) {
			if (stDetail.getStudent().isOpened()) {
				Evaluation evaluation = evalDao.getByStudentIdTypeId(stDetail.getStudent().getId(), typeId);
				if (evaluation !=null ) {
					evaluations.add(evaluation);
				}
			}
		}
		return evaluations;
	}
	
	/**
	 * 获取学生评审结果
	 * @param studentId
	 * @param typeId
	 * @return
	 */
	public Evaluation findEvaluation(long studentId, long typeId){
		return evalDao.getByStudentIdTypeId(studentId, typeId);
	}
	
	/**
	 * 指定教师查询选该教师题目后未开题的学生
	 * @param teacherId
	 * @return
	 */
	public List<StudentProject> findStudentNotOpenedByTeacherId(long teacherId){
		List<StudentProject> studentProjects = new ArrayList<>();
		for (SelectedTitleDetail selectedTitleDetail : selectedTitleDetailDao.listByTeacherIdAndconfirmed(teacherId, true)) {
			StudentProject studentProject = selectedTitleDetail.getStudent();
			if (!studentProject.isOpened()) {
				studentProjects.add(studentProject);
			}
		}
		return studentProjects;
	}
	
	/**
	 * 查询所有学生毕设模块
	 * @return
	 */
	public List<StudentProject> findAllStudents(){
		return studentProjectDao.list();
	}
	
	/**
	 * 查询未开题学生
	 * @return
	 */
	public List<StudentProject> findStudentsNotOpend(){
		List<StudentProject> studentProjects = new ArrayList<>();
		for (StudentProject studentProject : studentProjectDao.list()) {
			if (!studentProject.isOpened()) {
				studentProjects.add(studentProject);
			}
		}
		return studentProjects;
	}
	
	/**
	 * 查询未通过教师评审学生
	 * @param typeId
	 * @return
	 */
	public List<StudentProject> findNotPassTeacherByTypeId(long typeId){
		List<StudentProject> studentProjects = new ArrayList<>();
		studentProjects.addAll(studentProjectDao.listPassByEval(typeId, false, false));
		studentProjects.addAll(studentProjectDao.listPassByEval(typeId, false, true));
		return studentProjects;
	}
	
	/**
	 * 根据typeId返回教师评审未评审或未通过评审学生
	 * @param TypeId
	 * @return
	 */
	public List<StudentProject> findNotPassManagerByTypeId(long typeId){
		//所有学生
		List<StudentProject> studentsAll = studentProjectDao.list();
		//未评审或未通过评审学生
		studentsAll.removeAll(studentProjectDao.listPassByEval(typeId, true, true));
		studentsAll.removeAll(studentProjectDao.listPassByEval(typeId, false, true));
		return studentsAll;
	}
	/**
	 * 根据typeId分页返回Evaluation
	 * @param typeId
	 * @return
	 */
	public List<Evaluation> findByTypeId(long typeId, int page){
		if (isManageEval(typeId)) {
			return evalDao.listByTypeIdPage(typeId, page);
		}
		return null;
	}
	
	/**
	 * 获取指定类型的评审总数
	 * @param typeId
	 * @return
	 */
	public int getEvalCountByTypeId(long typeId){
		return evalDao.listByTypeId(typeId).size();
	}
	
	public List<Evaluation> findAllEvaluation(){
		return new ArrayList<>(evalDao.list());
	}
	
	/**
	 * 根据student和typeId获取评审结果
	 * @param studentId
	 * @param typeId
	 * @return
	 */
	public Evaluation findByStudentIdTypeId(long studentId, long typeId){
		return evalDao.getByStudentIdTypeId(studentId, typeId);
	}
	
	/**
	 * 修改确认选题的学生
	 * @param oldStudentId
	 * @param studentId
	 */
	public void updateSelect(long oldStudentId, String studentId){
		StudentProject studentProject = studentProjectDao.get(oldStudentId);
		studentProject.getSelectedTitleDetail().setConfirmed(false);
		if (studentId!=null) {
			studentProjectDao.get(Long.valueOf(studentId)).getSelectedTitleDetail().setConfirmed(true);
		}else{
			TeacherProject teacherProject = studentProject.getSelectedTitleDetail().getTitle().getTeacher();
			teacherProject.setLeadNum(teacherProject.getLeadNum()+1);
		}
		
	}
	
	/**
	 * 导出选题信息
	 * @return
	 */
	public ResponseEntity<byte[]> exportSelectSuccess(){
		return SelectedToExcelUtil.export(findSelectSuccess());
	}
	
	/**
	 * 未选题学生和选题失败学生
	 * @return
	 */
	public List<StudentProject> findSelectfailByPage(int page){
		return studentProjectDao.listSelectFailByPage(page);
	}
	
	/**
	 * 选题成功学生
	 * @return
	 */
	public List<StudentProject> findSelectSuccess(){
		List<StudentProject> students = new ArrayList<>();
		List<SelectedTitleDetail> selectedTitleDetails = selectedTitleDetailDao.listSuccess();
		for (SelectedTitleDetail selectedTitleDetail : selectedTitleDetails) {
			students.add(selectedTitleDetail.getStudent());
		}
		return students;
	}
	
	/**
	 * 选题未成功人数
	 * @return
	 */
	public long getCountSelectFail(){
		return studentProjectDao.list().size()-getCountSelectSuccess();
	}
	
	/**
	 * 选题成功人数
	 * @return
	 */
	public long getCountSelectSuccess(){
		return selectedTitleDetailDao.getCountSuccess();
	}
	
	/**
	 * 选题成功学生
	 * @return
	 */
	public List<StudentProject> findSelectSuccessByPage(int page){
		List<StudentProject> students = new ArrayList<>();
		List<SelectedTitleDetail> selectedTitleDetails = selectedTitleDetailDao.listSuccessByPage(page);
		for (SelectedTitleDetail selectedTitleDetail : selectedTitleDetails) {
			students.add(selectedTitleDetail.getStudent());
		}
		return students;
	}
	
	/**
	 * 查询指定教师未确认学生选题的题目
	 * @param teacherId
	 * @return
	 */
	public List<ProjectTitle> findUncomfirmedByTeacherId(long teacherId){
		List<ProjectTitle> titles = new ArrayList<>();
		
		List<ProjectTitle> confirmeds = new ArrayList<>();
		for (SelectedTitleDetail selectedTitleDetail : selectedTitleDetailDao.listByTeacherIdAndconfirmed(teacherId, true)) {
			confirmeds.add(selectedTitleDetail.getTitle());
		}
		
		for (SelectedTitleDetail selectedTitleDetail : selectedTitleDetailDao.listByTeacherIdAndconfirmed(teacherId, false)) {
			ProjectTitle title = selectedTitleDetail.getTitle();
			if (!confirmeds.contains(title) && !titles.contains(title)) {
				titles.add(title);
			}
		}
		return titles;
	}
	
	/**
	 * 指定教师所带毕设人数
	 * @param leadNum
	 */
	public void divideLeadNum(int[] leadNum){
		List<TeacherProject> teachers = teacherProjectDao.list();
		for (int i = 0; i < leadNum.length; i++) {
			TeacherProject teacherProject = teachers.get(i);
			teacherProject.setLeadNum(leadNum[i]);
			teacherProjectDao.update(teacherProject);
		}
	}
	
	/**
	 * 修改题目
	 * @param title
	 * @param projectFileDetailId
	 * @param uploadfile
	 */
	public void updateProject(ProjectTitle title, MultipartFile uploadfile){
		ProjectTitle title2 = projectTitleDao.get(title.getId());
		ProjectFileDetail projectFileDetail = projectFileDetailDao.getByTitleIdAndTypeId(title.getId(), FileTypes.DEMONSTRATIONREPORT);
		
		String fileName = projectFileDetail.getFileName();
		if (!title.getName().equals(title2.getName())) {
			String ext = StringUtils.getFilenameExtension(projectFileDetail.getFileName());
			fileName = ProjectFileUtil.getFileName(projectFileDetail.getProjectFileType().getName(), title.getName(), ext);
			FileTaskUtils.renameFileTaskFile(projectFileDetail.getDirectory(), projectFileDetail.getFileName(), fileName);
			projectFileDetail.setFileName(fileName);
			projectFileDetailDao.update(projectFileDetail);
		}
		
		if (!uploadfile.isEmpty()) {
			FileTaskUtils.deleteFileTaskFile(projectFileDetail.getDirectory(), fileName);
			File file = ProjectFileUtil.getOrCreateProjectFile(projectFileDetail.getDirectory(), fileName);
			ProjectFileUtil.transferTo(uploadfile, file);
		}
		
		title2.setName(title.getName());
		title2.setObjective(title.getObjective());
		title2.setProperty(title.getProperty());
		projectTitleDao.update(title2);
		projectTitleDao.flush();
		projectTitleDao.refresh(title2);
	}
	
	/**
	 * 删除title
	 * @param titleId
	 */
	public void delTitle(long titleId){
		ProjectFileDetail fileDetail = projectFileDetailDao.getByTitleIdAndTypeId(titleId, FileTypes.DEMONSTRATIONREPORT);
		FileTaskUtils.deleteFileTaskFile(fileDetail.getDirectory(), fileDetail.getFileName());
		projectFileDetailDao.delete(fileDetail);
		projectTitleDao.delete(fileDetail.getTitle());
	}
	
	/**
	 * 查询所有的teacher
	 * @return
	 */
	public List<TeacherProject> findAllTeacherProjects(){
		List<TeacherProject> teacherProjects = new ArrayList<>();
		for (TeacherProject teacherProject : teacherProjectDao.list()) {
			teacherProjects.add(teacherProject);
		}
		return teacherProjects;
	}
	
	/**
	 * 更新论证报告
	 * @param fileTypeId
	 * @param uploadfile
	 */
	public void updateDemonFile(long fileDetailId, MultipartFile uploadfile){
		if (!uploadfile.isEmpty()) {
			ProjectFileDetail projectFileDetail = projectFileDetailDao.get(fileDetailId);
			String directory = projectFileDetail.getProjectFileType().getName();
			ProjectFileUtil.getOrCreateProjectDirectory(directory);
			File file = ProjectFileUtil.getOrCreateProjectFile(directory, projectFileDetail.getFileName());
			ProjectFileUtil.transferTo(uploadfile, file);
			projectFileDetail.setInsertTime(new Date());
			projectFileDetailDao.update(projectFileDetail);
		}
	}
	
	
	/**
	 * 查询所有文件类型
	 * @return
	 */
	public List<ProjectFileType> findAllFileType(){
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
	
	public void addGuideRecord(long fileTypeId, long titleId, String comment, boolean opened, MultipartFile uploadfile){
		//初始化guiderecord的基本信息
		GuideRecord guideRecord = new GuideRecord();
		guideRecord.setComment(comment);
		ProjectFileDetail fileDetail = projectFileDetailDao.getByTitleIdAndTypeId(titleId, fileTypeId);
		guideRecord.setProjectFileDetail(fileDetail);
		
		//判断是否有修改文件上传
		if (opened) {
			if (!uploadfile.isEmpty()) {
				// 创建文件文件夹，同时返回文件夹名称
				guideRecord.setDirectory(ProjectFileUtil.getOrCreateProjectDirectory("指导记录"));
				
				String ext = StringUtils.getFilenameExtension(uploadfile.getOriginalFilename());
				String fileName = null;
				fileName = ProjectFileUtil.getGuideRecordName(fileDetail.getProjectFileType().getName(), selectedTitleDetailDao.getByTitleId(fileDetail.getTitle().getId()).getStudent().getStudent().getName(), ext);
				guideRecord.setFileName(fileName);		
				File file = ProjectFileUtil.getOrCreateProjectFile("指导记录", fileName);
				ProjectFileUtil.transferTo(uploadfile, file);
			}
		}
		
		guideRecordDao.persist(guideRecord);
	}
	
	/**
	 * 根据studentId指定毕业设计阶段查找指导记录
	 * @param studentId
	 * @param typeId
	 * @return
	 */
	public List<GuideRecord> findByStudentIdAndTypeId(long studentId, long typeId){
		SelectedTitleDetail seTitleDetail = studentProjectDao.get(studentId).getSelectedTitleDetail();
		if (seTitleDetail!=null) {
			return findByTypeIdAndTitleId(seTitleDetail.getTitle().getId(), typeId);
		}
		return null;
	}
	
	/**
	 * 指定毕业设计阶段和题目查找指导记录
	 * @param titleId
	 * @param typeId
	 * @return
	 */
	public List<GuideRecord> findByTypeIdAndTitleId(long titleId, long typeId){
		List<GuideRecord> guideRecords = new ArrayList<>();
		ProjectFileDetail fileDetail = projectFileDetailDao.getByTitleIdAndTypeId(titleId, typeId);
		if (fileDetail!=null) {
			for (GuideRecord guideRecord : fileDetail.getGuideRecords()) {
				guideRecords.add(guideRecord);
			}
		}
		return guideRecords;
	}
	
	/**
	 * 根据teacherid和文件类型id查看某阶段学生毕设详细信息
	 * @param teacherId
	 * @param fileTypeId
	 * @param page
	 * @return
	 */
	public List<ProjectFileDetail> findByTeacherIdAndTypeId(long teacherId, long fileTypeId){
		return projectFileDetailDao.listByTeacherIdAndTypeId(teacherId, fileTypeId);
	}
	
	/**
	 * 上传毕业设计相关文档（学生）
	 * @param userId
	 * @param fileTypeId
	 * @param uploadFile
	 */
	public void uploadProjectFile(long userId, long typeId, MultipartFile uploadFile){
		StudentProject studentProject = studentProjectDao.get(userId);
		studentProject.setOpened(true);
		ProjectFileType fileType = projectFileTypeDao.get(typeId);
		ProjectTitle title = studentProject.getSelectedTitleDetail().getTitle();
		ProjectFileDetail fileDetail = projectFileDetailDao.getByTitleIdAndTypeId(title.getId(), typeId);
		
		boolean isExist = true;
		//判断是否已上传过该文档，存在则创建新的对象
		if (fileDetail== null) {
			isExist = false;
			fileDetail = new ProjectFileDetail();
		}
		
		fileDetail.setTitle(title);
		fileDetail.setProjectFileType(fileType);
		
		if (!uploadFile.isEmpty()) {
			// 创建文件文件夹，同时返回文件夹名称
			String directory = ProjectFileUtil.getOrCreateProjectDirectory(fileType.getName());
			fileDetail.setDirectory(directory);
			String ext = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
			String fileName = null;
			fileName = ProjectFileUtil.getFileName(studentProject.getStudent().getStudentId() + "_" + studentProject.getStudent().getName(), fileType.getName(), ext);
			fileDetail.setFileName(fileName);
					
			File file = ProjectFileUtil.getOrCreateProjectFile(directory, fileName);
			ProjectFileUtil.transferTo(uploadFile, file);
			
			if (isExist) {
				projectFileDetailDao.update(fileDetail);
			} else {
				projectFileDetailDao.persist(fileDetail);
			}
			
		}
	}
	
	
	
	/**
	 * 根据教师id获取对应题目
	 * @param teacherId
	 * @return
	 */
	public List<ProjectTitle> findByTeacher(long teacherId){
		List<ProjectTitle> titles = new ArrayList<>();
		for (ProjectTitle projectTitle : teacherProjectDao.get(teacherId).getTitles()) {
			titles.add(projectTitle);
		}
		return titles;
	}
	
	/**
	 * 确认学生选题信息，删除未被确认的学生选题信息
	 * @param userId
	 * @param stIds
	 * @return
	 */
	public int updateSelectTitle(long userId, long[] stIds){
		TeacherProject teacher = teacherProjectDao.get(userId);
		teacher.setLeadNum(teacher.getLeadNum() - stIds.length);
		teacherProjectDao.update(teacher);
		teacherProjectDao.flush();
		teacherProjectDao.refresh(teacher);
		for (long studentId : stIds) {
			studentProjectDao.get(studentId).getSelectedTitleDetail().setConfirmed(true);
		}
		return teacher.getLeadNum();
	}
	
	/**
	 * 获取指导人数
	 * @param userId
	 * @return
	 */
	public int findLeadNumById(long userId){
		return  teacherProjectDao.get(userId).getLeadNum();
	}
	
	/**
	 * 学生选题
	 * @param studentId
	 * @param titleId
	 * @return
	 */
	public SelectedTitleDetail addSelectedTitleDetail(long studentId, long titleId){
		
		StudentProject studentProject = studentProjectDao.get(studentId);
		
		//如果已有选择题目，则删除已选择的题目记录
		SelectedTitleDetail exist = studentProject.getSelectedTitleDetail();
		if (exist != null) {
			selectedTitleDetailDao.delete(exist);
		}
		SelectedTitleDetail selectedTitleDetail = new SelectedTitleDetail();
		selectedTitleDetail.setStudent(studentProject);
		selectedTitleDetail.setTitle(projectTitleDao.get(titleId));
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
	
	public long getCountByTypeId(long typeId){
		return projectFileDetailDao.getCountByTypeId(typeId);
	}
	
	/**
	 * 根据文件类型id查找毕设题目详细信息
	 * @param typeId
	 * @return
	 */
	public List<ProjectFileDetail> findFileDetailsByTypeId(long typeId, int page){
		return projectFileDetailDao.listByTypeId(typeId, page);
	}
	
	/**
	 * 开启指定阶段，并上传模板文件
	 * @param typeId
	 * @param opened
	 * @param uploadFile
	 * @return
	 */
	public boolean openProjectType(long typeId,boolean opened, MultipartFile uploadFile){
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
		return true;
	}
	
	/**
	 * 根据阶段id判断题目已开启
	 * @param typeId
	 * @return
	 */
	public boolean stageIsOpenedBy(long typeId){
		if (projectFileTypeDao.get(typeId)!=null) {
			return projectFileTypeDao.get(typeId).isOpened();
		}
		return false;
	}
	
	/**
	 * 根据ProjectFileType的id修改是否开启状态
	 * @param checkeds
	 * @param opened
	 */
	public void updateFileTypeOpened(long id, boolean opened){
		ProjectFileType fileType = projectFileTypeDao.get(id);
		fileType.setOpened(opened);
		projectFileTypeDao.update(fileType);
	}
	
	public ProjectFileType findFileTypeById(long id){
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
	 */
	public void addTitle(long teacherId, ProjectTitle title, MultipartFile uploadFile){
		//添加title信息
		title.setTeacher(new TeacherProject(teacherId));
		projectTitleDao.persist(title);
		projectTitleDao.flush();
		projectTitleDao.refresh(title);
		
		//添加论证报告信息
		ProjectFileDetail projectFileDetail = new ProjectFileDetail();
		projectFileDetail.setTitle(title);
		ProjectFileType projectFileType = projectFileTypeDao.get(FileTypes.DEMONSTRATIONREPORT);
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
	}
	
	/**
	 * 根据teacherId和typeId查询文件信息
	 * @param teacherId
	 * @param typeId
	 * @return
	 */
	public List<ProjectFileDetail> findFileDetailsByTeacherIdAndTypeId(long teacherId,long typeId){
		return projectFileDetailDao.listByTeacherIdAndTypeId(teacherId, typeId);
	}
	
	/**
	 * 根据studentid和typeId选题信息
	 * @param studentId
	 * @param typeId
	 * @return
	 */
	public ProjectFileDetail findFileDetailByStudentId(long studentId, long typeId){
		SelectedTitleDetail selectedTitleDetail = studentProjectDao.get(studentId).getSelectedTitleDetail();
		if (selectedTitleDetail!= null && selectedTitleDetail.isConfirmed()) {
			return projectFileDetailDao.getByTitleIdAndTypeId(selectedTitleDetail.getTitle().getId(), typeId);
		}
		return null;
	}
}
