package com.ternnetwork.cms.service.impl.site;




import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.ExtendedFileUtils;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.cms.dao.site.SiteDao;
import com.ternnetwork.cms.model.site.Site;
import com.ternnetwork.cms.service.channel.ChannelService;
import com.ternnetwork.cms.service.file.CmsFileService;
import com.ternnetwork.cms.service.site.SiteService;


@Service("siteService")
public class SiteServiceImpl implements SiteService {

	@Resource
	private  SiteDao  siteDao;
	
	@Resource
	private ChannelService channelService;
	
	@Resource
	private  CmsFileService cmsFileService;
	
	
	public BaseResponse idoAdd(HttpServletRequest request,Site t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
       		res.setStatus(0);
       		res.setMessage("操作失败，名称不能为空！");
    		return res;
    	}
		if(StringUtils.isEmpty(t.getDomain())){
       		res.setStatus(1);
       		res.setMessage("操作失败，域名不能为空！");
    		return res;
    	}
		if(t.getDomain().contains("http://")){
       		res.setStatus(2);
       		res.setMessage("操作失败，域名不能带有http://！");
    		return res;
    	}
    	if(StringUtils.isEmpty(t.getCode())){
    		res.setStatus(3);
    		res.setMessage("操作失败，代码不能为空！");
    		return res;
    	}
     	long totalCount=siteDao.getTotalCount("select count(t.id) from Site t where t.code=?1",t.getCode());
       	if(totalCount>0L){
       		res.setStatus(4);
       		res.setMessage("操作失败，代码已在系重中存在！");
    		return res;
       	}
        totalCount=siteDao.getTotalCount("select count(t.id) from Site t where t.domain=?1",t.getDomain());
        if(totalCount>0L){
       		res.setStatus(5);
       		res.setMessage("操作失败，域名已在系重中存在！");
    		return res;
       	}
    	cmsFileService.createSiteFileDirs(request, t.getCode());
		siteDao.persist(t);
	   	res.setStatus(6);
   		res.setMessage("操作成功！");
   		return res;
	}



	
	public BaseResponse idoUpdate(HttpServletRequest request,Site t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getName())){
       		res.setStatus(0);
       		res.setMessage("操作失败，名称不能为空！");
    		return res;
    	}
		if(StringUtils.isEmpty(t.getDomain())){
       		res.setStatus(1);
       		res.setMessage("操作失败，域名不能为空！");
    		return res;
    	}
		if(t.getDomain().contains("http://")){
       		res.setStatus(2);
       		res.setMessage("操作失败，域名不能带有http://！");
    		return res;
    	}
    	if(StringUtils.isEmpty(t.getCode())){
    		res.setStatus(3);
    		res.setMessage("操作失败，代码不能为空！");
    		return res;
    	}
     	long totalCount=siteDao.getTotalCount("select count(t.id) from Site t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
       	if(totalCount>0L){
       		res.setStatus(4);
       		res.setMessage("操作失败，代码已在系重中存在！");
    		return res;
       	}
        totalCount=siteDao.getTotalCount("select count(t.id) from Site t where t.domain=?1 and t.id!=?2",t.getDomain(),t.getId());
        if(totalCount>0L){
       		res.setStatus(5);
       		res.setMessage("操作失败，域名已在系重中存在！");
    		return res;
       	}
        Site oldSite=siteDao.findById(Site.class, t.getId(), LockModeType.PESSIMISTIC_WRITE);
        
        if(!oldSite.getCode().equals(t.getCode())){
        	cmsFileService.updateSiteFileDirs(request, oldSite.getCode(), t.getCode());
        }
        
		siteDao.saveOrUpdate(t);
		
		res.setStatus(6);
   		res.setMessage("操作成功！");
		return res;
	}
	
	public List<Site> findByDomain(String domain){
		return siteDao.findAll("from Site t where t.domain=?1", domain);
	}
	
	public String query(Page page){
		String jpql="from Site t";
		Page<Site> result=siteDao.query("select count(t.id) from Site t",jpql, page,null);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public List<Site> findAll(){
		return siteDao.findAll("from Site t", null);
	}

	public Site getByDomain(String domain){
		List<Site> list=findAll();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Site findById(Long id){
		return siteDao.findById(Site.class, id);
	}
	
}
