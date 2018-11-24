package com.ternnetwork.wechat.enums;

public enum WechatAccountType {

	PUBLIC_DY{public String getName(){return "订阅号";}},
	PUBLIC_FW{public String getName(){return "服务号";}},
	ENTERPRICE{public String getName(){return "企业号";}};
	public abstract String getName();
}
