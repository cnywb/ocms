package com.ternnetwork.baseframework.redis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SecurityUtil {

	public static String getSecurityContextId(HttpServletRequest request) {
		
		return request.getSession().getId();
	/*	Cookie[] cookies = request.getCookies();
        for(Cookie c :cookies ){
            System.out.println(c.getName()+"--->"+c.getValue());
            if("JSESSIONID".equals(c.getName())){
            	return c.getValue();
            }
        }
		return null;*/
	}

}
