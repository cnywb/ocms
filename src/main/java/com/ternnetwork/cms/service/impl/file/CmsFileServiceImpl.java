package com.ternnetwork.cms.service.impl.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.http.KindEditorFileResponse;
import com.ternnetwork.baseframework.model.ui.KindEditorFile;
import com.ternnetwork.baseframework.model.ui.KindEditorFileNameComparator;
import com.ternnetwork.baseframework.model.ui.KindEditorFileSizeComparator;
import com.ternnetwork.baseframework.model.ui.KindEditorFileTypeComparator;
import com.ternnetwork.baseframework.util.ExtendedFileUtils;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.baseframework.util.ZipUtils;

import com.ternnetwork.cms.model.file.TreeFile;
import com.ternnetwork.cms.service.file.CmsFileService;


@Service("cmsFileService")
public class CmsFileServiceImpl implements CmsFileService {
	private static final Logger log = LoggerFactory.getLogger(CmsFileServiceImpl.class);
	
	
	public void createFileDirs(HttpServletRequest request,String siteCode,String code,String module) {
		String resourcesDir=getRealPath(request,PropUtils.getPropertyValue("file_path.properties", "cms_resources_path"),siteCode,code,module);
		String templateDir=getRealPath(request,PropUtils.getPropertyValue("file_path.properties", "cms_template_path"),siteCode,code,module);
		createFileDirs(resourcesDir, templateDir);
	}


