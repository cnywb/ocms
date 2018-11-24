package com.ternnetwork.toolkit.enums;

public enum SurveyQuestionType {
	REDIO{public String getName(){return "单选";}},
	CHECK_BOX{public String getName(){return "多选";}},
	TEXT{public String getName(){return "文本字段";}};
	public abstract String getName();
}
