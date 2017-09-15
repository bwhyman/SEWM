package com.se.working.task.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.se.working.exception.SEWMException;
import com.se.working.message.AlidayuMessage;
import com.se.working.task.dao.FileTaskDao;
import com.se.working.task.dao.FileTaskDetailDao;
import com.se.working.task.dao.FileTaskStatusDao;
import com.se.working.task.dao.FileTypeDao;
import com.se.working.task.dao.NotificationDao;
import com.se.working.task.dao.TeacherTaskDao;
import com.se.working.task.entity.FileTask;
import com.se.working.task.entity.FileTaskDetail;
import com.se.working.task.entity.FileTaskStatus;
import com.se.working.task.entity.FileType;
import com.se.working.task.entity.Notification;
import com.se.working.task.entity.TeacherTask;
import com.se.working.util.FileUtils;
import com.se.working.util.StringUtils;

@Service
@Transactional
public class TaskService {

	@Autowired
	private TeacherTaskDao teacherTaskDao;
	@Autowired
	private FileTypeDao fileTypeDao;
	@Autowired
	private FileTaskDao fileTaskDao;
	@Autowired
	private FileTaskDetailDao fileTaskDetailDao;
	@Autowired
	private FileTaskStatusDao fileTaskStatusDao;
	@Autowired
	private NotificationDao notificationDao;
	@Autowired
	private AlidayuMessage alidayuMessage;

	/**
	 * 全部教师任务信息
	 * 
	 * @return
	 */
	public List<TeacherTask> findTeacherTasks() {
		return teacherTaskDao.find();
	}

	/**
	 * 创建任务，添加任务与教师的详细信息
	 * 
	 * @param task
	 * @param teachers
	 * @return
	 * @throws SEWMException 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public long addFileTask(FileTask fileTask, long filetypeid, long[] teachers, MultipartFile uploadFile, long userId) {
		// TODO Auto-generated method stub
		
		fileTask.setCreateUser(new TeacherTask(userId));
		fileTask.setFileType(new FileType(filetypeid));
		fileTask.setCurrentStatus(new FileTaskStatus(FileTaskStatus.STARTED));
		fileTaskDao.persist(fileTask);
		fileTaskDao.flush();
		fileTaskDao.refresh(fileTask);
		// 创建任务文件夹，同时返回任务任务文件夹名称
		fileTask.setDirectory(FileUtils.getOrCreateTaskDirectory(fileTask.getId(), fileTask.getName()));

		// 创建模板文件
		if (!uploadFile.isEmpty()) {
			// 创建文件任务模板文件
			String ext = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
			String fileName = null;
			// 基于单文件、普通任务文件命名模板文件
			if (fileTask.isSingleFile()) {
				fileName = FileUtils.getSingalFileTaskTemplateName(fileTask.getName(), ext);
			} else {
				fileName = FileUtils.getFileTaskTemplateName(fileTask.getName(), ext);
			}
			fileTask.setTempleteFile(fileName);
			File file = FileUtils.getOrCreateFileTaskFile(fileTask.getDirectory(), fileName);
			FileUtils.transferTo(uploadFile, file);
		}

		// 创建任务及任务细节
		for (int i = 0; i < teachers.length; i++) {
			FileTaskDetail taskDetail = new FileTaskDetail();
			taskDetail.setFileTask(fileTask);
			taskDetail.setTeacher(teacherTaskDao.find(teachers[i]));
			fileTaskDetailDao.persist(taskDetail);
		}

		return fileTask.getId();
	}

	/**
	 * 返回相应状态的全部文件任务
	 * 
	 * @param statusId
	 * @return
	 */
	public List<FileTask> findFileTasksByStatusId(long statusId) {
		return new ArrayList<>(fileTaskStatusDao.find(statusId).getFileTasks());
	}

	/**
	 * 返回全部文件任务
	 * 
	 * @return
	 */
	public List<FileTask> findFileTasks() {
		return fileTaskDao.find();
	}

