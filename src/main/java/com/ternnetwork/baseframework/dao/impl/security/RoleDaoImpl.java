/**
 * 
 */
package com.ternnetwork.baseframework.dao.impl.security;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.dao.security.RoleDao;
import com.ternnetwork.baseframework.model.security.Role;




/**
 *
 * @author wenfeng.xu
 *2011-4-24下午09:26:28
 */
@Repository("roleDao")
public class RoleDaoImpl extends IBaseDaoImpl<Role> implements RoleDao {


}
