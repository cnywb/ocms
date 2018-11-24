package com.ternnetwork.cms.service.impl.page;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;


import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.cms.enums.CmsPageStatus;
import com.ternnetwork.cms.enums.ContentStatus;
import com.ternnetwork.cms.enums.SiteStatus;
import com.ternnetwork.cms.model.site.Site;
import com.ternnetwork.cms.model.content.Content;
import com.ternnetwork.cms.model.page.CmsPage;
import com.ternnetwork.cms.service.site.SiteService;
import com.ternnetwork.cms.service.content.ContentService;
import com.ternnetwork.cms.service.page.DynamicPageService;
import com.ternnetwork.cms.service.page.PageService;


@Service("dynamicPageService")
public class DynamicPageServiceImpl implements DynamicPageService {
	
	@Resource
	private PageService pageService;
	@Resource
	private SiteService siteService;
	
	@Resource
	private ContentService contentService;

	public ModelAndView dynamicIndex(HttpServletRequest request,ModelMap model){
		ModelAndView mv=new ModelAndView("/errors/site_error.jsp");
    	Site site=siteService.getByDomain(request.getServerName());
    	if(site==null){
    	    mv.addObject("message","站点不存在,请先创建站点");
    		return mv;
    	}
       	if(site.getStatus().equals(SiteStatus.CLOSE)){
    	    mv.addObject("message","站点已关闭");
    		return mv;
    	}
    	if(site.getStatus().equals(SiteStatus.MAINTAIN)){
    	    mv.addObject("message","站点维护中");
    		return mv;
    	}
    	String template=site.getIndexTemplate();
    	if(StringUtils.isEmpty(template)){
    		mv.addObject("message","页面未设置模版");
       		return mv;
    	}
    	template=PropUtils.getPropertyValue("file_path.properties", "cms_template_path").concat("site/").concat(site.getCode()).concat("/index/").concat(template);
    	
    	mv=new ModelAndView(template);
    	return mv;
	}
	
	
	
	public ModelAndView dynamicChannelPage(HttpServletRequest request,String channelCode,String pageCode,ModelMap model){
		ModelAndView mv=new ModelAndView("/errors/site_error.jsp");
		
		Site site=siteService.getByDomain(request.getServerName());
    	if(site==null){
    	    mv.addObject("message","站点不存在,请先创建站点");
    		return mv;
    	}
       	if(site.getStatus().equals(SiteStatus.CLOSE)){
    	    mv.addObject("message","站点已关闭");
    		return mv;
    	}
    	if(site.getStatus().equals(SiteStatus.MAINTAIN)){
    	    mv.addObject("message","站点维护中");
    		return mv;
    	}
   		CmsPage page=pageService.find(pageCode, channelCode, site.getDomain());
		if(page==null){
			 mv.addObject("message","页面不存在");
	         return mv;
		}
		if(page.getStatus().equals(CmsPageStatus.CLOSED)){
			 mv.addObject("message","页面已被关闭");
	         return mv;
		}
		if(StringUtils.isEmpty(page.getTemplate())){
    		mv.addObject("message","页面未设置模版");
       		return mv;
    	}
		String siteCode=site.getCode();
		String template=page.getTemplate();
		template=pageService.getChannelPageTemplateFile(channelCode, siteCode, template);
		mv=new ModelAndView(template);
		String basePath = request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(String.valueOf(request.getServerPort())).concat(request.getContextPath()).concat("/");
		mv.addObject("basePath", basePath);
		return mv;
	}

	public ModelAndView dynamicContentPage(String categoryCode,String contentId,HttpServletRequest request, ModelMap model){
		ModelAndView mv=new ModelAndView("/errors/site_error.jsp");
    	Site site=siteService.getByDomain(request.getServerName());
    	if(site==null){
    	    mv.addObject("message","站点不存在,请先创建站点");
    		return mv;
    	}
       	if(site.getStatus().equals(SiteStatus.CLOSE)){
    	    mv.addObject("message","站点已关闭");
    		return mv;
    	}
    	if(site.getStatus().equals(SiteStatus.MAINTAIN)){
    	    mv.addObject("message","站点维护中");
    		return mv;
    	}
    	Content content=contentService.findById(Long.parseLong(contentId));
    	if(content==null){
    		mv.addObject("message","内容不存在");
       		return mv;
    	}
      	if(content.getStatus().equals(ContentStatus.CLOSED)){
    		mv.addObject("message","内容已关闭!");
       		return mv;
    	}
    	if(content.getStatus().equals(ContentStatus.UNPUBLISHED)){
    		mv.addObject("message","内容未发布");
       		return mv;
    	}
    	if(StringUtils.isEmpty(content.getTemplate())){
    		mv.addObject("message","内容未设置模版");
       		return mv;
    	}
    	contentService.idoUpdateViewCount(Long.parseLong(contentId));
       	String siteCode=site.getCode();
		String template=content.getTemplate();
		template=contentService.getContentPageTemplateFile(categoryCode, siteCode, template);
		mv=new ModelAndView(template);
		String basePath = request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(String.valueOf(request.getServerPort())).concat(request.getContextPath()).concat("/");
		mv.addObject("basePath", basePath);
		mv.addObject("content", content);
  		return mv;
	}
	
	
	
}
