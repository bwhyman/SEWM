package com.se.working.task.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.task.entity.Notification;
@Repository
public class NotificationDao extends GenericDao<Notification, Long> {

	/**
	 * 查询已过期的通知
	 * @param currentCalendar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> listExpired(Calendar currentCalendar){
		String HQL = "FROM Notification n WHERE n.endTime <=:currentCalendar";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("currentCalendar", currentCalendar);
		return query.list();
	}
	
	/**
	 * 查询未过期的通知
	 * @param currentCalendar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> listStarted(Calendar currentCalendar){
		String HQL = "FROM Notification n WHERE n.endTime >:currentCalendar";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("currentCalendar", currentCalendar);
		return query.list();
	}
	
}
