package com.ternnetwork.toolkit.controller.dealer;

import java.io.PrintWriter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.dealer.BigArea;
import com.ternnetwork.toolkit.service.dealer.BigAreaService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/dealer/bigarea/*")
public class BigAreaController {
	
	
	@Resource
	private BigAreaService bigAreaService;
	
	@RequestMapping("findAll.htm")
	public void findAll(PrintWriter out,HttpServletResponse response){
		try{
			response.setContentType("text/javascript");
			out.print(bigAreaService.findAll());
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("findAllJSONP.htm")
	public void doDrawForWehcatUser(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String wechatId,String code){
	    try{
	    	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	        String result=bigAreaService.findAll().toString();
	        out.print(jsonpcallback+"("+result+")");
		}catch(Exception e){
			e.printStackTrace();
			out.print(jsonpcallback+"(System Exception)");
		}
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,BigArea t){
		return bigAreaService.idoUpdate(t);
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,BigArea t){
		return bigAreaService.idoAdd(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletResponse response,Long id){
		return bigAreaService.idoDelete(id);
	}

}
