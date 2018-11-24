package com.ternnetwork.baseframework.dao.impl.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.dao.base.IBaseDao;
import com.ternnetwork.baseframework.util.CacheUtils;

@Transactional
@Repository("iBaseDao")
public class IBaseDaoImpl<T> implements IBaseDao<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	public void delete(T t) {
		entityManager.remove(t);
	}

	// @Transactional(propagation=Propagation.SUPPORTS)
	public T findById(Class<T> clazz, Serializable id) {

		return entityManager.find(clazz, id);
	}

	public T findById(Class<T> clazz, Serializable id, LockModeType lockModeType) {
		return entityManager.find(clazz, id, lockModeType);
	}

	public void evictEntity(Class clazz, Serializable id) {
		CacheUtils.evictEntity(entityManager, clazz, id);
	}

	// @Transactional(propagation=Propagation.SUPPORTS)
	public T getReferenceById(Class<T> clazz, Serializable id) {
		return entityManager.getReference(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<T> query(String countJpql, String jpql, Page page,
			Object... param) {
		Query query = entityManager.createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		query.setFirstResult(page.getPageNo() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setResult(query.getResultList());
		page.setTotalCount(Integer
				.valueOf(getTotalCount(countJpql, param) + ""));
		return page;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Object[]> queryObjectArray(String countJpql, String jpql,
			Page page, Object... param) {
		Query query = entityManager.createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		query.setFirstResult(page.getPageNo() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setResult(query.getResultList());
		page.setTotalCount(Integer
				.valueOf(getTotalCount(countJpql, param) + ""));
		return page;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<T> findAll(String jpql, Object... param) {
		Query query = entityManager.createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return query.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object[]> queryAllObjectArray(String jpql, Object... param) {
		Query query = entityManager.createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return query.getResultList();
	}
	
	public List<Object[]> queryAllObjectArrayByNativeSQL(String sql, Object... param) {
		Query query = entityManager.createNativeQuery(sql);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return query.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object> queryAllObject(String jpql, Object... param) {
		Query query = entityManager.createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Long getTotalCount(String countJpql, Object... param) {
		Query query = entityManager.createQuery(countJpql);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return Long.parseLong(query.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object getSingleResult(String countJpql, Object... param) {
		Query query = entityManager.createQuery(countJpql);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}

		return query.getSingleResult();
	}

	public Object getSingleResultByNativeSQL(String jpql, Object... param) {
		Query query = entityManager.createNativeQuery(jpql);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return query.getSingleResult();
	}
	
	public int bulkUpdateByNativeSQL(String sql, Object... param) {
		Query query = entityManager.createNativeQuery(sql);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return query.executeUpdate();
	}

	public int bulkUpdate(String jpql, Object... param) {
		Query query = entityManager.createQuery(jpql);
		if (param != null) {
			for (int i = 1; i <= param.length; i++) {
				query.setParameter(i, param[i - 1]);
			}
		}
		return query.executeUpdate();
	}

	public void persist(T t) {
		entityManager.persist(t);
	}

	public void update(T t) {
		entityManager.merge(t);
	}

	public void saveOrUpdate(T t) {
		entityManager.merge(t);
	}

	public void lock(T t, LockModeType lockModeType) {
		entityManager.lock(t, lockModeType);
	}

	public void refresh(T t, LockModeType lockModeType) {
		entityManager.refresh(t, lockModeType);
	}
	public void refresh(T t) {
		entityManager.refresh(t);
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
