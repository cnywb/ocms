package com.ternnetwork.cms.service.channel;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.channel.Channel;

public interface ChannelService {
	public BaseResponse idoAdd(HttpServletRequest request,Channel t);
	
	public BaseResponse doAdd(Channel t);
	
	public BaseResponse idoUpdate(HttpServletRequest request,Channel t);
	
	public BaseResponse idoDeleteById(HttpServletRequest request,long id);
	
	public List<Channel> findRootBySiteId(long siteId);
	
	public String getZTreeJSON(long siteId);
	
	public Channel findById(long id);
	
	public List<Channel> findBySiteId(long siteId);
}
