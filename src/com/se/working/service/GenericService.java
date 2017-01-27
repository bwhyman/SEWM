package com.se.working.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.working.dao.GenericDao;


/**
 * 基于泛型的通用Service层实现
 * @author BO
 *
 * @param <T>
 * @param <ID>
 */
@Service
@Transactional
public abstract class GenericService<T> {
	@Autowired
	private GenericDao<T> genericDao;
	
	/**
	 * 添加
	 * @param entity
	 */
	public void add(T entity) {
		genericDao.persist(entity);
	}
	
	/**
	 * 修改
	 * @param entity
	 */
	public void update(T entity) {
		genericDao.update(entity);
	}
	/**
	 * 删除
	 * @param entity
	 */
	public void delete(T entity) {
		genericDao.delete(entity);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public T findById(long id) {
		return genericDao.get(id);
	}
	
	
	public GenericDao<T> getGenericDao() {
		return genericDao;
	}

	public void setGenericDao(GenericDao<T> genericDao) {
		this.genericDao = genericDao;
	}

}
