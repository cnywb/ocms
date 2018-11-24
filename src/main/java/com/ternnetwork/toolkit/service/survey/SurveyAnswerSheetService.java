package com.ternnetwork.toolkit.service.survey;

import javax.servlet.http.HttpServletRequest;

import com.ternnetwork.toolkit.model.survey.SurveyAnswerSheetRequest;
import com.ternnetwork.toolkit.model.survey.SurveyResponse;

public interface SurveyAnswerSheetService {
	
	public SurveyResponse idoAdd(SurveyAnswerSheetRequest answerSheetRequest,HttpServletRequest request);

	public void idoDeleteByAnswerId(Long answerId);
}
