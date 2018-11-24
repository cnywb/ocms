package com.ternnetwork.toolkit.controller.vote;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.vote.Vote;
import com.ternnetwork.toolkit.service.vote.VoteService;




@Controller@Scope("prototype")
@RequestMapping("/toolkit/vote/*")
public class VoteController {
	
	@Resource
	private VoteService voteService;
	
	@RequestMapping("manage.htm")
	public ModelAndView voteManage(){
		return new ModelAndView("/WEB-INF/view/toolkit/vote/vote_manage.jsp");
	}
	
	@RequestMapping("index.htm")
	public ModelAndView index(HttpServletRequest request){
		String currentLocation=request.getRequestURL().toString();
		return new ModelAndView("/WEB-INF/view/toolkit/vote/index.jsp").addObject("currentLocation",currentLocation);
	}

	@RequestMapping("result.htm")
	public ModelAndView voteResult(Long voteId){
		return new ModelAndView("/WEB-INF/view/toolkit/vote/vote_result.jsp").addObject("t",voteService.findById(voteId));
	}
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String code,String name,String startTime,String endTime,Boolean enable){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(voteService.queryToJsonStr(code,name, startTime, endTime, enable,page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,Vote t){
		try{
			response.setContentType("text/javascript");
		    out.print(voteService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,Vote t){
		try{
			response.setContentType("text/javascript");
		    out.print(voteService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("findByCode.htm")
	public void findByCode(PrintWriter out,HttpServletResponse response,String code){
		try{
			response.setContentType("text/javascript");
		    out.print(voteService.findByCode(code));
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
