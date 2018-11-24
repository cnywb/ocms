package com.ternnetwork.toolkit.dao.impl.survey;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.survey.SurveyLogDao;
import com.ternnetwork.toolkit.model.survey.SurveyLog;

@Repository("surveyLogDao")
public class SurveyLogDaoImpl extends IBaseDaoImpl<SurveyLog> implements
		SurveyLogDao {

}
