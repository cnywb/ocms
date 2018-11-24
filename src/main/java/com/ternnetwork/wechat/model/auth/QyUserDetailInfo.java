package com.ternnetwork.wechat.model.auth;

public class QyUserDetailInfo {
	
	private int errcode;
	
	private String errmsg;
	
	private String userid;
	
	private String name;
	
	private int[] department;
	
	private String position;
	
	private String mobile;
	
	private String gender;
	
	private String weixinid;
	
	private String avatar;
	
    private String status;
    
    private String email;
    
    
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private QyUserDetailInfoExtAttr extattr;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getDepartment() {
		return department;
	}

	public void setDepartment(int[] department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWeixinid() {
		return weixinid;
	}

	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public QyUserDetailInfoExtAttr getExtattr() {
		return extattr;
	}

	public void setExtattr(QyUserDetailInfoExtAttr extattr) {
		this.extattr = extattr;
	}
    
    
    
    
}
