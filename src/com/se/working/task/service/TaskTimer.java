package com.se.working.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskTimer {

	@Autowired
	private TaskService taskService;
	/**
	 * 每隔5min检查当前已开启文件任务是否有文件任务过期
	 * 	若过期，则设置过期状态，未完成的的自动减分
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void updateTaskStatus(){
		taskService.updateTaskStatusByTimer();
	}
}
