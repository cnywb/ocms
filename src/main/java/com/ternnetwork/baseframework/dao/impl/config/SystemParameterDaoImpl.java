package com.ternnetwork.baseframework.dao.impl.config;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.config.SystemParameterDao;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.model.config.SystemParameter;





@Repository("systemParameterDao")
public class SystemParameterDaoImpl extends IBaseDaoImpl<SystemParameter> implements SystemParameterDao {

}
