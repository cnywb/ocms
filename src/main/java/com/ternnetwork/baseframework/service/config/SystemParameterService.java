package com.ternnetwork.baseframework.service.config;

import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.config.SystemParameter;







public interface SystemParameterService {
	
	public void idoAdd(SystemParameter t);

	public void idoUpdate(SystemParameter t);

	public void delete(SystemParameter t);
	
	public int idoDeleteById(long id);

	public Page<SystemParameter> find(String countJpql,String jpql, Page page, Object... param);

	public List<SystemParameter> findAll(String jpql, Object... param);

	public SystemParameter findById(String id);

	public SystemParameter getReferenceById(String id);

	public Long getTotalCount(String countJpql, Object... param);

	public String getValueByKey(String key);
	
	public String queryToJsonStr(SystemParameter t,Page page);

}
