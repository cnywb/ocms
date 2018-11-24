package com.ternnetwork.cms.model.ui;

import com.ternnetwork.baseframework.model.ui.Ztree;

public class ChannelZtree extends Ztree {
private String code;//代码
	
	
	private Integer seqNum=0;//排序
	

	private String pageUrl;
	

	private Boolean visible;//是否可见


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Integer getSeqNum() {
		return seqNum;
	}


	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}


	public String getPageUrl() {
		return pageUrl;
	}


	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}


	public Boolean getVisible() {
		return visible;
	}


	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	
}
