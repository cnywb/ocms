package com.ternnetwork.wechat.model.auth;

import java.util.Date;



/**
 * 微信通用接口凭证
 * 
 * @author
 * @date 
 */
public class AccessToken {
	// 获取到的凭证
	private String access_token;
	// 凭证有效时间，单位：秒
	private int expires_in;
	
	private Date expires_time;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public Date getExpires_time() {
		return expires_time;
	}
	public void setExpires_time(Date expires_time) {
		this.expires_time = expires_time;
	}


}
