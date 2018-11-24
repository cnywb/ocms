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
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.report.ReportItem;
import com.ternnetwork.toolkit.service.report.ReportItemService;


@Controller@Scope("prototype")
@RequestMapping("/toolkit/report/item/*")
public class ReportItemController {
	
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(Long reportId){
		return new ModelAndView("/WEB-INF/view/toolkit/report/report_item_manage.jsp").addObject("reportId", reportId);
	}
	
	@Resource
	private ReportItemService reportItemService;
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String name,String reportId){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(reportItemService.queryToBootstrapGrid(page, name,reportId));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	    
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,ReportItem t){
		return reportItemService.idoUpdate(t);
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,ReportItem t){
		return reportItemService.idoAdd(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletResponse response,Long id){
		return reportItemService.idoDelete(id);
	}

}
