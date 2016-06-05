package com.se.working.task.dao;


import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.task.entity.FileTask;
@Repository
public class FileTaskDao extends GenericDao<FileTask, Long> {

	/**
	 * 查询截止时间早于当前时间且状态为开启的文件任务
	 * @param calendar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FileTask> listByStatusId(long statusId, Calendar calendar){
		String HQL = "FROM FileTask f WHERE f.currentStatus.id =:statusId AND f.endTime <=:calendar";
		return getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("statusId", statusId)
				.setCalendar("calendar", calendar)
				.list();
	}
}
