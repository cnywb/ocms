package com.ternnetwork.toolkit.controller.luckydraw;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.ternnetwork.baseframework.model.security.Role;
import com.ternnetwork.baseframework.service.security.RoleService;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDraw;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawService;





@Controller@Scope("prototype")
@RequestMapping("/toolkit/luckydraw/*")
public class LuckyDrawController {
	
	@Resource
	private LuckyDrawService luckyDrawService;

	@Resource
    private RoleService roleService;
	
	@Resource
	private UserService userService;
	

	@RequestMapping("index.htm")
	public ModelAndView index(HttpServletRequest request){
		String currentLocation=request.getRequestURL().toString();
		return new ModelAndView("/WEB-INF/view/toolkit/luckydraw/index.jsp").addObject("currentLocation",currentLocation);
	}
	
	/**
	 * 抽奖活动管理页面
	 * @return
	 */
	@RequestMapping("manage.htm")
	public ModelAndView luckyDrawManage(){
		List<Role> list=roleService.findAll("from Role",null);
		return new ModelAndView("/WEB-INF/view/toolkit/luckydraw/lucky_draw_manage.jsp").addObject("roleList",list);
	}
	
	
	
   
	/**
	 * 抽奖活动查询
	 * @param out
	 * @param response
	 * @param current
	 * @param rowCount
	 * @param code
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param enable
	 */
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
		    out.print(luckyDrawService.queryToJsonStr(code,name, startTime, endTime, enable,page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	/**
	 * 添加抽奖活动
	 * @param out
	 * @param response
	 * @param t
	 */
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,LuckyDraw t){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	/**
	 * 更新抽奖活动
	 * @param out
	 * @param response
	 * @param t
	 */
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,LuckyDraw t){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	/**
	 * 抽奖
	 * 只需传入活动代码
	 * @param out
	 * @param response
	 * @param code
	 */
	@RequestMapping("doDraw.htm")
	public void doDraw(PrintWriter out,HttpServletResponse response,String code){
		try{
			response.setContentType("text/javascript");
		    out.print(luckyDrawService.idoDraw(code));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("doDrawForPotentialCustomerUserJSONP.htm")//
	public void doDrawForPotentialCustomerUser(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String mobilePhoneNo,String code){
	    try{
	    
	    	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	    	Long retVal=userService.idoAddPotentialCustomerUser(mobilePhoneNo);
   	        String result=luckyDrawService.idoDraw(code, mobilePhoneNo).toString();
	        out.print(jsonpcallback+"("+result+")");
		}catch(Exception e){
			e.printStackTrace();
			out.print(jsonpcallback+"(System Exception)");
		}
	}
	
	@RequestMapping("doDrawForCarOwnerUserJSONP.htm")//
	public void doDrawForCarOwnerUserJSONP(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String mobilePhoneNo,String code){
	    try{
	      	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	    	Long retVal=userService.idoAddCarOwnerUser(mobilePhoneNo);
   	        String result=luckyDrawService.idoDraw(code, mobilePhoneNo).toString();
	        out.print(jsonpcallback+"("+result+")");
		}catch(Exception e){
			e.printStackTrace();
			out.print(jsonpcallback+"(System Exception)");
		}
	}
	
	
	@RequestMapping("findAllByCurrentWeekJSONP.htm")//
	public void findAllByCurrentWeek(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String codePrefix){
	    try{
	    	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	    	
			List<LuckyDraw> luckyDrawList=luckyDrawService.findAllByCurrentWeek(codePrefix);//获取当周活动
	        String result=luckyDrawList.toString();
	        out.print(jsonpcallback+"("+result+")");
		}catch(Exception e){
			e.printStackTrace();
			out.print(jsonpcallback+"(System Exception)");
		}
	}
	
	@RequestMapping("doDrawForWehcatUserJSONP.htm")//
	public void doDrawForWehcatUser(PrintWriter out,HttpServletRequest request,HttpServletResponse response,String jsonpcallback,String wechatId,String code){
	    try{
	    	response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
	    	userService.idoAddByWechat(wechatId,"车主用户");
	        String result=luckyDrawService.idoDrawForWehcatUser(code,wechatId).toString();
	        out.print(jsonpcallback+"("+result+")");
		}catch(Exception e){
			e.printStackTrace();
			out.print(jsonpcallback+"(System Exception)");
		}
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
