package com.ternnetwork.wechat.model.qy.message;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.ternnetwork.baseframework.util.JSONUtils;

@JsonAutoDetect
public class TextRequestBody {
    
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

}
