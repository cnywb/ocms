package com.ternnetwork.toolkit.dao.impl.survey;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.survey.SurveyQuestionGroupDao;
import com.ternnetwork.toolkit.model.survey.SurveyQuestionGroup;

@Repository("surveyQuestionGroupDao")
public class SurveyQuestionGroupDaoImpl extends
		IBaseDaoImpl<SurveyQuestionGroup> implements SurveyQuestionGroupDao {

}
