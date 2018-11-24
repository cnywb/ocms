package com.ternnetwork.baseframework.controller.util;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.service.util.ImageService;




@Controller@Scope("prototype")
@RequestMapping("/baseframework/util/image/*")
public class ImageController {
	@Resource
	private ImageService imageService;
	@Resource
	private SystemParameterService systemParameterService;
	
	@RequestMapping("scaling.htm")
	public void scaling(HttpServletResponse response,HttpServletRequest request){
		try{
			ServletOutputStream out = response.getOutputStream();
			byte[] image=imageService.imageScaling(response,request);
			response.setContentLength(image.length);
	        out.write(image);
	        out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("print.htm")
	public ModelAndView print(String imgSrc,HttpServletRequest request){
		String fileUploadDomain=systemParameterService.getValueByKey("FILE_UPLOAD_DOMAIN");
		String contextPath=request.getContextPath();
		String fullUploadDomain= request.getScheme()+"://"+fileUploadDomain+":"+request.getServerPort();
		imgSrc=fullUploadDomain+imgSrc;
		ModelAndView mv=new ModelAndView("/WEB-INF/view/admin/util/image/print.jsp").addObject("fullUploadDomain",fullUploadDomain).addObject("contextPath",contextPath).addObject("imgSrc",imgSrc);
		return mv;
	}

}
