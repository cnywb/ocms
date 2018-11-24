package com.ternnetwork.baseframework.dao.impl.security;



import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.dao.security.ResourcesDao;
import com.ternnetwork.baseframework.model.security.Resources;



@Repository("resourcesDao")
public class ResourcesDaoImpl extends IBaseDaoImpl<Resources> implements ResourcesDao {

}
