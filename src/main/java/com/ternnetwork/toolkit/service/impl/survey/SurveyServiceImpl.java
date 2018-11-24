package com.ternnetwork.toolkit.service.impl.survey;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.toolkit.dao.survey.SurveyDao;
import com.ternnetwork.toolkit.model.survey.Survey;
import com.ternnetwork.toolkit.service.survey.SurveyService;


@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {
	@Resource
	private SurveyDao surveyDao;

	public Survey findByCode(String code){
		String hql = "select bean from Survey bean where bean.code=?1";
		List<Survey> list=surveyDao.findAll(hql, code);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	
	public long idoAdd(Survey t){
		if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
			return 0L;
		}
		Long totalCount=surveyDao.getTotalCount("select count(t.id) from Survey t where t.code=?1",t.getCode());
		if(totalCount>0){
			return 1L;
		}
	  surveyDao.persist(t);
	  return 2L;
	}
	
	public long idoUpdate(Survey t){
		if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
			return 0L;
		}
		Long totalCount=surveyDao.getTotalCount("select count(t.id) from Survey t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(totalCount>0){
			return 1L;
		}
	  surveyDao.saveOrUpdate(t);
	  return 2L;
	}
	
	public Page<Survey> query(String code,String name,String startTime,String endTime,Boolean enable,Page page){
	    StringBuffer jpql=new StringBuffer("from Survey t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name);
			jpql.append(" and t.name=?"+params.size());
		}
		if(enable!=null){
			params.add(enable);
			jpql.append(" and t.enable=?"+params.size());
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
		return surveyDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page){
		Page<Survey> result=query(code, name, startTime, endTime, enable,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
}
