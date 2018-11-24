package com.ternnetwork.wechat.model.qy.txl;

import java.util.List;

public class WechatQyDepartmentResponse {

	private String errmsg;
	
	private String errcode;
	
	private List<WechatQyDepartment> department;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public List<WechatQyDepartment> getDepartment() {
		return department;
	}

	public void setDepartment(List<WechatQyDepartment> department) {
		this.department = department;
	}
	
	
	
}
