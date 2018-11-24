package com.ternnetwork.wechat.controller.qy.txl;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ternnetwork.wechat.service.qy.txl.WechatQyUserService;


@Controller@Scope("prototype")
@RequestMapping("/wechat/qy/txl/user/*")
public class WechatQyUserController {

	
	@Resource
	private WechatQyUserService wechatQyUserService;
	
	@RequestMapping(value="manage.htm")
	public String manage(){	
		return "/WEB-INF/view/wechat/qy/txl/user_manage.jsp";
	}

	
	@RequestMapping(value="queryByDepartmentId.htm")
	public void queryByDepartmentId(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String departmentId,String status,String sort,String order,int limit, int offset){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatQyUserService.queryByDepartmentId(departmentId, status);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="queryById.htm")
	public void queryById(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String id){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatQyUserService.queryById(id);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="create.htm")
	public void create(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage =wechatQyUserService.create(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="update.htm")
	public void update(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatQyUserService.update(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String id){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatQyUserService.delete(id);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
}
