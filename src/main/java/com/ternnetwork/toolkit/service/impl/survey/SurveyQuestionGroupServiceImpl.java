package com.ternnetwork.toolkit.service.impl.survey;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.dao.survey.SurveyQuestionGroupDao;
import com.ternnetwork.toolkit.model.survey.SurveyQuestionGroup;
import com.ternnetwork.toolkit.service.survey.SurveyQuestionGroupService;

@Service("surveyQuestionGroupService")
public class SurveyQuestionGroupServiceImpl implements
		SurveyQuestionGroupService {
	@Resource
	private SurveyQuestionGroupDao surveyQuestionGroupDao;
	
	public List<SurveyQuestionGroup> findAllBySurveyId(Long surveyId){
		String hql = "select bean from SurveyQuestionGroup bean where bean.survey.id=?1";
		return surveyQuestionGroupDao.findAll(hql, surveyId);
	}
	
	
	public long idoAdd(SurveyQuestionGroup t){
		long totalCount=surveyQuestionGroupDao.getTotalCount("select count(t.id) from SurveyQuestionGroup t where t.code=?1",t.getCode());
		if(totalCount>0L){
			return 0L;
		}
		surveyQuestionGroupDao.persist(t);
		return 1L;
	}
	
	public long idoUpdate(SurveyQuestionGroup t){
		long totalCount=surveyQuestionGroupDao.getTotalCount("select count(t.id) from SurveyQuestionGroup t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(totalCount>0L){
			return 0L;
		}
		surveyQuestionGroupDao.saveOrUpdate(t);
		return 1L;
	}
	
	public Page<SurveyQuestionGroup> query(Long surveyId,Page page){
	    StringBuffer jpql=new StringBuffer("from SurveyQuestionGroup t where 1=1");
		List<Object> params=new ArrayList<Object>();
    	if(surveyId!=null){
			params.add(surveyId);
			jpql.append(" and t.survey.id=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return surveyQuestionGroupDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}

	
	public String queryToJsonStr(Long surveyId,Page page){
		Page<SurveyQuestionGroup> result=query(surveyId, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
}
