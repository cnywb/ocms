package com.ternnetwork.baseframework.service.impl.util;


import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.HttpClient;  

import com.ternnetwork.baseframework.service.util.HttpClientService;

@Service("httpClientService")
public class HttpClientServiceImpl implements HttpClientService {

	public String executeGetRequestWithResponse(String url) throws Exception{
	       HttpClient httpClient = new HttpClient();
	       String responseBody="";
	        try {
	        	GetMethod getMethod = new GetMethod(url);

	            System.out.println("executing request " + getMethod.getURI());

	             httpClient.executeMethod(getMethod);
	            System.out.println("----------------------------------------");
	            System.out.println(responseBody);
	            System.out.println("----------------------------------------");

	        } finally {
	            // When HttpClient instance is no longer needed,
	            // shut down the connection manager to ensure
	            // immediate deallocation of all system resources
	            httpClient.getHttpConnectionManager();
	        }
	      return  responseBody;
	}
	
	
	/**
	 * 从远程获取图片输入流，对防爬虫网站依然有效。
	 * @param url
	 * @return
	 */
	public InputStream getRemoteImg(String url)
	{
		 try {
		    HttpClient client = new org.apache.commons.httpclient.HttpClient();
	        client.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5"); 
			GetMethod get = new GetMethod(url);   
			get.getParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
			client.executeMethod(get);   
			return get.getResponseBodyAsStream();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//获取输入流
	        
	        return null;
	}
	
}
