package com.ternnetwork.toolkit.controller.report;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ternnetwork.toolkit.service.report.ReportSendJobService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/report/job/*")
public class ReportSendJobController {
	
	@Resource
	private ReportSendJobService reportSendJobService;
	
	
	@RequestMapping("run.htm")
	public void run(PrintWriter out,HttpServletResponse response,Long id,String startDate,String endDate){
		    response.setContentType("text/javascript");
		    out.print(reportSendJobService.sendReport(id,startDate,endDate));
	}
	
	

}
