package com.ternnetwork.wechat.model.qy.txl;

public class WechatQyDepartment {
	
	private String id;
	
	private String name;//部门名称
	
	private String parentid;//上级部门ID
	
	private String order;//排序
	
	

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
