package com.ternnetwork.wechat.service.share;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.wechat.model.share.ShareLog;

public interface ShareLogService {
	
	public BaseResponse idoAdd(ShareLog t);
	public Page<ShareLog> query(String userName,String startTime,String endTime,String campaign,Page page);
	public String queryToJsonStr(String userName,String startTime,String endTime,String campaign,Page page);
	public BootstrapGrid queryToBootstrapGrid(String userName,String startTime,String endTime,String campaign,Page page);

}
