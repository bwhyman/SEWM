package com.se.working.invigilation.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.invigilation.dao.InviInfoDao;
import com.se.working.invigilation.dao.InviStatusDetailDao;
import com.se.working.invigilation.dao.MessageStatusDetailDao;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.invigilation.entity.InvigilationInfoStatusDetail;
import com.se.working.invigilation.entity.InvigilationStatusType;
import com.se.working.invigilation.entity.MessageStatusDetail;
import com.se.working.invigilation.entity.MessageStatusType;
import com.se.working.message.AlidayuMessage;

@Service
@Transactional
public class InviTimer {

	@Autowired
	private InviInfoDao infoDao;
	@Autowired
	private MessageStatusDetailDao messsageDetailDao;
	@Autowired
	private AlidayuMessage aMessage;

	/**
	 * 查询当前时间24hrs以内的已分配及未分配监考
	 */
	// @Scheduled(cron = "30 23 * * * ? ")
	public void inviRemind() {
		Date CurrentDateTime = new Date();
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(CurrentDateTime);
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(CurrentDateTime);
		// 支持跨月日期
		endTime.add(Calendar.DAY_OF_MONTH, 1);
		
		// 基于已分配，发送监考提醒
		List<InvigilationInfo> infos = infoDao.find(startTime, endTime, InvigilationStatusType.ASSIGNED);
		
		
		for (InvigilationInfo i : infos) {
			aMessage.sendInviRemind(i);
			
			for (Invigilation inv : i.getInvigilations()) {
				MessageStatusDetail detail = new MessageStatusDetail();
				detail.setInvigilation(inv);
				detail.setType(new MessageStatusType(MessageStatusType.REMINDED));
				messsageDetailDao.persist(detail);
			}
		}
	}
	
	

	public InviTimer() {
		// TODO Auto-generated constructor stub
	}

}
