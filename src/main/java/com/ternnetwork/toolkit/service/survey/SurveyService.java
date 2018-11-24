package com.ternnetwork.toolkit.service.survey;


import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.survey.Survey;

public interface SurveyService {
	public Survey findByCode(String code);
	public long idoAdd(Survey t);
	
	public long idoUpdate(Survey t);
	
	public Page<Survey> query(String code,String name,String startTime,String endTime,Boolean enable,Page page);
	
	public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page);
}
