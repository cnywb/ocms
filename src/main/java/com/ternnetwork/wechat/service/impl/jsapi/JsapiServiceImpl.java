package com.ternnetwork.wechat.service.impl.jsapi;


import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.service.util.HashService;
import com.ternnetwork.baseframework.util.ExtendedStringUtils;
import com.ternnetwork.wechat.model.auth.JsapiConfig;
import com.ternnetwork.wechat.service.jsapi.JsapiService;
import com.ternnetwork.wechat.util.JsapiTicketUtil;

@Service("jsapiService")
public class JsapiServiceImpl implements JsapiService {
    
	@Resource
	private SystemParameterService systemParameterService;
	@Resource
	private HashService hashService;
	
	public JsapiConfig getQyJsapiConfig(HttpServletRequest request) throws NoSuchAlgorithmException{
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String jsapi_ticket=JsapiTicketUtil.getQyJsapiTicket(corpId, corpSecret).getTicket();
		JsapiConfig config = getJsapiConfig(request, corpId, corpSecret,jsapi_ticket);
	  	return config;
	}
	
	public JsapiConfig getDyJsapiConfig(HttpServletRequest request) throws NoSuchAlgorithmException{
		String corpId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		String jsapi_ticket=JsapiTicketUtil.getDyJsapiTicket(corpId, corpSecret).getTicket();
		JsapiConfig config = getJsapiConfig(request, corpId, corpSecret,jsapi_ticket);
	  	return config;
	}
	public JsapiConfig getFwJsapiConfig(HttpServletRequest request) throws NoSuchAlgorithmException{
		String corpId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		String jsapi_ticket=JsapiTicketUtil.getFwJsapiTicket(corpId, corpSecret).getTicket();
		JsapiConfig config = getJsapiConfig(request, corpId, corpSecret,jsapi_ticket);
	  	return config;
	}

	private JsapiConfig getJsapiConfig(HttpServletRequest request,String corpId,String corpSecret,String jsapi_ticket) throws NoSuchAlgorithmException {
		JsapiConfig config=new JsapiConfig();
	    String url=request.getRequestURL().toString();
	    String noncestr=ExtendedStringUtils.getRandomString(5);
	    String timestamp=String.valueOf((new Date()).getTime()).substring(0, 10);//只需要10位
	    StringBuffer sb=new StringBuffer();
	    sb.append("jsapi_ticket=");
	    sb.append(jsapi_ticket);
	    sb.append("&");
	    sb.append("noncestr=");
	    sb.append(noncestr);
	    sb.append("&");
	    sb.append("timestamp=");
	    sb.append(timestamp);
	    sb.append("&");
	    sb.append("url=");
	    sb.append(url);
	    String ingatureString=sb.toString();
	    System.out.println(ingatureString);
	    String signature=hashService.encryptSHA(ingatureString);
	    config.setSignature(signature);
	    config.setAppId(corpId);
	    config.setNonceStr(noncestr);
	    config.setTimestamp(String.valueOf(timestamp));
		return config;
	}

}
