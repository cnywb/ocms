package com.ternnetwork.wechat.controller.user;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.wechat.service.user.WechatUserService;


@Controller@Scope("prototype")
@RequestMapping("/wechat/user/*")
public class WechatUserController {
	
	
	@Resource
	private WechatUserService wechatUserService;
	
	@RequestMapping(value="dyUserQuery.htm",method=RequestMethod.GET)
	public ModelAndView dyUserQuery(){	
		return new ModelAndView("/WEB-INF/view/wechat/dy/user/user_query.jsp");
	}
	
	@RequestMapping(value="fwUserQuery.htm",method=RequestMethod.GET)
	public ModelAndView fwUserQuery(){	
		return new ModelAndView("/WEB-INF/view/wechat/fw/user/user_query.jsp");
	}
	
	@RequestMapping("queryDyUser.htm")
	public void queryDyUser(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String name,String nextOpenid){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(wechatUserService.queryDyUser(page, nextOpenid));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("queryFwUser.htm")
	public void queryFwUser(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String name,String nextOpenid){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(wechatUserService.queryFwUser(page, nextOpenid));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

}
