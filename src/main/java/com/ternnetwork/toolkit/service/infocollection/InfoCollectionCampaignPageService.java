package com.ternnetwork.toolkit.service.infocollection;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaignPage;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaignPageRequest;

public interface InfoCollectionCampaignPageService {

    public BaseResponse idoAdd(InfoCollectionCampaignPage t);
	
    public BaseResponse idoUpdate(InfoCollectionCampaignPage t);
	
    public BaseResponse idoDelete(Long id);
	
    public BootstrapGrid queryToBootstrapGrid(Long campaignId,String code,String name,String startTime,String endTime,Boolean index,Page page);
	
	public void addCampaignPageDir(HttpServletRequest request,String campaignCode);
	
	public void updateCampaignPageDir(HttpServletRequest request,String oldCampaignCode,String newCampaignCode);
	
	public void deleteCampaignPageDir(HttpServletRequest request,String campaignCode);
	
	public List<String> getCampaignPageFileList(HttpServletRequest request,String campaignCode);
	
	public String getCampaignPageRealPath(HttpServletRequest request,String campaignCode);

	public ModelAndView dynamicPage(String campaignCode, String pageCode,InfoCollectionCampaignPageRequest campaignPageRequest, HttpServletRequest request,
			ModelMap model);
}
