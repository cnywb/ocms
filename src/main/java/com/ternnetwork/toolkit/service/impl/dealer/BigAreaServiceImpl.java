package com.ternnetwork.toolkit.service.impl.dealer;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.dao.dealer.BigAreaDao;
import com.ternnetwork.toolkit.dao.dealer.ProvinceDao;
import com.ternnetwork.toolkit.model.dealer.BigArea;
import com.ternnetwork.toolkit.service.dealer.BigAreaService;

@Service("bigAreaService")
public class BigAreaServiceImpl implements BigAreaService {

	@Resource
	private BigAreaDao bigAreaDao;
	
	@Resource
	private ProvinceDao provinceDao;
	
	public List<BigArea> findAll(){
	  return bigAreaDao.findAll("from BigArea", null);
	}
	public BigArea findById(Long id){
		return bigAreaDao.findById(BigArea.class, id);
	}
	
	public BaseResponse idoAdd(BigArea t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getTitle())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		long totalCount=bigAreaDao.getTotalCount("select count(id) from BigArea where title=?1", t.getTitle());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		bigAreaDao.persist(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoUpdate(BigArea t){
		
		BaseResponse res=new BaseResponse();
		BigArea old=bigAreaDao.findById(BigArea.class, t.getId());
		if(old==null){
			res.setStatus(0);
			res.setMessage("操作失败，对象不存在！");
			return res;
		}
		if(StringUtils.isEmpty(t.getTitle())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		long totalCount=bigAreaDao.getTotalCount("select count(id) from BigArea where title=?1 and id!=?2", t.getTitle(),t.getId());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		old.setTitle(t.getTitle());
		old.setLongitude(t.getLongitude());
		old.setLatitude(t.getLatitude());
		old.setDeleted(false);
	
		bigAreaDao.saveOrUpdate(old);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
	public BaseResponse idoDelete(long id){
		BaseResponse res=new BaseResponse();
		
		BigArea t=bigAreaDao.findById(BigArea.class, id,LockModeType.PESSIMISTIC_WRITE);
		if(t==null){
			res.setStatus(0);
			res.setMessage("操作失败，对象不存在！");
			return res;
		}
		long totalCount=provinceDao.getTotalCount("select count(id) from Province where bigArea.id=?1",id);
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，存在省份子节点，请删除子节点后再删除！");
			return res;
		}
		bigAreaDao.delete(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
	
}
