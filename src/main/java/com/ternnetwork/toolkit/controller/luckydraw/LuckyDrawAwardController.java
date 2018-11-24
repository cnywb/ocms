package com.ternnetwork.toolkit.controller.luckydraw;

import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawAward;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawAwardService;


@Controller@Scope("prototype")
@RequestMapping("/toolkit/luckydraw/award/*")
public class LuckyDrawAwardController {
	
	@Resource
	private LuckyDrawAwardService luckyDrawAwardService;

	
	@RequestMapping("manage.htm")
	public ModelAndView luckyDrawAwardManage(String luckyDrawId){
		return new ModelAndView("/WEB-INF/view/toolkit/luckydraw/lucky_draw_award_manage.jsp").addObject("luckyDrawId",luckyDrawId);
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
		    out.print(luckyDrawAwardService.queryToJsonStr(luckyDrawId,code,name, startTime, endTime, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,LuckyDrawAward t){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawAwardService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,LuckyDrawAward t){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawAwardService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawAwardService.idoDelete(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("findAllByluckyDrawId.htm")
	public void findAllByluckyDrawId(PrintWriter out,HttpServletResponse response,Long luckyDrawId){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawAwardService.findAllByluckyDrawIdToJsonStr(luckyDrawId));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
}
