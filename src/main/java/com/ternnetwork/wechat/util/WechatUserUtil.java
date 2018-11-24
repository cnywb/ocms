package com.ternnetwork.wechat.util;

import org.apache.commons.lang.StringUtils;

import com.ternnetwork.wechat.model.auth.AccessToken;

public class WechatUserUtil {
	
	
	public static String gz_user_get_url="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	public static String get_auth_user_info_url="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	
	
	 public static String queryDyUser(String appId,String appSecret,String nextOpenid){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appId, appSecret);
			String url="";
			if(!StringUtils.isEmpty(nextOpenid)){
				url = gz_user_get_url.replace("NEXT_OPENID",nextOpenid);
			}else{
				url = gz_user_get_url.replace("&next_openid=NEXT_OPENID","");
			}		
			url =url.replace("ACCESS_TOKEN", at.getAccess_token());
			String json = HttpConnectionUtil.httpRequest(url, "POST",null);
			return json;
	 }
	 
	 public static String queryFwUser(String appId,String appSecret,String nextOpenid){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appId, appSecret);
			String url="";
			if(!StringUtils.isEmpty(nextOpenid)){
				url = gz_user_get_url.replace("NEXT_OPENID",nextOpenid);
			}else{
				url = gz_user_get_url.replace("&next_openid=NEXT_OPENID","");
			}		
			url =url.replace("ACCESS_TOKEN", at.getAccess_token());
			String json = HttpConnectionUtil.httpRequest(url, "POST",null);
			return json;
	 }
	 
	 
    public static String getAuthUserinfo(String accessToken,String openid){
			String url=get_auth_user_info_url.replaceAll("ACCESS_TOKEN",accessToken).replaceAll("OPENID", openid);
			String json = HttpConnectionUtil.httpRequest(url, "POST",null);
			return json;
	 }
	 
	 
	 
	 
	
}
