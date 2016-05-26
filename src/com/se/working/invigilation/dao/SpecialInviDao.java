package com.se.working.invigilation.dao;

import java.util.Calendar;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.SpecialInvigilation;
@Repository
public class SpecialInviDao extends GenericDao<SpecialInvigilation, Long> {

	/**
	 * 指定用户还未完成的监考总数
	 * @param userId
	 * @return
	 */
	public long getCountUndo(long userId){
		String HQL = "SELECT COUNT(*) FROM SpecialInvigilation sp WHERE sp.teacher.id =:userId AND sp.specInv.dateTime >:currentTime";
		return (long) getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("userId", userId)
				.setCalendar("currentTime", Calendar.getInstance())
				.uniqueResult();
	}
	
	public SpecialInviDao() {
		// TODO Auto-generated constructor stub
	}

	
}
