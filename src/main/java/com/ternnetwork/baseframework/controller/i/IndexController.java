package com.ternnetwork.baseframework.controller.i;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.baseframework.service.security.UserService;

@Controller
@RequestMapping("/i/*")
public class IndexController {
	
	@Resource
	private UserService  userService;
	
	@RequestMapping("index.htm")
	public ModelAndView goToIndex(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("/WEB-INF/view/baseframework/i/index.jsp");
	    mv.addObject("sessionId", request.getSession().getId());
	    mv.addObject("user", userService.getCurrentUser());
		return mv;
	}
	
	
}
