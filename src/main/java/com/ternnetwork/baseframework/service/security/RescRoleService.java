package com.ternnetwork.baseframework.service.security;

public interface RescRoleService {
	public String queryRoleByResourcesIdToJSON(long resourcesId);
	public void idoUpdateBatch(long resourcesId,String deleteRoleIds,String addRoleIds);
	public void doDeleteBatch(long resourcesId,String deleteRoleIds);
	public void doAddBatch(long resourcesId,String addRoleIds);
}
