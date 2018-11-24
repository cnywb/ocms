package com.ternnetwork.baseframework.controller.config;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.config.SystemParameter;
import com.ternnetwork.baseframework.service.config.SystemParameterService;



@Controller
@RequestMapping("/baseframework/config/systemparameter/*")
public class SystemParameterController {
	
	@Resource
	private SystemParameterService systemParameterService;

	
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/baseframework/config/system_parameter_manage.jsp");
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,SystemParameter t){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(systemParameterService.queryToJsonStr(t, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,SystemParameter t){
		try{
			systemParameterService.idoAdd(t);
			response.setContentType("text/javascript");
			out.print("1");
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,SystemParameter t){
		try{
			systemParameterService.idoUpdate(t);
			response.setContentType("text/javascript");
			out.print("1");
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
			out.print(systemParameterService.idoDeleteById(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
}
