package com.ternnetwork.baseframework.service.impl.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.dao.config.SystemParameterDao;
import com.ternnetwork.baseframework.model.config.SystemParameter;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.config.SystemParameterService;








@Service("systemParameterService")
public class SystemParameterServiceImpl implements SystemParameterService {
	
	private static Map<String,String> parameterMap=new HashMap<String,String>();
	
	@Resource
	public SystemParameterDao systemParameterDao;
	
	public void idoAdd(SystemParameter t) {
		t.setParamKey(t.getParamKey().toUpperCase());
		systemParameterDao.persist(t);
	}

	public void idoUpdate(SystemParameter t) {
		t.setParamKey(t.getParamKey().toUpperCase());
		systemParameterDao.saveOrUpdate(t);
	}

	public void delete(SystemParameter t) {
		systemParameterDao.delete(t);
	}
	
	public int idoDeleteById(long id) {
		SystemParameter t=systemParameterDao.getReferenceById(SystemParameter.class, id);
		systemParameterDao.delete(t);
		return 1;
	}

	public Page<SystemParameter> find(String countJpql,String jpql, Page page, Object... param) {
		return systemParameterDao.query(countJpql, jpql, page, param);
	}

	public List<SystemParameter> findAll(String jpql, Object... param) {
		return systemParameterDao.findAll(jpql, param);
	}

	public SystemParameter findById(String id) {
		return systemParameterDao.findById(SystemParameter.class, id);
	}

	public SystemParameter getReferenceById(String id) {
		return systemParameterDao.getReferenceById(SystemParameter.class, id);
	}

	public Long getTotalCount(String countJpql, Object... param) {
		return systemParameterDao.getTotalCount(countJpql, param);
	}
	
	public String getValueByKey(String key){
		if(parameterMap.get(key)!=null){
			return parameterMap.get(key);
		}
		String jpql="from SystemParameter t where t.paramKey=?1";
		List<Object> params=new ArrayList<Object>();
		params.add(key);
		List<SystemParameter> list=systemParameterDao.findAll(jpql, params);
		if(list.size()>0){
			String value=list.get(0).getParamValue();
			parameterMap.put(key,value);
			return value;
		}
		return "";
	}
	
	public Page<SystemParameter> query(SystemParameter t,Page page) {
		StringBuffer jpql=new StringBuffer("from SystemParameter t where 1=1");
		
		List<Object> params=new ArrayList<Object>();
		
		if(StringUtils.isNotEmpty(t.getName())){
			params.add(t.getName().toUpperCase()+"%");
			jpql.append(" and t.genName like?"+params.size());
		}
		if(StringUtils.isNotEmpty(t.getParamKey())){
			params.add(t.getParamKey());
			jpql.append(" and t.paramKey=?"+params.size());
		}
		if(StringUtils.isNotEmpty(t.getCategory())){
			params.add(t.getCategory());
			jpql.append(" and t.category=?"+params.size());
		}
		return systemParameterDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(SystemParameter t,Page page) {
		Page<SystemParameter> result=query(t, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	
}
