package com.ternnetwork.toolkit.enums;

public enum CampaignDataSendFrequency {
	EVERY_DAY{public String getName(){return "每天";}},
	EVERY_WEEK{public String getName(){return "每周";}},
	EVERY_MONTH{public String getName(){return "每月";}},
	NEVER{public String getName(){return "从不";}};
	public abstract String getName();
}
