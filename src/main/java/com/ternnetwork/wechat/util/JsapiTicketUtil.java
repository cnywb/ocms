package com.ternnetwork.wechat.util;

import java.util.Date;


import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.wechat.model.auth.JsapiTicket;

public class JsapiTicketUtil {
    
	private static JsapiTicket qyJsapiTicket;
	
	private static JsapiTicket dyJsapiTicket;
	
	private static JsapiTicket fwJsapiTicket;
	
	private static String qy_get_jsapi_ticket_url="https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";

	private static String gz_get_jsapi_ticket_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	
	public static JsapiTicket getQyJsapiTicket(String corpId, String corpSecret) {
		if(qyJsapiTicket==null||qyJsapiTicket.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String accessToken=AccessTokenUtil.getQyAccessToken(corpId, corpSecret).getAccess_token();
			String requestUrl = qy_get_jsapi_ticket_url.replace("ACCESS_TOKEN",accessToken);
			String json = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);
			qyJsapiTicket = JSONUtils.jsonToObject(json,JsapiTicket.class);
			qyJsapiTicket.setExpires_time(new Date((new Date()).getTime()+qyJsapiTicket.getExpires_in()*1000L));
		}
		return qyJsapiTicket;
	}
	public static JsapiTicket getDyJsapiTicket(String corpId, String corpSecret) {
		if(dyJsapiTicket==null||dyJsapiTicket.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String accessToken=AccessTokenUtil.getDyAccessToken(corpId, corpSecret).getAccess_token();
			String requestUrl = gz_get_jsapi_ticket_url.replace("ACCESS_TOKEN",accessToken);
			String json = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);
			dyJsapiTicket = JSONUtils.jsonToObject(json,JsapiTicket.class);
			dyJsapiTicket.setExpires_time(new Date((new Date()).getTime()+dyJsapiTicket.getExpires_in()*1000L));
		}
		return dyJsapiTicket;
	}
	
	
	public static JsapiTicket getFwJsapiTicket(String corpId, String corpSecret) {
		if(fwJsapiTicket==null||fwJsapiTicket.getExpires_time().getTime()<(new Date()).getTime()){//如果不存在实例或是时间已经过期则重新获取实例
			String accessToken=AccessTokenUtil.getDyAccessToken(corpId, corpSecret).getAccess_token();
			String requestUrl = gz_get_jsapi_ticket_url.replace("ACCESS_TOKEN",accessToken);
			String json = HttpConnectionUtil.httpRequest(requestUrl, "GET", null);
			fwJsapiTicket = JSONUtils.jsonToObject(json,JsapiTicket.class);
			fwJsapiTicket.setExpires_time(new Date((new Date()).getTime()+fwJsapiTicket.getExpires_in()*1000L));
		}
		return fwJsapiTicket;
	}
}
