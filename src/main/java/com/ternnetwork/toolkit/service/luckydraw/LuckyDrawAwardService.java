package com.ternnetwork.toolkit.service.luckydraw;


import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawAward;



public interface LuckyDrawAwardService {
	public long idoAdd(LuckyDrawAward t);
	public long idoUpdate(LuckyDrawAward t);
	public long idoDelete(long id);
	public String queryToJsonStr(String luckyDrawId,String code,String name,String startTime,String endTime,Page page);
	public String findAllByluckyDrawIdToJsonStr(Long luckyDrawId);

}
