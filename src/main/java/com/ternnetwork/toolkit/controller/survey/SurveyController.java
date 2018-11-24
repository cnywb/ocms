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
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.toolkit.model.survey.Survey;
import com.ternnetwork.toolkit.service.survey.SurveyService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/survey/*")
public class SurveyController {
	@Resource
	private SurveyService surveyService;

	
	/**
	 * 活动管理页面
	 * @return
	 */
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/toolkit/survey/survey_manage.jsp");
	}
	
	
	@RequestMapping("index.htm")
	public ModelAndView index(HttpServletRequest request){
		String currentLocation=request.getRequestURL().toString();
		return new ModelAndView("/WEB-INF/view/toolkit/survey/index.jsp").addObject("currentLocation",currentLocation);
	}
	
	@RequestMapping("detail.htm")
	public ModelAndView detail(String code){
		Survey t=surveyService.findByCode(code);
		return new ModelAndView("/WEB-INF/view/toolkit/survey/survey_detail.jsp").addObject("t", t);
	}
	
	@RequestMapping("result.htm")
	public ModelAndView result(String code){
		Survey t=surveyService.findByCode(code);
		return new ModelAndView("/WEB-INF/view/toolkit/survey/survey_result.jsp").addObject("t", t);
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String code,String name,String startTime,String endTime,Boolean enable){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(surveyService.queryToJsonStr(code,name, startTime, endTime, enable,page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response, Survey t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response, Survey t){
		try{
			response.setContentType("text/javascript");
		    out.print(surveyService.idoUpdate(t));
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
