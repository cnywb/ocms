package com.ternnetwork.toolkit.service.dealer;

import java.util.List;



import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.dealer.City;
import com.ternnetwork.toolkit.model.dealer.Dealer;

public interface CityService {
	
	public List<City> findAll();
	public City findByTitle(List<City> list,String title);
	public City findById(Long id);
	public BaseResponse idoAdd(City t);
	public BaseResponse idoUpdate(City t);
	public BaseResponse idoDelete(long id);
	public City doAdd(String cityTitle,String provinceTitle);
	

}
