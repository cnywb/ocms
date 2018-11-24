package com.ternnetwork.toolkit.service.impl.infocollection;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionCampaignDao;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionDataDao;
import com.ternnetwork.toolkit.enums.CampaignDataSendFrequency;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaign;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionCampaignPageService;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionCampaignService;


@Service("infoCollectionCampaignService")
public class InfoCollectionCampaignServiceImpl implements InfoCollectionCampaignService {
	
	@Resource
	private InfoCollectionCampaignDao infoCollectionCampaignDao;
	
	@Resource
	private InfoCollectionDataDao infoCollectionDataDao;
	
	@Resource
	private InfoCollectionCampaignPageService infoCollectionCampaignPageService;
	
	
    public BaseResponse idoAdd(HttpServletRequest request,InfoCollectionCampaign t){
    	BaseResponse res=new BaseResponse();
       	if(StringUtils.isEmpty(t.getName())){
       		res.setStatus(0);
       		res.setMessage("操作失败，活动名称不能为空！");
    		return res;
    	}
    	if(StringUtils.isEmpty(t.getCode())){
    		res.setStatus(1);
    		res.setMessage("操作失败，活动代码不能为空！");
    		return res;
    	}
       	if(!t.getDataSendFrequency().equals(CampaignDataSendFrequency.NEVER)&&StringUtils.isEmpty(t.getDataReceiveEmail())){
       		res.setStatus(2);
       		res.setMessage("操作失败，开启数据发送时email不能为空！");
    		return res;
    	}
       	if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
       		res.setStatus(3);
       		res.setMessage("操作失败，开始时间不能小于结束时间！");
    		return res;
		}
       	long totalCount=infoCollectionCampaignDao.getTotalCount("select count(t.id) from InfoCollectionCampaign t where t.code=?1",t.getCode());
       	if(totalCount>0L){
       		res.setStatus(4);
       		res.setMessage("操作失败，活动代码已在系重中存在！");
    		return res;
       	}
       	infoCollectionCampaignPageService.addCampaignPageDir(request, t.getCode());
       	infoCollectionCampaignDao.persist(t);
       	res.setStatus(5);
       	res.setMessage("操作成功！");
		return res;
    }
	
    
    public BaseResponse idoUpdate(HttpServletRequest request,InfoCollectionCampaign t){
    	BaseResponse res=new BaseResponse();
       	if(StringUtils.isEmpty(t.getName())){
       		res.setStatus(0);
       		res.setMessage("操作失败，活动名称不能为空！");
    		return res;
    	}
    	if(StringUtils.isEmpty(t.getCode())){
    		res.setStatus(1);
    		res.setMessage("操作失败，活动代码不能为空！");
    		return res;
    	}
       	if(!t.getDataSendFrequency().equals(CampaignDataSendFrequency.NEVER)&&StringUtils.isEmpty(t.getDataReceiveEmail())){
       		res.setStatus(2);
       		res.setMessage("操作失败，开启数据发送时email不能为空！");
    		return res;
    	}
       	long totalCount=infoCollectionCampaignDao.getTotalCount("select count(t.id) from InfoCollectionCampaign t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
       	if(totalCount>0L){
       		res.setStatus(3);
       		res.setMessage("操作失败，活动代码已在系重中存在！");
    		return res;
       	}
    	if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
       		res.setStatus(4);
       		res.setMessage("操作失败，开始时间不能小于结束时间！");
    		return res;
		}
    	InfoCollectionCampaign oldInfoCollectionCampaign=infoCollectionCampaignDao.findById(InfoCollectionCampaign.class, t.getId());
    	infoCollectionCampaignPageService.updateCampaignPageDir(request, oldInfoCollectionCampaign.getCode(), t.getCode());
       	
    	infoCollectionCampaignDao.saveOrUpdate(t);
     	res.setStatus(5);
       	res.setMessage("操作成功！");
		return res;
    }
	
    public BaseResponse idoDelete(HttpServletRequest request,Long id){
    	BaseResponse res=new BaseResponse();
       	InfoCollectionCampaign t=infoCollectionCampaignDao.findById(InfoCollectionCampaign.class, id, LockModeType.PESSIMISTIC_WRITE);
        if(t==null){
          	res.setStatus(0);
           	res.setMessage("操作失败，活动不存在！");
    		return res;
        }
       	long totalCount=infoCollectionDataDao.getTotalCount("select count(t.id) from InfoCollectionData t where t.campaign.id=?1",t.getId());
       	if(totalCount>0L){
       		res.setStatus(1);
           	res.setMessage("操作失败，活动已被数据引用！");
           	return res;
       	}
       	infoCollectionCampaignDao.delete(t);
       	infoCollectionCampaignPageService.deleteCampaignPageDir(request, t.getCode());
       	res.setStatus(2);
       	res.setMessage("操作成功！");
       	return res;
    }
    
	public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page){
		Page<InfoCollectionCampaign> result=query(code, name, startTime, endTime, enable,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public BootstrapGrid queryToBootstrapGrid(String code,String name,String startTime,String endTime,Boolean enable,Page page){
		Page<InfoCollectionCampaign> result=query(code, name, startTime, endTime, enable,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid;
	}
    
    public Page<InfoCollectionCampaign> query(String code,String name,String startTime,String endTime,Boolean enable,Page page){
	    StringBuffer jpql=new StringBuffer("from InfoCollectionCampaign t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name+"%");
			jpql.append(" and t.name like?"+params.size());
		}
		if(enable!=null){
			params.add(enable);
			jpql.append(" and t.enable=?"+params.size());
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
		return infoCollectionCampaignDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	

    public List<InfoCollectionCampaign> findAll(){
    	return infoCollectionCampaignDao.findAll("from InfoCollectionCampaign", null);
    }
    
    
    public List<InfoCollectionCampaign> findAll(CampaignDataSendFrequency dataSendFrequency){
    	return infoCollectionCampaignDao.findAll("from InfoCollectionCampaign where dataSendFrequency=?1", dataSendFrequency);
    }

}
