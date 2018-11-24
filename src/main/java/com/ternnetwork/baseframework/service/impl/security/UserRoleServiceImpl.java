package com.ternnetwork.baseframework.service.impl.security;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.dao.security.UserRoleDao;
import com.ternnetwork.baseframework.model.security.UserRole;
import com.ternnetwork.baseframework.service.security.UserRoleService;



@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	
	@Resource
	private UserRoleDao userRoleDao;
	
	public void doAdd(UserRole t){
		userRoleDao.persist(t);
	}
	
	public void doDelete(UserRole t){
		userRoleDao.delete(t);
	}
	
	public List<UserRole> findByUserId(long userId){
		return userRoleDao.findAll("from UserRole t where t.user.id=?1", userId);
	}
	
	public void doDeleteBatch(List<UserRole> urList){
		for(UserRole t:urList){
			userRoleDao.delete(t);
		}
	}
	public int idoDeleteByUserId(long userId){
		return userRoleDao.bulkUpdate("delete from UserRole t where t.user.id=?1", userId);
	}
	public void doAddBatch(List<UserRole> urList){
		for(UserRole t:urList){
			userRoleDao.persist(t);
		}
	}

}
