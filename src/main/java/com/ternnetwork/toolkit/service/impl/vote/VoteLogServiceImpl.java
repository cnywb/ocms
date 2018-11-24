package com.ternnetwork.toolkit.service.impl.vote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.security.UserService;

import com.ternnetwork.toolkit.dao.vote.VoteDao;
import com.ternnetwork.toolkit.dao.vote.VoteItemDao;
import com.ternnetwork.toolkit.dao.vote.VoteItemLogDao;
import com.ternnetwork.toolkit.dao.vote.VoteLogDao;
import com.ternnetwork.toolkit.model.vote.Vote;
import com.ternnetwork.toolkit.model.vote.VoteItem;
import com.ternnetwork.toolkit.model.vote.VoteItemLog;
import com.ternnetwork.toolkit.model.vote.VoteLog;
import com.ternnetwork.toolkit.model.vote.VoteLogRequest;
import com.ternnetwork.toolkit.service.vote.VoteLogService;




@Service("voteLogService")
public class VoteLogServiceImpl implements VoteLogService {

	@Resource
	private VoteLogDao voteLogDao;
	
	@Resource
	private VoteItemDao voteItemDao;
	
	@Resource
	private VoteDao voteDao;
	
	@Resource
	private UserService userService;
	
	@Resource
	private VoteItemLogDao voteItemLogDao;
	
	

	
	public BaseResponse  idoAdd(VoteLogRequest request){
		User user=userService.getCurrentUser();
		return doAdd(request, user);
	}

	public BaseResponse  idoAddByWechat(VoteLogRequest request){
		User user=userService.findWechatId(request.getWechatId());
		return doAdd(request, user);
	}

	private BaseResponse doAdd(VoteLogRequest request, User user) {
		BaseResponse res=new BaseResponse();
		if(user==null){
			res.setMessage("操作失败,用户未登录!");
	    	res.setStatus(0);
	    	return res;
		}
	    Vote vote=voteDao.findById(Vote.class, request.getVoteId(), LockModeType.PESSIMISTIC_WRITE);
	    if(vote.getEnable()==false){
	    	res.setMessage("操作失败,活动未启用!");
	    	res.setStatus(1);
	    	return res;
		}
	    if(vote.getStartTime().getTime()>(new Date()).getTime()){
	    	res.setMessage("操作失败,活动未开始!");
	    	res.setStatus(2);
	    	return res;
		}
		if(vote.getEndTime().getTime()<(new Date()).getTime()){
			res.setMessage("操作失败,活动已结束!");
	    	res.setStatus(3);
	    	return res;
		}
   		long totalCount=voteLogDao.getTotalCount("select count(id) from VoteLog where user.id=?1 and vote.id=?2", user.getId(),request.getVoteId());
		if(totalCount>0L){
			res.setMessage("操作失败,请勿重复投票!");
	    	res.setStatus(4);
	    	return res;
		}
        vote.setVoteCount(vote.getVoteCount()+request.getItems().size());
		for(VoteItemLog vl:request.getItems()){
			VoteItem voteItem=vl.getVoteItem();
			voteItem=voteItemDao.findById(VoteItem.class, voteItem.getId(), LockModeType.PESSIMISTIC_WRITE);
			voteItem.setVoteCount(voteItem.getVoteCount()+1);
			voteItemDao.update(voteItem);
			vl.setUser(user);
			voteItemLogDao.persist(vl);
		}
		VoteLog voteLog=new VoteLog();
		voteLog.setUser(user);
		voteLog.setVote(vote);
		voteLogDao.persist(voteLog);
		voteDao.update(vote);
		res.setMessage("操作成功!");
    	res.setStatus(5);
    	return res;
	}
	
	
	public String queryToJsonStr(String voteId,Page page){
		Page<VoteLog> result=query(voteId,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Page<VoteLog> query(String voteId,Page page){
	    StringBuffer jpql=new StringBuffer("from VoteLog t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(voteId)){
			params.add(Long.parseLong(voteId));
			jpql.append(" and t.id=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return voteLogDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	
	public String queryMyLogToJsonStr(Page page){
		Page<VoteLog> result=queryMyLog(page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	public Page<VoteLog> queryMyLog(Page page){
	    StringBuffer jpql=new StringBuffer("from VoteLog t where 1=1");
		List<Object> params=new ArrayList<Object>();
		params.add(userService.getCurrentUser().getId());
		jpql.append(" and t.user.id=?"+params.size());
		jpql.append(" order by t.id desc");
		return voteLogDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	
	
}
