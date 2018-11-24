package com.ternnetwork.baseframework.service.config;


import com.ternnetwork.baseframework.model.config.QuartzJobTrigger;

public interface QuartzJobTriggerService {
	public long idoAdd(QuartzJobTrigger t)throws Exception;
	public long idoUpdate(QuartzJobTrigger t)throws Exception;
	public long idoDelete(Long id)throws Exception;
}
