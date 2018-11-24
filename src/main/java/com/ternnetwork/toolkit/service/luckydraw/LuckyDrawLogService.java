package com.ternnetwork.toolkit.service.luckydraw;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;

public interface LuckyDrawLogService {
	
	public String queryToJsonStr(String luckyDrawId,String code,String userName,String startTime,String endTime,Page page);
	public BaseResponse countByCurrentWeek(String wechatId);
}
