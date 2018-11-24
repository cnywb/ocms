package com.ternnetwork.baseframework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static void main(String[] args) {
	
		
	}
	
	public static String FORMAT_DATE_TIME_DEFAULT="yyyy-MM-dd HH:mm:ss";
	
	public static String FORMAT_DATE_DEFAULT="yyyy-MM-dd";
	
	public static String FORMAT_DATE_TIME_YYYYMMDDHHMMSS="yyyyMMddHHmmss";
	
	public static String FORMAT_DATE_YYYYMMDD="yyyyMMdd";
	
	public static Date parseDate(String text,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		try {
			return sdf.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(Date date,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
	    return sdf.format(date);
	}
	
	
	public static String getYear(Date date){
		String dateStr=format(date, FORMAT_DATE_DEFAULT);
		String[] array=dateStr.split("-");
		return array[0];
	}
	public static String getMonth(Date date){
		String dateStr=format(date, FORMAT_DATE_DEFAULT);
		String[] array=dateStr.split("-");
		return array[1];
	}
	public static String getDate(Date date){
		String dateStr=format(date, FORMAT_DATE_DEFAULT);
		String[] array=dateStr.split("-");
		return array[2];
	}
	
	 public static Date getMonthStart(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        int index = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar.add(Calendar.DATE, (1 - index));
	        return calendar.getTime();
	    }
	 
	 public static Date getMonthEnd(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MONTH, 1);
	        int index = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar.add(Calendar.DATE, (-index));
	        return calendar.getTime();
	  }
	 /**
	  * 得到上个月的月初
	  * @param date
	  * @return
	  */
	 public static Date getLastMonthStart(Date date) {
		 Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.MONTH, -1);
         int index = calendar.get(Calendar.DAY_OF_MONTH);
         calendar.add(Calendar.DATE, (1 - index));
         return calendar.getTime();
	  }
	 /**
	  * 得到上个月的月末
	  * @param date
	  * @return
	  */
	 public static Date getLastMonthEnd(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MONTH,0);
	        int index = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar.add(Calendar.DATE, (-index));
	        return calendar.getTime();
	  }
	 
	 public static Date getNext(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, 1);
	        return calendar.getTime();
	 }
	 
	 public static Date getYesterday(Date date){
		  Calendar cal=Calendar.getInstance();
		  cal.setTime(date);
		  cal.add(Calendar.DATE,-1);
		  return cal.getTime();
	 }
	 
	 /**
	  * 得到上周一
	  * @param date
	  * @return
	  */
	 public static Date getLastMonday(Date date) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
		    cal.add(Calendar.DATE, -1*7);
		    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		    return cal.getTime();
	}
	 /**
	  * 得到上周日
	  * @param date
	  * @return
	  */
	 public static Date getLastSunday(Date date) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
		    cal.add(Calendar.DATE, -1*7);
		    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		    return cal.getTime();
	 }
	 
	 /**
	  * 得到周一
	  * @param date
	  * @return
	  */
	 public static Date getMonday(Date date) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
		    cal.add(Calendar.DATE, 1);
		    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		    return cal.getTime();
	}
	 /**
	  * 得到当前周日
	  * @param date
	  * @return
	  */
	 public static Date getSunday(Date date) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
		    cal.add(Calendar.DATE, 1);
		    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		    return cal.getTime();
	 }

}
