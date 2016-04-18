package com.se.working.task.dao;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.task.entity.FileTaskDetail;
import com.se.working.util.EnumConstant;
@Repository
public class FileTaskDetailDao extends GenericDao<FileTaskDetail, Long> {

	/**
	 * 指定用户、指定任务的任务详细信息
	 * @param userId
	 * @param filetaskId
	 * @return
	 */
	public FileTaskDetail getByUserIdAndFileTaskId(long userId, long filetaskId) {
		String HQL = "FROM FileTaskDetail f WHERE f.fileTask.id=:filetaskId AND f.teacher.id=:userId";
		 Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("filetaskId", filetaskId);
		query.setLong("userId", userId);
		return (FileTaskDetail) query.uniqueResult();
	}
	
	/**
	 * 指定用户，指定任务状态，指定完成状态，的任务详细信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileTaskDetail> listByUserId(long userId, boolean done, long statusId) {
		String HQL = "FROM FileTaskDetail f WHERE f.done=:done AND f.teacher.id=:userId AND f.fileTask.currentStatus.id=:statusId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("userId", userId);
		query.setBoolean("done", done);
		query.setLong("statusId", statusId);
		return query.list();
	}
	/**
	 * 指定用户，指定任务状态的任务详细信息
	 * @param userId
	 * @param done
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileTaskDetail> listByUserId(long userId, boolean done, int page) {
		String HQL = "FROM FileTaskDetail f WHERE f.done=:done AND f.teacher.id=:userId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("userId", userId);
		query.setBoolean("done", done);
		return query.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount()).setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 指定用户，指定任务状态的任务详细信息
	 * @param userId
	 * @param done
	 * @return
	 */
	public long getCountByUserId(long userId, boolean done) {
		String HQL = "SELECT COUNT(*) FROM FileTaskDetail f WHERE f.done=:done AND f.teacher.id=:userId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("userId", userId);
		query.setBoolean("done", done);
		return (long) query.uniqueResult();
	}
	
	
}
