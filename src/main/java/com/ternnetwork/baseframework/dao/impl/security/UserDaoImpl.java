package com.ternnetwork.baseframework.dao.impl.security;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.dao.security.UserDao;
import com.ternnetwork.baseframework.model.security.User;




@Repository("userDao")
public class UserDaoImpl extends IBaseDaoImpl<User> implements UserDao {

	
	public User findByName(String username) {
		List<User> list=super.findAll("from User t where t.name=?1",username);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public User findWechatId(String wechatId) {
		List<User> list=super.findAll("from User t where t.wechatId=?1",wechatId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
