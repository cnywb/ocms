package com.ternnetwork.cms.controller.page;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.cms.model.page.PageComponent;
import com.ternnetwork.cms.service.page.PageComponentService;


@Controller@Scope("prototype")
@RequestMapping("/cms/page/component/*")
public class PageComponentController {

	@Resource
	private PageComponentService pageComponentService;
	
	
	@RequestMapping(value="add.htm",method = RequestMethod.GET)
	public ModelAndView gotoAdd(){
		return new ModelAndView("/WEB-INF/view/cms/page/page_component_add.jsp");
	}
	@RequestMapping("edit.htm")
	public ModelAndView gotoEdit(Long id,HttpServletRequest request){
		PageComponent pageComponent=null;
		try {
			pageComponent=pageComponentService.findById(id,request);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("/WEB-INF/view/cms/page/page_component_edit.jsp").addObject("t", pageComponent);
		
	}
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/cms/page/page_component_manage.jsp");
	}
	
	@RequestMapping(value="add.htm",method = RequestMethod.POST)
	public void add(PrintWriter out,HttpServletResponse response,HttpServletRequest request,PageComponent t){
		try{
			response.setContentType("text/javascript");
			t.setRealPath(request.getSession().getServletContext().getRealPath("/"));
			out.print(pageComponentService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,HttpServletRequest request,PageComponent t){
		try{
			response.setContentType("text/javascript");
			t.setRealPath(request.getSession().getServletContext().getRealPath("/"));
			out.print(pageComponentService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,HttpServletRequest request,Long id){
		try{
			response.setContentType("text/javascript");
			out.print(pageComponentService.idoDeleteById(id,request));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String name){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(pageComponentService.queryToJsonStr(page,name));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("queryAll.htm")
	public void queryAll(PrintWriter out,HttpServletResponse response,HttpServletRequest request,Long id){
		try{
			response.setContentType("text/javascript");
			out.print(pageComponentService.findAll());
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
}
