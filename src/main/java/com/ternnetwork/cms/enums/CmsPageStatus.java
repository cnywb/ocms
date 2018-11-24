package com.ternnetwork.cms.enums;

public enum CmsPageStatus {
	CLOSED{public String getName(){return "已关闭";}},
	PUBLISHED{public String getName(){return "已发布";}},
	UNPUBLISHED{public String getName(){return "未发布";}};
	public abstract String getName();

}
