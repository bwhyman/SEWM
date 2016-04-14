package com.se.working.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.entity.Student;

@Repository
public class StudentDao extends GenericDao<Student, Long> {

	public Student getBypassword(String employeeNumber, String password) {
		String HQL = "FROM Student u WHERE u.studentId=:studentId AND u.password=:password";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL)
				.setString("studentId", employeeNumber)
				.setString("password", password);
		return (Student) query.uniqueResult();
	}
	
	/**
	 * 删除学生信息
	 * @return
	 */
	public int delStudents(){
		String HQL = "DELETE FROM Student u WHERE 1=1";
		return getSessionFactory().getCurrentSession().createQuery(HQL).executeUpdate();
	}
}
