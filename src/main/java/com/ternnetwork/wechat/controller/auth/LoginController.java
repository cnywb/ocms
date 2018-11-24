package com.ternnetwork.wechat.controller.auth;

import java.net.URLEncoder;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ternnetwork.baseframework.service.config.SystemParameterService;


@Controller
@RequestMapping("/wechat/auth/*")
public class LoginController {
	
	private static final Logger log = Logger.getLogger(LoginController.class);
	
	@Resource
	private SystemParameterService systemParameterService;
	
	
	
	@RequestMapping("dyLogin.htm")//订阅号登录
	public ModelAndView wechatDyLogin(String code,HttpServletRequest request){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String wechatLoginURL=URLEncoder.encode(request.getRequestURL().toString());
		return new ModelAndView("/wechat_dy_login.jsp").addObject("wechatDyAuthCode",code).addObject("wechatLoginURL", wechatLoginURL).addObject("appId",appId);
	}
	
	@RequestMapping("fwLogin.htm")//服务号登录
	public ModelAndView wechatFwLogin(String code,HttpServletRequest request){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String wechatLoginURL=URLEncoder.encode(request.getRequestURL().toString());
		return new ModelAndView("/wechat_fw_login.jsp").addObject("wechatFwAuthCode",code).addObject("wechatLoginURL", wechatLoginURL).addObject("appId",appId);
	}
	
	@RequestMapping("qyLogin.htm")//企业号登录
	public ModelAndView wechatQyLogin(String code,HttpServletRequest request){
		String appId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");//注意这个与公众号的WECHAT_DY_APP_ID不一样的。
		String wechatLoginURL=URLEncoder.encode(request.getRequestURL().toString());
		return new ModelAndView("/wechat_qy_login.jsp").addObject("wechatQyAuthCode",code).addObject("wechatLoginURL", wechatLoginURL).addObject("appId",appId);
	}
	
	
	
}
