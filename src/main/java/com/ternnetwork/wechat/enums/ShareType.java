package com.ternnetwork.wechat.enums;

public enum ShareType {
	
	WECHAT_FRIEND_ZONE{public String getName(){return "微信朋友圈";}},
	WECHAT_FRIEND{public String getName(){return "微信朋友";}},
	QQ_FRIEND{public String getName(){return "QQ朋友";}},
	TX_WEIBO{public String getName(){return "腾讯微博";}};
	public abstract String getName();

}
