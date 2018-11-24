package com.ternnetwork.wechat.controller.user;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.wechat.service.user.GroupService;


@Controller@Scope("prototype")
@RequestMapping("/wechat/user/*")
public class GroupController {
	
	@Resource
	private GroupService groupService;

	
	
	@RequestMapping(value="dyGroupManage.htm",method=RequestMethod.GET)
	public ModelAndView qyMessageSend(){	
		return new ModelAndView("/WEB-INF/view/wechat/dy/user/group_manage.jsp");
	}
	
	
	@RequestMapping(value="fwGroupManage.htm",method=RequestMethod.GET)
	public ModelAndView fwMessageSend(){	
		return new ModelAndView("/WEB-INF/view/wechat/fw/user/group_manage.jsp");
	}
	
	@RequestMapping(value="queryDyGroup.htm")
	public void queryDyGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.queryDyGroup();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="deleteDyGroup.htm")
	public void deleteDyGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.deleteDyGroup(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="createDyGroup.htm")
	public void createDyGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.createDyGroup(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="updateDyGroup.htm")
	public void updateDyGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.updateDyGroup(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
	@RequestMapping(value="queryFwGroup.htm")
	public void queryFwGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.queryFwGroup();
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="deleteFwGroup.htm")
	public void deleteFwGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.deleteFwGroup(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="createFwGroup.htm")
	public void createFwGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.createFwGroup(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="updateFwGroup.htm")
	public void updateFwGroup(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = groupService.updateFwGroup(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
}
