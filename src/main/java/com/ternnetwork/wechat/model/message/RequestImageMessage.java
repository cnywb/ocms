package com.ternnetwork.wechat.model.message;

/**
 * 图片消息
 * 
 * @author 
 * @date 
 */
public class RequestImageMessage extends RequestBaseMessage {
	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}

