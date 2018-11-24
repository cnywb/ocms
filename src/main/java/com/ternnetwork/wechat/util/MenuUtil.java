package com.ternnetwork.wechat.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.wechat.model.auth.AccessToken;
import com.ternnetwork.wechat.model.common.ResponseError;
import com.ternnetwork.wechat.model.menu.Button;
import com.ternnetwork.wechat.model.menu.CommonButton;
import com.ternnetwork.wechat.model.menu.ComplexButton;
import com.ternnetwork.wechat.model.menu.Menu;

public class MenuUtil {
	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);
	
	// 菜单创建（POST） 限100（次/天）
	
	public static String gz_menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    
	public static String gz_menu_get_url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	public static String gz_menu_delete_url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	
	
    public static String qy_menu_create_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENT_ID";
    
	public static String qy_menu_get_url="https://qyapi.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN&agentid=AGENT_ID";
	
	public static String qy_menu_delete_url="https://qyapi.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN&agentid=AGENT_ID";
	
	

	
	 public static void main(String[] args) {  
	        
	 }  
	 
	 
	 public static String createDyMenu(String appId,String appSecret,String jsonMenu){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
			String url = gz_menu_create_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String json = HttpConnectionUtil.httpRequest(url, "POST",jsonMenu);
			return json;
    }
	 
	 public static String createFwMenu(String appId,String appSecret,String jsonMenu){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
			String url = gz_menu_create_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String json = HttpConnectionUtil.httpRequest(url, "POST",jsonMenu);
			return json;
 }
	 
	 public static String queryDyMenu(String appId,String appSecret){
		AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
		String url = gz_menu_get_url.replace("ACCESS_TOKEN", at.getAccess_token());
		String json = HttpConnectionUtil.httpRequest(url, "POST",null);
		return json;
	 }
	 
	 public static String queryFwMenu(String appId,String appSecret){
		AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
		String url = gz_menu_get_url.replace("ACCESS_TOKEN", at.getAccess_token());
		String json = HttpConnectionUtil.httpRequest(url, "POST",null);
		return json;
	 }
	 
	 
	 public static String deleteFwMenu(String appId,String appSecret){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
			String url = gz_menu_delete_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String json = HttpConnectionUtil.httpRequest(url, "POST",null);
			return json;
	 }
	 public static String deleteDyMenu(String appId,String appSecret){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
			String url = gz_menu_delete_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String json = HttpConnectionUtil.httpRequest(url, "POST",null);
			return json;
	 }
	  
	 
	 public static String createQyMenu(String corpId,String corpSecret,String jsonMenu,String agentId){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = qy_menu_create_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("AGENT_ID",agentId);
			String json = HttpConnectionUtil.httpRequest(url, "POST",jsonMenu);
			return json;
 }
	 
	 
	 public static String queryQyMenu(String corpId,String corpSecret,String agentId){
		AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
		String url = qy_menu_get_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("AGENT_ID",agentId);
		String json = HttpConnectionUtil.httpRequest(url, "POST",null);
		return json;
	 }
	 
	 public static String deleteQyMenu(String corpId,String corpSecret,String agentId){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = qy_menu_delete_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("AGENT_ID",agentId);
			String json = HttpConnectionUtil.httpRequest(url, "POST",null);
			return json;
	 }
	 
	 
	 
	
	
	
	
}  

