package com.ternnetwork.cms.service.page;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.cms.model.page.PageComponent;

public interface PageComponentService {
	
	public PageComponent findById(Long id,HttpServletRequest request)throws Exception;
	
	public long idoAdd(PageComponent t)throws Exception;
	
	public long idoUpdate(PageComponent t)throws Exception;

	public long idoDeleteById(long id,HttpServletRequest request)throws IOException;
	
	public Page<PageComponent> query(Page page,String name);
	
	public String queryToJsonStr(Page page,String name);

	public List<PageComponent> findAll();
	
	public PageComponent findByCode(String  code);
}
