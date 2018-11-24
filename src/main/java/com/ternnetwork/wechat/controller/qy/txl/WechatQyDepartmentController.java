package com.ternnetwork.wechat.controller.qy.txl;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ternnetwork.wechat.model.qy.txl.WechatQyDepartment;
import com.ternnetwork.wechat.service.qy.txl.WechatQyDepartmentService;


@Controller@Scope("prototype")
@RequestMapping("/wechat/qy/txl/department/*")
public class WechatQyDepartmentController {
	
	@Resource
	private WechatQyDepartmentService  wechatDepartmentService;
	
	@RequestMapping(value="manage.htm")
	public String manage(){	
		return "/WEB-INF/view/wechat/qy/txl/department_manage.jsp";
	}

	
	@RequestMapping(value="query.htm")
	public void query(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatDepartmentService.query(false);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="queryWithCheckBox.htm")
	public void queryWithCheckBox(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatDepartmentService.query(true);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="create.htm")
	public void create(PrintWriter out,HttpServletResponse response,HttpServletRequest request,WechatQyDepartment t){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatDepartmentService.create(t);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="update.htm")
	public void update(PrintWriter out,HttpServletResponse response,HttpServletRequest request,WechatQyDepartment t){
		try{
			response.setContentType("text/javascript");
			String respMessage = wechatDepartmentService.update(t);
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
			String respMessage = wechatDepartmentService.delete(id);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
}
