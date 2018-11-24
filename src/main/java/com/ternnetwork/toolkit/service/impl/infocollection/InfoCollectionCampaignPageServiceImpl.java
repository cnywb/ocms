package com.ternnetwork.toolkit.service.impl.infocollection;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.dao.base.IBaseDao;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.ExtendedFileUtils;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionCampaignPageDao;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionDataDao;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaignPage;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaignPageRequest;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionCampaignPageService;


@Service("infoCollectionCampaignPageService")
public class InfoCollectionCampaignPageServiceImpl implements InfoCollectionCampaignPageService {

	
	@Resource
	private InfoCollectionCampaignPageDao infoCollectionCampaignPageDao;
	
	private static final Logger log = LoggerFactory.getLogger(InfoCollectionCampaignPageServiceImpl.class);

	@Resource
	private IBaseDao iBaseDao;
	@Resource
	private InfoCollectionDataDao infoCollectionDataDao;
	
	public BaseResponse idoAdd(InfoCollectionCampaignPage t){
		
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
    	
     	long totalCount=infoCollectionCampaignPageDao.getTotalCount("select count(t.id) from InfoCollectionCampaignPage t where t.code=?1 and t.campaign.id=?2",t.getCode(),t.getCampaign().getId());
       	if(totalCount>0L){
       		res.setStatus(3);
       		res.setMessage("操作失败，页面代码已在当前活动中存在！");
    		return res;
       	}
       	
      	    infoCollectionCampaignPageDao.persist(t);
    	
       	    res.setStatus(5);
       	
       	    res.setMessage("操作成功！");
       	
		    return res;
		
	 }
	
	
    public BaseResponse idoUpdate(InfoCollectionCampaignPage t){
		
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
    	
     	long totalCount=infoCollectionCampaignPageDao.getTotalCount("select count(t.id) from InfoCollectionCampaignPage t where t.code=?1 and t.campaign.id=?2 and t.id!=?3",t.getCode(),t.getCampaign().getId(),t.getId());
       	if(totalCount>0L){
       		res.setStatus(3);
       		res.setMessage("操作失败，页面代码已在当前活动中存在！");
    		return res;
       	}
       	
    
    	    infoCollectionCampaignPageDao.saveOrUpdate(t);
    	
       	    res.setStatus(5);
       	
       	    res.setMessage("操作成功！");
       	
		    return res;
		
	 }
	
    
    public BaseResponse idoDelete(Long id){
    	BaseResponse res=new BaseResponse();
    	InfoCollectionCampaignPage t=infoCollectionCampaignPageDao.findById(InfoCollectionCampaignPage.class, id, LockModeType.PESSIMISTIC_WRITE);
        if(t==null){
          	res.setStatus(0);
           	res.setMessage("操作失败,被删除对象不存在！");
    		return res;
        }
        infoCollectionCampaignPageDao.delete(t);
       	res.setStatus(1);
       	res.setMessage("操作成功！");
       	return res;
    }
	
