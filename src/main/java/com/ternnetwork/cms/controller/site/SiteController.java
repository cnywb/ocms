package com.ternnetwork.cms.controller.site;

import java.io.PrintWriter;
import java.util.List;

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
import com.ternnetwork.cms.model.site.Site;
import com.ternnetwork.cms.service.site.SiteService;


@Controller@Scope("prototype")
@RequestMapping("/cms/site/*")
public class SiteController {
	
	@Resource
	private SiteService siteService;
	
	
	
	
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/cms/site/site_manage.jsp");
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order ,int limit, int offset){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(siteService.query(page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public  @ResponseBody BaseResponse add(HttpServletResponse response,HttpServletRequest request,Site t){
		return siteService.idoAdd(request,t);
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletRequest request,HttpServletResponse response,Site t){
		return siteService.idoUpdate(request,t);
	}
	
	@RequestMapping("findAll.htm")
	public @ResponseBody List<Site> findAll(HttpServletResponse response){
		return siteService.findAll();
	}
	
}
