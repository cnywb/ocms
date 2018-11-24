package com.ternnetwork.toolkit.service.luckydraw;

import org.springside.modules.orm.hibernate.Page;

public interface LuckyDrawResultService {
	public String queryToJsonStr(String luckyDrawId,String code,String userName,String startTime,String endTime,Page page);

}
