package com.ternnetwork.baseframework.model.ui;

import com.ternnetwork.baseframework.enums.ResourcesType;
import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.util.JSONUtils;

public class ResourcesZtree extends Ztree {
	
	private String nameZh;//中文名
	private String nameEn;//代码
	private String url="";//链接
	private int seqNum=0;//
	private ResourcesType type;
    private RoleType roleType;
    private String[] roleIds;
	private String tabUrl="";//链接
    
	public String getNameZh() {
		return nameZh;
	}
	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	public ResourcesType getType() {
		return type;
	}
	public void setType(ResourcesType type) {
		this.type = type;
	}
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	public String getTabUrl() {
		return tabUrl;
	}
	public void setTabUrl(String tabUrl) {
		this.tabUrl = tabUrl;
	}
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

}
