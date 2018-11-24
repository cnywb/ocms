package com.ternnetwork.baseframework.service.config;


import java.util.List;

import com.ternnetwork.baseframework.model.config.QuartzJob;
import com.ternnetwork.baseframework.model.ui.QuartzJobZtree;

public interface QuartzJobService {
	public long idoAdd(QuartzJob t);
	
	public long idoUpdate(QuartzJob t)throws Exception;
	
	public long idoDelete(Long id)throws Exception;
	
	public List<QuartzJobZtree>getZtreeList();
}
