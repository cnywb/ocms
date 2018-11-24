package com.ternnetwork.toolkit.service.impl.luckydraw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawLogDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDraw;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawLog;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawLogService;



@Service("luckyDrawLogService")
public class LuckyDrawLogServiceImpl implements LuckyDrawLogService {
	
	@Resource
	private LuckyDrawLogDao luckyDrawLogDao;

	
	public Page<LuckyDrawLog> query(String luckyDrawId,String code,String userName,String startTime,String endTime,Page page){
	    StringBuffer jpql=new StringBuffer("from LuckyDrawLog t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(luckyDrawId)){
			params.add(Long.parseLong(luckyDrawId));
			jpql.append(" and t.luckyDraw.id=?"+params.size());
		}
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.luckyDraw.code=?"+params.size());
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
		return luckyDrawLogDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(String luckyDrawId,String code,String userName,String startTime,String endTime,Page page){
		Page<LuckyDrawLog> result=query(luckyDrawId,code, userName, startTime, endTime, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public BaseResponse countByCurrentWeek(String wechatId){
		Date startTime=DateUtils.getMonday(new Date());
		Date endTime=DateUtils.getSunday(new Date());
		String startTimeStr=DateUtils.format(startTime, DateUtils.FORMAT_DATE_DEFAULT)+" 00:00:00";
		String endTimeStr=DateUtils.format(endTime, DateUtils.FORMAT_DATE_DEFAULT)+" 23:59:59";
		startTime=DateUtils.parseDate(startTimeStr, DateUtils.FORMAT_DATE_TIME_DEFAULT);
		endTime=DateUtils.parseDate(endTimeStr, DateUtils.FORMAT_DATE_TIME_DEFAULT);
		Long totalCount=luckyDrawLogDao.getTotalCount("select count(id) from LuckyDrawLog where createTime>=?1 and createTime<=?2 and user.wechatId=?3", startTime,endTime,wechatId);
		BaseResponse res=new BaseResponse();
		res.setData("");
		res.setStatus(1);
		res.setMessage(totalCount+"");
		return res;
	}
	
}
