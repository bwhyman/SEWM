package com.se.working.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 基于泛型的通用Dao层实现
 * @author BO
 *
 * @param <T>
 * @param <ID>
 */
@Repository
public abstract class GenericDao<T, ID extends Serializable> {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void persist(T entity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().persist(entity);
	}
	public void delete(T entity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(entity);
	}

	public void update(T entity) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(entity);
	}

	public T get(Class<T> clazz, ID id) {
		// TODO Auto-generated method stub
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(Class<T> clazz) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createCriteria(clazz).list();
	}
	
	/**
	 * 批量添加信息
	 * @param entities
	 */
	public int addBatch(List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			getSessionFactory().getCurrentSession().persist(entities.get(i));
			if ((i % 20) == 0) {
				getSessionFactory().getCurrentSession().flush();
			}
		}
		return entities.size();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public GenericDao() {
		// TODO Auto-generated constructor stub
	}

}
