package com.ternnetwork.baseframework.service.config;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.config.Seq;


public interface SeqService {
	public long idoAdd(Seq t);
	
	public long idoUpdate(Seq t);
	
	public long idoDeleteById(Long id);
	
	public Page<Seq> query(Seq t,Page page);
	
	public String idoGetNextValueBySeqName(String name);
	
	public String queryToJsonStr(Seq t,Page page);
}
