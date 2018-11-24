package com.ternnetwork.baseframework.controller.config;

import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ternnetwork.baseframework.model.config.ServerNode;
import com.ternnetwork.baseframework.model.config.WebAppInstance;
import com.ternnetwork.baseframework.service.config.ClusterService;


@Controller@Scope("prototype")
@RequestMapping("/baseframework/config/cluster/*")
public class ClusterController {
	
	@Resource
	private ClusterService clusterService;

	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/baseframework/config/cluster_manage.jsp");
	}
	
	@RequestMapping("addServerNode.htm")
	public void addServerNode(PrintWriter out,HttpServletResponse response,ServerNode t){
		try{
			response.setContentType("text/javascript");
			out.print(clusterService.idoAddServerNode(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("updateServerNode.htm")
	public void updateServerNode(PrintWriter out,HttpServletResponse response,ServerNode t){
		try{
			response.setContentType("text/javascript");
			out.print(clusterService.idoUpdateServerNode(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("deleteServerNode.htm")
	public void deleteServerNode(PrintWriter out,HttpServletResponse response,Long id){
		try{
			response.setContentType("text/javascript");
			out.print(clusterService.idoDeleteServerNode(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("addWebAppInstance.htm")
	public void addWebAppInstance(PrintWriter out,HttpServletResponse response,WebAppInstance t){
		try{
			response.setContentType("text/javascript");
			out.print(clusterService.idoAddWebAppInstance(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("updateWebAppInstance.htm")
	public void updateWebAppInstance(PrintWriter out,HttpServletResponse response,WebAppInstance t){
		try{
			response.setContentType("text/javascript");
			out.print(clusterService.idoUpdateWebAppInstance(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("deleteWebAppInstance.htm")
	public void deleteWebAppInstance(PrintWriter out,HttpServletResponse response,Long id){
		try{
			response.setContentType("text/javascript");
			out.print(clusterService.idoDeleteWebAppInstance(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("getZtreeJSON.htm")
	public void getClusterZtree(PrintWriter out,HttpServletResponse response){
		try{
			response.setContentType("text/javascript");
			out.print(clusterService.getZtreeList());
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
}
