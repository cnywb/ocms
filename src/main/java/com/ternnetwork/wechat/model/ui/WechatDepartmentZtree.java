package com.ternnetwork.wechat.model.ui;

import com.ternnetwork.baseframework.util.JSONUtils;

public class WechatDepartmentZtree {
	
	private String id;
	
	private String pId;
	
	private String name;
	
	private String parentid;//上级部门ID
	
	private String order;//排序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	
	
}
