package com.ternnetwork.toolkit.service.impl.vote;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.toolkit.dao.vote.VoteDao;
import com.ternnetwork.toolkit.model.vote.Vote;
import com.ternnetwork.toolkit.service.vote.VoteItemService;
import com.ternnetwork.toolkit.service.vote.VoteService;



@Service("voteService")
public class VoteServiceImpl implements VoteService {
	
	@Resource
	private VoteDao voteDao;
	
	@Resource
	private VoteItemService voteItemService;
	
	public long idoAdd(Vote t){
		if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
			return 0L;
		}
		Long totalCount=voteDao.getTotalCount("select count(t.id) from Vote t where t.code=?1 ",t.getCode());
		if(totalCount.longValue()>0L){
			return 1L;
		}
		voteDao.persist(t);
		return 2L;
	}
	
	public long idoUpdate(Vote t){
		if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
			return 0L;
		}
		Long totalCount=voteDao.getTotalCount("select count(t.id) from Vote t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(totalCount.longValue()>0L){
			return 1L;
		}
		voteDao.update(t);
		return 2L;
	}
	
	
	public Vote findById(long id){
		return voteDao.findById(Vote.class, id);
	}
	
	public Vote findByCode(String code){
		
		List<Vote> list=voteDao.findAll("from Vote t where t.code=?1", code);
		
		if(list.size()==0){
			return null;
		}
		Vote vote=list.get(0);
		vote.setItems(voteItemService.findAllByVoteId(vote.getId()));
		return vote;
	}
	
	
	public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page){
		Page<Vote> result=query(code, name, startTime, endTime, enable,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Page<Vote> query(String code,String name,String startTime,String endTime,Boolean enable,Page page){
	   
	
	    StringBuffer jpql=new StringBuffer("from Vote t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name);
			jpql.append(" and t.name=?"+params.size());
		}
		if(enable!=null){
			params.add(enable);
			jpql.append(" and t.enable=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(startTime)){
			params.add(DateUtils.parseDate(startTime+" 00:00:00",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime>=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(endTime)){
			params.add(DateUtils.parseDate(endTime+" 23:59:59",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime<=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return voteDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}


}
