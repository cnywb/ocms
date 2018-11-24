package com.ternnetwork.toolkit.controller.luckydraw;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawRule;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawRuleService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/luckydraw/rule/*")
public class LuckyDrawRuleController {
	
	@Resource
	private LuckyDrawRuleService luckyDrawRuleService;

	@RequestMapping("manage.htm")
	public ModelAndView luckyDrawRuleManage(String luckyDrawId){
		return new ModelAndView("/WEB-INF/view/toolkit/luckydraw/lucky_draw_rule_manage.jsp").addObject("luckyDrawId",luckyDrawId);
	}

	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset, String luckyDrawId,String code,String name,String startTime,String endTime){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawRuleService.queryToJsonStr(luckyDrawId,code,name, startTime, endTime, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,LuckyDrawRule t){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawRuleService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,LuckyDrawRule t){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawRuleService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawRuleService.idoDelete(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
