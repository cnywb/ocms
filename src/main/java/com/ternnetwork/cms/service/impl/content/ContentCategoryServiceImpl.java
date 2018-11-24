package com.ternnetwork.cms.service.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.cms.dao.content.ContentCategoryDao;
import com.ternnetwork.cms.model.content.ContentCategory;
import com.ternnetwork.cms.model.content.ContentCategoryVo;
import com.ternnetwork.cms.model.ui.ContentCategoryZtree;
import com.ternnetwork.cms.service.content.ContentCategoryService;
import com.ternnetwork.cms.service.content.ContentService;


@Service("contentCategoryService")
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Resource
	private ContentCategoryDao contentCategoryDao;
	
	@Resource
	private ContentService contentService ;
	
	public BaseResponse idoAdd(HttpServletRequest request,ContentCategory t){
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
		t.setDescription(t.getTempContentDescriptionString().getBytes());
		Long totalCount=contentCategoryDao.getTotalCount("select count(id) from ContentCategory where code=?1", t.getCode());
		if(totalCount.longValue()>0L){
			res.setStatus(2);
       		res.setMessage("操作失败，代码已在系统中存在！");
    		return res;
		}
		contentCategoryDao.persist(t);
		res.setStatus(3);
   		res.setMessage("操作成功！");
   		return res;
	}
	
	public BaseResponse idoUpdate(HttpServletRequest request,ContentCategory t){
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
		Long totalCount=contentCategoryDao.getTotalCount("select count(id) from ContentCategory where code=?1 and id!=?2", t.getCode(),t.getId());
		if(totalCount.longValue()>0L){
			res.setStatus(2);
       		res.setMessage("操作失败，代码已在系统中存在！");
    		return res;
		}
		t.setNameEn(t.getNameZh());
		t.setDescription(t.getTempContentDescriptionString().getBytes());
		contentCategoryDao.saveOrUpdate(t);
		res.setStatus(3);
   		res.setMessage("操作成功！");
   		return res;
	}
	
	
	
	
	
	public List<ContentCategoryZtree> getZTreeJSON(Long siteId){
		List<ContentCategory> result=contentCategoryDao.findAll("from ContentCategory sm where sm.site.id=?1 order by sm.seqNum",siteId);
		return convertToZtreeList(result);
	}
	public BaseResponse idoDeleteById(HttpServletRequest request,long id){
		ContentCategory t=contentCategoryDao.findById(ContentCategory.class, id);
		BaseResponse res=new BaseResponse();
		if(t==null){
			res.setStatus(0);
       		res.setMessage("操作失败，对象不存在！");
    		return res;
		}
		Long totalCount=contentService.countByCategoryId(id);
		if(totalCount.longValue()>0L){
			res.setStatus(1);
       		res.setMessage("操作失败，该类已被内容引用，请先删除引用它的所有内容！");
    		return res;
		}
		List<ContentCategory> resultList=contentCategoryDao.findAll("from ContentCategory t where t.parentId=?1",id);
		
		if(resultList.size()>0){
			res.setStatus(2);
       		res.setMessage("操作失败，存在子类，请先删除子类！");
    		return res;
		}
			contentCategoryDao.delete(t);
			res.setStatus(3);
	   		res.setMessage("操作成功！");
	   		return res;
	}
	
	private List<ContentCategoryZtree>convertToZtreeList(List<ContentCategory> list){
		List<ContentCategoryZtree> retVal=new ArrayList<ContentCategoryZtree>();
		for(ContentCategory t:list){
			ContentCategoryZtree ztree=new ContentCategoryZtree();
			ztree.setId(t.getId());
			ztree.setpId(t.getParentId());
			ztree.setCode(t.getCode());
			ztree.setName(t.getNameZh());
			ztree.setPageUrl(t.getPageUrl());
			ztree.setSeqNum(t.getSeqNum());
			ztree.setVisible(t.getVisible());
			ztree.setParentId(t.getParentId());
			ztree.setNameEn(t.getNameEn());
			ztree.setNameZh(t.getNameZh());
			ztree.setTempContentDescriptionString(t.getDescription()==null?"":new String(t.getDescription()));
			retVal.add(ztree);
		}
			return retVal;
	}
	
	public List<ContentCategoryVo> findByParentCode(String parentCode){
		List<Object[]> list=contentCategoryDao.queryAllObjectArray("select t.id,t.nameZh,t.code,t.description,t.pageUrl from ContentCategory t,ContentCategory p where t.parentId=p.id and p.code=?1 and t.visible=?2 order by t.seqNum",parentCode,true);
	    
		List<ContentCategoryVo> retVal=new ArrayList<ContentCategoryVo>();
		
		for(Object[] objArray:list){
			ContentCategoryVo t=new ContentCategoryVo();
			t.setId((Long)objArray[0]);
			t.setName((String)objArray[1]);
			t.setCode((String)objArray[2]);
			String description=((byte[])objArray[3]==null?"":new String((byte[])objArray[3]));
			t.setDescription(description);
			String pageUrl=((String)objArray[4]==null?"":(String)objArray[4]);
			t.setPageUrl(pageUrl);
			retVal.add(t);
		}
		
		return retVal;
	}


}
