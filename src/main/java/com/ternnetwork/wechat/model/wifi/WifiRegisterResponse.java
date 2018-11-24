package com.ternnetwork.wechat.model.wifi;

public class WifiRegisterResponse {
	
	private Integer errcode;
	
	private WifiRegisterResponseData data;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public WifiRegisterResponseData getData() {
		return data;
	}

	public void setData(WifiRegisterResponseData data) {
		this.data = data;
	}
	
	

}