	public void createFileDirs(String resourcesDir, String templateDir) {
		ExtendedFileUtils.makeDirs(resourcesDir);//创建资源文件目录
		ExtendedFileUtils.makeDirs(templateDir);//创建模版文件目录
	}
	

	
	public void updateFileDirs(HttpServletRequest request,String siteCode,String oldCode,String newCode,String module){
		
		String cmsResourcesPath=PropUtils.getPropertyValue("file_path.properties", "cms_resources_path");
		String cmsTemplatePath=PropUtils.getPropertyValue("file_path.properties", "cms_template_path");
		
		String oldResourcesDir=getRealPath(request,cmsResourcesPath,siteCode,oldCode,module);
		String oldTemplateDir=getRealPath(request,cmsTemplatePath,siteCode,oldCode,module);
	
		String newResourcesDir=getRealPath(request,cmsResourcesPath,siteCode,newCode,module);
		String newTemplateDir=getRealPath(request,cmsTemplatePath,siteCode,newCode,module);
		
		 File oldResources=new File(oldResourcesDir);
	     File oldTemplate=new File(oldTemplateDir);
	     File newResources=new File(newResourcesDir);
	     File newTemplate=new File(newTemplateDir);
	     
	     createFileDirs(cmsResourcesPath, cmsTemplatePath);
	    
	     try {
			 copyFileDirs(oldResources, newResources, oldTemplate, newTemplate);
			 deleteFileDirs(oldResources, oldTemplate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	
	public String getRealPath(HttpServletRequest request,String basePath,String siteCode,String moduleCode,String module){
		if("channel".equals(module)){
			return request.getSession().getServletContext().getRealPath(basePath).concat("/site/").concat(siteCode).concat("/").concat(module).concat("/").concat(moduleCode).concat("/");
		}else if("site".equals(module)){
			return request.getSession().getServletContext().getRealPath(basePath).concat("/site/").concat(moduleCode).concat("/");
		}else if("index".equals(module)){
			return request.getSession().getServletContext().getRealPath(basePath).concat("/site/").concat(siteCode).concat("/").concat("/index/");
		}else if("content".equals(module)){
			return request.getSession().getServletContext().getRealPath(basePath).concat("/site/").concat(moduleCode).concat("/").concat("/content/");
		}
		return request.getSession().getServletContext().getRealPath(basePath).concat("/site/");
	}
    private void copyFileDirs(File oldResources,File newResources,File oldTemplate,File newTemplate) throws IOException{
    	ExtendedFileUtils.copyDirectory(oldResources,newResources);
		ExtendedFileUtils.copyDirectory(oldTemplate, newTemplate);
    }
    private void deleteFileDirs(File resourcesDirs,File templateDirs) throws IOException{
   		ExtendedFileUtils.deleteDirectory(resourcesDirs);
		ExtendedFileUtils.deleteDirectory(templateDirs);
    }


	
	public void createChannelFileDirs(HttpServletRequest request, String siteCode, String code) {
		createFileDirs(request, siteCode, code, "channel");
	}

	
	public void updateChannelFileDirs(HttpServletRequest request, String siteCode, String oldCode, String newCode) {
		updateFileDirs(request, siteCode, oldCode, newCode, "channel");
	}

	
	public void createSiteFileDirs(HttpServletRequest request, String code) {
		createFileDirs(request, null, code, "site");
	}


	public void updateSiteFileDirs(HttpServletRequest request,  String oldCode, String newCode) {
		updateFileDirs(request, null, oldCode, newCode, "site");
	}
	
	public List<String> getContentTemplateFileList(HttpServletRequest request,String siteCode){
		String cmsTemplatePath=PropUtils.getPropertyValue("file_path.properties", "cms_template_path");
		String realPath=getRealPath(request, cmsTemplatePath, siteCode, siteCode, "content");
		return getTemplateFileList(realPath);
	}
	
	public List<String> getChannelTemplateFileList(HttpServletRequest request,String siteCode,String channelCode){
		String cmsTemplatePath=PropUtils.getPropertyValue("file_path.properties", "cms_template_path");
		String realPath=getRealPath(request, cmsTemplatePath, siteCode, channelCode, "channel");
		return getTemplateFileList(realPath);
	}
	public List<String> getSiteIndexTemplateFileList(HttpServletRequest request,String siteCode){
		String cmsTemplatePath=PropUtils.getPropertyValue("file_path.properties", "cms_template_path");
		String realPath=getRealPath(request, cmsTemplatePath, siteCode, "", "index");
		return getTemplateFileList(realPath);
	}


	private List<String> getTemplateFileList(String realPath) {
		ExtendedFileUtils.makeDirs(realPath);
		List<String> retVal=new ArrayList<String>();
		File file = new File(realPath);
		File[] fileArray=file.listFiles();
		for(File f:fileArray){
			if(f.getName().endsWith(".html")){
				retVal.add(f.getName());
			}
		}
		return retVal;
	}
	
	/**
	 * cms文件管理页获取相对根目录
	 * @param siteCode 站点代码
	 * @param moduleCode 模块代码
	 * @param module 模块 channel/content/index
	 * @param type 文件类型  resources/template
	 * @return
	 */
	public String getRootDir(String siteCode,String moduleCode,String module, String type) {
		String templatePath=PropUtils.getPropertyValue("file_path.properties", "cms_template_path");
		String resourcesPath=PropUtils.getPropertyValue("file_path.properties", "cms_resources_path");
		String rootDir=templatePath.concat("site/").concat(siteCode).concat("/").concat(module).concat("/").concat(moduleCode);
		if("resources".equals(type)){
			rootDir=resourcesPath.concat("site/").concat(siteCode).concat("/").concat(module).concat("/").concat(moduleCode);
		}
		return rootDir;
	}
	
	public BaseResponse save(HttpServletRequest request, String file, String code) {
		  BaseResponse resp=new BaseResponse();
		  if (file==null) {
	    	   resp.setMessage("文件不能为空！");
			   resp.setStatus(0);
			   return resp;
	     } 
		 if (!file.endsWith(".js")&&!file.endsWith(".css")&&!file.endsWith(".html")) {
	    	   resp.setMessage("非法的文件类型！");
			   resp.setStatus(1);
			   return resp;
	     } 
		String realFile=request.getSession().getServletContext().getRealPath("/")+file;
		if(checkPermitPath(request, realFile)==false){
			   resp.setMessage("非法的目录！");
			   resp.setStatus(2);
			   return resp;
		}
	    try {
			   ExtendedFileUtils.writeStringToFile(new File(realFile), code, "UTF-8", false);
			   resp.setMessage("操作成功！");
			   resp.setStatus(3);
			   return resp;
		} catch (IOException e) {
			resp.setMessage(e.getMessage());
			resp.setStatus(4);
		    return resp;
		}
	}
	public BaseResponse delete(HttpServletRequest request, String file) {
		BaseResponse resp=new BaseResponse();
		if (file == null) {
			   resp.setMessage("文件或目录不能为空！");
			   resp.setStatus(0);
			   return resp;
		}
 
		try {
		
			file = java.net.URLDecoder.decode(file, "UTF-8");
		    String rootPath=request.getSession().getServletContext().getRealPath("/");
			String realFile=rootPath+file.trim();
			if(checkPermitPath(request, realFile)==false){
				   resp.setMessage("非法的目录！");
				   resp.setStatus(1);
				   return resp;
			}
			File deleteFile=new File(realFile);
			if(deleteFile.exists()){
				if(deleteFile.isFile()){
					deleteFile.delete();
				}else{
					ExtendedFileUtils.deleteDirectory(deleteFile);
				}
				   resp.setMessage("操作成功！");
				   resp.setStatus(2);
				   return resp;
			}else{
				   resp.setMessage("文件或目录不存在！");
				   resp.setStatus(3);
				   return resp;
			}
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setStatus(3);
		    return resp;
		}
	}
	
	public BaseResponse add(HttpServletRequest request, String currentDir, String fileName, String fileType) {
	    BaseResponse resp=new BaseResponse();
	    if (currentDir == null) {
	    	   resp.setMessage("文件或目录不能为空！");
	 		   resp.setStatus(0);
	 		  return resp;
	    }
	    
	    if(!"js".equals(fileType)&&!"css".equals(fileType)&&!"html".equals(fileType)){
	    	   resp.setMessage("文件类型非法！");
	 		   resp.setStatus(1);
	 		  return resp;
	    }
	    if (currentDir.charAt(currentDir.length()-1) == '\\') {
			currentDir = currentDir.substring(0, currentDir.length()-1) + "/";
		} else if (currentDir.charAt(currentDir.length()-1) != '/') {
			currentDir += "/";
		}
		
		try {
			currentDir = java.net.URLDecoder.decode(currentDir, "UTF-8");
			String realPath=request.getSession().getServletContext().getRealPath("/");
			String fileRealPath=realPath+currentDir;
			if (checkPermitPath(request, fileRealPath)==false) {
				   resp.setMessage("非法的目录！");
				   resp.setStatus(2);
				   return resp;
			 }   
			
			File file=new File(fileRealPath);
			if(!file.exists()){
				file.mkdirs();
			}
			file=new File(fileRealPath+fileName+"."+fileType);
			file.createNewFile();
            resp.setMessage("操作成功！");
			resp.setStatus(3);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage(e.getMessage());
			resp.setStatus(4);
			return resp;
		}
    }
	
	public BaseResponse addFolder(HttpServletRequest request, String currentDir, String forderName) {
		    BaseResponse resp=new BaseResponse();
		    if (currentDir == null) {
		    	   resp.setMessage("文件或目录不能为空！");
		 		   resp.setStatus(0);
		 		  return resp;
		    }
		    if (currentDir.charAt(currentDir.length()-1) == '\\') {
				currentDir = currentDir.substring(0, currentDir.length()-1) + "/";
			} else if (currentDir.charAt(currentDir.length()-1) != '/') {
				currentDir += "/";
			}
			
			try {
				currentDir = java.net.URLDecoder.decode(currentDir, "UTF-8");
				String realPath=request.getSession().getServletContext().getRealPath("/");
				String fileRealPath=realPath+currentDir+forderName;
				
				if(checkPermitPath(request, fileRealPath)==false) {
					   resp.setMessage("非法的目录！");
					   resp.setStatus(1);
					   return resp;
				}   
				
				File file=new File(fileRealPath);
				if(!file.exists()){
					file.mkdirs();
				}
				   resp.setMessage("操作成功！");
				   resp.setStatus(2);
				   return resp;
				
			} catch (Exception e) {
				e.printStackTrace();
				resp.setMessage(e.getMessage());
				resp.setStatus(3);
				return resp;
			}
	}
	/**
	 * cms文件zip
	 */
	public BaseResponse zip(HttpServletRequest request, String srcFile, String destFile, String siteCode,String module, String moduleCode, String type) {
		BaseResponse resp=new BaseResponse();
		try{
		   String realPath=request.getSession().getServletContext().getRealPath("/");
		   srcFile=realPath+srcFile.trim();
		   destFile=realPath+destFile.trim();
		   if (checkPermitPath(request, srcFile)==false) {
			   resp.setMessage("非法的被压缩文件或目录！");
			   resp.setStatus(0);
			   return resp;
		   }   
		   if (checkPermitPath(request, destFile)==false) {
			   resp.setMessage("非法的目标路径！");
			   resp.setStatus(1);
			   return resp;
		   }
		  String rootDir=getRootDir(siteCode, moduleCode, module, type);
		  if(!rootDir.endsWith("/")){
			  rootDir=rootDir.concat("/");
		  }
		   if(srcFile.endsWith(rootDir)){//如果是压缩根目录中所有文件
			   String tempDestFilePath=destFile.replace(moduleCode+"/", "");
			   if("content".equals(module)){//如果是压缩内容模版和资源模版则不会有moduleCode
				   tempDestFilePath=destFile.replace(module+"/", "");
			   }
			   File  tempDestFile=new File(tempDestFilePath);
			   ZipUtils.getInstance().zip(new File(srcFile),tempDestFile );
			   ExtendedFileUtils.copyFile(tempDestFile, new File(destFile));
			   ExtendedFileUtils.deleteQuietly(tempDestFile);
			}else{
			   ZipUtils.getInstance().zip(new File(srcFile), new File(destFile));
		   }
		   resp.setMessage("操作成功！");
		   resp.setStatus(2);
		   return resp;
           }catch(Exception e){
		   e.printStackTrace();
		   resp.setMessage(e.getMessage());
		   resp.setStatus(3);
		   return resp;
           }
	}
	
	/**
	 * 活动文件zip
	 * @param request
	 * @param srcFile
	 * @param destFile
	 * @param campaignCode
	 * @param type
	 * @return
	 */
	public BaseResponse zip(HttpServletRequest request, String srcFile, String destFile,String campaignCode, String type) {
		BaseResponse resp=new BaseResponse();
		try{
		   String realPath=request.getSession().getServletContext().getRealPath("/");
		   srcFile=realPath+srcFile.trim();
		   destFile=realPath+destFile.trim();
		   if (checkPermitPath(request, srcFile)==false) {
			   resp.setMessage("非法的被压缩文件或目录！");
			   resp.setStatus(0);
			   return resp;
		   }   
		   if (checkPermitPath(request, destFile)==false) {
			   resp.setMessage("非法的目标路径！");
			   resp.setStatus(1);
			   return resp;
		   }
		  String rootDir=getRootDir(campaignCode, type);
		  if(!rootDir.endsWith("/")){
			  rootDir=rootDir.concat("/");
		  }
		   if(srcFile.endsWith(rootDir)){//如果是压缩根目录中所有文件
			   String tempDestFilePath=destFile.replace(campaignCode+"/", "");
			
			   File  tempDestFile=new File(tempDestFilePath);
			   ZipUtils.getInstance().zip(new File(srcFile),tempDestFile );
			   ExtendedFileUtils.copyFile(tempDestFile, new File(destFile));
			   ExtendedFileUtils.deleteQuietly(tempDestFile);
			}else{
			   ZipUtils.getInstance().zip(new File(srcFile), new File(destFile));
		   }
		   resp.setMessage("操作成功！");
		   resp.setStatus(2);
		   return resp;
           }catch(Exception e){
		   e.printStackTrace();
		   resp.setMessage(e.getMessage());
		   resp.setStatus(3);
		   return resp;
           }
	}
	
	public BaseResponse unZip(HttpServletRequest request, String srcFile, String destFile) {
		  BaseResponse resp=new BaseResponse();
		  try{
			   String realPath=request.getSession().getServletContext().getRealPath("/");
			   srcFile=realPath+srcFile.trim();
			   destFile=realPath+destFile.trim();
			   if (checkPermitPath(request, srcFile)==false) {
				   resp.setMessage("非法的压缩文件！");
				   resp.setStatus(0);
				   return resp;
			   }   
			   if (checkPermitPath(request, destFile)==false) {
				   resp.setMessage("非法的目标路径！");
				   resp.setStatus(1);
				   return resp;
			   }
			   ZipUtils.getInstance().unZip(srcFile, destFile);
			   resp.setMessage("操作成功！");
			   resp.setStatus(2);
			   return resp;
		}catch(Exception e){
			   e.printStackTrace();
    		   resp.setMessage(e.getMessage());
			   resp.setStatus(3);
			   return resp;
		}
	}
	
	public BaseResponse upload(MultipartFile[] file, String currentDir, String dir,HttpServletRequest request) {
		BaseResponse resp=new BaseResponse();
	    if (currentDir == null) {
	    	resp.setStatus(0);
	    	resp.setMessage("操作失败,当前目录为空！");
	    	return resp;
	    }
	    if (currentDir.charAt(currentDir.length()-1) == '\\') {
			currentDir = currentDir.substring(0, currentDir.length()-1) + "/";
		} else if (currentDir.charAt(currentDir.length()-1) != '/') {
			currentDir += "/";
		}
		try{
		currentDir = java.net.URLDecoder.decode(currentDir, "UTF-8");
		String realPath=request.getSession().getServletContext().getRealPath("/");
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("voice","amr");
		extMap.put("video","mp4");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,js,css,gif,jpg,jpeg,png,bmp");
	
		if(!ServletFileUpload.isMultipartContent(request)){
			resp.setStatus(0);
	    	resp.setMessage("操作失败,请选择文件！");
	    	return resp;
		}
		if (dir == null) {
			dir = "image";
		}
		if(!extMap.containsKey(dir)){
			resp.setStatus(0);
			resp.setMessage("操作失败,非法的文件类型！");
	    	return resp;
		}
		if(checkPermitPath(request, realPath+currentDir)==false){
			resp.setStatus(0);
			resp.setMessage("操作失败,非法的上传目录！");
	    	return resp;
		}
		ExtendedFileUtils.makeDirs(realPath+currentDir);
		BufferedInputStream bis = null;      
		BufferedOutputStream bos = null;
		String[] retVal=new String[file.length];
			 for(int i = 0,size = file.length;i<size;i++){      
					MultipartFile multipartFile = file[i];  
					String uploadFileName=multipartFile.getOriginalFilename();
			    	if(!checkUploadFileType(uploadFileName, extMap.get(dir))){
			    		resp.setStatus(0);
						resp.setMessage("操作失败,非法的文件格式！");
				    	return resp;
			    	}
			    	    retVal[i]=uploadFileName;
			    	    bis = new BufferedInputStream(multipartFile.getInputStream());  
				        bos = new BufferedOutputStream(new FileOutputStream(new File(realPath+currentDir,uploadFileName)));      
				        Streams.copy(bis, bos, true);
			 }
			resp.setStatus(1);
			resp.setData(retVal);
			resp.setMessage("操作成功！");
	    	return resp;
        } catch (Exception e) {      
        	resp.setStatus(0);
			resp.setMessage(e.getMessage());
	    	return resp;
        }
	}
	
	
	/**
	 * 检查操作目录是否为合法
	 * @param request
	 * @param realPath
	 * @return
	 */
	public Boolean checkPermitPath(HttpServletRequest request,String realPath){
		
    	String rootPath=request.getSession().getServletContext().getRealPath("/");
    	
    	String templatePath=rootPath.concat(PropUtils.getPropertyValue("file_path.properties", "cms_template_path"));
		String resourcesPath=rootPath.concat(PropUtils.getPropertyValue("file_path.properties", "cms_resources_path"));
		
		String campaignTemplatePath=rootPath.concat(PropUtils.getPropertyValue("file_path.properties", "campaign_template_path"));
		String campaignResourcesPath=rootPath.concat(PropUtils.getPropertyValue("file_path.properties", "campaign_resources_path"));
		
		String[] permitPathes={templatePath,resourcesPath,campaignTemplatePath,campaignResourcesPath};
		
		Boolean retVal=false;
		for(String permitPath:permitPathes){
			if(realPath.indexOf(permitPath)==0){
				retVal=true;
			}
		}
		return retVal;
	}
	
	public boolean checkUploadFileType(String fileName,String authFileType){
		String fileType=fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length()).toLowerCase();
		if(authFileType.indexOf(fileType)==-1){
			return false;
		}
		return true;
	}
	
   public BaseResponse list(HttpServletRequest request,String dir,Boolean showButtons){
	    if(showButtons==null){
			showButtons=true;
		}
	    List<TreeFile> retList=new ArrayList<TreeFile>();
	    BaseResponse resp=new BaseResponse();
	    if (StringUtils.isEmpty(dir)) {
	    	resp.setStatus(0);
			resp.setMessage("操作失败,非法的目录！");
	     	return resp;
	    }
		if (dir.charAt(dir.length()-1) == '\\') {
	    	dir = dir.substring(0, dir.length()-1) + "/";
		} else if (dir.charAt(dir.length()-1) != '/') {
		    dir += "/";
		}
		try {
			dir = java.net.URLDecoder.decode(dir, "UTF-8");
		}catch (UnsupportedEncodingException e) {
			resp.setStatus(0);
			resp.setMessage(e.getMessage());
	     	return resp;
		}	
		String realPath=request.getSession().getServletContext().getRealPath("/");
		String realDir=realPath+dir;
		log.info("获取目录："+realDir);
	    if (new File(realDir).exists()) {
			String[] files = new File(realDir).list(new FilenameFilter() {
			    public boolean accept(File realDir, String name) {
					return name.charAt(0) != '.';
			    }
			});
			Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
			// All dirs
			for (String file : files) {
			    if (new File(realDir, file).isDirectory()) {
			    	String rel=dir + file;
			    	TreeFile treeFile=new TreeFile();
			    	treeFile.setDirectory(true);
			    	treeFile.setFileName(file);
			    	treeFile.setFilePath(rel);
			    	treeFile.setShowButtons(showButtons);
			    	retList.add(treeFile);
			    }
			}
			// All files
			for (String file : files) {
			    if (!new File(realDir, file).isDirectory()) {
					int dotIndex = file.lastIndexOf('.');
					String ext = dotIndex > 0 ? file.substring(dotIndex + 1) : "";
					String rel=dir + file;
					TreeFile treeFile=new TreeFile();
			    	treeFile.setDirectory(false);
			    	treeFile.setExt(ext);
			    	treeFile.setFileName(file);
			    	treeFile.setFilePath(rel);
			    	treeFile.setShowButtons(showButtons);
			    	retList.add(treeFile);
			    }
			}
			resp.setStatus(1);
			resp.setMessage("操作成功!");
	 	    resp.setData(retList);
	   	   }
	      return resp;
	    }
   
   private String getRootDir(String campaignCode, String type) {
		String templatePath=PropUtils.getPropertyValue("file_path.properties", "campaign_template_path");
		String resourcesPath=PropUtils.getPropertyValue("file_path.properties", "campaign_resources_path");
		String rootDir=templatePath.concat(campaignCode);
		if("resources".equals(type)){
			rootDir=resourcesPath.concat(campaignCode);
		}
		return rootDir;
	}
   
   @SuppressWarnings("unchecked")
public KindEditorFileResponse listForKindEditor(HttpServletRequest request,String path,String order,String dir,String currentDir){
		
	   if(path==null){
		   path="";
	   }
	   if(order==null){
		   order="name";
	   }else{
		   order.toLowerCase();
	   }
	   
		KindEditorFileResponse kindEditorFileResponse=new KindEditorFileResponse();
		  
		String rootRealPath = request.getSession().getServletContext().getRealPath(currentDir).concat("/") ;
	
		//文件保存目录URL
	    String baseContextPath=request.getScheme().concat("://").concat(request.getServerName()).concat((request.getServerPort()==80?"":":"+request.getServerPort())).concat(request.getContextPath());
	
		//文件保存目录URL
		String rootUrl=baseContextPath+currentDir;
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		String dirName =dir;
		if (dirName != null) {
			if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file", "kv","thumbnail","carousel"}).contains(dirName)){
				dirName="image";
			}
			rootRealPath += dirName+"/";
			rootUrl += dirName +"/";
			File saveDirFile = new File(rootRealPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		//根据path参数，设置各路径和URL
	
		String currentPath = rootRealPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}


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
			Collections.sort(editorFileList, new KindEditorFileSizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(editorFileList, new KindEditorFileTypeComparator());
		} else {
			Collections.sort(editorFileList, new KindEditorFileNameComparator());
		}
		kindEditorFileResponse.setMoveup_dir_path(moveupDirPath);
		kindEditorFileResponse.setCurrent_dir_path(currentDirPath);
		kindEditorFileResponse.setCurrent_url(currentUrl);
		kindEditorFileResponse.setTotal_count(editorFileList.size());
		kindEditorFileResponse.setFile_list(editorFileList);
		return kindEditorFileResponse;
   }
	
  
  
}
