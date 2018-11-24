package com.ternnetwork.toolkit.controller.luckydraw;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawResultService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/luckydraw/result/*")
public class LuckyDrawResultController {
	
	@Resource
	private LuckyDrawResultService luckyDrawResultService;

	@Resource
	private UserService userService;
	
	@RequestMapping("manage.htm")
	public ModelAndView luckyDrawResultManage(String luckyDrawId){
		return new ModelAndView("/WEB-INF/view/toolkit/luckydraw/lucky_draw_result_manage.jsp").addObject("luckyDrawId",luckyDrawId);
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
		    out.print(luckyDrawResultService.queryToJsonStr(luckyDrawId,code,userName, startTime, endTime, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("queryMyWin.htm")
	public void queryMyWin(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String luckyDrawId,String code,String userName,String startTime,String endTime){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawResultService.queryToJsonStr(luckyDrawId,code,userService.getCurrentUserName(), startTime, endTime, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
}
