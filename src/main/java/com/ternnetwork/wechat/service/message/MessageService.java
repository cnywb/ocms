package com.ternnetwork.wechat.service.message;



import javax.servlet.http.HttpServletRequest;

import com.ternnetwork.wechat.model.qy.message.TextRequestMessage;

public interface MessageService {
	public boolean checkDySignature(String signature, String timestamp, String nonce);
	public boolean checkFwSignature(String signature, String timestamp, String nonce);
	public String checkQySignature(String msg_signature,String timestamp,String nonce,String echostr);
	public String idoProcessRequest(HttpServletRequest request);
	public String idoProcessQyRequest(String msg_signature,String timestamp,String nonce,HttpServletRequest request);
	public String sendQyMessage(String json);
	public String sendQyMessage(TextRequestMessage t);	
	public String sendDyMessage(String json);
	public String sendFwMessage(String json);

}