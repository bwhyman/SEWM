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
	 * 根据通知是否过期查询
	 * @param type
	 * @return
	 */
	public List<Notification> findNotifiByEndTime(String type){
		Calendar currentCalendar = Calendar.getInstance();
		List<Notification> notifications = null;
		switch (type) {
		case "expired":
			notifications = notificationDao.listExpired(currentCalendar);
			break;
		case "started":
			notifications = notificationDao.listStarted(currentCalendar);
			break;
		}
		return notifications;
	}
}
