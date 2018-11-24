package com.ternnetwork.baseframework.controller.org;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.baseframework.model.org.Department;
import com.ternnetwork.baseframework.service.org.DepartmentService;




@Controller
@RequestMapping("/baseframework/org/department/*")
public class DepartmentController {
	
	@Resource
	private DepartmentService departmentService;
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/baseframework/org/department_manage.jsp");
	}
	
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,Department t){
		try{
			response.setContentType("text/javascript");
			out.print(departmentService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,Department t){
		try{
        	response.setContentType("text/javascript");
			out.print(departmentService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,Department t){
		try{
			response.setContentType("text/javascript");
			out.print(departmentService.idoDeleteById(t.getId()));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("getTreeJSON.htm")
	public void getDepartmentTreeJSON(PrintWriter out,HttpServletResponse response){
		try{
			response.setContentType("text/javascript");
			out.print(departmentService.getDepartmentTreeJSON(1L));
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
		
	}
	
	@RequestMapping("getZTreeJSON.htm")
	public void getZTreeJSON(PrintWriter out,HttpServletResponse response){
		try{
			response.setContentType("text/javascript");
			out.print(departmentService.getZTreeJSON());
		}catch(Exception e){
			e.printStackTrace();
			out.println("error");
		}
	}


}
