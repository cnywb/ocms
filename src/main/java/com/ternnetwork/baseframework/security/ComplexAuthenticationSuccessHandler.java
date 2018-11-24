package com.ternnetwork.baseframework.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.log.AuthenticationLog;
import com.ternnetwork.baseframework.service.log.AuthenticationLogService;
import com.ternnetwork.baseframework.service.security.UserService;


/**
 * 登录成功处理器
 * 判断是从电脑或还是移动设备发起的请求，和响应的数据格式
 * 登录成功后会自动跳转上一个页面
 * @author xuwenfeng
 *
 */
public class ComplexAuthenticationSuccessHandler    extends SimpleUrlAuthenticationSuccessHandler{
	

  @Resource
  private AuthenticationLogService authenticationLogService;
  
  @Resource
	private UserService userService;
  
  
  private String defaultDesktopDeviceTargetUrl;
  private String defaultMobileDeviceTargetUrl;
  
  
  
  protected final Log logger = LogFactory.getLog(getClass());
   
  private RequestCache requestCache = new HttpSessionRequestCache();
   
 
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws ServletException, IOException
   {
	   
	   
	    User user=(User) authentication.getPrincipal();
		AuthenticationLog t=new AuthenticationLog();
		t.setAddress(request.getRemoteAddr());
		t.setUserName(user.getUsername());
		t.setSessionId(request.getSession().getId());
		t.setUser(userService.findByName(user.getUsername()));
		authenticationLogService.idoAdd(t);//写入登录日志
		String deviceType = request.getParameter("deviceType");
		String responseType = request.getParameter("responseType");
		if(!StringUtils.isEmpty(deviceType)&&"mobile".equals(deviceType)){//如果是从移动端发来的请求
		    setDefaultTargetUrl(defaultMobileDeviceTargetUrl);
		}else{
			 setDefaultTargetUrl(defaultDesktopDeviceTargetUrl);
		}
		if(!StringUtils.isEmpty(responseType)&&"json".equals(responseType)){//如果是要求反回json
			 response.setCharacterEncoding("UTF-8"); 
			 BaseResponse baseResponse=new BaseResponse();
			 baseResponse.setStatus(1);
			 baseResponse.setMessage("success");
             response.getWriter().write(baseResponse.toString());  
			 return;
		}
		
	 SavedRequest savedRequest = requestCache.getRequest(request, response);
     if (savedRequest == null) {//没有拦截前一个页面的情况
       super.onAuthenticationSuccess(request, response, authentication);
       return;
     }
     String targetUrlParameter = getTargetUrlParameter();
     if ((isAlwaysUseDefaultTargetUrl()) || ((targetUrlParameter != null) && (StringUtils.hasText(request.getParameter(targetUrlParameter)))))
     {
        requestCache.removeRequest(request, response);
        super.onAuthenticationSuccess(request, response, authentication);
       
        return;
     }
     
     clearAuthenticationAttributes(request);
     String targetUrl = savedRequest.getRedirectUrl();
     logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
     getRedirectStrategy().sendRedirect(request, response, targetUrl);
   }
   
   public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
   }

public String getDefaultMobileDeviceTargetUrl() {
	return defaultMobileDeviceTargetUrl;
}

public void setDefaultMobileDeviceTargetUrl(String defaultMobileDeviceTargetUrl) {
	this.defaultMobileDeviceTargetUrl = defaultMobileDeviceTargetUrl;
}

public String getDefaultDesktopDeviceTargetUrl() {
	return defaultDesktopDeviceTargetUrl;
}

public void setDefaultDesktopDeviceTargetUrl(
		String defaultDesktopDeviceTargetUrl) {
	this.defaultDesktopDeviceTargetUrl = defaultDesktopDeviceTargetUrl;
}
}
