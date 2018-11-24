package com.ternnetwork.baseframework.model.ui;

import com.ternnetwork.baseframework.util.JSONUtils;

public class Ztree {
	private long id;
	
	private long pId;
	
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getpId() {
		return pId;
	}

	public void setpId(long pId) {
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
