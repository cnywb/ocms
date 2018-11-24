package com.ternnetwork.toolkit.controller.dealer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.dealer.City;
import com.ternnetwork.toolkit.service.dealer.CityService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/dealer/city/*")
public class CityController {
	
	
	@Resource
	private CityService cityService;
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,City t){
		return cityService.idoUpdate(t);
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,City t){
		return cityService.idoAdd(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,Long id){
		return cityService.idoDelete(id);
	}

}
