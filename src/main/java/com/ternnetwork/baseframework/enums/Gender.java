/**
 * 
 */
package com.ternnetwork.baseframework.enums;


public enum Gender {
	FM{public String getName(){return "女";}},
	M{public String getName(){return "男";}},
	UNKNOWN{public String getName(){return "未知";}};
	public abstract String getName();

}
