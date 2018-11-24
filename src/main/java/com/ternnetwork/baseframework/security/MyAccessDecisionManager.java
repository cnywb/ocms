package com.ternnetwork.baseframework.security;


import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

//3
@Service("accessDecisionManager")
public class MyAccessDecisionManager implements AccessDecisionManager {
	
	private static final Logger log = Logger.getLogger(MySecurityFilter.class);
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null) {
			return;
		}
		//所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while(iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			//访问所请求资源所需要的权限
			String resName = configAttribute.getAttribute();
			log.info("请求资源名称： " + resName);
			//用户所拥有的权限authentication
			for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				if(resName.equals(grantedAuthority.getAuthority())) {
					return;
				}
			}
		}
		//没有权限让我们去捕捉
		throw new AccessDeniedException("没有权限访问！");
	}

	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
