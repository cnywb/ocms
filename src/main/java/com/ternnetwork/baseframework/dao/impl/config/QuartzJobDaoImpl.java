package com.ternnetwork.baseframework.dao.impl.config;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.config.QuartzJobDao;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.model.config.QuartzJob;


@Repository("quartzJobDao")
public class QuartzJobDaoImpl extends IBaseDaoImpl<QuartzJob> implements
		QuartzJobDao {

}
