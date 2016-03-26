package com.se.working.invigilation.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.Invigilation;

/**
 * 查询所有指定用户、指定监考状态的监考安排
 * @author BO
 *
 */
@Repository
public class InviDao extends GenericDao<Invigilation, Long>{
	@SuppressWarnings("unchecked")
	public List<Invigilation> listInvisByUserIdAndTypeId(long userId, long typeId) {
		String HQL = "FROM Invigilation i WHERE i.teacher.id=:userId AND i.invInfo.currentStatusType.id = :typeId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("userId", userId);
		query.setLong("typeId", typeId);
		return query.list();
	}
}
