package com.se.working.invigilation.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.invigilation.entity.Invigilation;
import com.se.working.util.EnumConstant;

/**
 * 查询所有指定用户、指定监考状态的监考安排
 * @author BO
 *
 */
@Repository
public class InviDao extends GenericDao<Invigilation, Long>{
	@SuppressWarnings("unchecked")
	public List<Invigilation> listInvisByUserIdAndTypeId(long userId, long typeId, int page) {
		String HQL = "FROM Invigilation i WHERE i.teacher.id=:userId AND i.invInfo.currentStatusType.id = :typeId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("userId", userId);
		query.setLong("typeId", typeId);
		return query.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	
	/**
	 * 根据教师完成情况查询当前状态监考总数
	 * @param userId
	 * @param typeId
	 * @return
	 */
	public long getCountByUserIdAndTypeId(long userId, long typeId) {
		String HQL = "SELECT COUNT(*) FROM Invigilation i WHERE i.teacher.id=:userId AND i.invInfo.currentStatusType.id = :typeId";
		Query query = getSessionFactory().getCurrentSession().createQuery(HQL);
		query.setLong("userId", userId);
		query.setLong("typeId", typeId);
		return (long) query.uniqueResult();
	}
}
