package com.ternnetwork.toolkit.controller.infocollection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionChannel;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionChannelService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/infocollection/channel/*")
public class InfoCollectionChannelController {
	
	@Resource
	private InfoCollectionChannelService infoCollectionChannelService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/toolkit/infocollection/channel_manage.jsp");
	}
	
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,InfoCollectionChannel t){
		return infoCollectionChannelService.idoAdd(t);
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,InfoCollectionChannel t){
		return infoCollectionChannelService.idoUpdate(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletResponse response,Long id){
		return infoCollectionChannelService.idoDelete(id);
	}
	
	
	@RequestMapping("query.htm")
	public @ResponseBody BootstrapGrid query(HttpServletResponse response,String sort,String order,int limit, int offset,String code,String name){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
	    return infoCollectionChannelService.queryToBootstrapGrid(code, name, page);
	}
	

}
