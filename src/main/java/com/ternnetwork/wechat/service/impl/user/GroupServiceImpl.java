package com.ternnetwork.wechat.service.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.wechat.model.user.GroupResponse;
import com.ternnetwork.wechat.service.user.GroupService;
import com.ternnetwork.wechat.util.GroupUtil;


@Service("groupService")
public class GroupServiceImpl implements GroupService {
	@Resource
	private SystemParameterService systemParameterService;
	
	
	public String queryDyGroup(){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		String json=GroupUtil.queryDyGroup(appId, appSecret);
		GroupResponse response=JSONUtils.jsonToObject(json,GroupResponse.class);
		Page page=new Page();
		page.setResult(response.getGroups());
		page.setPageSize(response.getGroups().size());
		page.setPageNo(0);
		page.setTotalCount(response.getGroups().size());
		BootstrapGrid grid=new BootstrapGrid(page);
		return grid.toString();
	}
	
	
	public String createDyGroup(String json){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return GroupUtil.createDyGroup(appId, appSecret, json);
	}
	
	public String deleteDyGroup(String json){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return GroupUtil.deleteDyGroup(appId, appSecret, json);
	}
	
	public String updateDyGroup(String json){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return GroupUtil.updateDyGroup(appId, appSecret, json);
	}
	
	
	public String queryFwGroup(){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		String json=GroupUtil.queryFwGroup(appId, appSecret);
		GroupResponse response=JSONUtils.jsonToObject(json,GroupResponse.class);
		Page page=new Page();
		page.setResult(response.getGroups());
		page.setPageSize(response.getGroups().size());
		page.setPageNo(0);
		page.setTotalCount(response.getGroups().size());
		BootstrapGrid grid=new BootstrapGrid(page);
		return grid.toString();
	}
	
	
	public String createFwGroup(String json){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		return GroupUtil.createFwGroup(appId, appSecret, json);
	}
	
	public String deleteFwGroup(String json){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		return GroupUtil.deleteFwGroup(appId, appSecret, json);
	}
	
	public String updateFwGroup(String json){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		return GroupUtil.updateFwGroup(appId, appSecret, json);
	}
	
}
