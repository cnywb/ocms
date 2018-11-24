package com.ternnetwork.baseframework.service.impl.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ternnetwork.baseframework.dao.security.ResourcesDao;
import com.ternnetwork.baseframework.enums.ResourcesType;
import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.model.security.RescRole;
import com.ternnetwork.baseframework.model.security.RescRoleId;
import com.ternnetwork.baseframework.model.security.Resources;
import com.ternnetwork.baseframework.model.security.Role;
import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.model.security.UserRole;
import com.ternnetwork.baseframework.model.ui.ResourcesZtree;
import com.ternnetwork.baseframework.service.security.RescRoleService;
import com.ternnetwork.baseframework.service.security.ResourcesService;
import com.ternnetwork.baseframework.service.security.UserService;



@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {
	
	@Resource
	private ResourcesDao resourcesDao;
	@Resource
	private RescRoleService rescRoleService;
	
	@Resource
	private UserService userService;
	
	public String getTreeJSON(RoleType type,ResourcesType resourcesType){
		List<Resources> list=resourcesDao.findAll("from Resources t where t.roleType=?1 and t.type=?2 order by t.seqNum",type,resourcesType);
		List<ResourcesZtree> retVal = convertToZtreeList(list);
		return retVal.toString();
	}
	
	
	public String getTreeJSON(String roleIds,RoleType type,ResourcesType resourcesType){
		List<Resources> list=resourcesDao.findAll("from Resources t where  t.roleType=?1 and t.type=?2 and t.id in(select rr.id.resourcesId from RescRole rr where rr.id.roleId in ("+roleIds+")) order by t.seqNum",type,resourcesType);
		List<ResourcesZtree> retVal = convertToZtreeList(list);
		for(ResourcesZtree t:retVal){
			t.setTabUrl(t.getUrl());
			t.setUrl("");
		}
		return retVal.toString();
	}


	private List<ResourcesZtree> convertToZtreeList(List<Resources> list) {
		List<ResourcesZtree> retVal=new ArrayList<ResourcesZtree>();
		for(int i=0;i<list.size();i++){
			Resources t=list.get(i);
			ResourcesZtree tree=new ResourcesZtree();
			tree.setId(t.getId());
			tree.setpId(t.getParentId());
			tree.setName(t.getNameZh());
			tree.setNameZh(t.getNameZh());
			tree.setNameEn(t.getNameEn());
			tree.setUrl(t.getUrl());
			tree.setRoleType(t.getRoleType());
			tree.setType(t.getType());
			tree.setSeqNum(t.getSeqNum());
			String[] roleIds=t.getRoleIds().equals("")?new String[]{}:t.getRoleIds().split(",");
			tree.setRoleIds(roleIds);
			retVal.add(tree);
		}
		return retVal;
	}
	
	
	public String getUserMenuTreeJSON(){
		User user = userService.getCurrentUser();
		StringBuffer roleIds=new StringBuffer(10);
		int i=0;
		for(UserRole userRole:user.getUserRoles()){
			if(i!=0){
				roleIds.append(",");
			}
			roleIds.append(userRole.getRole().getId());
			i=i+1;
		}
		return getTreeJSON(roleIds.toString(),RoleType.ADMIN, ResourcesType.MENU);
	}
	
	public Resources idoAdd(Resources t){
		List<Resources> list=resourcesDao.findAll("from Resources t where t.nameEn=?1",t.getNameEn());
		if(list.size()>0){
			t.setStatus(0);
			t.setMessage("系统中已存在相同的代码,请重新输入!");
		}
		resourcesDao.persist(t);
		setRescRoleByRoleIds(t);
		t.setStatus(1);
		t.setMessage("操作成功!");
		return t;
	}
	
	public Resources idoUpdate(Resources t){
		List<Resources> list=resourcesDao.findAll("from Resources t where t.nameEn=?1 and t.id!=?2",t.getNameEn(),t.getId());
		if(list.size()>0){
			t.setStatus(0);
			t.setMessage("系统中已存在相同的代码,请重新输入!");
		}
		resourcesDao.saveOrUpdate(t);
		rescRoleService.doDeleteBatch(t.getId(), t.getDeleteRoleIds());
		rescRoleService.doAddBatch(t.getId(), t.getRoleIds());
		t.setStatus(1);
		t.setMessage("操作成功!");
		return t;
	}
	
	public String idoDeleteById(long id){
		Resources t=resourcesDao.findById(Resources.class, id);
		if(t==null){
			return "0";
		}
		if(t.getParentId()==0L){
			return "1";
		}
		List<Resources> list=findChild(id);
		if(list.size()>0){
			return "2";
		}
		resourcesDao.delete(t);
		return "3";
	}
	
	public List<Resources>findChild(long id){
		return resourcesDao.findAll("from Resources t where t.parentId=?1",id);
	}
	
	public List<Resources> findAllChild(List<Resources> oriList,long id){
		Resources tr=new Resources();
		tr.setId(id);
		oriList.add(tr);
		List<Resources> list=findChild(id);
		for(Resources t:list){
			oriList.addAll(findAllChild(oriList,t.getId()));
		}
		return oriList;
	}
	
	
	public List<Resources> findAllParent(List<Resources> oriList,long id){
		Resources t=resourcesDao.findById(Resources.class, id);
		oriList.add(t);
		if(t.getParentId()!=0){
			oriList.addAll(findAllParent(oriList,t.getParentId()));
		}
		return oriList;
	}
	

	private Resources setRescRoleByRoleIds(Resources t) {
		Set<RescRole> rescRoles=new HashSet<RescRole>();
		String[] roleIdsArray=t.getRoleIds().equals("")?new String[]{}:t.getRoleIds().split(",");
		for(String roleId:roleIdsArray){
			Role r=new Role();
			r.setId(Long.parseLong(roleId));
			RescRole ur=new RescRole();
			ur.setRole(r);
			ur.setResources(t);
			RescRoleId id=new RescRoleId();
			id.setRoleId(r.getId());
			id.setResourcesId(t.getId());
			ur.setId(id);
			rescRoles.add(ur);
		}
		t.setRescRoles(rescRoles);;
		return t;
	}
}
