package com.ternnetwork.toolkit.controller.dealer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.model.dealer.Dealer;
import com.ternnetwork.toolkit.model.ui.DealerZtree;
import com.ternnetwork.toolkit.service.dealer.BigAreaService;
import com.ternnetwork.toolkit.service.dealer.CityService;
import com.ternnetwork.toolkit.service.dealer.DealerService;
import com.ternnetwork.toolkit.service.dealer.ProvinceService;

@Controller@Scope("prototype")
@RequestMapping("/toolkit/dealer/*")
public class DealerController {
	
	@Resource
	private DealerService dealerService;
	
	@Resource
	private CityService cityService;
	
	@Resource
	private BigAreaService bigAreaService;
	
	@Resource
	private ProvinceService provinceService;
	
	@RequestMapping("manage.htm")
	public ModelAndView manage(){
		return new ModelAndView("/WEB-INF/view/toolkit/dealer/dealer_manage.jsp");
	}
	
	@RequestMapping("manageTreeView.htm")
	public ModelAndView manageTreeView(){
		return new ModelAndView("/WEB-INF/view/toolkit/dealer/dealer_manage_tree_view.jsp").addObject("bigAreaList", bigAreaService.findAll()).addObject("provinceList", provinceService.findAll()).addObject("cityList", cityService.findAll());
	}
	
	@RequestMapping("update.htm")
	public @ResponseBody BaseResponse update(HttpServletResponse response,Dealer t){
		return dealerService.idoUpdate(t);
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse add(HttpServletResponse response,Dealer t){
		return dealerService.idoAdd(t);
	}
	
	@RequestMapping("idoUpdateLocationAndName.htm")
	public @ResponseBody BaseResponse idoUpdateLocationAndName(HttpServletResponse response,Dealer t){
		return dealerService.idoUpdateLocationAndName(t);
	}
	
	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletResponse response,Long id){
		return dealerService.idoDelete(id);
	}

	
	@RequestMapping("getZtreeList.htm")
	public @ResponseBody List<DealerZtree> getZtreeList(){
		return dealerService.getZtreeList();
	}
	
	@RequestMapping("query.htm")
	public void query(HttpServletResponse response,PrintWriter out,String sort,String order,int limit, int offset,String dealerCode,String dealerName,Boolean  addressUpdated){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(dealerService.queryToBootstrapGrid(dealerCode,dealerName, addressUpdated, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	  
	}
	
	@RequestMapping("upload.htm")
	@SuppressWarnings("unchecked")
	public void upload(@RequestParam MultipartFile[] imgFile,String dir,String imgWidth,String imgHeight,String align,String imgTitle,String localUrl,HttpServletResponse response,HttpServletRequest request){
	try{
		
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("file", "xls,xlsx");
		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			try {
				response.getWriter().println(getError("请选择文件。"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return;
		}
		if (dir == null) {
			dir = "image";
		}
		if(!extMap.containsKey(dir)){
			response.getWriter().println(getError("文件类型不正确。"));
			return;
		}
			for(int i = 0,size = imgFile.length;i<size;i++){      
					MultipartFile file = imgFile[i];      
				    try { 
				    	String responseMsg=dealerService.idoSaveFromFile(file.getInputStream()).getMessage();
				        JSONObject obj = new JSONObject();
						obj.put("error",0);
						obj.put("message",responseMsg);
						response.getWriter().println(obj.toString());
					    } catch (Exception e) {      
				    	response.getWriter().print(getError("上传出错！"));
				        e.printStackTrace();      
				    }          
				}
        } catch (Exception e) {      
	    	
	        e.printStackTrace(); 
        }
	}
	
	
	

	
	@SuppressWarnings("unchecked")
	private String getError(String message) {
		JSONObject obj = new JSONObject();
	
		try {
			obj.put("error", 1);
			obj.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}

}
