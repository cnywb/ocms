package com.ternnetwork.toolkit.controller.survey;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.ternnetwork.toolkit.model.survey.SurveyQuestionGroup;
import com.ternnetwork.toolkit.service.survey.SurveyQuestionGroupService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/survey/group/*")
public class SurveyQuestionGroupController{
	
	@Resource
	private SurveyQuestionGroupService surveyQuestionGroupService;

	
	@RequestMapping("manage.htm")
	public ModelAndView manage(String surveyId){
		return new ModelAndView("/WEB-INF/view/toolkit/survey/survey_group_manage.jsp").addObject("surveyId", surveyId);
	}
	
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,Long surveyId){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionGroupService.queryToJsonStr(surveyId, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("findAll.htm")
	public void findAll(PrintWriter out,HttpServletResponse response, Long surveyId){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionGroupService.findAllBySurveyId(surveyId));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response, SurveyQuestionGroup t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionGroupService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response, SurveyQuestionGroup t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyQuestionGroupService.idoUpdate(t));
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
