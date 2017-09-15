package com.se.working.task.dao;


import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.task.entity.FileTaskDetail;
@Repository
public class FileTaskDetailDao extends GenericDao<FileTaskDetail> {

	/**
	 * 指定用户、指定任务的任务详细信息
	 * @param userId
	 * @param filetaskId
	 * @return
	 */
	public FileTaskDetail getByUserIdAndFileTaskId(long userId, long filetaskId) {
		String HQL = "FROM FileTaskDetail f WHERE f.fileTask.id=:filetaskId AND f.teacher.id=:userId";
		 Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("filetaskId", filetaskId);
		query.setParameter("userId", userId);
		return (FileTaskDetail) query.getSingleResult();
	}
	
	/**
	 * 指定用户，指定任务状态，指定完成状态，的任务详细信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileTaskDetail> listByUserId(long userId, boolean done, long statusId) {
		String HQL = "FROM FileTaskDetail f WHERE f.done=:done AND f.teacher.id=:userId AND f.fileTask.currentStatus.id=:statusId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("userId", userId);
		query.setParameter("done", done);
		query.setParameter("statusId", statusId);
		return query.getResultList();
	}
	/**
	 * 指定用户，指定任务状态，的任务详细信息
	 * @param userId
	 * @param done
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileTaskDetail> listByUserId(long userId, boolean done) {
		String HQL = "FROM FileTaskDetail f WHERE f.done=:done AND f.teacher.id=:userId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("userId", userId);
		query.setParameter("done", done);
		return query.getResultList();
	}
	
	
}