    public BootstrapGrid queryToBootstrapGrid(Long campaignId,String code,String name,String startTime,String endTime,Boolean index,Page page){
		Page<InfoCollectionCampaignPage> result=query(campaignId, code, name, startTime, endTime, index, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid;
	}
    public Page<InfoCollectionCampaignPage> query(Long campaignId,String code,String name,String startTime,String endTime,Boolean index,Page page){
	    StringBuffer jpql=new StringBuffer("from InfoCollectionCampaignPage t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name);
			jpql.append(" and t.name=?"+params.size());
		}
		
		if(campaignId!=null){
			params.add(campaignId);
			jpql.append(" and t.campaign.id=?"+params.size());
		}
		if(!StringUtils.isEmpty(startTime)){
			params.add(DateUtils.parseDate(startTime+" 00:00:00",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.startTime>=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(endTime)){
			params.add(DateUtils.parseDate(endTime+" 23:59:59",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.startTime<=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return infoCollectionCampaignPageDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	
	
	
	public void addCampaignPageDir(HttpServletRequest request,String campaignCode){
		
		if(!StringUtils.isEmpty(campaignCode)){

			String realPath=getCampaignPageRootRealPath(request);
			
			String campaignDirName=realPath+campaignCode+"/";
		
            ExtendedFileUtils.makeDirs(campaignDirName);
					
		}
	}
	
	
	
	public void updateCampaignPageDir(HttpServletRequest request,String oldCampaignCode,String newCampaignCode){
		
		if(!StringUtils.isEmpty(oldCampaignCode)&&!oldCampaignCode.equals(newCampaignCode)){

			String realPath=getCampaignPageRootRealPath(request);
			
			String oldDirName=realPath+oldCampaignCode+"/";
			String newDirName=realPath+newCampaignCode+"/";
			try {
				File oldDir=new File(oldDirName);
				if(oldDir.exists()){
					File newDir=new File(newDirName);
					ExtendedFileUtils.makeDirs(newDirName);
					ExtendedFileUtils.copyDirectory(oldDir,newDir);
					ExtendedFileUtils.deleteDirectory(oldDir);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
   public void deleteCampaignPageDir(HttpServletRequest request,String campaignCode){
		updateCampaignPageDir(request, campaignCode, campaignCode+"_DELETED_"+System.currentTimeMillis());
   }
	
	
	/**
	 * 获取活动页面真实路径
	 * @param request
	 * @return
	 */
	private String getCampaignPageRootRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath(PropUtils.getPropertyValue("file_path.properties", "campaign_template_path"))+"/";
	}
	
	public String getCampaignPageRealPath(HttpServletRequest request,String campaignCode){
		return getCampaignPageRootRealPath(request)+campaignCode+"/";
	}
	
	public List<String> getCampaignPageFileList(HttpServletRequest request,String campaignCode){
		List<String> retVal=new ArrayList<String>();
		String realPath=getCampaignPageRealPath(request, campaignCode);
		ExtendedFileUtils.makeDirs(realPath);
		File file = new File(realPath);
		File[] fileArray=file.listFiles();
		for(File f:fileArray){
			if(f.getName().endsWith(".html")){
				retVal.add(f.getName());
			}
			
		}
		return retVal;
	}


	public InfoCollectionCampaignPage find(String campaignCode, String pageCode){
		 List<InfoCollectionCampaignPage> list=infoCollectionCampaignPageDao.findAll("from InfoCollectionCampaignPage t where t.code=?1 and t.campaign.code=?2",pageCode,campaignCode);
	     return list.size()==0?null:list.get(0);
	}
	
	

	public ModelAndView dynamicPage(String campaignCode, String pageCode,InfoCollectionCampaignPageRequest campaignPageRequest, HttpServletRequest request,ModelMap model) {
		ModelAndView mv=new ModelAndView("/errors/site_error.jsp");
		InfoCollectionCampaignPage page=find(campaignCode, pageCode);
		String basePath = request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(String.valueOf(request.getServerPort())).concat(request.getContextPath()).concat("/");
		if(page==null){
			 mv.addObject("message","页面不存在,请在活动页面管理中创建");
	         return mv;
		}
		String templatePath=PropUtils.getPropertyValue("file_path.properties", "campaign_template_path");
		if(!templatePath.endsWith("/")){
			templatePath=templatePath.concat("/");
		}else{
			templatePath=templatePath.concat(campaignCode).concat("/").concat(page.getTemplate());
		}
		mv=new ModelAndView(templatePath).addObject("basePath", basePath);
		if(!StringUtils.isEmpty(campaignPageRequest.getOpenId())){
			mv.addObject("isMember",isMember(campaignPageRequest.getOpenId()));
			mv.addObject("existData",existData(campaignPageRequest.getOpenId(),campaignCode));
		}
		log.info("动态访问："+templatePath);
		
		if(campaignPageRequest.getPreview()!=null&&campaignPageRequest.getPreview()==true){
			mv.addObject("kvUrl", campaignPageRequest.getKvUrl());
			mv.addObject("startTime", campaignPageRequest.getStartTime());
			mv.addObject("endTime", campaignPageRequest.getEndTime());
			mv.addObject("product", campaignPageRequest.getProduct());
			mv.addObject("subject", campaignPageRequest.getSubject());
			mv.addObject("award", campaignPageRequest.getAward());
			mv.addObject("memo", campaignPageRequest.getMemo());
			return mv;
		}
		mv.addObject("kvUrl", page.getCampaign().getKvUrl());
		mv.addObject("startTime", page.getCampaign().getStartTime());
		mv.addObject("endTime", page.getCampaign().getEndTime());
		mv.addObject("product", page.getCampaign().getProduct());
		mv.addObject("subject", page.getCampaign().getSubject());
		mv.addObject("award", page.getCampaign().getAward());
		mv.addObject("memo", page.getCampaign().getMemo());
		mv.addObject("thumbnailUrl", page.getCampaign().getThumbnailUrl());
		return mv;
	}
	
	private Boolean isMember(String wechatId){
		  BigDecimal memberCount=(BigDecimal)iBaseDao.getSingleResultByNativeSQL("select count(1) from jo_user ou ,jc_user cu where ou.user_id=cu.user_id and cu.group_id=2 and ou.WECHAT_USERNAME=?1",wechatId);
	      return memberCount.longValue()>0L?true:false;
	}
	private Boolean existData(String wechatId,String campaignCode){
		    long totalCount=infoCollectionDataDao.getTotalCount("select count(t.id) from InfoCollectionData t where t.wechatId=?1 and t.campaign.code=?2",wechatId,campaignCode);
		    return totalCount>0L?true:false;
	}
	
}
