package com.ternnetwork.baseframework.service.log;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.log.AuthenticationLog;

public interface AuthenticationLogService {
	
	public void idoAdd(AuthenticationLog t);
	public void idoAdd(String address,String userName);
	public void idoAddLogoutLog(String sessionId);
	public String queryToJsonStr(AuthenticationLog t,String createTimeMin,String createTimeMax,Page page);

}
