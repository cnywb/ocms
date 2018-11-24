package com.ternnetwork.baseframework.model.http;

import com.ternnetwork.baseframework.util.JSONUtils;

public class BaseResponse {
	
	private Integer status;
	
	private String message;
	
	private Object data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	

}
