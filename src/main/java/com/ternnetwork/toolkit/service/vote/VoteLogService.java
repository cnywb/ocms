package com.ternnetwork.toolkit.service.vote;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.vote.VoteLogRequest;



public interface VoteLogService {
	public BaseResponse  idoAdd(VoteLogRequest request);
	public BaseResponse  idoAddByWechat(VoteLogRequest request);
	public String queryToJsonStr(String voteId,Page page);
	public String queryMyLogToJsonStr(Page page);
}
