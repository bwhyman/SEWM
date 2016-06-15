package com.se.working.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.StudentDao;
import com.se.working.entity.Student;
import com.se.working.util.MD5;

@Service
@Transactional
public class StudentService extends GenericService<Student, Long> {

	@Autowired
	private StudentDao studentDao;
	
	public void updateStudentPassword(long studentId, String newPwd) {
		Student student = studentDao.get(studentId);
		student.setPassword(MD5.generateMD5(newPwd));
	}
	/**
	 * 学生登录验证
	 * @param userName
	 * @param password
	 * @return
	 */
	public Student findStudentByPassword(String studentId, String password) {
		return studentDao.getBypassword(studentId, MD5.generateMD5(password));
	}
}
