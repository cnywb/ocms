package com.ternnetwork.toolkit.model.survey;

import java.util.List;

public class SurveyAnswerSheetRequest {
	
	private String surveyCode;
	
	private List<SurveyAnswerSheet> answerSheetlist;

	public String getSurveyCode() {
		return surveyCode;
	}

	public void setSurveyCode(String surveyCode) {
		this.surveyCode = surveyCode;
	}

	public List<SurveyAnswerSheet> getAnswerSheetlist() {
		return answerSheetlist;
	}

	public void setAnswerSheetlist(List<SurveyAnswerSheet> answerSheetlist) {
		this.answerSheetlist = answerSheetlist;
	}
	
	
	
	

}
