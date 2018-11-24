package com.ternnetwork.cms.enums;

public enum SiteStatus {
	
	OPEN{public String getName(){return "开启";}},
	CLOSE{public String getName(){return "关闭";}},
	MAINTAIN{public String getName(){return "维护";}};
	public abstract String getName();

}
