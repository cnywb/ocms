package com.ternnetwork.baseframework.service.security;

import java.util.List;

import com.ternnetwork.baseframework.enums.ResourcesType;
import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.model.security.Resources;



public interface ResourcesService {
	public String getTreeJSON(RoleType type,ResourcesType resourcesType);
	public Resources idoAdd(Resources t);
	public Resources idoUpdate(Resources t);
	public String idoDeleteById(long id);
	public List<Resources> findAllChild(List<Resources> oriList,long id);
	public List<Resources> findAllParent(List<Resources> oriList,long id);
	public String getTreeJSON(String roleIds,RoleType type,ResourcesType resourcesType);
	public String getUserMenuTreeJSON();

}
