package com.ternnetwork.toolkit.service.vote;



import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.vote.Vote;




public interface VoteService {
	
	public long idoAdd(Vote t);
	public Vote findByCode(String code);
	public long idoUpdate(Vote t);
	public Vote findById(long id);
	public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page);

}
