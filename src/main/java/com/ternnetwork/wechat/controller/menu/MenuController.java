package com.ternnetwork.wechat.controller.menu;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ternnetwork.wechat.service.menu.MenuService;



@Controller@Scope("prototype")
@RequestMapping("/wechat/menu/*")
public class MenuController {

	@Resource
	private MenuService menuService;
	
	
	
	@RequestMapping(value="manageQyMenu.htm")
	public String manageQyMenu(){	
		return "/WEB-INF/view/wechat/qy/menu/menu_manage.jsp";
	}
	
	@RequestMapping(value="manageDyMenu.htm")
	public String manageDyMenu(){	
		return "/WEB-INF/view/wechat/dy/menu/menu_manage.jsp";
	}
	
	
	@RequestMapping(value="manageFwMenu.htm")
	public String manageFwMenu(){	
		return "/WEB-INF/view/wechat/fw/menu/menu_manage.jsp";
	}
	
	
	@RequestMapping(value="queryDyMenu.htm")
	public void queryDyMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.queryDyMenu();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="queryFwMenu.htm")
	public void queryFwMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.queryDyMenu();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="deleteFwMenu.htm")
	public void deleteFwMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.deleteFwMenu();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="createFwMenu.htm")
	public void createFwMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.createFwMenu(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="deleteDyMenu.htm")
	public void deleteDyMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.deleteDyMenu();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="createDyMenu.htm")
	public void createDyMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.createDyMenu(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="queryQyMenu.htm")
	public void queryQyMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.queryQyMenu();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="deleteQyMenu.htm")
	public void deleteQyMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.deleteQyMenu();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="createQyMenu.htm")
	public void createQyMenu(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = menuService.createQyMenu(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
}
