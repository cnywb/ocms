package com.ternnetwork.cms.service.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.http.KindEditorFileResponse;



public interface CmsFileService {
	public void createChannelFileDirs(HttpServletRequest request,String siteCode,String code);
	public void updateChannelFileDirs(HttpServletRequest request,String siteCode,String oldCode,String newCode);
	public void createSiteFileDirs(HttpServletRequest request, String code);
	public void updateSiteFileDirs(HttpServletRequest request,  String oldCode, String newCode);
	public List<String> getContentTemplateFileList(HttpServletRequest request,String siteCode);
	public List<String> getChannelTemplateFileList(HttpServletRequest request,String siteCode,String channelCode);
	public List<String> getSiteIndexTemplateFileList(HttpServletRequest request,String siteCode);
	public String getRealPath(HttpServletRequest request,String basePath,String siteCode,String moduleCode,String module);
	public String getRootDir(String siteCode,String moduleCode,String module, String type);
	public BaseResponse save(HttpServletRequest request, String file, String code);
	public BaseResponse delete(HttpServletRequest request, String file);
	public BaseResponse add(HttpServletRequest request, String currentDir, String fileName, String fileType);
	public BaseResponse addFolder(HttpServletRequest request, String currentDir, String forderName);
	public BaseResponse zip(HttpServletRequest request, String srcFile, String destFile, String siteCode,String module, String moduleCode, String type);
	public BaseResponse zip(HttpServletRequest request, String srcFile, String destFile,String campaignCode, String type);
	public BaseResponse unZip(HttpServletRequest request, String srcFile, String destFile);
	public Boolean checkPermitPath(HttpServletRequest request,String realPath);
	public BaseResponse upload(MultipartFile[] imgFile, String currentDir, String dir,HttpServletRequest request);
	public BaseResponse list(HttpServletRequest request,String dir,Boolean showButtons);
	public boolean checkUploadFileType(String fileName,String authFileType);
	public KindEditorFileResponse listForKindEditor(HttpServletRequest request,String path,String order,String dir,String currentDir);
}
