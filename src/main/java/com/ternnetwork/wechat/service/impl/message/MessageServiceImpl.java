package com.ternnetwork.wechat.service.impl.message;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.wechat.model.auth.QyExtAttr;
import com.ternnetwork.wechat.model.auth.QyUserDetailInfo;
import com.ternnetwork.wechat.model.message.ResponseTextMessage;
import com.ternnetwork.wechat.model.qy.message.TextRequestMessage;
import com.ternnetwork.wechat.service.media.MediaService;
import com.ternnetwork.wechat.service.message.MessageService;
import com.ternnetwork.wechat.service.qy.txl.WechatQyUserService;
import com.ternnetwork.wechat.util.MessageUtil;
import com.ternnetwork.wechat.util.SignUtil;


@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private SystemParameterService systemParameterService;
	@Resource
	private MediaService mediaService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private WechatQyUserService wechatQyUserService;
	
	private static final Logger log = Logger.getLogger(MessageServiceImpl.class);
	
	
	
	   public boolean checkDySignature(String signature, String timestamp, String nonce){
		   String token=systemParameterService.getValueByKey("WECHAT_DY_APP_TOKEN");
		   log.info("WECHAT_DY_APP_TOKEN:"+token);
		   return SignUtil.checkSignature(signature, timestamp, nonce, token);
	   }
	   
	   public boolean checkFwSignature(String signature, String timestamp, String nonce){
		   String token=systemParameterService.getValueByKey("WECHAT_FW_APP_TOKEN");
		   log.info("WECHAT_FW_APP_TOKEN:"+token);
		   return SignUtil.checkSignature(signature, timestamp, nonce, token);
	   }
	   
	   public String checkQySignature(String msg_signature,String timestamp,String nonce,String echostr){
		   String token=systemParameterService.getValueByKey("WECHAT_QY_APP_TOKEN");
		   String corpID=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		   String encodingAESKey=systemParameterService.getValueByKey("WECHAT_QY_AES_KEY");
		   return SignUtil.checkQySignature(msg_signature, timestamp, nonce, echostr, encodingAESKey, token, corpID);
	   }
	
		/**
		 * 处理微信发来的请求
		 * 
		 * @param request
		 * @return
		 */
		public String idoProcessRequest(HttpServletRequest request) {
			String respMessage = null;
			try {
				// 默认返回的文本消息内容
				String respContent = "请求处理异常，请稍候尝试！";

				// xml请求解析
				Map<String, String> requestMap = MessageUtil.parseXml(request);

				// 发送方帐号（open_id）
				String fromUserName = requestMap.get("FromUserName");
				// 公众帐号
				String toUserName = requestMap.get("ToUserName");
				// 消息类型
				String msgType = requestMap.get("MsgType");

				// 回复文本消息
				ResponseTextMessage textMessage = new ResponseTextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);

				// 文本消息
				if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
					
					
					respContent = "您发送的是文本消息！";
				}
				// 图片消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
					respContent = "您发送的是图片消息！";
				}
				// 地理位置消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					respContent = "您发送的是地理位置消息！";
				}
				// 链接消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
					respContent = "您发送的是链接消息！";
				}
				// 音频消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
					respContent = "您发送的是音频消息！";
				}
				// 事件推送
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
					// 事件类型
					String eventType = requestMap.get("Event");
					// 订阅
					if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						userService.idoAddByWechat(fromUserName);//注册用户
						respContent = "谢谢您的关注！";
					}
					// 取消订阅
					else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					}
					// 自定义菜单点击事件
					else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
						// TODO 自定义菜单权没有开放，暂不处理该类消息
					}
				}

				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				log.info(respMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return respMessage;
		}

		
		
		public String idoProcessQyRequest(String msg_signature,String timestamp,String nonce,HttpServletRequest request) {
			String respMessage = null;
			   String token=systemParameterService.getValueByKey("WECHAT_QY_APP_TOKEN");
			   String corpID=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
			   String encodingAESKey=systemParameterService.getValueByKey("WECHAT_QY_AES_KEY");
			try {
				// 默认返回的文本消息内容
				String respContent = "请求处理异常，请稍候尝试！";
				String requestXml=MessageUtil.inputStreamToString(request.getInputStream());
				
				String decryptRequestXml=SignUtil.decryptMsg(msg_signature, timestamp, nonce, requestXml, encodingAESKey, token, corpID);
				// xml请求解析
				Map<String, String> requestMap = MessageUtil.parseQyXml(decryptRequestXml);

				// 发送方帐号（open_id）
				String fromUserName = requestMap.get("FromUserName");
				// 公众帐号
				String toUserName = requestMap.get("ToUserName");
				// 消息类型
				String msgType = requestMap.get("MsgType");

				// 回复文本消息
				ResponseTextMessage textMessage = new ResponseTextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);

				// 文本消息
				if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
					
					
					respContent = "您发送的是文本消息！";
				}
				// 图片消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
					downloadQyMedia(request, requestMap, fromUserName);
					respContent = "您发送的是图片消息！";
				}
				// 地理位置消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					respContent = "您发送的是地理位置消息！";
				}
				// 链接消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
					respContent = "您发送的是链接消息！";
				}
				// 音频消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
					downloadQyMedia(request, requestMap, fromUserName);
					respContent = "您发送的是音频消息！";
				}
				// 事件推送
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
					// 事件类型
					String eventType = requestMap.get("Event");
					// 订阅
					
					if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						
						QyUserDetailInfo qyUserDetailInfo=wechatQyUserService.getById(fromUserName);
						
						List<QyExtAttr> qyExtAttrList=qyUserDetailInfo.getExtattr().getAttrs();
						
						userService.idoAddByQyWechat(fromUserName, qyUserDetailInfo.getName(), qyUserDetailInfo.getMobile(), qyUserDetailInfo.getGender(), qyUserDetailInfo.getAvatar(),"");
						
						log.info("企业号关注事件被触发-----------");
						respContent = "感谢您的关注！";
					}
					if (eventType.equals(MessageUtil.EVENT_TYPE_ENTER_AGENT)) {
						respContent = "";
					}
					// 取消订阅
					else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						log.info("企业号取消关注事件被触发-----------");
						// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					}
					// 自定义菜单点击事件
					else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
						// TODO 自定义菜单权没有开放，暂不处理该类消息
					}
				}

				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				log.info(respMessage);
				respMessage=SignUtil.encryptMsg(timestamp, nonce, respMessage, encodingAESKey, token, corpID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return respMessage;
		}

		private void downloadQyMedia(HttpServletRequest request,
				Map<String, String> requestMap, String fromUserName) {
			String savePath = request.getSession().getServletContext().getRealPath("/") + PropUtils.getPropertyValue("editor.properties","attached_save_path")+"/"+fromUserName+"/";
			String mediaId=requestMap.get("MediaId");
			mediaService.downloadQyMedia(mediaId, savePath);
		}

		@Override
		public String sendQyMessage(String json) {
			String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
			String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
			return MessageUtil.sendQyMessage(corpId, corpSecret, json);
		}
		
		
        public String sendQyMessage(TextRequestMessage t){
            String agentId=systemParameterService.getValueByKey("WECHAT_QY_AGENT_ID");
            t.setAgentid(agentId);
            return sendQyMessage(t.toString());
        }
		
		
		public String sendDyMessage(String json) {
			String corpId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
			String corpSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
			return MessageUtil.sendDyMessage(corpId, corpSecret, json);
		}

		
		public String sendFwMessage(String json) {
			String corpId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
			String corpSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
			return MessageUtil.sendFwMessage(corpId, corpSecret, json);
		}

}