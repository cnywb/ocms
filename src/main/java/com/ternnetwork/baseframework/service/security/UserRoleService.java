package com.ternnetwork.baseframework.service.security;

import java.util.List;
import java.util.Set;

import com.ternnetwork.baseframework.model.security.UserRole;



public interface UserRoleService {
	
	public void doAdd(UserRole t);
	public List<UserRole> findByUserId(long userId);
	
	public void doDeleteBatch(List<UserRole> urList);
	
	public int idoDeleteByUserId(long userId);
	
	public void doAddBatch(List<UserRole> urList);
	
	public void doDelete(UserRole t);

}
