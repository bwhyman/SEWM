package com.se.working.dao;

import org.springframework.stereotype.Repository;

import com.se.working.entity.Classes;

@Repository
public class ClassesDao extends GenericDao<Classes, Integer> {

	/**
	 * 根据name查Classes
	 * @param name
	 * @return
	 */
	public Classes getByName(String name){
		String HQL = "FROM Classes c WHERE c.name =:name";
		return (Classes) getSessionFactory().getCurrentSession().createQuery(HQL).setString("name", name).uniqueResult();
	}
}
