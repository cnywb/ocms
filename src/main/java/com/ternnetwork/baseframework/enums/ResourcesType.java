/**
 * 
 */
package com.ternnetwork.baseframework.enums;


public enum ResourcesType {
	MENU{public String getName(){return "菜单";}},
	FUNCTION_POINT{public String getName(){return "功能点";}};
	public abstract String getName();

}
