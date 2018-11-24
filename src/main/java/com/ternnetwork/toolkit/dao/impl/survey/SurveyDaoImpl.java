package com.ternnetwork.toolkit.dao.impl.survey;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.survey.SurveyDao;
import com.ternnetwork.toolkit.model.survey.Survey;

@Repository("surveyDao")
public class SurveyDaoImpl extends IBaseDaoImpl<Survey> implements SurveyDao {

}
