package com.ternnetwork.baseframework.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseContentType {
	
	public static Map<String,String> contentType=new HashMap<String,String>();
	public static String getContentTypeByFileExtendsName(String fileExtendsName){
		if(contentType.size()==0){
			contentType.put("bmp","image/bmp");
			contentType.put("gif","image/gif");
			contentType.put("jpeg","image/jpeg");
			contentType.put("tiff","image/tiff");
			contentType.put("x-dcx","image/x-dcx");
			contentType.put("x-pcx","image/x-pcx");
			contentType.put("html","text/html");
			contentType.put("txt","text/plain");
			contentType.put("xml","text/xml");
			contentType.put("afp","application/afp");
			contentType.put("pdf","application/pdf");
			contentType.put("rtf","application/rtf");
			contentType.put("doc","application/msword");
			contentType.put("docx","application/msword");
			contentType.put("xls","application/vnd.ms-excel");
			contentType.put("xlsx","application/vnd.ms-excel");
			contentType.put("ppt","application/vnd.ms-powerpoint");
			contentType.put("pptx","application/vnd.ms-powerpoint");
		}
		
		return contentType.get(fileExtendsName);
	}
	
	
	
	
}
