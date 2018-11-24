package com.ternnetwork.toolkit.service.impl.dealer;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.dao.dealer.CityDao;
import com.ternnetwork.toolkit.dao.dealer.DealerDao;
import com.ternnetwork.toolkit.dao.dealer.ProvinceDao;
import com.ternnetwork.toolkit.model.dealer.City;
import com.ternnetwork.toolkit.model.dealer.Dealer;
import com.ternnetwork.toolkit.model.dealer.Province;
import com.ternnetwork.toolkit.service.dealer.CityService;

@Service("cityService")
public class CityServiceImpl implements CityService {

	
	@Resource
    private CityDao cityDao;
	
	@Resource
	private DealerDao dealerDao;
	@Resource
	private ProvinceDao provinceDao;
	
	public List<City> findAll(){
		return cityDao.findAll("from City",null);
	}
	
	public City findById(Long id){
		return cityDao.findById(City.class,id);
	}
	
	public City findByTitle(List<City> list,String title){
		for(City t:list){
			if(title.contains(t.getTitle())){
				t=cityDao.findById(City.class, t.getId());
				return t;
			}
		}
		
		return null;
	}
	
	
	public City doAdd(String cityTitle,String provinceTitle){
		List<Province> provinceList=provinceDao.findAll("from Province",null);
		Province province=findProvinceByTitle(provinceList,provinceTitle);
		if(province==null){
			return null;
		}
		City newCity=new City();
		newCity.setDeleted(false);
		newCity.setProvince(province);
		newCity.setTitle(cityTitle);
		cityDao.persist(newCity);
		return newCity;
	}
	
	private Province findProvinceByTitle(List<Province> provinceList,String title){
		for(Province t:provinceList){
			if(title.contains(t.getTitle())){
				return t;
			}
		}
		return null;
	}
	
	
	
	public BaseResponse idoAdd(City t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getTitle())){
			res.setStatus(0);
			res.setMessage("操作失败，名称不能为空！");
			return res;
		}
		long totalCount=cityDao.getTotalCount("select count(id) from City where title=?1", t.getTitle());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		t.setProvince(provinceDao.findById(Province.class, t.getProvince().getId()));
		cityDao.persist(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoUpdate(City t){
		BaseResponse res=new BaseResponse();
		City old=cityDao.findById(City.class,t.getId());
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
		long totalCount=cityDao.getTotalCount("select count(id) from City where title=?1 and id!=?2", t.getTitle(),t.getId());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，名称已经存在，请用其它名称！");
			return res;
		}
		old.setTitle(t.getTitle());
		old.setLongitude(t.getLongitude());
		old.setLatitude(t.getLatitude());
		old.setProvince(provinceDao.findById(Province.class, t.getProvince().getId()));
		cityDao.saveOrUpdate(old);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
	public BaseResponse idoDelete(long id){
		BaseResponse res=new BaseResponse();
		
		City t=cityDao.findById(City.class, id,LockModeType.PESSIMISTIC_WRITE);
		if(t==null){
			res.setStatus(0);
			res.setMessage("操作失败，对象不存在！");
			return res;
		}
		long totalCount=dealerDao.getTotalCount("select count(id) from Dealer where city.id=?1",id);
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，存在经销商子节点，请删除子节点后再删除！");
			return res;
		}
		t.getProvince().getList().clear();
		cityDao.delete(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
}
