package com.se.working.invigilation.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.Invigilation;


@Repository
public class InviDao extends GenericDao<Invigilation> {
	@SuppressWarnings("unchecked")
	// 基于指定教师、指定监考状态，查询监考分配信息
	public List<Invigilation> findInvis(long userId, long typeId) {
		String HQL = "FROM Invigilation i WHERE i.teacher.id=:userId AND i.invInfo.currentStatusType.id = :typeId ORDER BY i.invInfo.startTime";
		Query query = getCurrentSession().createQuery(HQL);
		query.setParameter("userId", userId);
		query.setParameter("typeId", typeId);
		return query.getResultList();
	}
}
