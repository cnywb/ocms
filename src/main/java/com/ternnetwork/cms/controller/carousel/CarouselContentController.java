package com.ternnetwork.cms.controller.carousel;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.cms.model.carousel.CarouselContent;
import com.ternnetwork.cms.service.carousel.CarouselContentService;


@Controller@Scope("prototype")
@RequestMapping("/cms/carousel/content/*")
public class CarouselContentController {
	
	@Resource
	private CarouselContentService carouselContentService;

	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(String carouselId){
		return new ModelAndView("/WEB-INF/view/cms/carousel/carousel_content_manage.jsp").addObject("carouselId", carouselId);
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,CarouselContent t){
		try{
			response.setContentType("text/javascript");
			out.print(carouselContentService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,CarouselContent t){
		try{
			response.setContentType("text/javascript");
			out.print(carouselContentService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
			out.print(carouselContentService.idoDeleteById(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String order,int limit, int offset,String carouselId){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(carouselContentService.queryToJsonStr(page,carouselId));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("findByCode.htm")
	public void findAllByCarouselCode(PrintWriter out,HttpServletResponse response,String carouselCode){
		try{
			response.setContentType("text/javascript");
			out.print(carouselContentService.findAllByCarouselCode(carouselCode));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
}
