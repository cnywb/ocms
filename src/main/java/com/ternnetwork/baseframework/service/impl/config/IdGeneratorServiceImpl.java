package com.ternnetwork.baseframework.service.impl.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.dao.config.IdGeneratorDao;
import com.ternnetwork.baseframework.model.config.IdGenerator;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.config.IdGeneratorService;




@Service("idGeneratorService")
public class IdGeneratorServiceImpl implements IdGeneratorService {

	@Resource
	private IdGeneratorDao idGeneratorDao;
	
	public long  idoAdd(IdGenerator t) {
		List<Object> list=idGeneratorDao.queryAllObject("select t.id from IdGenerator t where t.genName=?1",t.getGenName());
		if(list.size()>0){
			return 0L;
		}
		t.setGenName(t.getGenName().toUpperCase());
		idGeneratorDao.persist(t);
		return t.getId();
	}

	public int idoUpdate(IdGenerator t) {
		List<Object> list=idGeneratorDao.queryAllObject("select t.id from IdGenerator t where t.genName=?1 and t.id!=?2",t.getGenName(),t.getId());
		if(list.size()>0){
			return 0;
		}
		t.setGenName(t.getGenName().toUpperCase());
		idGeneratorDao.saveOrUpdate(t);
		return 1;
	}

	public void delete(IdGenerator t) {
		idGeneratorDao.delete(t);
	}
	
	
	public String queryToJsonStr(IdGenerator t,Page page) {
		Page<IdGenerator> result=query(t, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Page<IdGenerator> query(IdGenerator t,Page page) {
		StringBuffer jpql=new StringBuffer("from IdGenerator t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(t.getGenName())){
			params.add(t.getGenName().toUpperCase()+"%");
			jpql.append(" and t.genName like?"+params.size());
		}
		return idGeneratorDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	

}
