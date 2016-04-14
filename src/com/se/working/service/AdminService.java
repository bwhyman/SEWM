package com.se.working.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.ClassesDao;
import com.se.working.dao.StudentDao;
import com.se.working.dao.TeacherTitleDao;
import com.se.working.dao.UserDao;
import com.se.working.entity.Classes;
import com.se.working.entity.Student;
import com.se.working.entity.TeacherTitle;
import com.se.working.entity.User;
import com.se.working.entity.UserAuthority;
import com.se.working.entity.UserAuthority.UserAuthorityLevel;
import com.se.working.entity.UserAuthority.UserAuthorityType;
import com.se.working.exception.SEWMException;
import com.se.working.invigilation.dao.TeacherInviDao;
import com.se.working.invigilation.entity.TeacherInvigilation;
import com.se.working.project.dao.StudentProjectDao;
import com.se.working.project.entity.StudentProject;
import com.se.working.util.MD5;
import com.se.working.util.StudentExcelUtil;



/**
 * 用户相关业务逻辑处理
 * 
 * @author BO
 *
 */
@Service
@Transactional
public class AdminService extends GenericService<User, Long> {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TeacherInviDao teacherInviDao;
	@Autowired
	private TeacherTitleDao teacherTitleDao;
	@Autowired
	private StudentProjectDao studentProjectDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ClassesDao classesDao;

	/**
	 * 查询所有通知者
	 * @return
	 */
	public List<User> findNotifusers(){
		List<User> users = new ArrayList<>();
		for (User user : userDao.list()) {
			users.add(user);
		}
		return users;
	}
	
	/**
	 * 清除所有学生信息（仅限于导入信息有误）
	 */
	public void clearStudents(){
		studentProjectDao.deleteAll();
		studentDao.delStudents();
	}
	
	/**
	 * 根据id删除学生信息（用于信息上传错误）
	 * @param studentId
	 */
	public void delStudent(long studentId){
		studentProjectDao.delete(new StudentProject(studentId));
		studentDao.delete(new Student(studentId));
	}
	
	/**
	 * 查询所有学生信息
	 * @return
	 */
	public List<Student> findStudents(){
		return studentDao.list();
	}
	
	/**
	 * 导入学生信息
	 * @param file
	 * @return
	 */
	public List<Student> importStudent(File file){
		List<Student> students = new ArrayList<>();
		students = StudentExcelUtil.getExcel(file);
		if (students == null) {
			if (file.exists()) {
				file.delete();
			}
			throw new SEWMException("读取学生信息为空");
		}
		List<Student> oldStudents = studentDao.list();
		for (Student student : students) {
			boolean isExist = false;
			for (Student student2 : oldStudents) {
				if (student2.getStudentId().equals(student.getStudentId())) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				student.setPassword(MD5.generateMD5(student.getStudentId()));
				student.setUserAuthority(new UserAuthority(UserAuthorityType.STUDENT));
				Classes classes = student.getClasses();
				Classes classes2 = classesDao.getByName(classes.getName());
				if (classes2 != null) {
					classes.setId(classes2.getId());
				}else {
					classesDao.persist(classes);
					classesDao.flush();
					classesDao.refresh(classes);
				}
				studentDao.persist(student);
				studentDao.flush();
				studentDao.refresh(student);
				StudentProject studentProject = new StudentProject();
				studentProject.setStudent(student);
				studentProjectDao.persist(studentProject);
				studentProjectDao.flush();
			}
		}
			
		return students;
	}
	
	/**
	 * 添加用户，需重写 单向关系，没有级联，需手动创建关联对象
	 */
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		user.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
		// user.setPassword(MD5.generateMD5(user.getEmployeeNumber()));
		userDao.persist(user);
		TeacherInvigilation teacherInvigilation = new TeacherInvigilation();
		teacherInvigilation.setUser(user);
		teacherInviDao.persist(teacherInvigilation);

	}
	
	/**
	 * 初始密码重置为用户名，即学号
	 * 
	 * @param studentId
	 */
	public void updateStudentDefaultPassword(long studentId) {
		Student student = studentDao.get(studentId);
		student.setPassword(MD5.generateMD5(student.getStudentId()));
	}

	/**
	 * 初始密码重置为用户名，即员工号
	 * 
	 * @param userId
	 */
	public void updateDefaultPassword(long userId) {
		User user = userDao.get(userId);
		user.setPassword(MD5.generateMD5(user.getEmployeeNumber()));
	}

	/**
	 * 除超级管理员全部修改为普通权限<br>
	 * 再将新管理员修改为管理员权限
	 * 
	 * @param newAdmins
	 */
	public void updateAdmins(long[] newAdmins) {
		for (User teacher : userDao.list()) {
			if (teacher.getUserAuthority().getLevel() <= UserAuthorityLevel.ADAMIN) {
				teacher.setUserAuthority(new UserAuthority(UserAuthorityType.TEACHER));
			}

		}
		for (int i = 0; i < newAdmins.length; i++) {
			User user = userDao.get(newAdmins[i]);
			if (user.getUserAuthority().getLevel() < UserAuthorityLevel.SUPERADMIN) {
				user.setUserAuthority(new UserAuthority(UserAuthorityType.ADAMIN));
			}
		}
	}

	/**
	 * 
	 * @return 所有职称
	 */
	public List<TeacherTitle> findTeacherTitles() {
		return teacherTitleDao.list();
	}


	/**
	 * 
	 * @param invqs 特殊监考次数
	 * @param checkeds 开启推荐用户IDs
	 */
	public void updateTeacherInviSetting(int[] invqs, long[] checkeds) {
		for (int i = 0; i < invqs.length; i++) {
			teacherInviDao.list().get(i).setSqecQuantity((invqs[i]));
		}
		for (TeacherInvigilation t : teacherInviDao.list()) {
			boolean checked = false;
			for (int i = 0; i < checkeds.length; i++) {
				if (t.getId() == checkeds[i]) {
					checked = true;
					break;
				}
			}
			t.setEnabledRecommend(checked);
		}
	}

	/**
	 * 修改用户通知设置，同时修改用户监考推荐设置
	 * @param checkeds 开启通知用户IDs
	 */
	public void updateUserNotifSetting(long[] checkeds) {
		for (User u : userDao.list()) {
			boolean checked = false;
			for (int i = 0; i < checkeds.length; i++) {
				if (u.getId() == checkeds[i]) {
					checked = true;
					break;
				}
			}
			u.setEnabledMessage(checked);
		}
		// 修改监考推荐设置
		for (TeacherInvigilation t : teacherInviDao.list()) {
			boolean checked = false;
			for (int i = 0; i < checkeds.length; i++) {
				if (t.getId() == checkeds[i]) {
					checked = true;
					break;
				}
			}
			t.setEnabledRecommend(checked);
		}
	}
	/**
	 * 所有通知关闭用户
	 * @return
	 */
	public List<User> findDisabledUsers() {
		return userDao.listDisableds();
	}
}
