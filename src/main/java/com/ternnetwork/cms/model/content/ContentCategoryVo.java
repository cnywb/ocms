package com.ternnetwork.cms.model.content;

public class ContentCategoryVo {

	private long id;
	
	private String name;//
	
	private String code;
	
	
	private String description;
	
	
	private String pageUrl;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	
	public void setDescription(String description) {
		this.description = description;
	}


	public String getPageUrl() {
		return pageUrl;
	}


	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}


	public String getDescription() {
		return description;
	}


	public ContentCategoryVo() {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.pageUrl = pageUrl;
	}


	
	
	
	
	
	
}
