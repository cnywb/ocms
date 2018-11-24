package com.ternnetwork.baseframework.controller.config;

import java.io.IOException;
import java.io.PrintWriter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ternnetwork.baseframework.annotation.QuartzJobImpl;
import com.ternnetwork.baseframework.model.config.QuartzJob;
import com.ternnetwork.baseframework.model.config.QuartzJobTrigger;
import com.ternnetwork.baseframework.service.config.QuartzJobService;
import com.ternnetwork.baseframework.service.config.QuartzJobTriggerService;
import com.ternnetwork.baseframework.util.PackageClassesScaner;


@Controller@Scope("prototype")
@RequestMapping("/baseframework/config/job/*")
public class QuartzJobController {
	
	@Resource
	private QuartzJobService quartzJobService;
	
	@Resource
	private QuartzJobTriggerService quartzJobTriggerService;
	
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage() throws IOException{
		String[] packagesToScan={"com.ternnetwork"};
		PackageClassesScaner  packageClassesScaner=new PackageClassesScaner(packagesToScan,QuartzJobImpl.class);
		return new ModelAndView("/WEB-INF/view/baseframework/config/quartz_job_manage.jsp").addObject("jobClassList",packageClassesScaner.getClassesNameList());
	}
	
	@RequestMapping("addTrigger.htm")
	public void addQuartzJobTrigger(PrintWriter out,HttpServletResponse response,QuartzJobTrigger t){
		try{
			response.setContentType("text/javascript");
			out.print(quartzJobTriggerService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("updateTrigger.htm")
	public void updateQuartzJobTrigger(PrintWriter out,HttpServletResponse response,QuartzJobTrigger t){
		try{
			response.setContentType("text/javascript");
			out.print(quartzJobTriggerService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("deleteTrigger.htm")
	public void deleteQuartzJobTrigger(PrintWriter out,HttpServletResponse response,Long id){
		try{
			response.setContentType("text/javascript");
			out.print(quartzJobTriggerService.idoDelete(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("add.htm")
	public void addQuartzJob(PrintWriter out,HttpServletResponse response,QuartzJob t){
		try{
			response.setContentType("text/javascript");
			out.print(quartzJobService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void updateQuartzJob(PrintWriter out,HttpServletResponse response,QuartzJob t){
		try{
			response.setContentType("text/javascript");
			out.print(quartzJobService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void deleteQuartzJob(PrintWriter out,HttpServletResponse response,Long id){
		try{
			response.setContentType("text/javascript");
			out.print(quartzJobService.idoDelete(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	

	@RequestMapping("getZtreeJSON.htm")
	public void getQuartzZtreeJSON(PrintWriter out,HttpServletResponse response){
		try{
			response.setContentType("text/javascript");
			out.print(quartzJobService.getZtreeList());
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

}
