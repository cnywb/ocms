package com.ternnetwork.baseframework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
	
	/**
	 * 匹配手机
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){       
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");       
        Matcher m = p.matcher(mobiles);       
        return m.matches();       
    }   
     
	/**
	 * 匹配邮箱
	 * @param email
	 * @return
	 */
    public static boolean isEmail(String email){       
     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";  
        Pattern p = Pattern.compile(str);       
        Matcher m = p.matcher(email);       
        return m.matches();       
    }   
    
    /**
     * 匹配
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
       	if(str==null||"".equals(str)){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 匹配整数或小数
     * @param str
     * @return
     */
    public static boolean isIntegerOrDecimal(String str){       
        Pattern p = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    }
    
    /**
     * 匹配指定小数位数的正小数
     * @param str
     * @param len
     * @return
     */
    public static boolean isPositiveDecimal(String str,Integer len){       
        Pattern p = Pattern.compile("^[0-9]+(.[0-9]{"+len+"})?$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    }
    
    /**
     * 匹配中文字符
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str){ 
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5]{0,}$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    } 
    /**
     * 匹配英文字母
     * @param str
     * @return
     */
    public static boolean isLetter(String str){ 
        Pattern p = Pattern.compile("^[A-Za-z]+$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    } 
    /**
     * 匹配小写英文字母
     * @param str
     * @return
     */
    public static boolean isLowerLetter(String str){ 
        Pattern p = Pattern.compile("^[a-z]+$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    } 
    /**
     *匹配大写英文字母
     * @param str
     * @return
     */
    public static boolean isUpperLetter(String str){ 
        Pattern p = Pattern.compile("^[A-Z]+$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    } 
    /**
     * 匹配字母或数字
     * @param str
     * @return
     */
    public static boolean isLetterOrNumber(String str){       
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    }   
    /**
     * 匹配正整数
     * @param str
     * @return
     */
    public static boolean isPositiveInteger(String str){       
        Pattern p = Pattern.compile("^\\+?[0-9][0-9]*$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    }
    /**
     * 匹配大于0的正整数
     * @param str
     * @return
     */
    public static boolean isPositiveIntegerExceptZero(String str){       
        Pattern p = Pattern.compile("^\\+?[1-9][0-9]*$");       
        Matcher m = p.matcher(str);       
        return m.matches();       
    }  
    /**
     * 匹配URL
     * @param str
     * @return
     */
    public static boolean isURL(String str){ 
        Pattern p = Pattern.compile("^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");       
        Matcher m = p.matcher(str);
        Pattern p2 = Pattern.compile("^https://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");       
        Matcher m2 = p2.matcher(str);  
        return m.matches()||m2.matches();       
    } 
	public static void main(String args[]) {

		System.out.println(isPositiveDecimal("1.00",2));
	}

}
