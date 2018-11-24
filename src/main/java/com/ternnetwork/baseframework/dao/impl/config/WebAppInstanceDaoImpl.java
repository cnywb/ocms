package com.ternnetwork.baseframework.dao.impl.config;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.config.WebAppInstanceDao;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.model.config.WebAppInstance;


@Repository("webAppInstanceDao")
public class WebAppInstanceDaoImpl extends IBaseDaoImpl<WebAppInstance>
		implements WebAppInstanceDao {

}
