package com.ternnetwork.toolkit.service.impl.survey;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.dao.survey.SurveyAnswerDao;
import com.ternnetwork.toolkit.dao.survey.SurveyAnswerSheetDao;
import com.ternnetwork.toolkit.model.survey.SurveyAnswer;
import com.ternnetwork.toolkit.service.survey.SurveyAnswerService;

@Service("surveyAnswerService")
public class SurveyAnswerServiceImpl implements SurveyAnswerService {

	@Resource
	private SurveyAnswerDao surveyAnswerDao;
	
	@Resource
	private SurveyAnswerSheetDao surveyAnswerSheetDao;
	
	
	
	public List<SurveyAnswer> findAllByQuestionId(Long questionId){
		String hql = "select bean from SurveyAnswer bean where bean.question.id=?1";
		List<SurveyAnswer> list=surveyAnswerDao.findAll(hql, questionId);
		for(SurveyAnswer t:list){
		    Long sheetCount=surveyAnswerSheetDao.getTotalCount("select count(id) from SurveyAnswerSheet where answer.id=?1", t.getId());
			t.setSheetCount(sheetCount);
		}
		
		return list;
	}
	
	
	public long idoAdd(SurveyAnswer t){
		long totalCount=surveyAnswerDao.getTotalCount("select count(t.id) from SurveyAnswer t where t.code=?1",t.getCode());
		if(totalCount>0L){
			return 0L;
		}
		surveyAnswerDao.persist(t);
		return 1L;
	}
	public void doDeleteAllByQuestionId(Long questionId){
		 List<SurveyAnswer> list=findAllByQuestionId(questionId);
		 for(SurveyAnswer t:list){
			 t=surveyAnswerDao.findById(SurveyAnswer.class, t.getId(), LockModeType.PESSIMISTIC_WRITE);
			 surveyAnswerSheetDao.idoDeleteByAnswerId(t.getId());
			 surveyAnswerDao.delete(t);
		 }
	}

	public Long idoDeleteById(Long id){
		SurveyAnswer t=surveyAnswerDao.findById(SurveyAnswer.class, id, LockModeType.PESSIMISTIC_WRITE);
		if(t==null){
			return 0L;
		}
		if(t.getQuestion().getGroup().getSurvey().getEnable()==true){
			return 1L;
		}
		
		surveyAnswerSheetDao.idoDeleteByAnswerId(t.getId());
	    surveyAnswerDao.delete(t);
		return 2L;
	}
	
	public long idoUpdate(SurveyAnswer t){
		long totalCount=surveyAnswerDao.getTotalCount("select count(t.id) from SurveyAnswer t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(totalCount>0L){
			return 0L;
		}
		surveyAnswerDao.saveOrUpdate(t);
		return 1L;
	}
	
	public Page<SurveyAnswer> query(Long questionId,Page page){
	    StringBuffer jpql=new StringBuffer("from SurveyAnswer t where 1=1");
		List<Object> params=new ArrayList<Object>();
    	if(questionId!=null){
			params.add(questionId);
			jpql.append(" and t.question.id=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return surveyAnswerDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}

	
	public String queryToJsonStr(Long questionId,Page page){
		Page<SurveyAnswer> result=query(questionId, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
}
