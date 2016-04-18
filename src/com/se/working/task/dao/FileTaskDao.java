package com.se.working.task.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.task.entity.FileTask;
import com.se.working.util.EnumConstant;
@Repository
public class FileTaskDao extends GenericDao<FileTask, Long> {

	/**
	 * 分页查询FileTask
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileTask> listByPage(int page){
		return getSessionFactory().getCurrentSession().createQuery("FROM FileTask")
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 根据文件任务当前状态分页查询
	 * @param statusId
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileTask> listByStatusIdPage(long statusId, int page){
		String HQL = "FROM FileTask f WHERE f.currentStatus.id =:statusId";
		return getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("statusId", statusId)
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
}
