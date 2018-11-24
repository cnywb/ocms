package com.ternnetwork.toolkit.service.infocollection;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.enums.CampaignDataSendFrequency;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaign;

public interface InfoCollectionCampaignService {
	    public BaseResponse idoAdd(HttpServletRequest request,InfoCollectionCampaign t);
	    public BaseResponse idoUpdate(HttpServletRequest request,InfoCollectionCampaign t);
	    public BaseResponse idoDelete(HttpServletRequest request,Long id);
		public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page);
		public BootstrapGrid queryToBootstrapGrid(String code,String name,String startTime,String endTime,Boolean enable,Page page);
	    public List<InfoCollectionCampaign> findAll();
	    public List<InfoCollectionCampaign> findAll(CampaignDataSendFrequency dataSendFrequency);

}
