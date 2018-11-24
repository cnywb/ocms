package com.ternnetwork.baseframework.dao.impl.config;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.config.QuartzJobTriggerDao;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.model.config.QuartzJobTrigger;


@Repository("quartzJobTriggerDao")
public class QuartzJobTriggerDaoImpl extends IBaseDaoImpl<QuartzJobTrigger>
		implements QuartzJobTriggerDao {

}
