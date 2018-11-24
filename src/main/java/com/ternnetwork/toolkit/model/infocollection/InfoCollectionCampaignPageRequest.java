package com.ternnetwork.toolkit.model.infocollection;

import java.util.Date;

public class InfoCollectionCampaignPageRequest {
	
	private String openId;
	
	private String kvUrl;
	
	private Date startTime;
	
	private Date endTime;
	
	private String subject;
	
	private String award;
	
	private String product;
	
	private String memo;
	
	private Boolean preview;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getKvUrl() {
		return kvUrl;
	}

	public void setKvUrl(String kvUrl) {
		this.kvUrl = kvUrl;
	}

	

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getPreview() {
		return preview;
	}

	public void setPreview(Boolean preview) {
		this.preview = preview;
	}
	
	

}
