package com.ternnetwork.baseframework.controller.security;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.service.security.RescRoleService;


@Controller
@RequestMapping("/baseframework/security/*")
public class RescRoleController {
	
	@Resource
	private RescRoleService rescRoleService;
	
	@RequestMapping("queryRoleFromRescRoleByResourcesId.htm")
	public void queryRoleFromRescRoleByResourcesId(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
			out.print(rescRoleService.queryRoleByResourcesIdToJSON(id));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("updateResourcesBatch.htm")
	public void updateResourcesBatch(PrintWriter out,HttpServletResponse response,long resourcesId,String deleteRoleIds,String addRoleIds){
		try{
			response.setContentType("text/javascript");
			rescRoleService.idoUpdateBatch(resourcesId, deleteRoleIds, addRoleIds);
			BaseResponse retResponse=new BaseResponse();
			retResponse.setStatus(1);
			retResponse.setMessage("操作成功!");
			out.print(retResponse);
		}catch(Exception e){
			e.printStackTrace();
			BaseResponse retResponse=new BaseResponse();
			retResponse.setStatus(0);
			retResponse.setMessage("操作失败,系统异常!");
			out.print(retResponse);
		}
	}

}
