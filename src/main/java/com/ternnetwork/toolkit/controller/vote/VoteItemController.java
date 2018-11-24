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

import com.ternnetwork.toolkit.model.vote.VoteItem;
import com.ternnetwork.toolkit.service.vote.VoteItemService;
import com.ternnetwork.toolkit.service.vote.VoteService;




@Controller@Scope("prototype")
@RequestMapping("/toolkit/vote/item/*")
public class VoteItemController {
	
	@Resource
	private VoteItemService voteItemService;
	
	@Resource
	private VoteService voteService;

	
	@RequestMapping("manage.htm")
	public ModelAndView voteItemManage(String voteId){
		
		return new ModelAndView("/WEB-INF/view/toolkit/vote/vote_item_manage.jsp").addObject("voteId",voteId);
	}
	
	
	@RequestMapping("voteItemList.htm")
	public ModelAndView voteItemList(Long voteId,HttpServletRequest request){
		String currentLocation=request.getRequestURL().toString();
		return new ModelAndView("/WEB-INF/view/toolkit/vote/vote_item_list.jsp").addObject("t",voteService.findById(voteId)).addObject("currentLocation",currentLocation);
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String voteId){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(voteItemService.queryToJsonStr(voteId,page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,VoteItem t){
		try{
			response.setContentType("text/javascript");
		    out.print(voteItemService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,VoteItem t){
		try{
			response.setContentType("text/javascript");
		    out.print(voteItemService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
		    out.print(voteItemService.idoDelete(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("findAllByVoteId.htm")
	public void findAllByVoteId(PrintWriter out,HttpServletResponse response,long voteId){
		try{
			response.setContentType("text/javascript");
		    out.print(voteItemService.findAllByVoteIdToJsonStr(voteId));
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
