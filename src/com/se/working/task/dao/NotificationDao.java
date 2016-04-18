package com.se.working.task.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.task.entity.Notification;
import com.se.working.util.EnumConstant;
@Repository
public class NotificationDao extends GenericDao<Notification, Long> {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> listByPage(int page){
		return getSessionFactory().getCurrentSession().createQuery("FROM Notification")
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount()).setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 分页查询已过期的通知
	 * @param currentCalendar
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> listExpiredByPage(Calendar currentCalendar, int page){
		String HQL = "FROM Notification n WHERE n.endTime <=:currentCalendar";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("currentCalendar", currentCalendar);
		return query.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	/**
	 * 查询已过期的通知总数
	 * @param currentCalendar
	 * @return
	 */
	public long getCountExpired(Calendar currentCalendar){
		String HQL = "SELECT COUNT(*) FROM Notification n WHERE n.endTime <=:currentCalendar";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("currentCalendar", currentCalendar);
		return (long) query.uniqueResult();
	}
	
	/**
	 * 分页查询未过期的通知
	 * @param currentCalendar
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> listStartedByPage(Calendar currentCalendar, int page){
		String HQL = "FROM Notification n WHERE n.endTime >:currentCalendar";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("currentCalendar", currentCalendar);
		return query.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 查询未过期的通知总数
	 * @param currentCalendar
	 * @return
	 */
	public long getCountStarted(Calendar currentCalendar){
		String HQL = "SELECT COUNT(*) FROM Notification n WHERE n.endTime >:currentCalendar";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("currentCalendar", currentCalendar);
		return (long) query.uniqueResult();
	}
	
}
