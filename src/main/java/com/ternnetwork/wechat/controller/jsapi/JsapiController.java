package com.ternnetwork.wechat.controller.jsapi;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.wechat.model.auth.JsapiConfig;
import com.ternnetwork.wechat.service.jsapi.JsapiService;





@Controller@Scope("prototype")
@RequestMapping("/wechat/jsapi/*")
public class JsapiController {
	
	
	@Resource
	private JsapiService jsapiService;
	
	@RequestMapping(value="dyTest.htm")
	public ModelAndView dyTest(HttpServletRequest request){	
		JsapiConfig jsapiConfig=new JsapiConfig();
		try {
		    jsapiConfig=jsapiService.getDyJsapiConfig(request);
		} catch (NoSuchAlgorithmException e) {
		   e.printStackTrace();
		}
		return new ModelAndView("/WEB-INF/view/wechat/jsapi/test.jsp").addObject("jsapiConfig",jsapiConfig);
	}
	
	@RequestMapping(value="fwTest.htm")
	public ModelAndView fwTest(HttpServletRequest request){	
		JsapiConfig jsapiConfig=new JsapiConfig();
		try {
		    jsapiConfig=jsapiService.getFwJsapiConfig(request);
		} catch (NoSuchAlgorithmException e) {
		   e.printStackTrace();
		}
		return new ModelAndView("/WEB-INF/view/wechat/jsapi/test.jsp").addObject("jsapiConfig",jsapiConfig);
	}

	
	@RequestMapping(value="qyTest.htm")
	public ModelAndView qyTest(HttpServletRequest request){	
		JsapiConfig jsapiConfig=new JsapiConfig();
		try {
		    jsapiConfig=jsapiService.getQyJsapiConfig(request);
		} catch (NoSuchAlgorithmException e) {
		   e.printStackTrace();
		}
		return new ModelAndView("/WEB-INF/view/wechat/jsapi/test.jsp").addObject("jsapiConfig",jsapiConfig);
	}
}
