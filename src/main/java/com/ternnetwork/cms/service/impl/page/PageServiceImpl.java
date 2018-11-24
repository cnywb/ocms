package com.ternnetwork.cms.service.impl.page;



import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.cms.dao.page.PageDao;
import com.ternnetwork.cms.model.page.CmsPage;
import com.ternnetwork.cms.service.channel.ChannelService;
import com.ternnetwork.cms.service.page.PageService;

@Service("pageService")
public class PageServiceImpl implements PageService {
	
	@Resource
	private PageDao pageDao;
	
	@Resource
	private ChannelService channelService;
	
	public BaseResponse idoAdd(CmsPage t){
	
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
       		res.setStatus(0);
       		res.setMessage("操作失败，名称不能为空！");
    		return res;
    	}
		
		if(StringUtils.isEmpty(t.getCode())){
       		res.setStatus(1);
       		res.setMessage("操作失败，代码不能为空！");
    		return res;
    	}
		
		long totalCount=pageDao.getTotalCount("select count(t.id) from CmsPage t where t.code=?1 and t.channel.id=?2",t.getCode(),t.getChannel().getId());
       	if(totalCount>0L){
       		res.setStatus(2);
       		res.setMessage("操作失败，代码已在该栏目中存在！");
    		return res;
       	}
       	pageDao.persist(t);
       	res.setStatus(3);
   		res.setMessage("操作成功！");
		return res;
	}
	
	/**
	 *更新页面
	 * 
	 */
	public BaseResponse idoUpdate(CmsPage t) throws Exception{
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
       		res.setStatus(0);
       		res.setMessage("操作失败，名称不能为空！");
    		return res;
    	}
		
		if(StringUtils.isEmpty(t.getCode())){
       		res.setStatus(1);
       		res.setMessage("操作失败，代码不能为空！");
    		return res;
    	}
		
		long totalCount=pageDao.getTotalCount("select count(t.id) from CmsPage t where t.code=?1 and t.channel.id=?2 and t.id!=?3",t.getCode(),t.getChannel().getId(),t.getId());
       	if(totalCount>0L){
       		res.setStatus(2);
       		res.setMessage("操作失败，代码已在该栏目中存在！");
    		return res;
       	}
     	pageDao.saveOrUpdate(t);
       	res.setStatus(3);
   		res.setMessage("操作成功！");
   		return res;
	}
	
	public BaseResponse idoDelete(Long id) throws Exception{
		BaseResponse res=new BaseResponse();
		CmsPage t=pageDao.findById(CmsPage.class, id, LockModeType.PESSIMISTIC_WRITE);
		if(t==null){
          	res.setStatus(0);
           	res.setMessage("操作失败,被删除对象不存在！");
    		return res;
        }
		pageDao.delete(t);
	 	res.setStatus(1);
       	res.setMessage("操作成功！");
       	return res;
	}
	
	public CmsPage findById(Long id){
		CmsPage page=pageDao.findById(CmsPage.class, id);
		return page;
	}
	
	public Page query(Page page,String channelId,String name){
		StringBuffer jpql=new StringBuffer("from CmsPage t where 1=1");
	    List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(channelId)){
			params.add(Long.parseLong(channelId));
			jpql.append(" and t.channel.id=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name+"%");
			jpql.append(" and t.name like?"+params.size());
		}
		return pageDao.query("select count(t.id)"+jpql.toString(),jpql.toString(), page,params.toArray());
	}
	public String queryToJsonStr(Page page,String channelId,String name){
		BootstrapGrid grid=new BootstrapGrid(query(page,channelId,name));
		return grid.toString();
	}
	
   public CmsPage find(String pageCode,String channelCode,String domain){
	   List<CmsPage> list=pageDao.findAll("from CmsPage t where t.code=?1 and t.channel.code=?2 and t.channel.site.domain=?3 ", pageCode,channelCode,domain);
	   if(list.size()==0){
		   return null;
	   }
	   return list.get(0);
   }
	
	public List<CmsPage> findAll(){
		StringBuffer jpql=new StringBuffer("from CmsPage t where 1=1");
	   	return	pageDao.findAll(jpql.toString(),null);
	}
	
	public List<String> findAllUrl(){
	
		List<CmsPage> list=findAll();
		List<String> retVal=new ArrayList<String>();
		for(CmsPage t:list){
			retVal.add(t.getUrl());
		}
		return retVal;
	}
	
	public String getChannelPageTemplateFile(String channelCode, String siteCode, String template) {
		  return PropUtils.getPropertyValue("file_path.properties", "cms_template_path").concat("/site/").concat(siteCode).concat("/channel/").concat(channelCode).concat("/").concat(template);
	}
		
	
}
