package com.ternnetwork.toolkit.dao.impl.survey;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.survey.SurveyQuestionDao;
import com.ternnetwork.toolkit.model.survey.SurveyQuestion;

@Repository("surveyQuestionDao")
public class SurveyQuestionDaoImpl extends IBaseDaoImpl<SurveyQuestion>
		implements SurveyQuestionDao {

}
