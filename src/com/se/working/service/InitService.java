package com.se.working.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.GroupsDao;
import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserAuthorityDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.Groups;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.exception.SEWMException;
import com.se.working.invigilation.dao.InviTypeDao;
import com.se.working.invigilation.dao.MessageStatusTypeDao;
import com.se.working.invigilation.dao.SpecialInviTypeDao;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.InvigilationStatusType;
import com.se.working.invigilation.entity.MessageStatusType;
import com.se.working.invigilation.entity.SpecialInvigilationType;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.task.dao.FileTaskStatusDao;
import com.se.working.task.dao.FileTypeDao;
import com.se.working.task.dao.TeacherTaskDao;
import com.se.working.task.entity.FileTaskStatus;
import com.se.working.task.entity.FileType;
import com.se.working.task.entity.TeacherTask;
import com.se.working.util.FileUtils;
import com.se.working.util.InitUsersUtils;
import com.se.working.util.MD5;

@Service
@Transactional
public class InitService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TeacherInviDao teacherInviDao;
	@Autowired
	private TeacherTaskDao teacherTaskDao;
	@Autowired
	private TeacherTitleDao teacherTitleDao;
	@Autowired
	private UserAuthorityDao userAuthorityDao;
	@Autowired
	private InviTypeDao invigilationStatusTypeDao;
	@Autowired
	private GroupsDao groupsDao;
	@Autowired
	private SpecialInviTypeDao specialInviTypeDao;
	@Autowired
	private MessageStatusTypeDao messageStatusTypeDao;
	@Autowired
	private FileTypeDao fileTypeDao;
	@Autowired
	private FileTaskStatusDao fileTaskStatusDao;

	/**
	 * 初始化职称
	 * 
	 * @return
	 */
	public void initTeacherTitle() {
		if (teacherTitleDao.find().size() == 0) {
			TeacherTitle lecture = new TeacherTitle();
			lecture.setName("讲师");
			teacherTitleDao.persist(lecture);
			TeacherTitle ap = new TeacherTitle();
			ap.setName("副教授");
			teacherTitleDao.persist(ap);
			TeacherTitle prof = new TeacherTitle();
			prof.setName("教授");
			teacherTitleDao.persist(prof);
			TeacherTitle ass = new TeacherTitle();
			ass.setName("助教");
			teacherTitleDao.persist(ass);
		}
	}

	/**
	 * 初始化权限
	 */
	public void initUserAuthority() {
		if (userAuthorityDao.find().size() == 0) {
			UserAuthority teacher = new UserAuthority();
			teacher.setName("教师");
			teacher.setLevel(UserAuthority.TEACHER_LEVEL);
			userAuthorityDao.persist(teacher);
			UserAuthority admin = new UserAuthority();
			admin.setName("管理员");
			admin.setLevel(UserAuthority.ADAMIN_LEVEL);
			userAuthorityDao.persist(admin);
			UserAuthority superadmin = new UserAuthority();
			superadmin.setName("超级管理员");
			superadmin.setLevel(UserAuthority.SUPERADMIN_LEVEL);
			userAuthorityDao.persist(superadmin);
			UserAuthority student = new UserAuthority();
			student.setName("学生");
			student.setLevel(UserAuthority.STUDENT_LEVEL);
			userAuthorityDao.persist(student);
		}
	}

	/**
	 * 初始化监考状态
	 */
	public void initInviStatusType() {
		if (invigilationStatusTypeDao.find().size() == 0) {
			InvigilationStatusType unass = new InvigilationStatusType();
			unass.setName("未分配");
			invigilationStatusTypeDao.persist(unass);
			InvigilationStatusType ass = new InvigilationStatusType();
			ass.setName("已分配");
			invigilationStatusTypeDao.persist(ass);
			InvigilationStatusType done = new InvigilationStatusType();
			done.setName("已完成");
			invigilationStatusTypeDao.persist(done);
		}
	}

	/**
	 * 初始化监考消息类型
	 */
	public void initInviMessageType() {
		if (messageStatusTypeDao.find().size() == 0) {
			MessageStatusType notice = new MessageStatusType();
			notice.setName("已通知");
			messageStatusTypeDao.persist(notice);

			MessageStatusType reminded = new MessageStatusType();
			reminded.setName("已提醒");
			messageStatusTypeDao.persist(reminded);
		}
	}

	public void initGroup() {
		if (groupsDao.find().size() == 0) {
			Groups group = new Groups();
			group.setName("软件工程专业");
			group.setInviRegexPrefix("软件");
			groupsDao.persist(group);
			Groups group2 = new Groups();
			group2.setName("会计专业");
			group2.setInviRegexPrefix("会计");
			groupsDao.persist(group2);
		}
	}

	public void initSpecInviType() {
		if (specialInviTypeDao.find().size() == 0) {
			SpecialInvigilationType en46 = new SpecialInvigilationType();
			en46.setName("英语四六级");
			specialInviTypeDao.persist(en46);

			SpecialInvigilationType servant = new SpecialInvigilationType();
			servant.setName("公务员");
			specialInviTypeDao.persist(servant);

			SpecialInvigilationType postgrad = new SpecialInvigilationType();
			postgrad.setName("研究生");
			specialInviTypeDao.persist(postgrad);
		}
	}

	public void initFileType() {
		if (fileTypeDao.find().size() == 0) {
			FileType fnone = new FileType();
			fnone.setName("无格式要求");
			fnone.setType("");
			fileTypeDao.persist(fnone);
			FileType doc = new FileType();
			doc.setName("Word文档");
			doc.setType(".doc,.docx");
			fileTypeDao.persist(doc);
			FileType xls = new FileType();
			xls.setName("Excel表格");
			xls.setType(".xls,.xlsx");
			fileTypeDao.persist(xls);
		}

	}

	public void initFileTaskStatus() {
		if (fileTaskStatusDao.find().size() == 0) {
			FileTaskStatus started = new FileTaskStatus();
			started.setName("已开启");
			fileTaskStatusDao.persist(started);
			FileTaskStatus expired = new FileTaskStatus();
			expired.setName("已过期");
			fileTaskStatusDao.persist(expired);
			FileTaskStatus closed = new FileTaskStatus();
			closed.setName("已关闭");
			fileTaskStatusDao.persist(closed);
		}
	}

	/**
	 * 
	 */
	public void importUser() {
		if (userDao.find().size() == 0) {
			List<User> users = null;
			try (InputStream is = FileUtils.getInitUsersExcel()) {
				users = InitUsersUtils.getUsers(is);
				if (users == null) {
					throw new SEWMException("初始化用户文件未发现用户信息");
				}
				for (User u : users) {
					u.setPassword(MD5.generateMD5(u.getEmployeeNumber()));
					userDao.persist(u);
					TeacherInvigilation t = new TeacherInvigilation();
					t.setUser(u);
					teacherInviDao.persist(t);
					TeacherTask task = new TeacherTask();
					task.setUser(u);
					task.setPoint(100);
					teacherTaskDao.persist(task);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new SEWMException("文件操作错误" + e.getMessage());
			}
		}

	}
}
