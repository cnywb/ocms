package com.ternnetwork.baseframework.dao.impl.log;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.dao.log.AuthenticationLogDao;
import com.ternnetwork.baseframework.model.log.AuthenticationLog;


@Repository("authenticationLogDao")
public class AuthenticationLogDaoImpl extends IBaseDaoImpl<AuthenticationLog>
		implements AuthenticationLogDao {

}
