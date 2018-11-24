package com.ternnetwork.toolkit.service.dealer;

import java.util.List;



import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.dealer.BigArea;

public interface BigAreaService {
	
	public List<BigArea> findAll();
	public BigArea findById(Long id);
	public BaseResponse idoAdd(BigArea t);
	public BaseResponse idoUpdate(BigArea t);
	public BaseResponse idoDelete(long id);

}
