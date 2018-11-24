package com.ternnetwork.toolkit.controller.survey;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ternnetwork.toolkit.model.survey.SurveyAnswerSheetRequest;
import com.ternnetwork.toolkit.service.survey.SurveyAnswerSheetService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/survey/answer/sheet/*")
public class SurveyAnswerSheetController {
	
	@Resource
	private SurveyAnswerSheetService surveyAnswerSheetSevice;
	
	
	
	/**
	 * 提交问卷结果
	 * @param out
	 * @param response
	 * @param answerSheetRequest
	 * @param request
	 */
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,SurveyAnswerSheetRequest answerSheetRequest,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyAnswerSheetSevice.idoAdd(answerSheetRequest, request));
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
