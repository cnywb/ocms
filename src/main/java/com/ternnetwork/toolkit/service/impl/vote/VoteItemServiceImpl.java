package com.ternnetwork.toolkit.service.impl.vote;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.toolkit.dao.vote.VoteDao;
import com.ternnetwork.toolkit.dao.vote.VoteItemDao;
import com.ternnetwork.toolkit.dao.vote.VoteItemLogDao;
import com.ternnetwork.toolkit.dao.vote.VoteLogDao;
import com.ternnetwork.toolkit.model.vote.Vote;
import com.ternnetwork.toolkit.model.vote.VoteItem;
import com.ternnetwork.toolkit.service.vote.VoteItemService;




@Service("voteItemService")
public class VoteItemServiceImpl implements VoteItemService {
	
	@Resource
	private VoteItemDao voteItemDao;
	
	@Resource
	private VoteDao voteDao;
	
	@Resource
	private VoteLogDao voteLogDao;
	
	@Resource
	private VoteItemLogDao voteItemLogDao;

	
	public long idoAdd(VoteItem t){
		
		Long totalCount=voteItemDao.getTotalCount("select count(t.id) from VoteItem t where t.code=?1", t.getCode());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		
		voteItemDao.persist(t);
		return 1L;
	}
	
	public long idoUpdate(VoteItem t){
		
		Long totalCount=voteItemDao.getTotalCount("select count(t.id) from VoteItem t where t.code=?1 and t.id!=?2", t.getCode(),t.getId());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		voteItemDao.update(t);
		return 1L;
	}
	
	public long idoDelete(long id){
		VoteItem item=voteItemDao.findById(VoteItem.class, id,LockModeType.PESSIMISTIC_WRITE);
		if(item.getVote().getEnable()==true){
			return 0L;
		}
		Vote vote=voteDao.findById(Vote.class, item.getVote().getId(),LockModeType.PESSIMISTIC_WRITE);
		vote.setVoteCount(vote.getVoteCount()-item.getVoteCount());
		voteItemLogDao.bulkUpdate("delete from VoteItemLog where voteItem.id=?1", item.getId());
		
		
		voteItemDao.delete(item);
		voteDao.update(vote);
		return 1L;
	}
	
	
	
	public String queryToJsonStr(String voteId,Page page){
		Page<VoteItem> result=query(voteId,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Page<VoteItem> query(String voteId,Page page){
	    StringBuffer jpql=new StringBuffer("from VoteItem t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(voteId)){
			params.add(Long.parseLong(voteId));
			jpql.append(" and t.vote.id=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return voteItemDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	
	public List<VoteItem> findAllByVoteId(Long voteId){
		return voteItemDao.findAll("from VoteItem where vote.id=?1", voteId);
	}
	
	public String findAllByVoteIdToJsonStr(Long voteId){
		return  JSONUtils.objectToJson(findAllByVoteId(voteId));
	}
	
}
