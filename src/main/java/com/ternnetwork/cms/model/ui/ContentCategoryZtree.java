package com.ternnetwork.cms.model.ui;



import com.ternnetwork.baseframework.model.ui.Ztree;

public class ContentCategoryZtree extends Ztree {

	private String code;//代码
	
	
	private Integer seqNum=0;//排序
	

	private String pageUrl;
	
	private String nameZh;
	
	private String nameEn;

	private Boolean visible;//是否可见
	
	private long parentId;
	
	
	private String tempContentDescriptionString;


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


	public String getTempContentDescriptionString() {
		return tempContentDescriptionString;
	}


	public void setTempContentDescriptionString(String tempContentDescriptionString) {
		this.tempContentDescriptionString = tempContentDescriptionString;
	}


	public long getParentId() {
		return parentId;
	}


	public void setParentId(long parentId) {
		this.parentId = parentId;
	}


	public String getNameZh() {
		return nameZh;
	}


	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}


	public String getNameEn() {
		return nameEn;
	}


	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	
	
	
}
