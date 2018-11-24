package com.ternnetwork.baseframework.security;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ternnetwork.baseframework.redis.RedisTemplateService;
import com.ternnetwork.baseframework.service.log.AuthenticationLogService;
import com.ternnetwork.baseframework.util.PropUtils;

public class ComplexHttpSessionEventPublisher extends HttpSessionEventPublisher implements HttpSessionAttributeListener ,ServletContextListener {

	
	private AuthenticationLogService authenticationLogService;
	
	private RedisTemplateService redisTemplateService;
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		super.sessionCreated(event);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		String userSessionId=event.getSession().getId();
		authenticationLogService.idoAddLogoutLog(userSessionId);
		String redisStatus=PropUtils.getPropertyValue("redis.properties","redis.status");
		if(redisStatus.equals("on")){
			redisTemplateService.deleteSecurityContext(userSessionId);
		}
	  	super.sessionDestroyed(event);
	}
	



	public void contextInitialized(ServletContextEvent sce) {
	      WebApplicationContext applicationContext =  WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());  
	      authenticationLogService=(AuthenticationLogService)applicationContext.getBean("authenticationLogService");
	      redisTemplateService=(RedisTemplateService)applicationContext.getBean("redisTemplateService");
	}

	
	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
		// TODO Auto-generated method stub
		
	}

	
	public void attributeAdded(HttpSessionBindingEvent paramHttpSessionBindingEvent) {
		// TODO Auto-generated method stub
		
	}

	
	public void attributeRemoved(HttpSessionBindingEvent paramHttpSessionBindingEvent) {
		// TODO Auto-generated method stub
		
	}


	public void attributeReplaced(HttpSessionBindingEvent paramHttpSessionBindingEvent) {
		// TODO Auto-generated method stub
		
	}

}
