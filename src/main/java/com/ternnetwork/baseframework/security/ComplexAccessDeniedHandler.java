package com.ternnetwork.baseframework.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.model.http.BaseResponse;


/**
 * 拒拒访问处理器
 * @author xuwenfeng
 *
 */
public class ComplexAccessDeniedHandler implements AccessDeniedHandler {

	private String defaultTargetUrl;
	
	private String defaultDesktopDeviceTargetUrl;
	
	private String defaultMobileDeviceTargetUrl;
	
	
	public void handle(HttpServletRequest request,HttpServletResponse response,AccessDeniedException accessDeniedException)throws IOException, ServletException {
		
		
		String deviceType = request.getParameter("deviceType");
		String responseType = request.getParameter("responseType");
		if(!StringUtils.isEmpty(deviceType)&&"mobile".equals(deviceType)){//如果是从移动端发来的请求
		     setDefaultTargetUrl(defaultMobileDeviceTargetUrl);
		}else{
			 setDefaultTargetUrl(defaultDesktopDeviceTargetUrl);
		}
        if (!response.isCommitted()) {
    		if(!StringUtils.isEmpty(responseType)&&"json".equals(responseType)){//如果是要求反回json
   			 response.setCharacterEncoding("UTF-8"); 
   			 BaseResponse baseResponse=new BaseResponse();
   			 baseResponse.setStatus(0);
   			 baseResponse.setMessage("Access Denied");
                response.getWriter().write(baseResponse.toString());  
   			 return;
   		     }
        	
            if (defaultTargetUrl != null) {  
                // Put exception into request scope (perhaps of use to a view)  
                request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);  
  
                // Set the 403 status code.  
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  
  
                // forward to error page.  
                RequestDispatcher dispatcher = request.getRequestDispatcher(defaultTargetUrl);  
                dispatcher.forward(request, response);  
            } else {  
                response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());  
            }  
        }  
		
	}

	
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}


	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}


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



	

}
