package com.ternnetwork.wechat.model.auth;


import org.codehaus.jackson.annotate.JsonProperty;



/**
 * 企业号用户信息
 * @author xuwenfeng
 *
 */
public class QyAuthUserInfo {
	
	
	@JsonProperty(value = "UserId") //报文里属性开头字母是大写，不作此注解会报错
	private String userId;
	
	@JsonProperty(value = "OpenId")
	private String openId;
	
	@JsonProperty(value = "DeviceId")
	private String deviceId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}



	
	
	
	

}
