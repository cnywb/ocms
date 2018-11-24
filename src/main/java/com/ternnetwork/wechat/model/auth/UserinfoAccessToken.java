package com.ternnetwork.wechat.model.auth;

import java.util.Date;




/**
 * 
 * 公众号授权用户得到详细用户信息
 * 首先从https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
 * 得到code
 * 
 * 再通过以下地址得到UserinfoAccessToken
 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
 * @author xuwenfeng
 *
 */
public class UserinfoAccessToken  {
	
	private String access_token;
	// 凭证有效时间，单位：秒
	private int expires_in;
	
	private Date expires_time;
	
	private String openid;
	
	private String scope;
	
	private String unionid;
	
	private String refresh_token;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

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
