package com.ternnetwork.cms.controller.content;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.content.Content;
import com.ternnetwork.cms.service.site.SiteService;
import com.ternnetwork.cms.service.content.ContentService;
import com.ternnetwork.cms.service.file.CmsFileService;




@Controller@Scope("prototype")
@RequestMapping("/cms/content/*")
public class ContentController {
	@Resource
	private ContentService contentService;
	@Resource
	private SiteService siteService;
	
	@Resource
	private CmsFileService cmsFileService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		
		return new ModelAndView("/WEB-INF/view/cms/content/content_manage.jsp").addObject("siteList",siteService.findAll());
	}
	
	
	@RequestMapping(method=RequestMethod.GET,path="add.htm")
	public ModelAndView add(HttpServletRequest request,Long siteId,String siteCode){
		List<String> fileNameList=cmsFileService.getContentTemplateFileList(request, siteCode);
		return new ModelAndView("/WEB-INF/view/cms/content/content_add.jsp").addObject("siteId", siteId).addObject("fileNameList", fileNameList);
	}
	
	@RequestMapping(method=RequestMethod.GET,path="edit.htm")
	public ModelAndView edit(HttpServletRequest request,Long siteId,String siteCode,Long id){
		List<String> fileNameList=cmsFileService.getContentTemplateFileList(request, siteCode);
		return new ModelAndView("/WEB-INF/view/cms/content/content_edit.jsp").addObject("siteId", siteId).addObject("fileNameList", fileNameList).addObject("t",contentService.findById(id));
	}
	
	@RequestMapping("preview.htm")
	public ModelAndView preview(Long id){
		return new ModelAndView("/WEB-INF/view/cms/content/content_preview.jsp").addObject("t",contentService.findById(id));
	}
	
	@RequestMapping(method=RequestMethod.POST,path="add.htm")
	public @ResponseBody BaseResponse  add(HttpServletRequest request,HttpServletResponse response,Content t){
		return contentService.idoAdd(request,t);
	}
	
	@RequestMapping(method=RequestMethod.POST,path="update.htm")
	public @ResponseBody BaseResponse  update(HttpServletRequest request,HttpServletResponse response,Content t){
		return contentService.idoUpdate(request,t);
	}
	
	@RequestMapping("delete.htm")
	public  @ResponseBody BaseResponse delete(long id){
		return contentService.idoDelete(id);
	}
	
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order ,int limit, int offset,String minPublishTime,String maxPublishTime,String minCreateTime,String maxCreateTime,Integer status,String title,Boolean commentAble,Long contentCategoryId,Long channelId,String author){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(contentService.queryToJsonStr(minPublishTime, maxPublishTime, minCreateTime, maxCreateTime, status, title, commentAble, contentCategoryId, channelId, author, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("queryPublished.htm")
	public void queryPublished(PrintWriter out,HttpServletResponse response,String sort,String order ,int limit, int offset,String minPublishTime,String maxPublishTime,String minCreateTime,String maxCreateTime,String title,Boolean commentAble,Long contentCategoryId,Long channelId,String author){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(contentService.queryToJsonStr(minPublishTime, maxPublishTime, minCreateTime, maxCreateTime,1, title, commentAble, contentCategoryId, channelId, author, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

}
