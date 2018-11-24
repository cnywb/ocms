package com.ternnetwork.toolkit.service.impl.luckydraw;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawResultDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawResult;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawResultService;



@Service("luckyDrawResultService")
public class LuckyDrawResultServiceImpl implements LuckyDrawResultService {
	
	@Resource
	private LuckyDrawResultDao luckyDrawResultDao;

	public Page<LuckyDrawResult> query(String luckyDrawId,String code,String userName,String startTime,String endTime,Page page){
	    StringBuffer jpql=new StringBuffer("from LuckyDrawResult t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(luckyDrawId)){
			params.add(Long.parseLong(luckyDrawId));
			jpql.append(" and t.award.luckyDraw.id=?"+params.size());
		}
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.award.luckyDraw.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(userName)){
			params.add(userName);
			jpql.append(" and t.user.name=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(startTime)){
			params.add(DateUtils.parseDate(startTime+" 00:00:00",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime>=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(endTime)){
			params.add(DateUtils.parseDate(endTime+" 23:59:59",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime<=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return luckyDrawResultDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(String luckyDrawId,String code,String userName,String startTime,String endTime,Page page){
		Page<LuckyDrawResult> result=query(luckyDrawId,code, userName, startTime, endTime, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
}
