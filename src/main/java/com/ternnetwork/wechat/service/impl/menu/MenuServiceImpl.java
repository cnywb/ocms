package com.ternnetwork.wechat.service.impl.menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.wechat.service.menu.MenuService;
import com.ternnetwork.wechat.util.MenuUtil;


@Service("menuService")
public class MenuServiceImpl implements MenuService {
	
	@Resource
	private SystemParameterService systemParameterService;
	
	public  String queryDyMenu(){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MenuUtil.queryDyMenu(appId, appSecret);
	}
	
	public  String createDyMenu(String json){
     	String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		MenuUtil.deleteDyMenu(appId, appSecret);
		json=replaceBlank(json);
		return MenuUtil.createDyMenu(appId, appSecret,json);
	}
	
	public  String deleteDyMenu(){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MenuUtil.deleteDyMenu(appId, appSecret);
	}
	
	
	
	
	
	
	public  String queryQyMenu(){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String agentId=systemParameterService.getValueByKey("WECHAT_QY_AGENT_ID");
		return MenuUtil.queryQyMenu(corpId, corpSecret, agentId);
	}
	
	
	public  String createQyMenu(String json){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String agentId=systemParameterService.getValueByKey("WECHAT_QY_AGENT_ID");
		MenuUtil.deleteQyMenu(corpId, corpSecret, agentId);
		json=replaceBlank(json);
		return MenuUtil.createQyMenu(corpId, corpSecret, json, agentId);
	}
	
	public String deleteQyMenu(){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String agentId=systemParameterService.getValueByKey("WECHAT_QY_AGENT_ID");
		return MenuUtil.deleteQyMenu(corpId, corpSecret, agentId);
	}
	
	
	private String replaceBlank(String str){
		String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
	}

	public  String queryFwMenu(){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		return MenuUtil.queryFwMenu(appId, appSecret);
	}
	
	public  String createFwMenu(String json){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		MenuUtil.deleteFwMenu(appId, appSecret);
		json=replaceBlank(json);
		return MenuUtil.createFwMenu(appId, appSecret,json);
	}
	
	
	public  String deleteFwMenu(){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		return MenuUtil.deleteFwMenu(appId, appSecret);
	}

}
