package com.ternnetwork.wechat.share;

import java.io.Serializable;

/**
 * Created by huangwen on 17/26/6.
 */
public abstract class BasePageInterfaceRes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6014139352146024505L;
	public Integer status;
	public String message;
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
	
}
