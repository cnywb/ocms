package com.ternnetwork.toolkit.service.dealer;

import java.util.List;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.dealer.Province;

public interface ProvinceService {
	
	public List<Province> findAll();
	public Province findById(Long id);
	public BaseResponse idoAdd(Province t);
	public BaseResponse idoUpdate(Province t);
	public BaseResponse idoDelete(long id);

}
