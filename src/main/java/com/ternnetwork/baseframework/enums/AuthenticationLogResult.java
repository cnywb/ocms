package com.ternnetwork.baseframework.enums;

public enum AuthenticationLogResult {
	SUCCESS{public String getName(){return "成功";}},
	FAILURE{public String getName(){return "失败";}};
	public abstract String getName();
}
