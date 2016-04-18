package com.se.working.project.dao;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.project.entity.ProjectFileDetail;
import com.se.working.util.EnumConstant;

@Repository
public class ProjectFileDetailDao extends GenericDao<ProjectFileDetail, Long> {
	
	/**
	 * 根据typeId查询文件详细信息总数
	 * @param typeId
	 * @return
	 */
	public long getCountByTypeId(long typeId){
		String HQL = "SELECT count(*) FROM ProjectFileDetail p WHERE p.projectFileType =:typeId";
		return (long) getSessionFactory().getCurrentSession().createQuery(HQL).setLong("typeId", typeId).uniqueResult();
	}
	
	/**
	 * 根据typeId查询文件详细信息
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectFileDetail> listByTypeId(long typeId, int page){
		String HQL = "FROM ProjectFileDetail p WHERE p.projectFileType =:typeId";
		return getSessionFactory().getCurrentSession().createQuery(HQL).setLong("typeId", typeId)
				.setFirstResult((page-1) * EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 根据teacherId和fileTypeId查询文件详细信息
	 * @param teacherId
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectFileDetail> listByTeacherIdAndTypeId(long teacherId, long typeId){
		String HQL = "FROM ProjectFileDetail p WHERE p.title.teacher.id =:teacherId AND p.projectFileType =:typeId";
		return getSessionFactory().getCurrentSession().createQuery(HQL).setLong("teacherId", teacherId).setLong("typeId", typeId).list();
	}
	
	/**
	 * 根据titleId和fileTypeId查询ProjectFileDetail
	 * @param titleId
	 * @param typeId
	 * @return
	 */
	public ProjectFileDetail getByTitleIdAndTypeId(long titleId, long typeId){
		String HQL = "FROM ProjectFileDetail p WHERE p.title.id =:titleId AND p.projectFileType.id =:typeId";
		return (ProjectFileDetail) getSessionFactory().getCurrentSession().createQuery(HQL).setLong("titleId", titleId).setLong("typeId", typeId).uniqueResult();
	}
}
