package com.ternnetwork.toolkit.service.impl.survey;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;


import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.toolkit.dao.survey.SurveyAnswerSheetDao;
import com.ternnetwork.toolkit.model.survey.Survey;
import com.ternnetwork.toolkit.model.survey.SurveyAnswerSheet;
import com.ternnetwork.toolkit.model.survey.SurveyAnswerSheetRequest;
import com.ternnetwork.toolkit.model.survey.SurveyLog;
import com.ternnetwork.toolkit.model.survey.SurveyResponse;
import com.ternnetwork.toolkit.service.survey.SurveyAnswerSheetService;
import com.ternnetwork.toolkit.service.survey.SurveyLogService;
import com.ternnetwork.toolkit.service.survey.SurveyService;


@Service("surveyAnswerSheetService")
public class SurveyAnswerSheetServiceImpl implements SurveyAnswerSheetService {
	
	@Resource
	private SurveyAnswerSheetDao surveyAnswerSheetDao;
	
	@Resource
	private UserService userService;
	
	@Resource
	private SurveyService surveyService;
	
	@Resource
	private SurveyLogService surveyLogService;
	
	public List<SurveyAnswerSheet> findByUserIdAndAnswerId(Long answerId,Long userId){
		String hql = "select bean from SurveyAnswerSheet bean where bean.answer.id=?1 and bean.user.id=?2";
		List<SurveyAnswerSheet> list=surveyAnswerSheetDao.findAll(hql,answerId,userId);
		return list;
	}
	
	

	
	
	public void add(SurveyAnswerSheet t){
		List<SurveyAnswerSheet> list=findByUserIdAndAnswerId(t.getAnswer().getId(), t.getUser().getId());
		if(list.size()==0){
			surveyAnswerSheetDao.persist(t);
		}
	}
	public void addBatch(List<SurveyAnswerSheet> list){
		for(SurveyAnswerSheet t:list){
		 add(t);
		}
	}
	
	/**
	 * 提交调研答案
	 * @param answerSheetRequest
	 * @param request
	 * @return
	 */
	public SurveyResponse idoAdd(SurveyAnswerSheetRequest answerSheetRequest,HttpServletRequest request){
		SurveyResponse res=new SurveyResponse();
        User user =userService.getCurrentUser();
		if(user==null){
			res.setStatus(0);
			res.setMessage("操作失败,用户未登录!");
			return res;
		}
		Survey t=surveyService.findByCode(answerSheetRequest.getSurveyCode());
		Date currentTime=new Date();
		if(t==null){
			res.setStatus(0);
			res.setMessage("操作失败,该调研不存在！");
			return res;
    	}
		if(t!=null&&(currentTime.getTime()<t.getStartTime().getTime())){
			res.setStatus(0);
			res.setMessage("操作失败,该调研还未开始！");
			return res;
		}
		if(t!=null&&(currentTime.getTime()>t.getEndTime().getTime())){
			res.setStatus(0);
			res.setMessage("操作失败,该调研已经结束！");
			return res;
		}
		if(t!=null&&t.getEnable()==false){
			res.setStatus(0);
			res.setMessage("操作失败,该调研已经停用！");
			return res;
		}
		
		Long totalCount=surveyLogService.getTotalCountBySurveyIdAndUserId(t.getId(), user.getId());
		if(totalCount>0){
			res.setStatus(0);
			res.setMessage("操作失败,当前用户已经参与过本调研,请勿重复参与！");
			return res;
		}
		List<SurveyAnswerSheet> list=answerSheetRequest.getAnswerSheetlist();
		for(SurveyAnswerSheet s:list){
			s.setUser(user);
		}
		addBatch(list);
		SurveyLog log=new SurveyLog();
		log.setSurvey(t);
		log.setUser(user);
		surveyLogService.add(log);
		res.setMessage("操作成功!");
		res.setStatus(1);
	
		return res;
		
	}






	public void idoDeleteByAnswerId(Long answerId) {
		// TODO Auto-generated method stub
		
	}

}
