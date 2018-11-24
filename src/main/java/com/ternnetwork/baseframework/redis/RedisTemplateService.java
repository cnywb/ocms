package com.ternnetwork.baseframework.redis;

public interface RedisTemplateService {
	
	public void putSecurityContext(String userSessionId, Object value);

	public void deleteSecurityContext(String userSessionId);

	public Object getSecurityContext(String userSessionId);

}
