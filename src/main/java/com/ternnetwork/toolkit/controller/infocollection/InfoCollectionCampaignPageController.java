package com.ternnetwork.toolkit.controller.infocollection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaignPage;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaignPageRequest;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionCampaignPageService;


@Controller@Scope("prototype")
@RequestMapping("/toolkit/infocollection/campaign/page/*")
public class InfoCollectionCampaignPageController {
	
	
	@Resource
	private InfoCollectionCampaignPageService infoCollectionCampaignPageService ;

	@RequestMapping("manage.htm")
	public ModelAndView manage(HttpServletRequest request,Long campaignId,String campaignCode){
		List<String> fileNameList=infoCollectionCampaignPageService.getCampaignPageFileList(request, campaignCode);
		return new ModelAndView("/WEB-INF/view/toolkit/infocollection/campaign_page_manage.jsp").addObject("fileNameList", fileNameList).addObject("campaignId", campaignId).addObject("campaignCode", campaignCode);
	}
	
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,HttpServletRequest request,InfoCollectionCampaignPage t){
		return infoCollectionCampaignPageService.idoAdd(t);
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,HttpServletRequest request,InfoCollectionCampaignPage t){
		return infoCollectionCampaignPageService.idoUpdate(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletResponse response,HttpServletRequest request,Long id){
		return infoCollectionCampaignPageService.idoDelete(id);
	}
	
	
	@RequestMapping("query.htm")
	public @ResponseBody BootstrapGrid query(HttpServletResponse response,String sort,String order,int limit, int offset,Long campaignId,String code,String name,String startTime,String endTime,Boolean enable){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
	    return infoCollectionCampaignPageService.queryToBootstrapGrid(campaignId,code, name, startTime, endTime, enable, page);
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value = "{campaignCode}/{pageCode}.htm")
	public ModelAndView dynamicPage(@PathVariable String campaignCode,@PathVariable String pageCode,InfoCollectionCampaignPageRequest campaignPageRequest,HttpServletRequest request,ModelMap model) {
		return infoCollectionCampaignPageService.dynamicPage(campaignCode, pageCode,campaignPageRequest, request, model);
	}
	
}
