package com.ternnetwork.baseframework.controller.config;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.baseframework.dao.base.IBaseDao;


@Controller@Scope("prototype")
@RequestMapping("/baseframework/config/sql/execute/*")
public class SQLExecuteController {
	
	@Resource
	private IBaseDao iBaseDao;
	
	
	@RequestMapping("index.htm")
	public ModelAndView execute(){
		return new ModelAndView("/WEB-INF/view/baseframework/config/sql_execute.jsp");
	}
	
	@RequestMapping("bulkUpdate.htm")
	public void  bulkUpdate(PrintWriter out,HttpServletResponse response,String sql){
		try{
			response.setContentType("text/javascript");
			String[] array=sql.split(";");
			int totcalCount=0;
			for(String singleSql:array){
				if(!StringUtils.isEmpty(singleSql)){
					totcalCount=totcalCount+iBaseDao.bulkUpdateByNativeSQL(singleSql.trim(), null);
				}
			}
			out.print(totcalCount);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	

}
