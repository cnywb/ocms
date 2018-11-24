package com.ternnetwork.baseframework.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.model.http.BaseResponse;


/**
 * 未登录入口
 * 判断是从电脑或还是移动设备发起的请求，和响应的数据格式
 * @author xuwenfeng
 *
 */
public class ComplexAuthenticationEntryPoint  implements AuthenticationEntryPoint, InitializingBean{
                private static final Log logger = LogFactory.getLog(ComplexAuthenticationEntryPoint.class);
   
 


 
              private PortMapper portMapper = new PortMapperImpl();
             
              private PortResolver portResolver = new PortResolverImpl();
              
              private String desktopDeviceLoginFormUrl;
              
              private String mobileDeviceLoginFormUrl;
                
              private boolean forceHttps = false;
                  
              private boolean useForward = false;
                    
              private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                     
              public ComplexAuthenticationEntryPoint(String desktopDeviceLoginFormUrl,String mobileDeviceLoginFormUrl) {
                    Assert.notNull(desktopDeviceLoginFormUrl, "desktopDeviceLoginFormUrl cannot be null");
                    this.desktopDeviceLoginFormUrl = desktopDeviceLoginFormUrl;
                    this.mobileDeviceLoginFormUrl=mobileDeviceLoginFormUrl;
              }
                         
                      
              public void afterPropertiesSet()throws Exception{
               
            	  Assert.isTrue((StringUtils.hasText(desktopDeviceLoginFormUrl)) && (UrlUtils.isValidRedirectUrl(desktopDeviceLoginFormUrl)), "desktopDeviceLoginFormUrl must be specified and must be a valid redirect URL");
               
            	  if ((useForward) && (UrlUtils.isAbsoluteUrl(desktopDeviceLoginFormUrl))) {
                     throw new IllegalArgumentException("useForward must be false if using an absolute loginFormURL");
                   }
                	  Assert.notNull(portMapper, "portMapper must be specified");
                      Assert.notNull(portResolver, "portResolver must be specified");
                  }
 
            protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            {
                String deviceType = request.getParameter("deviceType");
            	if(!StringUtils.isEmpty(deviceType)&&"mobile".equals(deviceType)){//如果是从移动端发来的请求
            	   return mobileDeviceLoginFormUrl;
            	}
                  return getDesktopDeviceLoginFormUrl();
            }
 



 public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException, ServletException{
    String redirectUrl = null;
  
    String responseType = request.getParameter("responseType");

	if(!StringUtils.isEmpty(responseType)&&"json".equals(responseType)){//如果是要求反回json
		 response.setCharacterEncoding("UTF-8");  
		 BaseResponse baseResponse=new BaseResponse();
		 baseResponse.setStatus(0);
		 baseResponse.setMessage("User not login");
         response.getWriter().write(baseResponse.toString());  
		 return;
	}
    
    if (useForward){
       if ((forceHttps) && ("http".equals(request.getScheme()))){
           redirectUrl = buildHttpsRedirectUrlForRequest(request);
       }
       
      if (redirectUrl == null) {
        String loginForm = determineUrlToUseForThisRequest(request, response, authException);
         if (logger.isDebugEnabled()) {
          logger.debug("Server side forward to: " + loginForm);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
         
        dispatcher.forward(request, response);
      }
       
     }else{

       redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
     
     }
     
 
     redirectStrategy.sendRedirect(request, response, redirectUrl);
   }

  protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
   {
    String loginForm = determineUrlToUseForThisRequest(request, response, authException);
    

    if (UrlUtils.isAbsoluteUrl(loginForm)) {
      return loginForm;
    }
     
     int serverPort = portResolver.getServerPort(request);
     String scheme = request.getScheme();
     
     RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
     urlBuilder.setScheme(scheme);
    urlBuilder.setServerName(request.getServerName());
    urlBuilder.setPort(serverPort);
     urlBuilder.setContextPath(request.getContextPath());
     urlBuilder.setPathInfo(loginForm);
     
    if ((forceHttps) && ("http".equals(scheme))) {
       Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));
       
      if (httpsPort != null)
       {
         urlBuilder.setScheme("https");
         urlBuilder.setPort(httpsPort.intValue());
       }
      else {
         logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port " + serverPort);
    }
     }
     
 
     return urlBuilder.getUrl();
  }

   protected String buildHttpsRedirectUrlForRequest(HttpServletRequest request)
     throws IOException, ServletException
   {
    int serverPort = portResolver.getServerPort(request);
    Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));
    
    if (httpsPort != null) {
       RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
       urlBuilder.setScheme("https");
       urlBuilder.setServerName(request.getServerName());
       urlBuilder.setPort(httpsPort.intValue());
       urlBuilder.setContextPath(request.getContextPath());
       urlBuilder.setServletPath(request.getServletPath());
       urlBuilder.setPathInfo(request.getPathInfo());
       urlBuilder.setQuery(request.getQueryString());
       return urlBuilder.getUrl();
     }
 
     logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port " + serverPort);
     

     return null;
   }

  public void setForceHttps(boolean forceHttps)
   {
     this.forceHttps = forceHttps;
  }
  
  protected boolean isForceHttps() {
     return forceHttps;
  }
   
   
   
  public void setPortMapper(PortMapper portMapper) {
    Assert.notNull(portMapper, "portMapper cannot be null");
    this.portMapper = portMapper;
   }
   
   protected PortMapper getPortMapper() {
     return portMapper;
   }
   
            public void setPortResolver(PortResolver portResolver) {
            Assert.notNull(portResolver, "portResolver cannot be null");
            this.portResolver = portResolver;
            }
    
            protected PortResolver getPortResolver() {
            return portResolver;
            }
            public void setUseForward(boolean useForward)
            {
             this.useForward = useForward;
            }
   
            protected boolean isUseForward() {
              return useForward;
            }


			public String getMobileDeviceLoginFormUrl() {
				return mobileDeviceLoginFormUrl;
			}


			public void setMobileDeviceLoginFormUrl(String mobileDeviceLoginFormUrl) {
				this.mobileDeviceLoginFormUrl = mobileDeviceLoginFormUrl;
			}


			public String getDesktopDeviceLoginFormUrl() {
				return desktopDeviceLoginFormUrl;
			}


			public void setDesktopDeviceLoginFormUrl(String desktopDeviceLoginFormUrl) {
				this.desktopDeviceLoginFormUrl = desktopDeviceLoginFormUrl;
			}


			


		
}
