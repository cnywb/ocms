package com.ternnetwork.wechat.model.user;

public class UserResponse {
	
	private String total;
	private String count;
	private String next_openid;
	private UserResponseData data;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
	public UserResponseData getData() {
		return data;
	}
	public void setData(UserResponseData data) {
		this.data = data;
	}
	
	
	
	

}
