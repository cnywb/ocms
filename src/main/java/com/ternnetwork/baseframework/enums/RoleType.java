/**
 * 
 */
package com.ternnetwork.baseframework.enums;


public enum RoleType {
	ADMIN{public String getName(){return "管理员";}},
	ORDINARY_USER{public String getName(){return "普通用户";}},
	APP{public String getName(){return "APP";}};
	public abstract String getName();

}
