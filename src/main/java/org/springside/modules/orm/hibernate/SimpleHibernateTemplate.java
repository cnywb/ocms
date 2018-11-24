/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package org.springside.modules.orm.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springside.modules.utils.BeanUtils;

public class SimpleHibernateTemplate<T, PK extends Serializable> {
	protected Logger logger = LoggerFactory.getLogger(super.getClass());
	protected SessionFactory sessionFactory;
	protected Class<T> entityClass;

	public SimpleHibernateTemplate(SessionFactory sessionFactory, Class<T> entityClass) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void save(T entity) {
		Assert.notNull(entity);
		getSession().saveOrUpdate(entity);
		this.logger.info("save entity: {}", entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity);
		getSession().delete(entity);
		this.logger.info("delete entity: {}", entity);
	}

	public void delete(PK id) {
		Assert.notNull(id);
		delete(get(id));
	}

	public List<T> findAll() {
		return findByCriteria(new Criterion[0]);
	}

	public Page<T> findAll(Page<T> page) {
		return findByCriteria(page, new Criterion[0]);
	}

	public T get(PK id) {
		return (T) getSession().load(this.entityClass, id);
	}

	public List find(String hql, Object[] values) {
		return createQuery(hql, values).list();
	}

	public Page<T> find(Page<T> page, String hql, Object[] values) {
		Assert.notNull(page);

		if (page.isAutoCount()) {
			this.logger.warn("HQL查询暂不支持自动获取总结果数,hql为{}", hql);
		}
		Query q = createQuery(hql, values);
		if (page.isFirstSetted()) {
			q.setFirstResult(page.getFirst());
		}
		if (page.isPageSizeSetted()) {
			q.setMaxResults(page.getPageSize());
		}
		page.setResult(q.list());
		return page;
	}

	public Object findUnique(String hql, Object[] values) {
		return createQuery(hql, values).uniqueResult();
	}

	public Integer findInt(String hql, Object[] values) {
		return ((Integer) findUnique(hql, values));
	}

	public Long findLong(String hql, Object[] values) {
		return ((Long) findUnique(hql, values));
	}

	public List<T> findByCriteria(Criterion[] criterion) {
		return createCriteria(criterion).list();
	}

	public Page<T> findByCriteria(Page page, Criterion[] criterion) {
		Assert.notNull(page);

		Criteria c = createCriteria(criterion);

		if (page.isAutoCount()) {
			page.setTotalCount(countQueryResult(page, c));
		}
		if (page.isFirstSetted()) {
			c.setFirstResult(page.getFirst());
		}
		if (page.isPageSizeSetted()) {
			c.setMaxResults(page.getPageSize());
		}

		if (page.isOrderBySetted()) {
			if (page.getOrder().endsWith("asc"))
				c.addOrder(Order.asc(page.getOrderBy()));
			else {
				c.addOrder(Order.desc(page.getOrderBy()));
			}
		}
		page.setResult(c.list());
		return page;
	}

	public List<T> findByProperty(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return createCriteria(new Criterion[] { Restrictions.eq(propertyName, value) }).list();
	}

	public T findUniqueByProperty(String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(new Criterion[] { Restrictions.eq(propertyName, value) }).uniqueResult();
	}

	public Query createQuery(String queryString, Object[] values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; ++i) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	public Criteria createCriteria(Criterion[] criterions) {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public boolean isPropertyUnique(String propertyName, Object newValue, Object orgValue) {
		if ((newValue == null) || (newValue.equals(orgValue))) {
			return true;
		}
		Object object = findUniqueByProperty(propertyName, newValue);
		return (object == null);
	}

	protected int countQueryResult(Page<T> page, Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List orderEntries = null;
		try {
			orderEntries = (List) BeanUtils.getFieldValue(impl, "orderEntries");
			BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			this.logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		int totalCount = ((Integer) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		if (totalCount < 1) {
			return -1;
		}

		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			BeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			this.logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}
}