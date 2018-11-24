package com.ternnetwork.cms.controller.file;


import java.io.File;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.http.KindEditorFileResponse;
import com.ternnetwork.baseframework.util.ExtendedFileUtils;
import com.ternnetwork.baseframework.util.PropUtils;
import com.ternnetwork.cms.service.file.CmsFileService;


@Controller@Scope("prototype")
@RequestMapping("/cms/file/*")
public class CmsFileController {
	
	@Resource
	private CmsFileService cmsFileService;

	
	@RequestMapping("manage.htm")
	public ModelAndView manage(String siteCode,String module,String moduleCode,String type){
		String rootDir = cmsFileService.getRootDir(siteCode,moduleCode,module,type);
		return new ModelAndView("/WEB-INF/view/cms/file/manage.jsp").addObject("rootDir", rootDir).addObject("type", type).addObject("siteCode", siteCode).addObject("module", module).addObject("moduleCode", moduleCode);
	}
	
	
	
	@RequestMapping("edit.htm")
	public ModelAndView edit(HttpServletRequest request,String file,String siteCode,String module,String moduleCode) throws IOException{
		file=file.trim();
		String mode="htmlmixed";
		if(file.endsWith(".js")){
			mode="text/javascript";
		}else 	if(file.endsWith(".css")){
			mode="text/css";
		}
		String realFile=request.getSession().getServletContext().getRealPath("/").concat(file);
		String code="非法的文件路径";
		if(cmsFileService.checkPermitPath(request, realFile)==true){
			 code=ExtendedFileUtils.readFileToString(new File(realFile), "UTF-8");
		}
		return new ModelAndView("/WEB-INF/view/cms/file/edit.jsp").addObject("file",file).addObject("mode",mode).addObject("code",code).addObject("siteCode",siteCode).addObject("module",module).addObject("moduleCode",moduleCode);
	}
	
	@RequestMapping("save.htm")
	public @ResponseBody BaseResponse save(HttpServletRequest request,HttpServletResponse response,String file,String code) {
		  return cmsFileService.save(request, file, code);
	}

	@RequestMapping("delete.htm")
	public @ResponseBody BaseResponse delete(HttpServletRequest request,HttpServletResponse response,String file,String forderName){
		    return cmsFileService.delete(request, file);	
	}
	
	@RequestMapping("add.htm")
	public @ResponseBody BaseResponse  addFile(HttpServletRequest request,HttpServletResponse response,String currentDir,String fileName,String fileType){
		   return cmsFileService.add(request, currentDir, fileName, fileType);	
	}

	@RequestMapping("folder/add.htm")
	public @ResponseBody BaseResponse addFolder(HttpServletRequest request,HttpServletResponse response,String currentDir,String forderName){
		   return cmsFileService.addFolder(request, currentDir, forderName);	
	}

	@RequestMapping("zip.htm")
	public @ResponseBody BaseResponse zip(HttpServletRequest request,HttpServletResponse response,String srcFile,String destFile,String siteCode,String module,String moduleCode,String type){
			return cmsFileService.zip(request, srcFile, destFile, siteCode, module, moduleCode, type);
	}

	@RequestMapping("unZip.htm")
	public @ResponseBody BaseResponse unZip(HttpServletRequest request,HttpServletResponse response,String srcFile,String destFile){
		  return cmsFileService.unZip(request, srcFile, destFile);
	}

	@RequestMapping("list.htm")
	public  @ResponseBody BaseResponse list(HttpServletRequest request,HttpServletResponse response,String dir,Boolean showButtons){
	     return cmsFileService.list(request, dir,showButtons);
	}

	@RequestMapping("upload.htm")
	public @ResponseBody BaseResponse upload(@RequestParam MultipartFile[] file,String currentDir,String dir,String imgWidth,String imgHeight,String align,String imgTitle,String localUrl,HttpServletResponse response,HttpServletRequest request){
		return cmsFileService.upload(file, currentDir, dir,request);
	}

	@RequestMapping("getChannelTemplateFileList.htm")
	public @ResponseBody List<String> getChannelTemplateFileList(HttpServletRequest request,String siteCode,String channelCode){
		return cmsFileService.getChannelTemplateFileList(request, siteCode, channelCode);
	}
	@RequestMapping("getSiteIndexTemplateFileList.htm")
	public @ResponseBody List<String> getSiteIndexTemplateFileList(HttpServletRequest request,String siteCode){
		return cmsFileService.getSiteIndexTemplateFileList(request, siteCode);
	}
	
	@RequestMapping("listForKindEditor.htm")
	public @ResponseBody KindEditorFileResponse listForKindEditor(HttpServletRequest request,String path,String order,String dir){
		String currentDir=PropUtils.getPropertyValue("file_path.properties", "cms_resources_path");
		return cmsFileService.listForKindEditor(request, path, order, dir,currentDir);
	}
}
