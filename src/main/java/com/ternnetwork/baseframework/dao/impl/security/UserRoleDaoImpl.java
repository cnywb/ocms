package com.ternnetwork.baseframework.dao.impl.security;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.dao.security.UserRoleDao;
import com.ternnetwork.baseframework.model.security.UserRole;



@Repository("userRoleDao")
public class UserRoleDaoImpl extends IBaseDaoImpl<UserRole> implements UserRoleDao {

}
