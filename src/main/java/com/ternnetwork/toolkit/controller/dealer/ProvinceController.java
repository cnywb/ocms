package com.ternnetwork.toolkit.controller.dealer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.dealer.Province;
import com.ternnetwork.toolkit.service.dealer.ProvinceService;


@Controller@Scope("prototype")
@RequestMapping("/toolkit/dealer/province/*")
public class ProvinceController {
	
	@Resource
	private ProvinceService provinceService;
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,Province t){
		return provinceService.idoUpdate(t);
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,Province t){
		return provinceService.idoAdd(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,Long id){
		return provinceService.idoDelete(id);
	}

}
