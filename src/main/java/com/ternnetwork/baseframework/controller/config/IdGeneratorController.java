package com.ternnetwork.baseframework.controller.config;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.config.IdGenerator;
import com.ternnetwork.baseframework.service.config.IdGeneratorService;



@Controller@Scope("prototype")
@RequestMapping("/baseframework/config/idgenerator/*")
public class IdGeneratorController {
	
	@Resource
	private IdGeneratorService  idGeneratorService;
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/baseframework/config/id_cenerator_manage.jsp");
	}
	
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,IdGenerator t){
		try{
			response.setContentType("text/javascript");
			out.print(idGeneratorService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,IdGenerator t){
		try{
			response.setContentType("text/javascript");
			out.print(idGeneratorService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,IdGenerator t){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
			out.print(idGeneratorService.queryToJsonStr(t, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

}
