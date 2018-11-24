package com.ternnetwork.cms.controller.carousel;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.cms.model.carousel.Carousel;
import com.ternnetwork.cms.service.carousel.CarouselService;


@Controller@Scope("prototype")
@RequestMapping("/cms/carousel/*")
public class CarouselController {
	@Resource
	private CarouselService carouselService;

	
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		return new ModelAndView("/WEB-INF/view/cms/carousel/carousel_manage.jsp");
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,Carousel t){
		try{
			response.setContentType("text/javascript");
			out.print(carouselService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,Carousel t){
		try{
			response.setContentType("text/javascript");
			out.print(carouselService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("delete.htm")
	public void delete(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
			out.print(carouselService.idoDeleteById(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,String name){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(carouselService.queryToJsonStr(page,name));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
}
