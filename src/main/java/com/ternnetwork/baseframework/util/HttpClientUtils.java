package com.ternnetwork.baseframework.util;



import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class HttpClientUtils {
	 private static Log log = LogFactory.getLog(HttpClientUtils.class);

		
		public static void main(String[] args) throws HttpException, IOException  {
			
		} 
		
		public static String getApClientInfo(String protocol,String host,int port,String username,String password,int start,int limit,int pageNo) throws HttpException, IOException{
			   String requestXml="<ajax-request action=\"getstat\" comp=\"stamgr\" updater=\"clientsummary.1436927934973\" GET_PREFERENCE=\"column-edit\"><client LEVEL=\"1\"/><pieceStat start=\""+start+"\" pid=\""+pageNo+"\" number=\""+limit+"\" requestId=\"clientsummary.1436928304161\" cleanupId=\"clientsummary.1436928181954\"/></ajax-request>";
			   return postToApServer(protocol, host, port, username, password,requestXml);
		}

		public static String getApClientEvent(String protocol,String host,int port,String username,String password,int start,int limit,int pageNo) throws HttpException, IOException{
			   String requestXml="<ajax-request action=\"getstat\" comp=\"eventd\" updater=\"clientevent.1438752985778\"><xevent c=\"user\" sortBy=\"time\" sortDirection=\"-1\" /><pieceStat start=\""+start+"\" pid=\""+pageNo+"\" number=\""+limit+"\" requestId=\"clientevent.1438753598177\" cleanupId=\"clientevent.1438753476779\" /></ajax-request>";
			   return postToApServer(protocol, host, port, username, password,requestXml);
		}
		
		public static String deleteAllApClientEvent(String protocol,String host,int port,String username,String password) throws HttpException, IOException{
			   String requestXml="<ajax-request action=\"docmd\" comp=\"eventd\" updater=\"rid.0.5164712106997303\" xcmd=\"del-all-events\" checkAbility=\"6\"><xcmd cmd=\"del-all-events\"/></ajax-request>";
			   return postToApServer(protocol, host, port, username, password,requestXml);
		}

		public static String getApUseSummary(String protocol,String host,int port,String username,String password) throws HttpException, IOException{
			   String requestXml="<ajax-request action=\"getstat\" comp=\"stamgr\" updater=\"usage.1439284505899\"><system/></ajax-request>";
			   return postToApServer(protocol, host, port, username, password,requestXml);
		}

		
		public static String logoutByMac(String protocol,String host,int port,String username,String password,String clientMac) throws HttpException, IOException{
			   String requestXml="<ajax-request action=\"docmd\" comp=\"stamgr\" updater=\"rid.0.17939180750998418\" xcmd=\"delete\" checkAbility=\"2\"><xcmd cmd=\"delete\" tag=\"client\" client=\""+clientMac+"\"/></ajax-request>";
    		   return postToApServer(protocol, host, port, username, password,requestXml);
		}

		private static String postToApServer(String protocol, String host,
				int port, String username, String password, String requestXml)
				throws IOException, HttpException, UnsupportedEncodingException {
			  HttpClient client = new HttpClient(); 
				
		      client.getHostConfiguration().setHost(host, port,protocol);  
		  
		      // 模拟登录页面 login.jsp->main.jsp  
		      PostMethod post = new PostMethod( "/admin/login.jsp" );  
		      NameValuePair name = new NameValuePair( "username" , username );  
		      NameValuePair pass = new NameValuePair( "password" , password);  
		      NameValuePair url = new NameValuePair( "url" , "" );  
		      NameValuePair ok = new NameValuePair( "ok" , "Log on" ); 
		      post.setRequestBody( new NameValuePair[]{name,pass,url,ok});
		      
		      int status = client.executeMethod(post);  
		      System.out.println(post.getResponseBodyAsString());  
		      post.releaseConnection();  
		  
		      // 查看 cookie 信息  
		      CookieSpec cookiespec = CookiePolicy.getDefaultSpec();  
		      Cookie[] cookies = cookiespec.match(host, port, "/" , false , client.getState().getCookies());  
		      if (cookies.length == 0) {  
		    	     log.info( "None" );  
		      } else {  
		         for ( int i = 0; i < cookies.length; i++) { 
		        	 log.info(cookies[i].toString());
		         
		         }  
		      }  
		  
		      // 访问所需的页面 main2.jsp   
		       String retVal="";
		      PostMethod post2 = new PostMethod( "/admin/_cmdstat.jsp" );
		      
		      post2.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		      post2.setRequestHeader("X-Requested-With","XMLHttpRequest"); 
		      post2.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 5.2; rv:36.0) Gecko/20100101 Firefox/36.0");  
	          InputStream is = new ByteArrayInputStream(requestXml.getBytes());
		      post2.setRequestBody(is);
		      client.executeMethod(post2); 
		      InputStream retIs=null;
		      retIs=post2.getResponseBodyAsStream();
		      BufferedReader bf = new BufferedReader(new InputStreamReader(retIs, "UTF-8"));
			  String valueString = null;
		      while ((valueString=bf.readLine())!=null){
		  	    	  retVal=retVal+valueString;
		      }
		      post2.releaseConnection();  
		      log.info("控制器响应报文-----》》"+retVal);
			return retVal;
		}


}