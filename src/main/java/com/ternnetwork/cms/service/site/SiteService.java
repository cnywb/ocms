package com.ternnetwork.cms.service.site;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.site.Site;

public interface SiteService {
	public BaseResponse idoAdd(HttpServletRequest request,Site t);
	
	public BaseResponse idoUpdate(HttpServletRequest request,Site t);
	
	public List<Site> findByDomain(String domain);
	
	public String query(Page page);
	
	public List<Site> findAll();
	
	public Site getByDomain(String domain);
	
	public Site findById(Long id);
}
