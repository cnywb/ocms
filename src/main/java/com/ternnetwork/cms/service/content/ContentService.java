package com.ternnetwork.cms.service.content;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.model.content.Content;

public interface ContentService {
	public BaseResponse idoAdd(HttpServletRequest request,Content t);
	public BaseResponse idoUpdate(HttpServletRequest request,Content t);
	public Content findById(Long id);
	public String queryToJsonStr(String minPublishTime,String maxPublishTime,String minCreateTime,String maxCreateTime,Integer status,String title,Boolean commentAble,Long contentCategoryId,Long channelId,String author,Page page);
	public Long countByCategoryId(Long id);
	public Long countByChannelId(Long id);
	public long idoUpdateViewCount(Long id);
	public String getContentPageTemplateFile(String categoryCode, String siteCode, String template);
	public BaseResponse idoDelete(long id);
	public List<String> findAllUrl();
}
