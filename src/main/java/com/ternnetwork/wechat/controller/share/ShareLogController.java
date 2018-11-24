package com.ternnetwork.wechat.controller.share;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.wechat.model.share.ShareLog;
import com.ternnetwork.wechat.service.share.ShareLogService;

@Controller
@RequestMapping("/wechat/share/log/*")
public class ShareLogController {
	
	
	@Resource
	private ShareLogService shareLogService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/wechat/share/log_manage.jsp");
	}
	
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,ShareLog t){
		return shareLogService.idoAdd(t);
	}
	

	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String userName,String startTime,String endTime,String campaign){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(shareLogService.queryToJsonStr(userName, startTime, endTime,campaign,page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

}
