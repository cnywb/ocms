package com.ternnetwork.toolkit.service.survey;


import java.util.List;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.toolkit.model.survey.SurveyQuestionGroup;

public interface SurveyQuestionGroupService {
	public List<SurveyQuestionGroup> findAllBySurveyId(Long surveyId);
	public long idoAdd(SurveyQuestionGroup t);
	public long idoUpdate(SurveyQuestionGroup t);
	public Page<SurveyQuestionGroup> query(Long surveyId,Page page);
	public String queryToJsonStr(Long surveyId,Page page);
}
