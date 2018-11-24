package com.ternnetwork.wechat.model.qy.txl;

import java.util.List;

import com.ternnetwork.baseframework.util.JSONUtils;

public class WechatQyUserResponse {
    
	private String errmsg;
	
	private String errcode;
	
	private List<WechatQyUser> userlist;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public List<WechatQyUser> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<WechatQyUser> userlist) {
		this.userlist = userlist;
	}
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	
}
