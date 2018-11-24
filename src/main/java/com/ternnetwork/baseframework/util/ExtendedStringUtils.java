package com.ternnetwork.baseframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class ExtendedStringUtils extends StringUtils{
	    public static String getRandomString(int length) { //length表示生成字符串的长度  
	        String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	        Random random = new Random();     
	        StringBuffer sb = new StringBuffer();     
	        for (int i = 0; i < length; i++) {     
	            int number = random.nextInt(base.length());     
	            sb.append(base.charAt(number));     
	        }     
	        return sb.toString();     
	     }    
	    
	    public static   String inputStream2String(InputStream   in)   throws   IOException   {
	        StringBuffer   out   =   new   StringBuffer();
	        byte[]   b   =   new   byte[4096];
	        for   (int   n;   (n   =   in.read(b))   !=   -1;)   {
	                out.append(new   String(b,   0,   n));
	        }
	        return   out.toString();
	    } 
	    
	    /**
	     * 填充字符串
	     * @param length 总长度
	     * @param orinString 原字符串
	     * @param fillWith  用来填充的字符串
	     * @return
	     */
	    public static String fillString(int length, String orinString,String fillWith){
			StringBuilder result = new StringBuilder(orinString);
			for(int i=result.length(); i<length ; i++){
				result.insert(0,fillWith);
			}
			return result.toString();
		}
	    
	    public static String convertMAC(String  mac){
	    	if(isEmpty(mac)) {  
	            return null;  
	        } else if(mac.contains(":")){  
	            mac = mac.replace(":", "");  
	        } else {  
	            String[] macs = new String[6];  
	            for (int i = 0; i <= 5; i++) {  
	                macs[i] = mac.substring(i * 2, i * 2 + 2);  
	            }  
	            mac = macs[0];  
	            for (int i = 1; i < macs.length; i++) {  
	                mac += ":" + macs[i];  
	            }  
	        }  
	        return mac;  
	    }
}
