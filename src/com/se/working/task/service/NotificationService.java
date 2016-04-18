package com.se.working.task.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.service.GenericService;
import com.se.working.task.dao.NotificationDao;
import com.se.working.task.entity.Notification;

@Service
@Transactional
public class NotificationService extends GenericService<Notification, Long> {

	@Autowired
	private NotificationDao notificationDao;
	
	/**
	 * 分页查询通知消息
	 * @param page
	 * @return
	 */
	public List<Notification> findByPage(int page){
		return notificationDao.listByPage(page);
	}
	
	/**
	 * 根据状态信息查询总数
	 * @param type
	 * @return
	 */
	public long getCountByStatusType(String type){
		long count = 0;
		if (type.equals("expired")) {
			count = notificationDao.getCountExpired(Calendar.getInstance());
		}else if (type.equals("started")) {
			count = notificationDao.getCountStarted(Calendar.getInstance());
		}
		return count;
	}
	
	/**
	 * 根据通知是否过期查询
	 * @param type
	 * @return
	 */
	public List<Notification> findNotifiByEndTime(String type, int page){
		Calendar currentCalendar = Calendar.getInstance();
		List<Notification> notifications = null;
		switch (type) {
		case "expired":
			notifications = notificationDao.listExpiredByPage(currentCalendar, page);
			break;
		case "started":
			notifications = notificationDao.listStartedByPage(currentCalendar, page);
			break;
		}
		return notifications;
	}
}
