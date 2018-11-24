package com.ternnetwork.toolkit.service.survey;

import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.toolkit.model.survey.SurveyAnswer;

public interface SurveyAnswerService {
	
	public List<SurveyAnswer> findAllByQuestionId(Long questionId);
	public long idoAdd(SurveyAnswer t);
	public long idoUpdate(SurveyAnswer t);
	public Page<SurveyAnswer> query(Long questionId,Page page);
	public String queryToJsonStr(Long questionId,Page page);
	
	public void doDeleteAllByQuestionId(Long questionId);
	
	public Long idoDeleteById(Long id);

}
