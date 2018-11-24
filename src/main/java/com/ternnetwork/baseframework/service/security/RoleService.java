package com.ternnetwork.baseframework.service.security;

import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.security.Role;



public interface RoleService {
	public long idoAdd(Role t);

	public long idoUpdate(Role t);

	public void delete(Role t);


	public List<Role> findAll(String jpql, Object... param);

	public Role findById(long id);

	public Role getReferenceById(long id);

	public Long getTotalCount(String countJpql, Object... param);
	
	public String query(Page page);
	
	

}