	/**
	 * 返回指定用户的全部任务
	 * 
	 * @param id
	 * @return
	 */
	public List<FileTask> findFileTasksByUserId(long id) {
		List<FileTask> fileTasks = new ArrayList<>();
		for (FileTaskDetail d : teacherTaskDao.find(id).getfDetails()) {
			fileTasks.add(d.getFileTask());
		}
		return fileTasks;
	}

	/**
	 * 所有文件类型
	 * 
	 * @return
	 */
	public List<FileType> findFileTypes() {
		return fileTypeDao.find();
	}

	/**
	 * 查找指定用户，指定任务状态，指定任务详细状态，的所有任务详细信息
	 * @param userId
	 * @param done
	 * @param statusId
	 * @return
	 */
	public List<FileTaskDetail> findFileTaskDetails(long userId, boolean done, long statusId) {
		return fileTaskDetailDao.listByUserId(userId, done, statusId);
	}

	/**
	 * 查找指定用户，指定任务详细状态，的所有任务详细信息
	 * 
	 * @param userId
	 * @param done
	 * @return
	 */
	public List<FileTaskDetail> findFileTaskDetails(long userId, boolean done) {
		return fileTaskDetailDao.listByUserId(userId, done);
	}

	/**
	 * 基于用户ID及任务ID查找任务细节，没有为空
	 * 
	 * @param userId
	 * @param fileTaskId
	 * @return
	 */
	public FileTaskDetail findfileTaskDetail(long userId, long fileTaskId) {
		return fileTaskDetailDao.getByUserIdAndFileTaskId(userId, fileTaskId);
	}

