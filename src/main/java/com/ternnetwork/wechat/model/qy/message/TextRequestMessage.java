package com.ternnetwork.wechat.model.qy.message;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.ternnetwork.baseframework.util.JSONUtils;

@JsonAutoDetect
public class TextRequestMessage extends BaseRequestMessage {
    
	private String msgtype="text";
	
	private TextRequestBody text;

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public TextRequestBody getText() {
		return text;
	}

	public void setText(TextRequestBody text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

}
