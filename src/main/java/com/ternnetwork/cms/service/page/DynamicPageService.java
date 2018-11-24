package com.ternnetwork.cms.service.page;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
public interface DynamicPageService {
	public ModelAndView dynamicIndex(HttpServletRequest request,ModelMap model);
	public ModelAndView dynamicChannelPage(HttpServletRequest request,String channelCode,String pageCode,ModelMap model);
	public ModelAndView dynamicContentPage(String categoryCode,String contentId,HttpServletRequest request, ModelMap model);
}
