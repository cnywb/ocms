package com.ternnetwork.wechat.model.qy.message;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.ternnetwork.baseframework.util.JSONUtils;



@JsonAutoDetect
public class BaseRequestMessage {
    
	private String touser;
	private String toparty;
	private String totag;
	private String agentid;
	private String safe="0";
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getSafe() {
		return safe;
	}
	public void setSafe(String safe) {
		this.safe = safe;
	}
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

	

}