package com.se.working.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.se.working.dao.GenericDao;
import com.se.working.project.entity.Evaluation;
import com.se.working.util.EnumConstant;

@Repository
public class EvaluationDao extends GenericDao<Evaluation, Long> {

	/**
	 * 根据typeId分页获取评审结果
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Evaluation> listByTypeIdPage(long typeId, int page){
		String HQL = "FROM Evaluation e WHERE e.fileType.id =:typeId";
		return getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("typeId", typeId)
				.setFirstResult((page-1)*EnumConstant.values()[0].getPageCount())
				.setMaxResults(EnumConstant.values()[0].getPageCount()).list();
	}
	/**
	 * 根据typeId获取评审结果
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Evaluation> listByTypeId(long typeId){
		String HQL = "FROM Evaluation e WHERE e.fileType.id =:typeId";
		return getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("typeId", typeId).list();
	}
	
	/**
	 * 根据student和typeId获取评审结果
	 * @param studentId
	 * @param typeId
	 * @return
	 */
	public Evaluation getByStudentIdTypeId(long studentId, long typeId){
		String HQL = "FROM Evaluation e WHERE e.student.id =:studentId AND e.fileType.id =:typeId";
		return (Evaluation) getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("studentId", studentId)
				.setLong("typeId", typeId).uniqueResult();
	}
	
	/**
	 * 查询管理员评审已通过的人数
	 * @return
	 */
	public long getCountManagerEval(long typeId){
		String HQL = "SELECT COUNT(*) FROM Evaluation e WHERE e.managerEval =true AND e.fileType.id =:typeId";
		return (long) getSessionFactory().getCurrentSession().createQuery(HQL)
				.setLong("typeId", typeId).uniqueResult();
	}
}
