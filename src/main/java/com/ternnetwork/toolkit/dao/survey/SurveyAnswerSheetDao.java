package com.ternnetwork.toolkit.dao.survey;

import java.util.List;

import com.ternnetwork.baseframework.dao.base.IBaseDao;
import com.ternnetwork.toolkit.model.survey.SurveyAnswerSheet;



public interface SurveyAnswerSheetDao extends IBaseDao<SurveyAnswerSheet>{
	
	public List<SurveyAnswerSheet> findByAnswerId(Long answerId);
	public void idoDeleteByAnswerId(Long answerId);

}
