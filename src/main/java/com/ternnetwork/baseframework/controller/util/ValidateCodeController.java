package com.ternnetwork.baseframework.controller.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ternnetwork.baseframework.util.ValidateCodeUtils;


@Controller@Scope("prototype")
@RequestMapping("/baseframework/util/validatecode/*")
public class ValidateCodeController {
	
	@RequestMapping("get.htm")
	public void get(HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("image/jpeg");  
	        // 禁止图像缓存。  
	        response.setHeader("Pragma", "no-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        ValidateCodeUtils instance = new ValidateCodeUtils();  
	        request.getSession().setAttribute("ValidateCode", instance.getCode());
	        instance.write(response.getOutputStream());  
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
