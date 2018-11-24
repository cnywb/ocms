package com.ternnetwork.cms.service.impl.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.cms.dao.content.ContentCommentDao;
import com.ternnetwork.cms.dao.content.ContentDao;
import com.ternnetwork.cms.dao.page.PageDao;
import com.ternnetwork.cms.enums.ContentStatus;
import com.ternnetwork.cms.model.content.Content;
import com.ternnetwork.cms.service.content.ContentService;



@Service("contentService")
public class ContentServiceImpl implements ContentService {
	
	@Resource
	private ContentDao contentDao;
	@Resource
	private UserService userSerivce;
	
	@Resource
	private PageDao pageDao;
	
	@Resource
	private ContentCommentDao contentCommentDao;
	
	
	
	public BaseResponse idoAdd(HttpServletRequest request,Content t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getTitle())){
       		res.setStatus(0);
       		res.setMessage("操作失败，标题不能为空！");
    		return res;
    	}
		List<Object> list=contentDao.queryAllObject("select id from Content where title=?1",t.getTitle());
		if(list.size()>0){
			res.setStatus(1);
       		res.setMessage("操作失败，标题已在系统中存在！");
    		return res;
		}
		if(t.getStatus().equals(ContentStatus.PUBLISHED)){
			t.setPublishTime(new Date());
		}
		if(t.getColor()==null){
			t.setColor("");
		}
		t.setUser(userSerivce.getCurrentUser());
		t.setContent(t.getTempContentString().getBytes());
		contentDao.persist(t);
		res.setStatus(2);
   		res.setMessage("操作成功！");
		return res;
	}


	
	
	
	public BaseResponse idoUpdate(HttpServletRequest request,Content t){
		BaseResponse res=new BaseResponse();
		List<Object> list=contentDao.queryAllObject("select id from Content where title=?1 and id!=?2",t.getTitle(),t.getId());
		if(list.size()>0){
			res.setStatus(1);
       		res.setMessage("操作失败，标题已在系统中存在！");
    		return res;
		}
		if(t.getStatus().equals(ContentStatus.PUBLISHED)){
			t.setPublishTime(new Date());
		}
		t.setContent(t.getTempContentString().getBytes());
		t.setUser(userSerivce.getCurrentUser());
		contentDao.saveOrUpdate(t);
		res.setStatus(2);
   		res.setMessage("操作成功！");
		return res;
	}
	
	
	public Content findById(Long id){
		return contentDao.findById(Content.class, id);
	}
	
	
	public Page<Content> query(String minPublishTime,String maxPublishTime,String minCreateTime,String maxCreateTime,Integer status,String title,Boolean commentAble,Long contentCategoryId,Long channelId,String author,Page page){
	    StringBuffer jpql=new StringBuffer("from Content t where 1=1");
	    String minTime=" 00:00:00";
        String maxTime=" 23:59:59";
	    List<Object> params=new ArrayList<Object>();
	    if(StringUtils.isNotEmpty(minPublishTime)){
			params.add(DateUtils.parseDate(minPublishTime+minTime, DateUtils.FORMAT_DATE_DEFAULT));
			jpql.append(" and t.publishTime>=?"+(params.size()));
		}
	    if(StringUtils.isNotEmpty(maxPublishTime)){
	    	params.add(DateUtils.parseDate(maxPublishTime+maxTime, DateUtils.FORMAT_DATE_DEFAULT));
			jpql.append(" and t.publishTime<=?"+(params.size()));
		}
	    
	    if(StringUtils.isNotEmpty(minCreateTime)){
				params.add(DateUtils.parseDate(minCreateTime+minTime, DateUtils.FORMAT_DATE_DEFAULT));
				jpql.append(" and t.createTime>=?"+(params.size()));
		}
		if(StringUtils.isNotEmpty(maxCreateTime)){
		    	params.add(DateUtils.parseDate(maxCreateTime+maxTime, DateUtils.FORMAT_DATE_DEFAULT));
				jpql.append(" and t.createTime<=?"+(params.size()));
		}
	   
		if(StringUtils.isNotEmpty(title)){
			params.add(title+"%");
			jpql.append(" and t.title like?"+params.size());
		}
		if(StringUtils.isNotEmpty(author)){
			params.add(author);
			jpql.append(" and t.author＝?"+params.size());
		}
		if(commentAble!=null){
			params.add(commentAble);
			jpql.append(" and t.commentAble=?"+params.size());
		}
		if(contentCategoryId!=null){
			params.add(contentCategoryId);
			jpql.append(" and t.contentCategory.id=?"+params.size());
		}
		if(channelId!=null){
			params.add(channelId);
			jpql.append(" and t.channel.id=?"+params.size());
		}
		
		if(status!=null){
			params.add(status);
			jpql.append(" and t.status=?"+params.size());
		}
		
		jpql.append(" order by t.createTime desc");
		return contentDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}

	
	public String queryToJsonStr(String minPublishTime,String maxPublishTime,String minCreateTime,String maxCreateTime,Integer status,String title,Boolean commentAble,Long contentCategoryId,Long channelId,String author,Page page){
		Page<Content> result=query(minPublishTime, maxPublishTime, minCreateTime, maxCreateTime, status, title, commentAble, contentCategoryId, channelId, author, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Long countByCategoryId(Long id){
		return contentDao.getTotalCount("select count(t.id) from Content t where t.contentCategory.id=?1",id );
	}
	public Long countByChannelId(Long id){
		return contentDao.getTotalCount("select count(t.id) from Content t where t.channel.id=?1",id );
	}
	
	public long idoUpdateViewCount(Long id){
		return contentDao.bulkUpdate("update Content set viewCount=viewCount+1 where id=?1", id);
	}
	public String getContentPageTemplateFile(String categoryCode, String siteCode, String template) {
		  return PropUtils.getPropertyValue("file_path.properties", "cms_template_path").concat("site/").concat(siteCode).concat("/content/").concat(template);
	}





	
	public BaseResponse idoDelete(long id) {
		BaseResponse res=new BaseResponse();
		Content t=contentDao.findById(Content.class, id, LockModeType.PESSIMISTIC_WRITE);
		if(t==null){
			res.setStatus(0);
       		res.setMessage("操作失败，被删除对象不存在！");
    		return res;
		}
		contentCommentDao.bulkUpdate("delete from ContentComment where content.id=?1", id);
		contentDao.delete(t);
		res.setStatus(1);
   		res.setMessage("操作成功！");
		return res;
	}
	
	public List<String> findAllUrl(){
		List<Content> list=contentDao.findAll("select new com.ternnetwork.cms.model.content.Content(id,contentCategory) from Content where 1=1", null);
		List<String> retVal=new ArrayList<String>();
		for(Content t:list){
			retVal.add(t.getUrl());
		}
	  return retVal;
	}

}
