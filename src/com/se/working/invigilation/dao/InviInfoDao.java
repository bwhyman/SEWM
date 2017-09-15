package com.se.working.invigilation.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.InvigilationInfo;

@Repository
public class InviInfoDao extends GenericDao<InvigilationInfo> {
	
	public InvigilationInfo findInviInfo(long inviinfoId, long groupId) {
		String HQL = "FROM InvigilationInfo i WHERE i.id=:inviinfoId AND i.groups.id=:groupId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("inviinfoId", inviinfoId);
		query.setParameter("groupId", groupId);
		return (InvigilationInfo) query.getSingleResult();
	}
	
	/**
	 * 基于时间查找相应监考，全部监考状态<br>
	 * 基于组<br>
	 * 查询条件：开始时间在监考时间内，或，结束时间在监考时间内，或，开始时间在监考时间前同时结束时间在监考时间后
	 * @param startTime
	 * @param endTime
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> find(Calendar startTime, Calendar endTime, long groupId) {
		String HQL = "FROM InvigilationInfo i WHERE (i.groups.id=:groupId) AND (:st>=i.startTime AND :st<=i.endTime) "
				+ "OR (:et>=i.startTime AND :et<=i.endTime) " + "OR(:st<=i.startTime AND :et>=i.endTime)";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("st", startTime);
		query.setParameter("et", endTime);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}

	/**
	 * 基于指定时间，查询指定类型的监考信息
	 * 
	 * @param startTime
	 * @param endTime
	 * @param typeId
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	public List<InvigilationInfo> listInviInfos(Calendar startTime, Calendar endTime, long groupId, long typeId) {
		String HQL = "FROM InvigilationInfo i WHERE (:st>=i.startTime AND :st<=i.endTime) "
				+ "OR (:et>=i.startTime AND :et<=i.endTime) "
				+ "OR(:st<=i.startTime AND :et>=i.endTime) AND i.currentStatusType.id = :typeId";
		Query query = getCurrentSession().createQuery(HQL);
		query.setCalendar("st", startTime);
		query.setCalendar("et", endTime);
		query.setLong("typeId", typeId);
		return query.list();
	}*/

	/**
	 * 基于指定监考信息状态，分页查询
	 * 
	 * @param inviTypeId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> find(long groupId , long inviTypeId, int firstResult, int maxResults) {
		String HQL = "FROM InvigilationInfo i WHERE i.groups.id=:groupId AND i.currentStatusType.id = :typeId ORDER BY startTime";
		Query query = getCurrentSession().createQuery(HQL);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		query.setParameter("typeId", inviTypeId);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> find(long groupId, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		String HQL = "FROM InvigilationInfo i WHERE i.groups.id=:groupId ORDER BY startTime";
		Query query = getCurrentSession().createQuery(HQL);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<InvigilationInfo> findInviInfos(long groupId) {
		// TODO Auto-generated method stub
		String HQL = "FROM InvigilationInfo i WHERE i.groups.id=:groupId ORDER BY startTime";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("groupId", groupId);
		return (List<InvigilationInfo>) query.getResultList();
	}

}
