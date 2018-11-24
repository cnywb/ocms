package com.ternnetwork.cms.controller.channel;

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

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.channel.Channel;
import com.ternnetwork.cms.model.site.Site;
import com.ternnetwork.cms.service.channel.ChannelService;
import com.ternnetwork.cms.service.site.SiteService;


@Controller@Scope("prototype")
@RequestMapping("/cms/channel/*")
public class ChannelController {
	
	@Resource
	private ChannelService channelService;
	@Resource
	private SiteService siteService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/cms/channel/channel_manage.jsp").addObject("siteList",siteService.findAll());
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletRequest request,Channel t){
		 return channelService.idoAdd(request,t);
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletRequest request,Channel t){
		return channelService.idoUpdate(request,t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletRequest request,long id){
		 return channelService.idoDeleteById(request,id);
	}

	
	@RequestMapping("getZTreeJSON.htm")
	public void getZTreeJSON(PrintWriter out,HttpServletResponse response,Long siteId){
		try{
			response.setContentType("text/javascript");
			out.print(channelService.getZTreeJSON(siteId));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("findBySite.htm")
	public  @ResponseBody List<Channel>  findBySiteId(PrintWriter out,HttpServletRequest request,HttpServletResponse response){
		Site site=siteService.getByDomain(request.getServerName());
		return channelService.findBySiteId(site.getId());
	}
}
