package com.ternnetwork.baseframework.service.impl.config;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.dao.config.SeqDao;
import com.ternnetwork.baseframework.model.config.Seq;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.config.SeqService;
import com.ternnetwork.baseframework.util.ExtendedStringUtils;

@Service("seqService")
public class SeqServiceImpl implements SeqService {
	
	@Resource
	private SeqDao seqDao;
	
	
	public long idoAdd(Seq t){
		long totalCount=seqDao.getTotalCount("select count(id) from Seq where name=?1", t.getName());
		if(totalCount>0L){
			return 0L;
		}
		seqDao.persist(t);
		return t.getId();
	}
	
	public long idoUpdate(Seq t){
		long totalCount=seqDao.getTotalCount("select count(id) from Seq where name=?1 and id!=?2", t.getName(),t.getId());
		if(totalCount>0L){
			return 0L;
		}
		seqDao.saveOrUpdate(t);
		return t.getId();
	}
	
	public long idoDeleteById(Long id){
		Seq t=seqDao.findById(Seq.class, id);
		if(t==null){
			return 0L;
		}
		seqDao.delete(t);
		return 1L;
	}
	
	
	public Page<Seq> query(Seq t,Page page) {
		StringBuffer jpql=new StringBuffer("from Seq t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(t.getName())){
			params.add(t.getName().toUpperCase()+"%");
			jpql.append(" and t.name like?"+params.size());
		}
		return seqDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String idoGetNextValueBySeqName(String name){
		List<Seq> list=seqDao.findAll("from Seq where name=?1", name);
		if(list.size()==0){
			return null;
		}
		String nextValue="";
		for(Seq t:list){
			t=seqDao.findById(Seq.class, t.getId(), LockModeType.PESSIMISTIC_WRITE);
			nextValue=t.getPrefix()+ExtendedStringUtils.fillString(t.getLength(), String.valueOf(t.getNextValue()), "0");
			t.setNextValue(t.getNextValue()+t.getAllocationSize());
			seqDao.saveOrUpdate(t);
		}
		return nextValue;
	}
	
	
	public String queryToJsonStr(Seq t,Page page) {
		Page<Seq> result=query(t, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}

}
