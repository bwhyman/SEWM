package com.se.working.project.dao;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.project.entity.ProjectFileDetail;

@Repository
public class ProjectFileDetailDao extends GenericDao<ProjectFileDetail, Long> {
	
	/**
	 * 根据文件类型id查找毕设题目详细信息
	 * @param teacherId
	 * @param projectFileTypeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectFileDetail> listByFileTypeId(long projectFileTypeId){
		String HQL = "FROM ProjectFileDetail pfd WHERE pfd.projectFileType.id=:projectFileTypeId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("projectFileTypeId", projectFileTypeId);
		return query.list();
	}
	
	/**
	 * 根据teacherid和文件类型id查找毕设题目详细信息
	 * @param teacherId
	 * @param projectFileTypeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectFileDetail> listByTeacherIdAndFileTypeId(long teacherId, long projectFileTypeId){
		String HQL = "FROM ProjectFileDetail pfd WHERE pfd.title.teacher.id=:teacherId AND pfd.projectFileType.id=:projectFileTypeId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("teacherId", teacherId);
		query.setLong("projectFileTypeId", projectFileTypeId);
		return query.list();
	}
	/**
	 * 删除已存在的文件记录
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public int deleteProjectFileDetailByDirectoryAndName(String directory, String fileName){
		String HQL = "DELETE FROM ProjectFileDetail pfd WHERE pfd.directory=:directory AND pfd.fileName=:fileName";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setString("directory", directory);
		query.setString("fileName", fileName);
		return query.executeUpdate();
	}
	
	/**
	 * 根据文件夹和文件名称查找文件详细信息
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public ProjectFileDetail getProjectFileDetailByDirectoryAndName(String directory, String fileName){
		String HQL = "FROM ProjectFileDetail pfd WHERE pfd.directory=:directory AND pfd.fileName like :fileName";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setString("directory", directory);
		query.setString("fileName", fileName + "%");
		return (ProjectFileDetail) query.uniqueResult();
	}
	
	/**
	 * 根据studentid和文件类型id查询毕设文档详细信息
	 * @param titleId
	 * @param projectFileTypeId
	 * @return
	 */
	public ProjectFileDetail getByStudentIdAndFileTypeId(long studentId, long projectFileTypeId){
		String HQL = "FROM ProjectFileDetail pfd WHERE pfd.student.user.id=:studentId AND pfd.projectFileType.id=:projectFileTypeId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("studentId", studentId);
		query.setLong("projectFileTypeId", projectFileTypeId);
		return (ProjectFileDetail) query.uniqueResult();
	}
}
