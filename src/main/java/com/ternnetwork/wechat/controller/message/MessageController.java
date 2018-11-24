package com.ternnetwork.wechat.controller.message;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.wechat.service.message.MessageService;





@Controller@Scope("prototype")
@RequestMapping("/wechat/message/*")
public class MessageController {
	private static final Logger log = Logger.getLogger(MessageController.class);
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private SystemParameterService systemParameterService;
	
	@RequestMapping(value="qyMessageSend.htm",method=RequestMethod.GET)
	public ModelAndView qyMessageSend(){	
		String agentId=systemParameterService.getValueByKey("WECHAT_QY_AGENT_ID");
		return new ModelAndView("/WEB-INF/view/wechat/qy/message/message_send.jsp").addObject("agentid",agentId);
	}
	
	
	@RequestMapping(value="dyMessageSend.htm",method=RequestMethod.GET)
	public ModelAndView dyMessageSend(){	
	   return new ModelAndView("/WEB-INF/view/wechat/dy/message/message_send.jsp");
	}
	
	
	@RequestMapping(value="fwMessageSend.htm",method=RequestMethod.GET)
	public ModelAndView fwMessageSend(){	
	   return new ModelAndView("/WEB-INF/view/wechat/fw/message/message_send.jsp");
	}
	
	/**
	 * 验证接口
	 * @param out
	 * @param response
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@RequestMapping(value="dyReceive.htm",method=RequestMethod.GET)
	public void dyReceiveGet(PrintWriter out,HttpServletResponse response,String signature,String timestamp,String nonce,String echostr){
		try{
			response.setContentType("text/javascript");
			log.info("signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+" timestamp:"+timestamp+" echostr:"+echostr);
			Boolean retVal=messageService.checkDySignature(signature,timestamp,nonce);
			log.info("校验结果："+retVal);
			if(retVal){
				out.print(echostr);
			}
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	/**
	 * 消息接收接口
	 * @param out
	 * @param response
	 * @param request
	 */
	@RequestMapping(value="dyReceive.htm",method=RequestMethod.POST)
	public void dyReceivePost(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = messageService.idoProcessRequest(request);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="fwReceive.htm",method=RequestMethod.GET)
	public void fwReceiveGet(PrintWriter out,HttpServletResponse response,String signature,String timestamp,String nonce,String echostr){
		try{
			response.setContentType("text/javascript");
			log.info("signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+" timestamp:"+timestamp+" echostr:"+echostr);
			Boolean retVal=messageService.checkFwSignature(signature,timestamp,nonce);
			log.info("校验结果："+retVal);
			if(retVal){
				out.print(echostr);
			}
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="fwReceive.htm",method=RequestMethod.POST)
	public void fwReceivePost(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = messageService.idoProcessRequest(request);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	/**
	 * 企业号验证接口
	 * @param out
	 * @param response
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@RequestMapping(value="qyReceive.htm",method=RequestMethod.GET)
	public void qyReceiveGet(PrintWriter out,HttpServletResponse response,String msg_signature,String timestamp,String nonce,String echostr){
		try{
			response.setContentType("text/javascript");
			log.info("signature:"+msg_signature+" timestamp:"+timestamp+" nonce:"+nonce+" timestamp:"+timestamp);
			out.print(messageService.checkQySignature(msg_signature,timestamp,nonce,echostr));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	/**
	 * 企业号消息接收接口
	 * @param out
	 * @param response
	 * @param request
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 */
	@RequestMapping(value="qyReceive.htm",method=RequestMethod.POST)
	public void qyReceivePost(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String msg_signature,String timestamp,String nonce){
		try{
			response.setContentType("text/javascript");
			String respMessage = messageService.idoProcessQyRequest(msg_signature,timestamp,nonce,request);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

	
	@RequestMapping(value="sendQyMessage.htm",method=RequestMethod.POST)
	public void sendQyMessage(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = messageService.sendQyMessage(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="sendDyMessage.htm",method=RequestMethod.POST)
	public void sendDyMessage(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = messageService.sendDyMessage(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="sendFwMessage.htm",method=RequestMethod.POST)
	public void sendFwMessage(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = messageService.sendFwMessage(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
}
