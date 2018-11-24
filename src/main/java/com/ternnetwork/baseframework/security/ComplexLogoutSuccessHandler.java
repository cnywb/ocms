package com.ternnetwork.baseframework.security;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.model.http.BaseResponse;




public class ComplexLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	private String defaultDesktopDeviceTargetUrl;
	private String defaultMobileDeviceTargetUrl;
	

	  
    public String getDefaultDesktopDeviceTargetUrl() {
		return defaultDesktopDeviceTargetUrl;
	}



	public void setDefaultDesktopDeviceTargetUrl(
			String defaultDesktopDeviceTargetUrl) {
		this.defaultDesktopDeviceTargetUrl = defaultDesktopDeviceTargetUrl;
	}



	public String getDefaultMobileDeviceTargetUrl() {
		return defaultMobileDeviceTargetUrl;
	}



	public void setDefaultMobileDeviceTargetUrl(String defaultMobileDeviceTargetUrl) {
		this.defaultMobileDeviceTargetUrl = defaultMobileDeviceTargetUrl;
	}



	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException, ServletException{
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
    	  
        super.handle(request, response, authentication);
    }
}
