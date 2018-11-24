package com.ternnetwork.wechat.model.qy.txl;

import java.util.List;

import com.ternnetwork.baseframework.util.JSONUtils;

public class WechatQyUserExtraAttribute {
	
	private List<WechatQyUserAttribute> attrs;

	public List<WechatQyUserAttribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<WechatQyUserAttribute> attrs) {
		this.attrs = attrs;
	}
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	

}
