package com.ternnetwork.toolkit.service.dealer;

import java.io.InputStream;
import java.util.List;

import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.dealer.Dealer;
import com.ternnetwork.toolkit.model.ui.DealerZtree;


public interface DealerService {
	
	public BaseResponse idoSaveFromFile(InputStream in);
	public BootstrapGrid queryToBootstrapGrid(String dealerCode,String dealerName,Boolean  addressUpdated,Page page);
	public List<DealerZtree>getZtreeList();
	public BaseResponse idoAdd(Dealer t);
	public BaseResponse idoUpdate(Dealer t);
	public BaseResponse idoDelete(long id);
	public BaseResponse idoUpdateLocationAndName(Dealer t);
	public Dealer findByDealerServiceCode(String dealerServiceCode);
	public Dealer findByDealerCode(String dealerCode);

}
