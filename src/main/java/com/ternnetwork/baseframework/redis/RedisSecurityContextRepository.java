package com.ternnetwork.baseframework.redis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;



@Service("redisSecurityContextRepository")
public class RedisSecurityContextRepository implements SecurityContextRepository {

	 private static final Logger log = LoggerFactory.getLogger(RedisSecurityContextRepository.class);

	  @Resource
	  private RedisTemplateService redisTemplateService;

	  public boolean containsContext(HttpServletRequest request) {
	    // get USER SESSION COOKIE
	    String securityContextId = SecurityUtil.getSecurityContextId(request);
	    log.debug("check containContext method : {}", securityContextId);
	    if (StringUtils.isNotEmpty(securityContextId)) {
	      return redisTemplateService.getSecurityContext(securityContextId) != null;
	    }
	    return false;
	  }

	  
	  public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
	    log.debug("load context...");
	    HttpServletRequest request = requestResponseHolder.getRequest();
	    // get USER SESSION COOKIE
	    SecurityContext savedSecurityContext = null;
	    String securityContextId = SecurityUtil.getSecurityContextId(request);
	    if (StringUtils.isNotEmpty(securityContextId)) {
	      log.debug("found security context :{}", securityContextId);
	      savedSecurityContext = (SecurityContext) redisTemplateService.getSecurityContext(securityContextId);
	    }
	    if (savedSecurityContext != null) {
	      log.debug("return security context :{}", savedSecurityContext);
	      return savedSecurityContext;
	    }
	    log.debug("returning empty security context");
	    return SecurityContextHolder.createEmptyContext();
	  }

	
	  public void saveContext(SecurityContext sc, HttpServletRequest request,HttpServletResponse response) {
	    if (sc.getAuthentication() != null&& !(sc.getAuthentication() instanceof AnonymousAuthenticationToken)&&sc.getAuthentication().isAuthenticated()) {
	      String securityContextId = SecurityUtil.getSecurityContextId(request);
	      log.debug("saving security context, id : {}", securityContextId);
	      if (StringUtils.isNotEmpty(securityContextId)) {
	        log.debug("security context : {} is not empty", securityContextId);
	        SecurityContext savedSecurityContext=(SecurityContext) redisTemplateService.getSecurityContext(securityContextId);
	        if (savedSecurityContext == null|| !sc.getAuthentication().getName().equals(savedSecurityContext.getAuthentication().getName())) {
	          log.debug("no saved security context, put session into redis.");
	          redisTemplateService.putSecurityContext(securityContextId, sc);
	        }
	      }
	    }
	  }

}
