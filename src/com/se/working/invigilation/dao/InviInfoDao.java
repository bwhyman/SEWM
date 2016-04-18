package com.se.working.invigilation.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.InvigilationInfo;
import com.se.working.util.EnumConstant;
@Repository
public class InviInfoDao extends GenericDao<InvigilationInfo, Long>{
	
	/**
	 * 分页查询某老师的所有监考信息
	 * @param userId
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> listByUserIdPage(long userId, int page){
		String HQL = "SELECT i.invInfo FROM Invigilation i WHERE i.teacher.user.id = :userId";
		return getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("userId", userId)
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 根据状态类型分页查询
	 * @param typeId
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> listByTypeIdPage(long typeId, int page){
		String HQL = "FROM InvigilationInfo i WHERE i.currentStatusType.id =:typeId";
		return getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("typeId", typeId).setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> listByPage(int page){
		return getSessionFactory().getCurrentSession().createQuery("FROM InvigilationInfo")
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 基于时间查找相应监考，全部监考状态<br>
	 * 查询条件：开始时间在监考时间内，或，结束时间在监考时间内，或，开始时间在监考时间前同时结束时间在监考时间后
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> listInviInfos(Calendar startTime, Calendar endTime) {
		String HQL = "FROM InvigilationInfo i WHERE (:st>=i.startTime AND :st<=i.endTime) "
				+ "OR (:et>=i.startTime AND :et<=i.endTime) "
				+ "OR(:st<=i.startTime AND :et>=i.endTime)";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("st", startTime);
		query.setCalendar("et", endTime);
		
		return query.list();
	}
	
	/**
	 * 查找当日全部监考
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> listInviInfosByDate(Calendar date) {
		Calendar cDate = Calendar.getInstance();
		cDate.setTime(date.getTime());
		cDate.set(Calendar.HOUR_OF_DAY, 0);
		cDate.set(Calendar.MINUTE, 0);
		Calendar nDate = Calendar.getInstance();
		nDate.setTime(cDate.getTime());
		nDate.add(Calendar.DATE, 1);
		
		String HQL = "FROM InvigilationInfo i WHERE i.startTime >= :date AND i.endTime<=:nextDate";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("date", cDate);
		query.setCalendar("nextDate", nDate);
		return query.list();
	}
	
	/**
	 * 基于指定时间，查询指定类型的监考信息
	 * @param startTime
	 * @param endTime
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> listInviInfos(Calendar startTime, Calendar endTime, long typeId) {
		String HQL = "FROM InvigilationInfo i WHERE (:st>=i.startTime AND :st<=i.endTime) "
				+ "OR (:et>=i.startTime AND :et<=i.endTime) "
				+ "OR(:st<=i.startTime AND :et>=i.endTime) AND i.currentStatusType.id = :typeId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setCalendar("st", startTime);
		query.setCalendar("et", endTime);
		query.setLong("typeId", typeId);
		return query.list();
	}
}
