package com.ternnetwork.baseframework.dao.impl.config;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.config.ServerNodeDao;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.model.config.ServerNode;


@Repository("serverNodeDao")
public class ServerNodeDaoImpl extends IBaseDaoImpl<ServerNode> implements
		ServerNodeDao {

}
