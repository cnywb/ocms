package com.ternnetwork.wechat.model.message;

/**
 * 音频消息
 * 
 * @author 
 * @date 
 */
public class RequestVoiceMessage extends RequestBaseMessage {
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}

