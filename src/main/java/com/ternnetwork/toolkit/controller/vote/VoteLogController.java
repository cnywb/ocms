package com.ternnetwork.toolkit.controller.vote;

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
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionDataRequest;
import com.ternnetwork.toolkit.model.vote.VoteLogRequest;
import com.ternnetwork.toolkit.service.vote.VoteLogService;



@Controller@Scope("prototype")
@RequestMapping("/toolkit/vote/log/*")
public class VoteLogController {
	
	@Resource
	private VoteLogService  voteLogService;
	
	@Resource
	private UserService userService;

	
	@RequestMapping("queryMyLog.htm")
	public void queryMyLog(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(voteLogService.queryMyLogToJsonStr(page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,VoteLogRequest t){
		try{
			response.setContentType("text/javascript");
		    out.print(voteLogService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	

	@RequestMapping("addByWechatForPotentialCustomerUserJSONP.htm")
	public void addByWechatForPotentialCustomerUserJSONP(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String jsonStr){
	       	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	       	VoteLogRequest voteLogRequest=JSONUtils.jsonToObject(jsonStr, VoteLogRequest.class);
	      	userService.idoAddByWechat(voteLogRequest.getWechatId(),"潜客用户",voteLogRequest.getRealName(), voteLogRequest.getMobilePhoneNo());
			String result=voteLogService.idoAddByWechat(voteLogRequest).toString();
	        out.print(jsonpcallback+"("+result+")");
    }
	
	@RequestMapping("addByWechatForOwnerUserJSONP.htm")
	public void addAddByWechatForOwnerUserJSONP(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String jsonStr){
	       	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	       	VoteLogRequest voteLogRequest=JSONUtils.jsonToObject(jsonStr, VoteLogRequest.class);
	      	userService.idoAddByWechat(voteLogRequest.getWechatId(),"车主用户",voteLogRequest.getRealName(), voteLogRequest.getMobilePhoneNo());
			String result=voteLogService.idoAddByWechat(voteLogRequest).toString();
	        out.print(jsonpcallback+"("+result+")");
    }
	
	@RequestMapping("addByWechatForPotentialCustomerUser.htm")
	public void addByWechatForPotentialCustomerUser(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonStr){
	       	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	       	VoteLogRequest voteLogRequest=JSONUtils.jsonToObject(jsonStr, VoteLogRequest.class);
	      	userService.idoAddByWechat(voteLogRequest.getWechatId(),"潜客用户",voteLogRequest.getRealName(), voteLogRequest.getMobilePhoneNo());
			String result=voteLogService.idoAddByWechat(voteLogRequest).toString();
	        out.print(result);
    }
	
	@RequestMapping("addByWechatForOwnerUser.htm")
	public void addAddByWechatForOwnerUser(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonStr){
	       	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	       	VoteLogRequest voteLogRequest=JSONUtils.jsonToObject(jsonStr, VoteLogRequest.class);
	      	userService.idoAddByWechat(voteLogRequest.getWechatId(),"车主用户",voteLogRequest.getRealName(), voteLogRequest.getMobilePhoneNo());
			String result=voteLogService.idoAddByWechat(voteLogRequest).toString();
	        out.print(result);
    }
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
