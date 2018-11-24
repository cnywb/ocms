package com.ternnetwork.cms.controller.content;

import javax.annotation.Resource;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.content.ContentCategory;
import com.ternnetwork.cms.model.content.ContentCategoryVo;
import com.ternnetwork.cms.model.ui.ContentCategoryZtree;
import com.ternnetwork.cms.service.content.ContentCategoryService;
import com.ternnetwork.cms.service.site.SiteService;


@Controller@Scope("prototype")
@RequestMapping("/cms/content/category/*")
public class ContentCategoryController {
	@Resource
	private ContentCategoryService contentCategoryService;
	@Resource
	private SiteService siteService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/cms/content/content_category_manage.jsp").addObject("siteList",siteService.findAll());
	}
	
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletRequest request,HttpServletResponse response,ContentCategory t){
		return contentCategoryService.idoAdd(request,t);
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletRequest request,HttpServletResponse response,ContentCategory t){
		return contentCategoryService.idoUpdate(request,t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletRequest request,HttpServletResponse response,long id){
		return contentCategoryService.idoDeleteById(request,id);
	}

	@RequestMapping("getZTreeJSON.htm")
	public @ResponseBody List<ContentCategoryZtree> getZTreeJSON(Long siteId){
		return contentCategoryService.getZTreeJSON(siteId);
	}

	@RequestMapping("findByParentCode.htm")
	public @ResponseBody List<ContentCategoryVo> findByParentCode(String parentCode){
		return contentCategoryService.findByParentCode(parentCode);
	}
	
}
