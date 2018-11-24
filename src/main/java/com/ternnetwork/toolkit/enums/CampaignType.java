package com.ternnetwork.toolkit.enums;

public enum CampaignType {
	TYPE_1{public String getName(){return "老用户荐新";}},
	TYPE_2{public String getName(){return "老用户再购";}},
	TYPE_3{public String getName(){return "新用户自荐";}},
	TYPE_4{public String getName(){return "售后活动";}};
	public abstract String getName();
}
