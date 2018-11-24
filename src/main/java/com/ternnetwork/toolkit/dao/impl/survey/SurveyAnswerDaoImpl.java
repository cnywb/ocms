package com.ternnetwork.toolkit.dao.impl.survey;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.survey.SurveyAnswerDao;
import com.ternnetwork.toolkit.model.survey.SurveyAnswer;


@Repository("surveyAnswerDao")
public class SurveyAnswerDaoImpl extends IBaseDaoImpl<SurveyAnswer> implements
		SurveyAnswerDao {

}
