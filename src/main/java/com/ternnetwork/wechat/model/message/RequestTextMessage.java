package com.ternnetwork.wechat.model.message;


/**
 * 文本消息
 * 
 * @author 
 * @date 
 */
public class RequestTextMessage extends RequestBaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
