package com.ternnetwork.toolkit.service.vote;



import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.vote.VoteItem;



public interface VoteItemService {
	
	public long idoAdd(VoteItem t);
	
	public long idoUpdate(VoteItem t);
	
	public long idoDelete(long id);

	public String queryToJsonStr(String voteId,Page page);
	public String findAllByVoteIdToJsonStr(Long voteId);
	
	public List<VoteItem> findAllByVoteId(Long voteId);

}
