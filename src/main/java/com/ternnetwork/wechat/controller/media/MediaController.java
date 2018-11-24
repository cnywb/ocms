package com.ternnetwork.wechat.controller.media;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.wechat.service.media.MediaService;



@Controller@Scope("prototype")
@RequestMapping("/wechat/media/*")
public class MediaController {
	
	@Resource
	private MediaService mediaService;
	
	
	@Resource
	private UserService userService;
	
	
	
	
	
	@RequestMapping("uploadQyMedia.htm")
	@SuppressWarnings("unchecked")
	public void uploadQyMedia(@RequestParam MultipartFile[] imgFile,String dir,String imgWidth,String imgHeight,String align,String imgTitle,String localUrl,HttpServletResponse response,HttpServletRequest request){
		 
		try{
		
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("voice","amr");
		extMap.put("video","mp4");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
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
				    	String responseMsg=mediaService.uploadQyMedia(file, dir);
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
	
	
	@RequestMapping("uploadDyMedia.htm")
	@SuppressWarnings("unchecked")
	public void uploadDyMedia(@RequestParam MultipartFile[] imgFile,String dir,String imgWidth,String imgHeight,String align,String imgTitle,String localUrl,HttpServletResponse response,HttpServletRequest request){
		 
		try{
		
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("thumb", "gif,jpg,jpeg,png,bmp");
		extMap.put("voice","amr");
		extMap.put("video","mp4");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
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
				    	String responseMsg=mediaService.uploadDyMedia(file, dir);
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
	

	

	
	
	//将新闻上传得到媒id
	@RequestMapping(value="uploadDyNews.htm",method=RequestMethod.POST)
	public void uploadDyNews(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = mediaService.uploadDyNews(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
	/**
	 * 将通过基础支持中的上传下载多媒体文件来得到的media_id上传换取另一种媒体id
	 * 此接口的设计有点蛋疼
	 * @param out
	 * @param response
	 * @param request
	 * @param json 
	 * { "media_id": "rF4UdIMfYK3efUfyoddYRMU50zMiRmmt_l0kszupYh_SzrcW5Gaheq05p_lHuOTQ",//传下载多媒体文件来得到的media_id
          "title": "TITLE",
          "description": "Description"
       }
       
       响应为{
  "type":"video",
  "media_id":"IhdaAQXuvJtGzwwc0abfXnzeezfO0NgPK6AQYShD8RQYMTtfzbLdBIQkQziv2XJc",
  "created_at":1398848981
       }
	 */
	@RequestMapping(value="uploadDyVideo.htm",method=RequestMethod.POST)
	public void uploadDyVideo(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = mediaService.uploadDyVideo(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
	
	
	@RequestMapping("uploadFwMedia.htm")
	@SuppressWarnings("unchecked")
	public void uploadFwMedia(@RequestParam MultipartFile[] imgFile,String dir,String imgWidth,String imgHeight,String align,String imgTitle,String localUrl,HttpServletResponse response,HttpServletRequest request){
		 
		try{
		
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("thumb", "gif,jpg,jpeg,png,bmp");
		extMap.put("voice","amr");
		extMap.put("video","mp4");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
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
				    	String responseMsg=mediaService.uploadFwMedia(file, dir);
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
	

	

	
	
	//将新闻上传得到媒id
	@RequestMapping(value="uploadFwNews.htm",method=RequestMethod.POST)
	public void uploadFwNews(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = mediaService.uploadFwNews(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	
	/**
	 * 将通过基础支持中的上传下载多媒体文件来得到的media_id上传换取另一种媒体id
	 * 此接口的设计有点蛋疼
	 * @param out
	 * @param response
	 * @param request
	 * @param json 
	 * { "media_id": "rF4UdIMfYK3efUfyoddYRMU50zMiRmmt_l0kszupYh_SzrcW5Gaheq05p_lHuOTQ",//传下载多媒体文件来得到的media_id
          "title": "TITLE",
          "description": "Description"
       }
       
       响应为{
  "type":"video",
  "media_id":"IhdaAQXuvJtGzwwc0abfXnzeezfO0NgPK6AQYShD8RQYMTtfzbLdBIQkQziv2XJc",
  "created_at":1398848981
       }
	 */
	@RequestMapping(value="uploadFwVideo.htm",method=RequestMethod.POST)
	public void uploadFwVideo(PrintWriter out,HttpServletResponse response,HttpServletRequest request,String json){
		try{
			response.setContentType("text/javascript");
			String respMessage = mediaService.uploadFwVideo(json);
			out.print(respMessage);
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping(value="downloadQyMedia.htm")
	public void downloadQyMedia(PrintWriter out,String mediaId,HttpServletResponse response,HttpServletRequest request){
	    String fileName=mediaService.downloadQyMedia(mediaId,getRealSavePath(request));
		out.print(getMediaFullUrl(request)+fileName);
	}
	
	@RequestMapping(value="downloadDyMedia.htm")
	public void downloadDyMedia(PrintWriter out,String mediaId,HttpServletResponse response,HttpServletRequest request){
	    String fileName=mediaService.downloadDyMedia(mediaId,getRealSavePath(request));
		out.print(getMediaFullUrl(request)+fileName);
	}
	
	@RequestMapping(value="downloadFwMedia.htm")
	public void downloadFwMedia(PrintWriter out,String mediaId,HttpServletResponse response,HttpServletRequest request){
	    String fileName=mediaService.downloadFwMedia(mediaId,getRealSavePath(request));
		out.print(getMediaFullUrl(request)+fileName);
	}
	
	private String getRealSavePath(HttpServletRequest request){
		long userId=userService.getCurrentUser().getId();
		String savePath = request.getSession().getServletContext().getRealPath("/") + PropUtils.getPropertyValue("editor.properties","attached_save_path")+"/";
		savePath += "image" +"/"+userId+"/";
		String ymd = DateUtils.format(new Date(),DateUtils.FORMAT_DATE_YYYYMMDD);
		savePath += ymd + "/";
		return savePath;
	}
	
	private String getMediaFullUrl(HttpServletRequest request){
		long userId=userService.getCurrentUser().getId();
	    String baseContextPath=request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath();
		String saveUrl  =baseContextPath+PropUtils.getPropertyValue("editor.properties","attached_save_path")+"/";
		saveUrl +=  "image" +"/"+userId+"/";
		String ymd = DateUtils.format(new Date(),DateUtils.FORMAT_DATE_YYYYMMDD);
		saveUrl += ymd + "/";
		return saveUrl;
	}
	
	@SuppressWarnings("unchecked")
	private String getError(String message) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}

}
