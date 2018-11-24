package com.ternnetwork.wechat.util;



import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.wechat.model.auth.AccessToken;
import com.ternnetwork.wechat.model.auth.UserinfoAccessToken;

public class AccessTokenUtil {
	
	private static AccessToken dy_token;//订阅号
	
	private static AccessToken qy_token;//企业号
	
	private static AccessToken fw_token;//服务号
	
	private static UserinfoAccessToken  dyUserinfoAccessToken;
	
	private static UserinfoAccessToken  fwUserinfoAccessToken;
	
	private static Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);
	

	
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	public final static String qy_access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORP_ID&corpsecret=CORP_SECRET";

	public static String get_auth_user_access_token_url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getDyAccessToken(String appid, String appsecret) {
		if(dy_token==null||dy_token.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			String json = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);
			dy_token = JSONUtils.jsonToObject(json,AccessToken.class);
			dy_token.setExpires_time(new Date((new Date()).getTime()+dy_token.getExpires_in()*1000L));
		}
		return dy_token;
	}
	
	public static AccessToken getQyAccessToken(String corpId, String corpSecret) {
		if(qy_token==null||qy_token.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String requestUrl = qy_access_token_url.replace("CORP_ID", corpId).replace("CORP_SECRET", corpSecret);
			String json = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);
			qy_token = JSONUtils.jsonToObject(json,AccessToken.class);
			qy_token.setExpires_time(new Date((new Date()).getTime()+qy_token.getExpires_in()*1000L));
		}
		return qy_token;
	}
	
	
	public static AccessToken getFwAccessToken(String appid, String appsecret) {
		if(fw_token==null||fw_token.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
			String json = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);
			fw_token = JSONUtils.jsonToObject(json,AccessToken.class);
			fw_token.setExpires_time(new Date((new Date()).getTime()+fw_token.getExpires_in()*1000L));
		}
		return fw_token;
	}
	
	public static UserinfoAccessToken getDyUserinfoAccessToken(String appId,String appSecret,String code){
		//if(dyUserinfoAccessToken==null||dyUserinfoAccessToken.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String json =getGzUserinfoAccessToken(appId, appSecret, code);
			dyUserinfoAccessToken = JSONUtils.jsonToObject(json,UserinfoAccessToken.class);
			log.info("dyUserinfoAccessToken:"+dyUserinfoAccessToken);
			dyUserinfoAccessToken.setExpires_time(new Date((new Date()).getTime()+dyUserinfoAccessToken.getExpires_in()*1000L));
		//}
		return dyUserinfoAccessToken;
	}
	
	
	public static UserinfoAccessToken getFwUserinfoAccessToken(String appId,String appSecret,String code){
		//if(fwUserinfoAccessToken==null||fwUserinfoAccessToken.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String json =getGzUserinfoAccessToken(appId, appSecret, code);
			fwUserinfoAccessToken = JSONUtils.jsonToObject(json,UserinfoAccessToken.class);
			fwUserinfoAccessToken.setExpires_time(new Date((new Date()).getTime()+fwUserinfoAccessToken.getExpires_in()*1000L));
		//}
		return fwUserinfoAccessToken;
	}
	
	 public static String getGzUserinfoAccessToken(String appId,String appSecret,String code){
			String url=get_auth_user_access_token_url.replaceAll("APPID",appId).replaceAll("SECRET", appSecret).replaceAll("CODE", code);
			String json = HttpConnectionUtil.httpRequest(url, "POST",null);
			return json;
	 }
	 
	 
	 
	
}
