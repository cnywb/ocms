package com.ternnetwork.cms.controller.page;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.cms.service.page.DynamicPageService;


@Controller
public class DynamicPageController {
	

	@Resource
	private DynamicPageService dynamicPageService;
	
	
	/**
	 * 动态首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index.htm",method = RequestMethod.GET)
	public ModelAndView  dynamicInedx(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		return dynamicPageService.dynamicIndex(request, model);
	}
	
	/**
	 * 动态栏目页
	 */
	@RequestMapping(value ="/channel/{channelCode}/{pageCode}.htm")
	public ModelAndView dynamicChannelPage(HttpServletRequest request,@PathVariable String channelCode,@PathVariable String pageCode, ModelMap model) {
		return dynamicPageService.dynamicChannelPage(request, channelCode, pageCode, model);
	}
	

	
	
	/**
	 * 动态内容详情页
	 * @param channelId
	 * @param contentId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/content/{categoryCode}/{contentId}.htm")
	public ModelAndView dynamicContentPage(@PathVariable String categoryCode,@PathVariable String contentId,HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		return dynamicPageService.dynamicContentPage(categoryCode, contentId, request, model);
	}

   
}
