package com.ternnetwork.baseframework.controller.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ternnetwork.baseframework.model.http.KindEditorFileResponse;
import com.ternnetwork.baseframework.model.ui.KindEditorFile;
import com.ternnetwork.baseframework.service.config.ClusterService;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.PropUtils;



@Controller@Scope("prototype")
@RequestMapping("/baseframework/util/editor/*")
public class EditorController {
	
	@Resource
	private UserService userService;
	@Resource
	private ClusterService clusterService;
	
	
	@RequestMapping("upload.htm")
	@SuppressWarnings("unchecked")
	public void upload(@RequestParam MultipartFile[] imgFile,String dir,String imgWidth,String imgHeight,String align,String imgTitle,String localUrl,HttpServletResponse response,HttpServletRequest request){
		 
		try{
		
		long userId=userService.getCurrentUser().getId();
		
      
		String savePath = request.getSession().getServletContext().getRealPath("/") + PropUtils.getPropertyValue("editor.properties","attached_save_path")+"/";

		//文件保存目录URL
	    String baseContextPath=request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath();
		
		String saveUrl=baseContextPath+PropUtils.getPropertyValue("editor.properties","attached_save_path")+"/";

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = 1000000;

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
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
				response.getWriter().println(getError("上传目录不存在。"));
				return;
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			response.getWriter().println(getError("上传目录没有写权限。"));
			return;
		}
		if (dir == null) {
			dir = "image";
		}
		if(!extMap.containsKey(dir)){
			response.getWriter().println(getError("目录名不正确。"));
			return;
		}
		//创建文件夹
		savePath += dir +"/"+userId+"/";
		saveUrl += dir +"/"+userId+"/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		String ymd = DateUtils.format(new Date(),DateUtils.FORMAT_DATE_YYYYMMDD);
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
				BufferedInputStream bis = null;      
				BufferedOutputStream bos = null; 
				for(int i = 0,size = imgFile.length;i<size;i++){      
					MultipartFile file = imgFile[i];      
				    try { 
				    	/*if(file.getTotalSpace()>maxSize){
				    		try {
								response.getWriter().println(getError("上传文件大小超过限制。"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    	}*/
				    	String uploadFileName=file.getOriginalFilename();
				    	if(!checkFileType(uploadFileName, extMap.get(dir))){
				    		response.getWriter().print(getError("不允许上传该文件类型！"));
				    		return;
				    	}
				    	String fileFullName =DateUtils.format(new Date(), DateUtils.FORMAT_DATE_TIME_YYYYMMDDHHMMSS)+"_"+ new Random().nextInt(1000)+"_";
				        bis = new BufferedInputStream(file.getInputStream());  
				        String newFileName=fileFullName+uploadFileName;
				        bos = new BufferedOutputStream(new FileOutputStream(new File(savePath,newFileName)));      
				        Streams.copy(bis, bos, true);
				        clusterService.saveToCluster(savePath, saveUrl, newFileName);
					      
				        JSONObject obj = new JSONObject();
						obj.put("error",0);
						obj.put("url",saveUrl+newFileName);
					
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
	
	@RequestMapping("manage.htm")
	@SuppressWarnings("unchecked")
	public @ResponseBody KindEditorFileResponse manage(HttpServletRequest request){
		
		KindEditorFileResponse kindEditorFileResponse=new KindEditorFileResponse();
		
		long userId=userService.getCurrentUser().getId();
		  
		String rootPath = request.getSession().getServletContext().getRealPath("/") + PropUtils.getPropertyValue("editor.properties","attached_save_path")+"/";
	
		//文件保存目录URL
	    String baseContextPath=request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath();
	
		//文件保存目录URL
		String rootUrl=baseContextPath+PropUtils.getPropertyValue("editor.properties","attached_save_path")+"/";
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
				dirName="image";
			}
			rootPath += dirName+"/"+userId+"/";
			rootUrl += dirName +"/"+userId+"/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		//根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			path=path.replace("..", "");
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			path=path.concat("/");
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
            return kindEditorFileResponse;
		}

		//遍历目录取的文件信息
		
		List<KindEditorFile> editorFileList = new ArrayList<KindEditorFile>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				KindEditorFile lindEditorFile=new KindEditorFile();
			     String fileName = file.getName();
				if(file.isDirectory()) {
					lindEditorFile.setIs_dir(true);
					lindEditorFile.setHas_file((file.listFiles() != null));
					lindEditorFile.setFilesize(0L);
					lindEditorFile.setIs_photo(false);
					lindEditorFile.setFiletype("");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					lindEditorFile.setIs_dir(false);
					lindEditorFile.setHas_file(false);
					lindEditorFile.setFilesize(file.length());
					lindEditorFile.setIs_photo(Arrays.<String>asList(fileTypes).contains(fileExt));
					lindEditorFile.setFiletype(fileExt);
				}
				lindEditorFile.setFilename(fileName);
				lindEditorFile.setDatetime( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				editorFileList.add(lindEditorFile);
			}
		}
		if ("size".equals(order)) {
			Collections.sort(editorFileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(editorFileList, new TypeComparator());
		} else {
			Collections.sort(editorFileList, new NameComparator());
		}
		kindEditorFileResponse.setMoveup_dir_path(moveupDirPath);
		kindEditorFileResponse.setCurrent_dir_path(currentDirPath);
		kindEditorFileResponse.setCurrent_url(currentUrl);
		kindEditorFileResponse.setTotal_count(editorFileList.size());
		kindEditorFileResponse.setFile_list(editorFileList);
		return kindEditorFileResponse;
	}
	
	public class NameComparator implements Comparator {
		@SuppressWarnings("rawtypes")
		public int compare(Object a, Object b) {
			KindEditorFile hashA = (KindEditorFile)a;
			KindEditorFile hashB = (KindEditorFile)b;
			if (((Boolean)hashA.getIs_dir()) && !((Boolean)hashB.getIs_dir())) {
				return -1;
			} else if (!((Boolean)hashA.getIs_dir()) && ((Boolean)hashB.getIs_dir())) {
				return 1;
			} else {
				return ((String)hashA.getFilename()).compareTo((String)hashB.getFilename());
			}
		}
	}
	public class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			KindEditorFile hashA = (KindEditorFile)a;
			KindEditorFile hashB = (KindEditorFile)b;
			if (((Boolean)hashA.getIs_dir()) && !((Boolean)hashB.getIs_dir())) {
				return -1;
			} else if (!((Boolean)hashA.getIs_dir()) && ((Boolean)hashB.getIs_dir())) {
				return 1;
			} else {
				if (((Long)hashA.getFilesize()) > ((Long)hashB.getFilesize())) {
					return 1;
				} else if (((Long)hashA.getFilesize()) < ((Long)hashB.getFilesize())) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	public class TypeComparator implements Comparator {
		@SuppressWarnings("rawtypes")
		public int compare(Object a, Object b) {
			KindEditorFile hashA = (KindEditorFile)a;
			KindEditorFile hashB = (KindEditorFile)b;
			if (((Boolean)hashA.getIs_dir()) && !((Boolean)hashB.getIs_dir())) {
				return -1;
			} else if (!((Boolean)hashA.getIs_dir()) && ((Boolean)hashB.getIs_dir())) {
				return 1;
			} else {
				return ((String)hashA.getFiletype()).compareTo((String)hashB.getFiletype());
			}
		}
	}


	
	@SuppressWarnings("unchecked")
	private String getError(String message) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}
	
	private boolean checkFileType(String fileName,String authFileType){
		String fileType=fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length()).toLowerCase();
		if(authFileType.indexOf(fileType)==-1){
			return false;
		}
		return true;
	}
}
