package com.ternnetwork.toolkit.service.luckydraw;

import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.luckydraw.LuckyDraw;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawResponse;



public interface LuckyDrawService {
	public long idoAdd(LuckyDraw t);
	public long idoUpdate(LuckyDraw t);
	public List<LuckyDraw> findAllByCurrentWeek(String codePrefix);
	public LuckyDrawResponse idoDraw(String code);
	public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page);
	public LuckyDrawResponse idoDraw(String code,String mobilePhoneNo);
	public LuckyDrawResponse idoDrawForWehcatUser(String code,String wechatId);
}
