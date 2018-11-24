package com.ternnetwork.toolkit.service.survey;


import java.util.List;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.toolkit.model.survey.SurveyQuestion;

public interface SurveyQuestionService {
	
	public List<SurveyQuestion> findAllQuestionAndAnswerByGroupId(Long groupId);
	
	public List<SurveyQuestion> findAllQuestionAndAnswerBySurveyId(Long surveyId);
	
	public List<SurveyQuestion> findAllQuestionAndAnswerBySurveyCode(String code);
	
	public long idoAdd(SurveyQuestion t);
	
	public long idoUpdate(SurveyQuestion t);
	
	public Page<SurveyQuestion> query(Long groupId,Long surveyId,Page page);

	public String queryToJsonStr(Long groupId,Long surveyId,Page page);
	
	public Long idoDelete(Long id);

}
