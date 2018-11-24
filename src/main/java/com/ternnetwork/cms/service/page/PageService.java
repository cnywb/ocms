package com.ternnetwork.cms.service.page;

import java.util.List;


import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.page.CmsPage;

public interface PageService {
	
	
	public BaseResponse idoAdd(CmsPage t);
	public BaseResponse idoUpdate(CmsPage t) throws Exception;
	public Page query(Page page,String channelId,String name);
	public String queryToJsonStr(Page page,String channelId,String name);
	public BaseResponse idoDelete(Long id) throws Exception;
	public List<CmsPage> findAll();
	public CmsPage findById(Long id);
	public CmsPage find(String pageCode,String channelCode,String domain);
	public String getChannelPageTemplateFile(String channelCode, String siteCode, String template);
	public List<String> findAllUrl();
}
