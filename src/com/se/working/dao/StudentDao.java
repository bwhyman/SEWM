package com.se.working.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.entity.Student;
import com.se.working.util.EnumConstant;

@Repository
public class StudentDao extends GenericDao<Student, Long> {

	/**
	 * 分页查询学生信息
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Student> listByPage(int page){
		return getSessionFactory().getCurrentSession().createQuery("FROM Student")
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 根据用户名密码查找用户信息
	 * @param employeeNumber
	 * @param password
	 * @return
	 */
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
