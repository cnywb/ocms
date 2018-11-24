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
import com.ternnetwork.toolkit.model.survey.SurveyAnswer;
import com.ternnetwork.toolkit.service.survey.SurveyAnswerService;


@Controller@Scope("prototype")
@RequestMapping("/toolkit/survey/answer/*")
public class SurveyAnswerController {
	
	@Resource
	private SurveyAnswerService surveyAnswerService;

	
	@RequestMapping("manage.htm")
	public ModelAndView manage(Long questionId){
			return new ModelAndView("/WEB-INF/view/toolkit/survey/survey_answer_manage.jsp").addObject("questionId", questionId);
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,Long questionId){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(surveyAnswerService.queryToJsonStr(questionId,page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("findAllByQuestionId.htm")
	public void findAllByQuestionId(PrintWriter out,HttpServletResponse response,Long questionId){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyAnswerService.findAllByQuestionId(questionId));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response, SurveyAnswer t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyAnswerService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response, SurveyAnswer t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyAnswerService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyAnswerService.idoDeleteById(id));
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
