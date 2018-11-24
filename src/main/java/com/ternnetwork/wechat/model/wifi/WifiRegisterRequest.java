package com.ternnetwork.wechat.model.wifi;

import com.ternnetwork.baseframework.util.JSONUtils;

public class WifiRegisterRequest {
	
	private String shop_id;
	
	private String ssid;
	
	private Boolean reset=false;

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public Boolean getReset() {
		return reset;
	}

	public void setReset(Boolean reset) {
		this.reset = reset;
	} 
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	

}
