package com.ternnetwork.toolkit.service.impl.survey;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.dao.survey.SurveyQuestionDao;
import com.ternnetwork.toolkit.model.survey.SurveyQuestion;
import com.ternnetwork.toolkit.service.survey.SurveyAnswerService;
import com.ternnetwork.toolkit.service.survey.SurveyQuestionService;

@Service("surveyQuestionService")
public class SurveyQuestionServiceImpl implements SurveyQuestionService {
	
	@Resource
	private SurveyQuestionDao  surveyQuestionDao;
	
	@Resource
	private SurveyAnswerService surveyAnswerService;

	
	public List<SurveyQuestion> findAllByGroupId(Long groupId){
		String hql = "select bean from SurveyQuestion bean where bean.group.id=?1";
		List<SurveyQuestion> list=surveyQuestionDao.findAll(hql, groupId);
		return list;
	}

	
	public List<SurveyQuestion> findAllBySurveyId(Long surveyId){
		String hql = "select bean from SurveyQuestion bean where bean.group.survey.id=?1 order by bean.group.id asc,bean.sequence asc";
		List<SurveyQuestion> list=surveyQuestionDao.findAll(hql, surveyId);
		return list;
	}
	
	public List<SurveyQuestion> findAllBySurveyCode(String code){
		String hql = "select bean from SurveyQuestion bean where bean.group.survey.code=?1 order by bean.group.id asc,bean.sequence asc";
		List<SurveyQuestion> list=surveyQuestionDao.findAll(hql, code);
		return list;
	}
	
	public List<SurveyQuestion> findAllQuestionAndAnswerByGroupId(Long groupId){
		List<SurveyQuestion> list=findAllByGroupId(groupId);
		for(SurveyQuestion q:list){
			q.setAnswerList(surveyAnswerService.findAllByQuestionId(q.getId()));
		}
		return list;
	}
	
	public List<SurveyQuestion> findAllQuestionAndAnswerBySurveyId(Long surveyId){
		List<SurveyQuestion> list=findAllBySurveyId(surveyId);
		for(SurveyQuestion q:list){
			q.setAnswerList(surveyAnswerService.findAllByQuestionId(q.getId()));
		}
		return list;
	}
	
	public List<SurveyQuestion> findAllQuestionAndAnswerBySurveyCode(String code){
		List<SurveyQuestion> list=findAllBySurveyCode(code);
		for(SurveyQuestion q:list){
			q.setAnswerList(surveyAnswerService.findAllByQuestionId(q.getId()));
		}
		return list;
	}
	
	public long idoAdd(SurveyQuestion t){
		long totalCount=surveyQuestionDao.getTotalCount("select count(t.id) from SurveyQuestion t where t.code=?1",t.getCode());
		if(totalCount>0L){
			return 0L;
		}
		surveyQuestionDao.persist(t);
		return 1L;
	}
	
	public long idoUpdate(SurveyQuestion t){
		long totalCount=surveyQuestionDao.getTotalCount("select count(t.id) from SurveyQuestion t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(totalCount>0L){
			return 0L;
		}
		surveyQuestionDao.saveOrUpdate(t);
		return 1L;
	}
	
	
	public Long idoDelete(Long id){
		SurveyQuestion q=surveyQuestionDao.findById(SurveyQuestion.class, id, LockModeType.PESSIMISTIC_WRITE);
		if(q==null){
			return 0L;
		}
		if(q.getGroup().getSurvey().getEnable()==true){
			return 1L;
		}
		surveyAnswerService.doDeleteAllByQuestionId(id);
		surveyQuestionDao.delete(q);
		return 2L;
	}
	
	public Page<SurveyQuestion> query(Long groupId,Long surveyId,Page page){
	    StringBuffer jpql=new StringBuffer("from SurveyQuestion t where 1=1");
		List<Object> params=new ArrayList<Object>();
    	if(groupId!=null){
			params.add(groupId);
			jpql.append(" and t.group.id=?"+params.size());
		}
    	if(surveyId!=null){
			params.add(surveyId);
			jpql.append(" and t.group.survey.id=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return surveyQuestionDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}

	
	public String queryToJsonStr(Long groupId,Long surveyId,Page page){
		Page<SurveyQuestion> result=query(groupId, surveyId,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
}
