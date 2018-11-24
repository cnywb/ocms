package com.ternnetwork.cms.enums;

public enum CmsPageType {

	INDEX{public String getName(){return "首页";}},
	CHANNEL_INDEX{public String getName(){return "栏目首页";}},
	CHANNEL_ALONE{public String getName(){return "栏目单页";}},
	CONTENT_LIST{public String getName(){return "内容列表页";}},
	CONTENT_DETAIL{public String getName(){return "内容详情页";}};
	public abstract String getName();
}
