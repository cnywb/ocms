package com.ternnetwork.baseframework.service.impl.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.dao.security.RescRoleDao;
import com.ternnetwork.baseframework.model.security.RescRole;
import com.ternnetwork.baseframework.model.security.RescRoleId;
import com.ternnetwork.baseframework.model.security.Resources;
import com.ternnetwork.baseframework.service.security.RescRoleService;
import com.ternnetwork.baseframework.service.security.ResourcesService;



@Service("rescRoleService")
public class RescRoleServiceImpl implements RescRoleService {

	@Resource
	private RescRoleDao rescRoleDao;
	@Resource
	private ResourcesService resourcesService;
	
	public List<RescRole> queryByResourcesId(long resourcesId){
		return rescRoleDao.findAll("from RescRole t where t.id.resourcesId=?1",resourcesId);
	}
	
	public String queryRoleByResourcesIdToJSON(long resourcesId){
		 List<RescRole> result=queryByResourcesId(resourcesId);
		 StringBuffer sb=new StringBuffer("[");
		 int i=0;
		 for(RescRole t:result){
				if(i!=0)
		    	 {
		    		sb.append(",");
		    	 }
				sb.append("{\"id\":\""+t.getId().getRoleId()+"\",\"name\":\""+t.getRole().getNameZh()+"\"}");
	    		i=i+1; 
		 }
		 sb.append("]");
		return sb.toString();
	}
	
	public void idoUpdateBatch(long resourcesId,String deleteRoleIds,String addRoleIds){
		doDeleteBatch(resourcesId, deleteRoleIds);
		doAddBatch(resourcesId, addRoleIds);
	}
	
	public void doDeleteBatch(long resourcesId,String deleteRoleIds){
		String[] deleteRoleId=deleteRoleIds.equals("")?new String[]{}:deleteRoleIds.split(",");
		List<Resources> list=resourcesService.findAllChild(new ArrayList<Resources>(),resourcesId);
		for(String roleId:deleteRoleId){
				for(Resources t:list){
				RescRoleId id=new RescRoleId();
				id.setResourcesId(t.getId());
				id.setRoleId(Long.parseLong(roleId));
				rescRoleDao.bulkUpdate("delete from RescRole t where t.id=?1",id);
			}
		}
		
	}
	public void doAddBatch(long resourcesId,String addRoleIds){
		String[] addRoleId=addRoleIds.equals("")?new String[]{}:addRoleIds.split(",");
    	List<Resources> list=resourcesService.findAllParent(new ArrayList<Resources>(),resourcesId);
		for(String roleId:addRoleId){
				for(Resources t:list){
				RescRoleId id=new RescRoleId();
				id.setResourcesId(t.getId());
				id.setRoleId(Long.parseLong(roleId));
				RescRole rescRole=rescRoleDao.findById(RescRole.class,id);
				if(rescRole==null){
					rescRole=new RescRole();
					rescRole.setId(id);
					rescRoleDao.persist(rescRole);
				}
			}
		}
   	}
}
