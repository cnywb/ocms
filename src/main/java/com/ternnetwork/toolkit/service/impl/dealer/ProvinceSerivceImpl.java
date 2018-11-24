package com.ternnetwork.toolkit.service.impl.dealer;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.dao.dealer.BigAreaDao;
import com.ternnetwork.toolkit.dao.dealer.CityDao;
import com.ternnetwork.toolkit.dao.dealer.ProvinceDao;
import com.ternnetwork.toolkit.model.dealer.BigArea;
import com.ternnetwork.toolkit.model.dealer.Province;
import com.ternnetwork.toolkit.service.dealer.ProvinceService;


@Service("provinceService")
public class ProvinceSerivceImpl implements ProvinceService {
	
	@Resource
	private ProvinceDao provinceDao;
	
	@Resource
	private BigAreaDao bigAreaDao;
	
	@Resource
	private CityDao cityDao;
	
	public Province findById(Long id){
		return provinceDao.findById(Province.class, id);
	}
	
	public List<Province> findAll(){
		return provinceDao.findAll("from Province", null);
	}
	
	public BaseResponse idoAdd(Province t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getTitle())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		long totalCount=provinceDao.getTotalCount("select count(id) from Province where title=?1", t.getTitle());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		t.setBigArea(bigAreaDao.findById(BigArea.class, t.getBigArea().getId()));
		provinceDao.persist(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoUpdate(Province t){
		BaseResponse res=new BaseResponse();
		Province old=provinceDao.findById(Province.class, t.getId());
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
		long totalCount=provinceDao.getTotalCount("select count(id) from Province where title=?1 and id!=?2", t.getTitle(),t.getId());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		old.setTitle(t.getTitle());
		old.setLongitude(t.getLongitude());
		old.setLatitude(t.getLatitude());
		old.setBigArea(bigAreaDao.findById(BigArea.class, t.getBigArea().getId()));
		provinceDao.saveOrUpdate(old);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
	public BaseResponse idoDelete(long id){
		BaseResponse res=new BaseResponse();
		
		Province t=provinceDao.findById(Province.class, id,LockModeType.PESSIMISTIC_WRITE);
		if(t==null){
			res.setStatus(0);
			res.setMessage("操作失败，对象不存在！");
			return res;
		}
		long totalCount=cityDao.getTotalCount("select count(id) from City where province.id=?1",id);
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，存在城市子节点，请删除子节点后再删除！");
			return res;
		}
		t.getBigArea().getList().clear();
		provinceDao.delete(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	

}
