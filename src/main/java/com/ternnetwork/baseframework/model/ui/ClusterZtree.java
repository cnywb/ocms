package com.ternnetwork.baseframework.model.ui;

import com.ternnetwork.baseframework.util.JSONUtils;

public class ClusterZtree {
	
	   private String id;
	
	   private String pId;
	
	   private String name;

	   private   Boolean enable;
	   
	   private Long parentId;
	   
	   private String ip;
	   
	   private Integer ftpPort;
	   
	   private String ftpPassword;
	   
	   private Boolean isParent;
	   
	   private Long realId;
	   
	   

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Long getRealId() {
		return realId;
	}

	public void setRealId(Long realId) {
		this.realId = realId;
	}

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
	   
	   
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
}
