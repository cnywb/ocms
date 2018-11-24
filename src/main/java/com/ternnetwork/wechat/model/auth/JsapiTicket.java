package com.ternnetwork.wechat.model.auth;

import java.util.Date;

public class JsapiTicket {
    
	private int errcode;
	
	private String errmsg;
	
	private String ticket;
	
    private int expires_in;
	
	private Date expires_time;

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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public Date getExpires_time() {
		return expires_time;
	}

	public void setExpires_time(Date expires_time) {
		this.expires_time = expires_time;
	}
	
	
	

}
