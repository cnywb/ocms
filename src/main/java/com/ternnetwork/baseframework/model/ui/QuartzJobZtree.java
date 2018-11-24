package com.ternnetwork.baseframework.model.ui;

import com.ternnetwork.baseframework.util.JSONUtils;

public class QuartzJobZtree {
	
	   private String id;
		
	   private String pId;
	
	   private String name;
	   
	   private String group;
	   
	   private String className;
	   
	   private Long realId;
	   
	   private String cronExpression;
	   
	   private Long parentId;
	   
	   private   Boolean enable;
	   
	   private Boolean isParent;
	   
	   @Override
		public String toString() {
			return JSONUtils.objectToJson(this);
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getRealId() {
		return realId;
	}

	public void setRealId(Long realId) {
		this.realId = realId;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

}
