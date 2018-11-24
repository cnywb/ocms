package com.ternnetwork.baseframework.dao.base;

import java.io.Serializable; 
import java.util.List; 

import javax.persistence.LockModeType;

import org.springside.modules.orm.hibernate.Page;

public interface IBaseDao<T> { 
	public T findById(Class<T> clazz, Serializable id); 
	public T findById(Class<T> clazz, Serializable id,LockModeType lockModeType);
	public void persist(T t); 
	public void delete(T t); 
	public List<T> findAll(String jpql, Object... param); 
	public List<Object[]> queryAllObjectArray(String jpql, Object... param);
	public T getReferenceById(Class<T> clazz, Serializable id);
	public void update(T t); 
	public Long getTotalCount(String countJpql, Object... param) ;
	public Object getSingleResult(String countJpql, Object... param);
	public Object getSingleResultByNativeSQL(String jpql, Object... param);
	public int bulkUpdate(String jpql, Object... param);
	public int bulkUpdateByNativeSQL(String sql, Object... param);
	public Page<T> query(String countJpql,String jpql,Page page,Object... param);
	public Page<Object[]> queryObjectArray(String countJpql,String jpql,Page page,Object... param);
	public List<Object[]> queryAllObjectArrayByNativeSQL(String sql, Object... param);
	public void saveOrUpdate(T t) ;
	public void lock(T t,LockModeType lockModeType);
	public void refresh(T t,LockModeType lockModeType);
	public void refresh(T t);
	public List<Object> queryAllObject(String jpql, Object... param) ;
	public void evictEntity(Class clazz, Serializable id);
} 

