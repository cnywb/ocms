package com.ternnetwork.baseframework.dao.impl.security;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.dao.security.RescRoleDao;
import com.ternnetwork.baseframework.model.security.RescRole;



@Repository("rescRoleDao")
public class RescRoleDaoImpl extends IBaseDaoImpl<RescRole> implements
		RescRoleDao {

}
