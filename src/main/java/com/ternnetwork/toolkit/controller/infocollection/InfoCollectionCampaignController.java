package com.ternnetwork.toolkit.controller.infocollection;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaign;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionCampaignService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/infocollection/campaign/*")
public class InfoCollectionCampaignController {
	
	@Resource
	private InfoCollectionCampaignService infoCollectionCampaignService;
	
	
	
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/toolkit/infocollection/campaign_manage.jsp");
	}
	
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,HttpServletRequest request,InfoCollectionCampaign t){
		return infoCollectionCampaignService.idoAdd(request,t);
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,HttpServletRequest request,InfoCollectionCampaign t){
		return infoCollectionCampaignService.idoUpdate(request,t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletResponse response,HttpServletRequest request,Long id){
		return infoCollectionCampaignService.idoDelete(request,id);
	}
	
	
	@RequestMapping("query.htm")
	public @ResponseBody BootstrapGrid query(HttpServletResponse response,String sort,String order,int limit, int offset,String code,String name,String startTime,String endTime,Boolean enable){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
	    return infoCollectionCampaignService.queryToBootstrapGrid(code, name, startTime, endTime, enable, page);
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	

}
