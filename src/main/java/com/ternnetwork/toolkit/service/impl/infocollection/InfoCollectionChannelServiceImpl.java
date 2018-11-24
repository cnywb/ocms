package com.ternnetwork.toolkit.service.impl.infocollection;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionChannelDao;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionDataDao;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionChannel;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionChannelService;

@Service("infoCollectionChannelService")
public class InfoCollectionChannelServiceImpl implements InfoCollectionChannelService {

	
	
	@Resource
	private InfoCollectionChannelDao infoCollectionChannelDao;
	
	@Resource
	private InfoCollectionDataDao infoCollectionDataDao;
	
	
    public BaseResponse idoAdd(InfoCollectionChannel t){
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
       
       	long totalCount=infoCollectionChannelDao.getTotalCount("select count(t.id) from InfoCollectionChannel t where t.code=?1",t.getCode());
       	if(totalCount>0L){
       		res.setStatus(2);
       		res.setMessage("操作失败，代码已在系重中存在！");
    		return res;
       	}
       	infoCollectionChannelDao.persist(t);
       	res.setStatus(3);
       	res.setMessage("操作成功！");
		return res;
    }
	
    
    public BaseResponse idoUpdate(InfoCollectionChannel t){
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
       	long totalCount=infoCollectionChannelDao.getTotalCount("select count(t.id) from InfoCollectionChannel t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
       	if(totalCount>0L){
       		res.setStatus(2);
       		res.setMessage("操作失败，活动代码已在系重中存在！");
    		return res;
       	}
       	infoCollectionChannelDao.saveOrUpdate(t);
     	res.setStatus(3);
       	res.setMessage("操作成功！");
		return res;
    }
	
    public BaseResponse idoDelete(Long id){
    	BaseResponse res=new BaseResponse();
    	InfoCollectionChannel t=infoCollectionChannelDao.findById(InfoCollectionChannel.class, id, LockModeType.PESSIMISTIC_WRITE);
        if(t==null){
          	res.setStatus(0);
           	res.setMessage("操作失败，对象不存在！");
    		return res;
        }
       	long totalCount=infoCollectionDataDao.getTotalCount("select count(t.id) from InfoCollectionData t where t.channel.id=?1",t.getId());
       	if(totalCount>0L){
       		res.setStatus(1);
           	res.setMessage("操作失败，对象已被数据引用！");
           	return res;
       	}
       	infoCollectionChannelDao.delete(t);
       	res.setStatus(2);
       	res.setMessage("操作成功！");
       	return res;
    }
    
	
	
	public BootstrapGrid queryToBootstrapGrid(String code,String name,Page page){
		Page<InfoCollectionChannel> result=query(code, name,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid;
	}
    
    public Page<InfoCollectionChannel> query(String code,String name,Page page){
	    StringBuffer jpql=new StringBuffer("from InfoCollectionChannel t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name);
			jpql.append(" and t.name=?"+params.size());
		}
		
		jpql.append(" order by t.id desc");
		return infoCollectionChannelDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
    
    public List<InfoCollectionChannel>findAll(){
    	return infoCollectionChannelDao.findAll("from InfoCollectionChannel", null);
    }
	
}
