package com.ternnetwork.baseframework.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.enums.AuthenticationLogResult;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.log.AuthenticationLog;
import com.ternnetwork.baseframework.service.log.AuthenticationLogService;
import com.ternnetwork.baseframework.service.security.UserService;

/**
 * 用户登录失败处理器
 * 返回登录失败页面或是打印出失败信息
 * 
 * @author xuwenfeng
 *
 */
public class ComplexAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {
	
	@Resource
	private AuthenticationLogService authenticationLogService;
	@Resource
	private UserService userService;
	
	private String defaultDesktopDeviceFailureUrl;
	
	private String defaultMobileDeviceFailureUrl;
	
	



	





	public String getDefaultMobileDeviceFailureUrl() {
		return defaultMobileDeviceFailureUrl;
	}











	public void setDefaultMobileDeviceFailureUrl(
			String defaultMobileDeviceFailureUrl) {
		this.defaultMobileDeviceFailureUrl = defaultMobileDeviceFailureUrl;
	}











	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)throws IOException, ServletException
	{
		String username=request.getParameter("username")==null?"":request.getParameter("username").toString();
		AuthenticationLog t=new AuthenticationLog();
		t.setAddress(request.getRemoteAddr());
		t.setUserName(username);
		t.setUser(userService.findByName(username));
		t.setResult(AuthenticationLogResult.FAILURE);
		authenticationLogService.idoAdd(t);
		String deviceType = request.getParameter("deviceType");
		String responseType = request.getParameter("responseType");
		if(!StringUtils.isEmpty(deviceType)&&"mobile".equals(deviceType)){//如果是从移动端发来的请求
		     setDefaultFailureUrl(defaultMobileDeviceFailureUrl);
		}else{
			 setDefaultFailureUrl(defaultDesktopDeviceFailureUrl);
		}
		if(!StringUtils.isEmpty(responseType)&&"json".equals(responseType)){//如果是要求反回json
			 response.setCharacterEncoding("UTF-8");  
			 BaseResponse baseResponse=new BaseResponse();
			 baseResponse.setStatus(0);
			 baseResponse.setMessage(exception.getMessage());
             response.getWriter().write(baseResponse.toString());  
			 return;
		}
		
		super.onAuthenticationFailure(request, response, exception);
	}











	public String getDefaultDesktopDeviceFailureUrl() {
		return defaultDesktopDeviceFailureUrl;
	}











	public void setDefaultDesktopDeviceFailureUrl(
			String defaultDesktopDeviceFailureUrl) {
		this.defaultDesktopDeviceFailureUrl = defaultDesktopDeviceFailureUrl;
	}

}
