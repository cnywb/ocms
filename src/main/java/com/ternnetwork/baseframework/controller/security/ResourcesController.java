package com.ternnetwork.baseframework.controller.security;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.baseframework.enums.ResourcesType;
import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.model.security.Resources;
import com.ternnetwork.baseframework.model.security.Role;
import com.ternnetwork.baseframework.service.security.ResourcesService;
import com.ternnetwork.baseframework.service.security.RoleService;
import com.ternnetwork.baseframework.util.MvcRequestMappingUtils;
import com.ternnetwork.cms.service.content.ContentService;
import com.ternnetwork.cms.service.page.PageService;




@Controller
@RequestMapping("/baseframework/security/resources/*")
public class ResourcesController {
	
	@Resource
	private ResourcesService resourcesService;
	
	@Resource
	private RoleService roleService ;
	
	@Resource
	private PageService pageService;
	@Resource
	private ContentService  contentService;
	
	@RequestMapping("menuManage.htm")
	public ModelAndView menuManage(){
		String[] packagesToScan={"com.ternnetwork","com.fafc"};
		List<String> mvcRequestMappingList=MvcRequestMappingUtils.getMvcRequestMappingList(packagesToScan);
		mvcRequestMappingList.addAll(pageService.findAllUrl());
		mvcRequestMappingList.addAll(contentService.findAllUrl());
		List<Role> list=roleService.findAll("from Role",null);
		return new ModelAndView("/WEB-INF/view/baseframework/security/menu_manage.jsp").addObject("roleList",list).addObject("mvcRequestMappingList", mvcRequestMappingList);
	}
	
	@RequestMapping("functionManage.htm")
	public ModelAndView functionManage(){
		String[] packagesToScan={"com.ternnetwork","com.fafc"};
		List<String> mvcRequestMappingList=MvcRequestMappingUtils.getMvcRequestMappingList(packagesToScan);
		mvcRequestMappingList.addAll(pageService.findAllUrl());
		mvcRequestMappingList.addAll(contentService.findAllUrl());
		List<Role> list=roleService.findAll("from Role",null);
		return new ModelAndView("/WEB-INF/view/baseframework/security/function_manage.jsp").addObject("roleList",list).addObject("mvcRequestMappingList", mvcRequestMappingList);
	}
	
	@RequestMapping("getTreeJSON.htm")
	public void getResourcesTreeJSON(PrintWriter out,HttpServletResponse response,RoleType roleType,ResourcesType resourcesType){
		try{
			response.setContentType("text/javascript");
			out.print(resourcesService.getTreeJSON(roleType,resourcesType));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getUserMenuTreeJSON.htm")
	public void getUserMenuTreeJSON(PrintWriter out,HttpServletResponse response){
		try{
			response.setContentType("text/javascript");
			out.print(resourcesService.getUserMenuTreeJSON());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,Resources resources){
		try{
			response.setContentType("text/javascript");
			
			out.print(resourcesService.idoAdd(resources));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,Resources resources){
		try{
			response.setContentType("text/javascript");
			out.print(resourcesService.idoUpdate(resources));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
			out.print(resourcesService.idoDeleteById(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}

}
