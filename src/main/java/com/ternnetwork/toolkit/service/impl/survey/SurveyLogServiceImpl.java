package com.ternnetwork.toolkit.service.impl.survey;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.toolkit.dao.survey.SurveyLogDao;
import com.ternnetwork.toolkit.model.survey.SurveyLog;
import com.ternnetwork.toolkit.service.survey.SurveyLogService;

@Service("surveyLogService")
public class SurveyLogServiceImpl implements SurveyLogService {
	
	@Resource
	private SurveyLogDao surveyLogDao;

	@Resource
	private UserService userService;
	
	
	public long  getTotalCountBySurveyIdAndUserId(long surveyId,Long userId){
	
		return surveyLogDao.getTotalCount("select count(id) from SurveyLog bean where bean.survey.id=?1 and bean.user.id=?2", surveyId,userId);
	}
	public void add(SurveyLog t) {
		surveyLogDao.persist(t);
	}
	
	public Page<SurveyLog> query(String surveyId,String startTime,String endTime,Page page){
	    StringBuffer jpql=new StringBuffer("from SurveyLog t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(surveyId)){
			params.add(Long.parseLong(surveyId));
			jpql.append(" and t.survey.id=?"+params.size());
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
		return surveyLogDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(String surveyId,String startTime,String endTime,Page page){
		Page<SurveyLog> result=query(surveyId, startTime, endTime, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Page<SurveyLog> queryMyLog(Page page){
	    StringBuffer jpql=new StringBuffer("from SurveyLog t where 1=1");
		List<Object> params=new ArrayList<Object>();
		params.add(userService.getCurrentUser().getId());
		jpql.append(" and t.user.id=?"+params.size());
		jpql.append(" order by t.id desc");
		return surveyLogDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	public String queryMyLogToJsonStr(Page page){
		Page<SurveyLog> result=queryMyLog(page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
}
