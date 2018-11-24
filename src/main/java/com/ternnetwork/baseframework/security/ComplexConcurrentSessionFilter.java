package com.ternnetwork.baseframework.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.redis.RedisTemplateService;


/**
 * 会话过滤器，
 * 会话过期会跳出到过期页面或是打印过期提示
 * 
 * @author xuwenfeng
 *
 */
public class ComplexConcurrentSessionFilter extends GenericFilterBean
           {
            private SessionRegistry sessionRegistry;
            private String expiredUrl;
            private String mobileDeviceExpiredUrl;
            private String desktopDeviceExpiredUrl;
            private LogoutHandler[] handlers = { new SecurityContextLogoutHandler() };
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
          
            
            
            
            public String getExpiredUrl() {
				return expiredUrl;
			}

			public void setExpiredUrl(String expiredUrl) {
				this.expiredUrl = expiredUrl;
			}

			public String getMobileDeviceExpiredUrl() {
				return mobileDeviceExpiredUrl;
			}

			public void setMobileDeviceExpiredUrl(String mobileDeviceExpiredUrl) {
				this.mobileDeviceExpiredUrl = mobileDeviceExpiredUrl;
			}

			public ComplexConcurrentSessionFilter(SessionRegistry sessionRegistry)
            {
                  Assert.notNull(sessionRegistry, "SessionRegistry required");
                     this.sessionRegistry = sessionRegistry;
            }
             
            public ComplexConcurrentSessionFilter(SessionRegistry sessionRegistry, String desktopDeviceExpiredUrl,String mobileDeviceExpiredUrl) {
                       Assert.notNull(sessionRegistry, "SessionRegistry required");
                       Assert.isTrue((desktopDeviceExpiredUrl == null) || (UrlUtils.isValidRedirectUrl(desktopDeviceExpiredUrl)), desktopDeviceExpiredUrl + " isn't a valid redirect URL");
                       this.sessionRegistry = sessionRegistry;
                       this.desktopDeviceExpiredUrl = desktopDeviceExpiredUrl;
                       this.mobileDeviceExpiredUrl=mobileDeviceExpiredUrl;
            }
               public void afterPropertiesSet()
            {
              Assert.notNull(sessionRegistry, "SessionRegistry required");
              Assert.isTrue((desktopDeviceExpiredUrl == null) || (UrlUtils.isValidRedirectUrl(desktopDeviceExpiredUrl)), desktopDeviceExpiredUrl + " isn't a valid redirect URL");
              }
               
             public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                 throws IOException, ServletException{
                      HttpServletRequest request = (HttpServletRequest)req;
                      HttpServletResponse response = (HttpServletResponse)res;
                      HttpSession session = request.getSession(false);
                         
               if (session != null) {
            	   
                       SessionInformation info = sessionRegistry.getSessionInformation(session.getId());
                         
                     
                       if (info != null) {
                               if (info.isExpired()){
                                 
                             
                            	doLogout(request, response);
                            	String deviceType = request.getParameter("deviceType");
                        		String responseType = request.getParameter("responseType");
                        		if(!StringUtils.isEmpty(deviceType)&&"mobile".equals(deviceType)){//如果是从移动端发来的请求
                        			expiredUrl=this.mobileDeviceExpiredUrl;
                        		}else{
                        			expiredUrl=this.desktopDeviceExpiredUrl;
                        		}
                        		if(!StringUtils.isEmpty(responseType)&&"json".equals(responseType)){//如果是要求反回json
                        			 response.setCharacterEncoding("UTF-8"); 
                        			 BaseResponse baseResponse=new BaseResponse();
                        			 baseResponse.setStatus(0);
                        			 baseResponse.setMessage("This session has been expired");
                                     response.getWriter().write(baseResponse.toString());  
                        			 return;
                        		}
                            
                            	String targetUrl = determineExpiredUrl(request, info);
                              if (targetUrl != null) {
                                  redirectStrategy.sendRedirect(request, response, targetUrl);
                                  return;
                              }
                                  
                               response.getWriter().print("This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).");
                                       
                               response.flushBuffer();
                                   return;
                              }
                                   
                                sessionRegistry.refreshLastRequest(info.getSessionId());
                             }
                         }
                
               
                         chain.doFilter(request, response);
                   }
                  
                  protected String determineExpiredUrl(HttpServletRequest request, SessionInformation info)
                   {
                         return expiredUrl;
                   }
                   
                    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
                              Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                               
                              for (LogoutHandler handler : handlers) {
                                           handler.logout(request, response, auth);
                              }
                   }
                      
                  public void setLogoutHandlers(LogoutHandler[] handlers) {
                               Assert.notNull(handlers);
                                   this.handlers = handlers;
                  }
                        
                  public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
                                  this.redirectStrategy = redirectStrategy;
                  }

				public String getDesktopDeviceExpiredUrl() {
					return desktopDeviceExpiredUrl;
				}

				public void setDesktopDeviceExpiredUrl(String desktopDeviceExpiredUrl) {
					this.desktopDeviceExpiredUrl = desktopDeviceExpiredUrl;
				}

			

				
                  
                  
}
