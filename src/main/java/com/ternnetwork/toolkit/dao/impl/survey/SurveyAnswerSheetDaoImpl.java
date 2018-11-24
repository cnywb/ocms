package com.ternnetwork.toolkit.dao.impl.survey;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.survey.SurveyAnswerSheetDao;
import com.ternnetwork.toolkit.model.survey.SurveyAnswerSheet;
@Repository("surveyAnswerSheetDao")
public class SurveyAnswerSheetDaoImpl extends IBaseDaoImpl<SurveyAnswerSheet>
		implements SurveyAnswerSheetDao {
	
	public List<SurveyAnswerSheet> findByAnswerId(Long answerId){
		String hql = "select bean from SurveyAnswerSheet bean where bean.answer.id=?1";
		List<SurveyAnswerSheet> list=findAll(hql,answerId);
		return list;
	}
	
	public void idoDeleteByAnswerId(Long answerId){
		
		List<SurveyAnswerSheet> list=findByAnswerId(answerId);
		
		for(SurveyAnswerSheet sheet:list){
			sheet=findById(SurveyAnswerSheet.class, sheet.getId(), LockModeType.PESSIMISTIC_WRITE);
		   if(sheet!=null){
			  delete(sheet);
		   }
		
		}
		
	}

}
