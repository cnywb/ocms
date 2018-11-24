package com.ternnetwork.cms.controller.page;

import java.io.PrintWriter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.page.CmsPage;
import com.ternnetwork.cms.service.site.SiteService;
import com.ternnetwork.cms.service.page.PageService;


@Controller@Scope("prototype")
@RequestMapping("/cms/page/*")
public class PageController {
	
	@Resource
	private PageService pageService;
	@Resource
	private SiteService siteService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/cms/page/page_manage.jsp").addObject("siteList",siteService.findAll());
	}

	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,HttpServletRequest request,CmsPage t){
		return pageService.idoAdd(t);
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,HttpServletRequest request,CmsPage t) throws Exception{
		return pageService.idoUpdate(t);
	}
	
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String name,String channelId){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(pageService.queryToJsonStr(page, channelId, name));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
}
