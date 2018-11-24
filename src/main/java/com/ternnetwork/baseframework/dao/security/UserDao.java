package com.ternnetwork.baseframework.dao.security;

import com.ternnetwork.baseframework.dao.base.IBaseDao;
import com.ternnetwork.baseframework.model.security.User;



public interface UserDao extends IBaseDao<User> {

	public User findByName(String username);
	public User findWechatId(String wechatId);

}
