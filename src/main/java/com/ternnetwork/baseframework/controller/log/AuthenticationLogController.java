package com.ternnetwork.baseframework.controller.log;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.log.AuthenticationLog;
import com.ternnetwork.baseframework.service.log.AuthenticationLogService;


@Controller@Scope("prototype")
@RequestMapping("/baseframework/log/authentication/*")
public class AuthenticationLogController {
	
  @Resource
  private AuthenticationLogService authenticationLogService;
  
  
  
  @RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/baseframework/log/authentication_log_manage.jsp");
	}
  
  @RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,AuthenticationLog t,String createTimeMin,String createTimeMax,String sort,String order,int limit, int offset){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(authenticationLogService.queryToJsonStr(t,createTimeMin,createTimeMax, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

}
