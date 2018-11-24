package com.ternnetwork.toolkit.controller.report;


import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.report.Report;
import com.ternnetwork.toolkit.service.report.ReportService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/report/*")
public class ReportController {
	
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/toolkit/report/report_manage.jsp");
	}
	
	@Resource
	private ReportService reportService;
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String name){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		response.setContentType("text/javascript");
		out.print(reportService.queryToBootstrapGrid(page, name));
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,Report t){
		return reportService.idoUpdate(t);
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,Report t){
		return reportService.idoAdd(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletResponse response,Long id){
		return reportService.idoDelete(id);
	}

}
