package com.ternnetwork.wechat.util;

import com.ternnetwork.wechat.model.auth.AccessToken;

public class GroupUtil {

	
	
	public static String gz_group_create_url="https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
    
	public static String gz_group_get_url="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	
	public static String gz_group_delete_url="https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";

	public static String gz_group_update_url="https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";

	
	 public static String createDyGroup(String appId,String appSecret,String json){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
			String url = gz_group_create_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String jsonRetval = HttpConnectionUtil.httpRequest(url, "POST",json);
			return jsonRetval;
     }
	 
	 
	 public static String queryDyGroup(String appId,String appSecret){
		AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
		String url = gz_group_get_url.replace("ACCESS_TOKEN", at.getAccess_token());
		String json = HttpConnectionUtil.httpRequest(url, "POST",null);
		return json;
	 }
	 
	 public static String deleteDyGroup(String appId,String appSecret,String json){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
			String url = gz_group_delete_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String jsonRetval = HttpConnectionUtil.httpRequest(url, "POST",json);
			return jsonRetval;
	 }
	 
	 public static String updateDyGroup(String appId,String appSecret,String json){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
			String url = gz_group_update_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String jsonRetval = HttpConnectionUtil.httpRequest(url, "POST",json);
			return jsonRetval;
	 }
	 
	 
	 
	 public static String createFwGroup(String appId,String appSecret,String json){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
			String url = gz_group_create_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String jsonRetval = HttpConnectionUtil.httpRequest(url, "POST",json);
			return jsonRetval;
  }
	 
	 
	 public static String queryFwGroup(String appId,String appSecret){
		AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
		String url = gz_group_get_url.replace("ACCESS_TOKEN", at.getAccess_token());
		String json = HttpConnectionUtil.httpRequest(url, "POST",null);
		return json;
	 }
	 
	 public static String deleteFwGroup(String appId,String appSecret,String json){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
			String url = gz_group_delete_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String jsonRetval = HttpConnectionUtil.httpRequest(url, "POST",json);
			return jsonRetval;
	 }
	 
	 public static String updateFwGroup(String appId,String appSecret,String json){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
			String url = gz_group_update_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String jsonRetval = HttpConnectionUtil.httpRequest(url, "POST",json);
			return jsonRetval;
	 }
	 
	
}
