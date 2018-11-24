package com.ternnetwork.cms.service.impl.channel;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.dao.channel.ChannelDao;
import com.ternnetwork.cms.model.channel.Channel;
import com.ternnetwork.cms.model.ui.ChannelZtree;
import com.ternnetwork.cms.service.channel.ChannelService;
import com.ternnetwork.cms.service.content.ContentService;
import com.ternnetwork.cms.service.file.CmsFileService;
import com.ternnetwork.cms.service.site.SiteService;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelDao channelDao;
	@Resource
	private ContentService contentService;
	@Resource
	private SiteService siteService;
	@Resource
	private  CmsFileService cmsFileService;
	
	public BaseResponse idoAdd(HttpServletRequest request,Channel t){
		
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getCode())){
       		res.setStatus(0);
       		res.setMessage("操作失败，代码不能为空！");
    		return res;
    	}
		if(StringUtils.isEmpty(t.getNameZh())){
       		res.setStatus(1);
       		res.setMessage("操作失败，名称不能为空！");
    		return res;
    	}
		if(t.getSeqNum()==null){
			t.setSeqNum(0);
		}
		t.setNameEn(t.getNameZh());
		List<Channel> list=channelDao.findAll("from Channel t where t.code=?1",t.getCode());
		if(list.size()>0){
			res.setStatus(2);
       		res.setMessage("操作失败，代码已在系统中存在！");
    		return res;
		}
		channelDao.persist(t);
		res.setStatus(3);
   		res.setMessage("操作成功！");
   		String siteCode=siteService.findById(t.getSite().getId()).getCode();
   		cmsFileService.createChannelFileDirs(request,siteCode,t.getCode());
		return res;
	}
	
	
	
    
	public BaseResponse doAdd(Channel t){
	
		channelDao.persist(t);
		
	    return null;
	}
	public BaseResponse idoUpdate(HttpServletRequest request,Channel t){

		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getCode())){
       		res.setStatus(0);
       		res.setMessage("操作失败，代码不能为空！");
    		return res;
    	}
		if(StringUtils.isEmpty(t.getNameZh())){
       		res.setStatus(1);
       		res.setMessage("操作失败，名称不能为空！");
    		return res;
    	}
		if(t.getSeqNum()==null){
			t.setSeqNum(0);
		}
		t.setNameEn(t.getNameZh());
		List<Channel> list=channelDao.findAll("from Channel t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(list.size()>0){
			res.setStatus(2);
       		res.setMessage("操作失败，代码已在系统中存在！");
    		return res;
		}
		
		Channel oldChannel=channelDao.findById(Channel.class, t.getId(), LockModeType.PESSIMISTIC_WRITE);
		if(!oldChannel.getCode().equals(t.getCode())){
			String siteCode=siteService.findById(t.getSite().getId()).getCode();
			cmsFileService.updateChannelFileDirs(request,siteCode,oldChannel.getCode(), t.getCode());
		}
		
		channelDao.saveOrUpdate(t);
		res.setStatus(3);
   		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoDeleteById(HttpServletRequest request,long id){
		
		Channel t=channelDao.findById(Channel.class, id);
		BaseResponse res=new BaseResponse();
		if(t==null){
			res.setStatus(0);
       		res.setMessage("操作失败，对象不存在！");
    		return res;
		}
		
		long count=channelDao.getTotalCount("select count(t.id) from Channel t where t.parentId=?1", id);
		if(count>0L){
			res.setStatus(1);
       		res.setMessage("操作失败，存在子栏目，请先删除子栏目！");
    		return res;
		}
		count=contentService.countByChannelId(id);
		if(count>0L){
			res.setStatus(2);
       		res.setMessage("操作失败，该栏目被内容引用，请先删除该栏目下的内容！");
    		return res;
		}
		cmsFileService.updateChannelFileDirs(request, t.getSite().getCode(), t.getCode(), t.getCode().concat("_DELETED_").concat(String.valueOf(System.currentTimeMillis())));
		
		channelDao.delete(t);
		res.setStatus(3);
   		res.setMessage("操作成功！");
   		return res;
	}
	
	public String getZTreeJSON(long siteId){
		List<Channel> result=channelDao.findAll("from Channel sm where sm.site.id=?1 order  by sm.seqNum", siteId);
		return convertToZtreeList(result).toString();
	}
	private List<ChannelZtree>convertToZtreeList(List<Channel> list){
		List<ChannelZtree> retVal=new ArrayList<ChannelZtree>();
		for(Channel t:list){
			ChannelZtree ztree=new ChannelZtree();
			ztree.setId(t.getId());
			ztree.setpId(t.getParentId());
			ztree.setCode(t.getCode());
			ztree.setName(t.getNameZh());
			ztree.setSeqNum(t.getSeqNum());
			ztree.setVisible(t.getVisible());
			retVal.add(ztree);
		}
			return retVal;
	}
	
	public List<Channel> findRootBySiteId(long siteId){
		return channelDao.findAll("from Channel t where t.parentId=?1 and t.site.id=?2",0L,siteId);
	}
	
	public Channel findById(long id){
		return channelDao.findById(Channel.class, id);
	}
	
	public List<Channel> findBySiteId(long siteId){
		return channelDao.findAll("from Channel t where t.site.id=?1 and t.visible=?2",siteId,true);
	}
	
}
