package com.ternnetwork.baseframework.service.config;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.config.IdGenerator;



public interface IdGeneratorService {
	

	public long idoAdd(IdGenerator t);

	public int idoUpdate(IdGenerator t);

	public void delete(IdGenerator t);
	
	public String queryToJsonStr(IdGenerator t,Page page);

}
