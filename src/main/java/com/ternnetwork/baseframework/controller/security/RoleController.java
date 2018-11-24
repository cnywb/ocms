package com.ternnetwork.baseframework.controller.security;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.security.Role;
import com.ternnetwork.baseframework.service.security.RoleService;



@Controller
@RequestMapping("/baseframework/security/role/*")
public class RoleController {
	
	@Resource
	private RoleService roleService;
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/baseframework/security/role_manage.jsp");
	}
	
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,Role t){
		try{
            response.setContentType("text/javascript");
			out.print(roleService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,Role t){
		try{
			response.setContentType("text/javascript");
			out.print(roleService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
			out.print(roleService.query(page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("findAll.htm")
	public void findAllRole(PrintWriter out,HttpServletResponse response){
		
		try{
			response.setContentType("text/javascript");
			out.print(roleService.findAll("from Role"));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
}
