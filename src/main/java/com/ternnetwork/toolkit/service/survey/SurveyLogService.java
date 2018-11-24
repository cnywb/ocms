package com.ternnetwork.toolkit.service.survey;


import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.toolkit.model.survey.SurveyLog;

public interface SurveyLogService {
	public long  getTotalCountBySurveyIdAndUserId(long surveyId,Long userId);
	public void add(SurveyLog t);
	public Page<SurveyLog> query(String surveyId,String startTime,String endTime,Page page);
	public String queryToJsonStr(String surveyId,String startTime,String endTime,Page page);
	public String queryMyLogToJsonStr(Page page);
}
