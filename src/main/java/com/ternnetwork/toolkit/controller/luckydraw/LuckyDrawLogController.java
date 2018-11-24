package com.ternnetwork.toolkit.controller.luckydraw;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawLogService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/luckydraw/log/*")
public class LuckyDrawLogController {
	
	@Resource
	private LuckyDrawLogService luckyDrawLogService;

	@RequestMapping("manage.htm")
	public ModelAndView luckyDrawLogManage(String luckyDrawId){
		return new ModelAndView("/WEB-INF/view/toolkit/luckydraw/lucky_draw_log_manage.jsp").addObject("luckyDrawId",luckyDrawId);
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String luckyDrawId,String code,String userName,String startTime,String endTime){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawLogService.queryToJsonStr(luckyDrawId,code,userName, startTime, endTime, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("countByCurrentWeekJSONP.htm")
	public void countByCurrentWeekJSONP(PrintWriter out,HttpServletResponse response,String jsonpcallback,String wechatId){
		 try{
				response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
			    String result=luckyDrawLogService.countByCurrentWeek(wechatId).toString();
		        out.print(jsonpcallback+"("+result+")");
			}catch(Exception e){
				e.printStackTrace();
				out.print(jsonpcallback+"(System Exception)");
			}
	}
	
}