	/**
	 * 完成文件任务<br>
	 * 单一文件直接基于模板文件修改，不再为每位教师生成单独的文件
	 * @param userId
	 * @param fileTaskId
	 * @param uploadFile
	 * @throws SEWMException 
	 */
	public void implementFileTask(long userId, long fileTaskId, MultipartFile uploadFile) {
		FileTask task = fileTaskDao.find(fileTaskId);
		// 教师本任务细节
		FileTaskDetail detail = findfileTaskDetail(userId, fileTaskId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		detail.setCompleteTime(calendar);
		// 当前为开启状态
		if (task.getCurrentStatus().getId() == FileTaskStatus.STARTED) {
			detail.setDone(true);
		}
		if (!uploadFile.isEmpty()) {
			// 开始拼装文件名
			// 文件扩展名，没有点
			String ext = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
			// 单一文件直接基于模板文件修改，不再为每位教师生成单独的文件
			if (task.isSingleFile()) {
				// 单一文件名称
				String fileName = FileUtils.getSingalFileTaskTemplateName(task.getName(), ext);
				File file = FileUtils.getOrCreateFileTaskFile(task.getDirectory(), fileName);
				FileUtils.transferTo(uploadFile, file);
				// 删除原模板文件
				FileUtils.deleteFileTaskFile(task.getDirectory(), task.getTempleteFile());
				// 更新模板文件名称
				task.setTempleteFile(fileName);
			} else {
				// 上传任务教师姓名
				String userName = detail.getTeacher().getUser().getName();
				// 任务文件名称
				String fileName = FileUtils.getFileTaskName(task.getName(), userName, ext);
				detail.setFile(fileName);
				File file = FileUtils.getOrCreateFileTaskFile(task.getDirectory(), fileName);
				FileUtils.transferTo(uploadFile, file);
			}

		} else {
			// 如果上传空文件，并且曾经上传过文件，则删除原上传文件
			if (detail.getFile() != null) {
				FileUtils.deleteFileTaskFile(task.getDirectory(), detail.getFile());
				//  清空file字段
				detail.setFile(null);
			}
		}
	}

	/**
	 * 返回指定文件任务的全部参与人员
	 * 
	 * @param id
	 * @return
	 */
	public List<TeacherTask> findTeachersByFileTaskId(long id) {
		List<TeacherTask> teacherTasks = new ArrayList<>();
		FileTask task = fileTaskDao.find(id);
		for (FileTaskDetail d : task.getFileTaskDetails()) {
			teacherTasks.add(d.getTeacher());
		}
		return teacherTasks;
	}
	

	/**
	 * 删除相应任务，及所有文件
	 * 
	 * @param id
	 * @throws SEWMException
	 */
	public void deleteFileTask(long id) {
		FileTask task = findById(id);
		// 删除任务文件夹
		FileUtils.deleteDirectory(task.getDirectory());
		// 删除数据信息
		fileTaskDao.remove(findById(id));
	}

	/**
	 * 修改文件任务信息
	 * @param fileTask
	 * @param filetypeid
	 * @param teachers
	 * @param uploadFile
	 * @param userId
	 * @throws SEWMException 
	 */
	public void updateFileTask(FileTask fileTask, long filetypeid, long[] teachers, MultipartFile uploadFile,
			long userId) {
		FileTask old = findById(fileTask.getId());
		old.setComment(fileTask.getComment());
		old.setCreateUser(new TeacherTask(userId));
		old.setPoint(fileTask.getPoint());
		old.setFileType(new FileType(filetypeid));

		// 创建模板文件
		if (!uploadFile.isEmpty()) {
			File file = FileUtils.getOrCreateFileTaskFile(fileTask.getDirectory(), fileTask.getTempleteFile());
			
			FileUtils.transferTo(uploadFile, file);
		}

		// 删除原人员
		for (FileTaskDetail d : old.getFileTaskDetails()) {
			fileTaskDetailDao.remove(d);
		}
		// 创建新任务及任务细节
		for (int i = 0; i < teachers.length; i++) {
			FileTaskDetail taskDetail = new FileTaskDetail();
			taskDetail.setFileTask(old);
			taskDetail.setTeacher(teacherTaskDao.find(teachers[i]));
			fileTaskDetailDao.persist(taskDetail);
		}
	}
	
	/**
	 * 关闭文件任务
	 * @param task
	 * @param undoneUserId
	 */
	public void closeFileTask(long filetaskid, long[] undoneUserId) {
		FileTask task = findById(filetaskid);
		// 先将所有人置为完成
		for (FileTaskDetail d : task.getFileTaskDetails()) {
			d.setDone(true);
			// 如果没有时间，即可能没有正式完成，则已当前时间
			if (d.getCompleteTime() == null) {
				Calendar completeTime = Calendar.getInstance();
				completeTime.setTime(new Date());
				d.setCompleteTime(completeTime);
			}
		}
		task.setCurrentStatus(new FileTaskStatus(FileTaskStatus.CLOSED));
		
		if (undoneUserId.length >  0) {
			for (int i = 0; i < undoneUserId.length; i++) {
				FileTaskDetail detail = fileTaskDetailDao.getByUserIdAndFileTaskId(undoneUserId[i], task.getId());
				detail.setDone(false);
			}
		}
	}
	
	/**
	 * 添加通知
	 * @param notification
	 * @param teacherIds
	 */
	public Notification addNotification(Notification notification, long[] teacherIds) {
		Set<TeacherTask> teachers = new LinkedHashSet<>();
		for (int i = 0; i < teacherIds.length; i++) {
			teachers.add(new TeacherTask(teacherIds[i]));
		}
		notification.setTeachers(teachers);
		notificationDao.persist(notification);
		notificationDao.flush();
		notificationDao.refresh(notification);
		alidayuMessage.sendNotification(notification);
		// 同步至数据库
		notificationDao.flush();
		// 更新
		notificationDao.refresh(notification);
		alidayuMessage.sendNotification(notification);
		return notification;
	}
	
	public FileTask findById(long id) {
		return fileTaskDao.find(id);
	}
}
