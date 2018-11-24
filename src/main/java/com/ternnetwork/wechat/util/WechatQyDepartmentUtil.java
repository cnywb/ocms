package com.ternnetwork.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ternnetwork.wechat.model.auth.AccessToken;

public class WechatQyDepartmentUtil {
	
	private static Logger log = LoggerFactory.getLogger(WechatQyDepartmentUtil.class);

    public static String create_url = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
    
 	public static String get_url="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
 	
	public static String update_url="https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
	
	public static String delete_url="https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=DEL_ID";
	
	
	
	 public static String create(String corpId,String corpSecret,String json){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = create_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",json);
			return respJson;
     }
	 
	 public static String update(String corpId,String corpSecret,String json){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = update_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",json);
			return respJson;
     }
	 
	 public static String delete(String corpId,String corpSecret,String id){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = delete_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("DEL_ID",id);
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",null);
			return respJson;
    }
	 
	 public static String get(String corpId,String corpSecret){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = get_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",null);
			return respJson;
    }
}
