package com.ternnetwork.toolkit.controller.survey;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.toolkit.model.survey.SurveyQuestion;
import com.ternnetwork.toolkit.model.survey.SurveyQuestionGroup;
import com.ternnetwork.toolkit.service.survey.SurveyQuestionGroupService;
import com.ternnetwork.toolkit.service.survey.SurveyQuestionService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/survey/question/*")
public class SurveyQuestionController {
	
	@Resource
	private SurveyQuestionService surveyQuestionService;
	
	@Resource
	private SurveyQuestionGroupService surveyQuestionGroupService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(Long surveyId){
		List<SurveyQuestionGroup> groupList=surveyQuestionGroupService.findAllBySurveyId(surveyId);
		return new ModelAndView("/WEB-INF/view/toolkit/survey/survey_question_manage.jsp").addObject("surveyId", surveyId).addObject("groupList", groupList);
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,Long surveyId,Long groupId){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionService.queryToJsonStr(groupId,surveyId, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("findAllBySurveyId.htm")
	public void findAllBySurveyId(PrintWriter out,HttpServletResponse response, Long surveyId){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionService.findAllQuestionAndAnswerBySurveyId(surveyId));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("findAllBySurveyCode.htm")
	public void findAllBySurveyCode(PrintWriter out,HttpServletResponse response,String code){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionService.findAllQuestionAndAnswerBySurveyCode(code));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response, SurveyQuestion t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response, SurveyQuestion t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response, Long id){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionService.idoDelete(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
