package com.ternnetwork.toolkit.service.luckydraw;


import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawRule;



public interface LuckyDrawRuleService {
	public long idoAdd(LuckyDrawRule t);
	
	public long idoUpdate(LuckyDrawRule t);
	
	public long idoDelete(long id);
	public List<LuckyDrawRule> findAllByAwardId(long  awardId);
	public String queryToJsonStr(String luckyDrawId,String code,String name,String startTime,String endTime,Page page);

}
