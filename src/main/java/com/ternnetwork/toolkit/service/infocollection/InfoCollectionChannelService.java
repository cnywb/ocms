package com.ternnetwork.toolkit.service.infocollection;


import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionChannel;

public interface InfoCollectionChannelService {
	    public BaseResponse idoAdd(InfoCollectionChannel t);
	    public BaseResponse idoUpdate(InfoCollectionChannel t);
	    public BaseResponse idoDelete(Long id);
		public BootstrapGrid queryToBootstrapGrid(String code,String name,Page page);
	    public Page<InfoCollectionChannel> query(String code,String name,Page page);
	    public List<InfoCollectionChannel>findAll();
}
