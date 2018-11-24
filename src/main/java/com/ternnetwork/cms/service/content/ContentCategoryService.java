package com.ternnetwork.cms.service.content;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.content.ContentCategory;
import com.ternnetwork.cms.model.content.ContentCategoryVo;
import com.ternnetwork.cms.model.ui.ContentCategoryZtree;

public interface ContentCategoryService {
	public BaseResponse idoAdd(HttpServletRequest request,ContentCategory t);
	
	public BaseResponse idoUpdate(HttpServletRequest request,ContentCategory t);
	
	public List<ContentCategoryZtree>  getZTreeJSON(Long siteId);
	
	public BaseResponse idoDeleteById(HttpServletRequest request,long id);
	
	public List<ContentCategoryVo> findByParentCode(String parentCode);

}
