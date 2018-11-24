package com.ternnetwork.baseframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class MvcRequestMappingUtils {
	
	
	public static void main(String[] ags){
		//demo
		String[] packagesToScan={"com.ternnetwork"};
		List<String> mvcRequestMappingList=MvcRequestMappingUtils.getMvcRequestMappingList(packagesToScan);
	}
	
	public static List<String> getMvcRequestMappingList(String[] packagesToScan){
		PackageClassesScaner packageClassesScaner=new PackageClassesScaner(packagesToScan,Controller.class);
		return getMvcRequestMappingList(packageClassesScaner.getClassesNameList());
	} 
	
	
	public static List<String> getMvcRequestMappingList(List<String> classNameList){
		
		List<String> retVal=new ArrayList<String>();
		for(String className:classNameList){
			getMvcRequestMappingListByClass(retVal, className);
		}
		return retVal;
		
	}


	private static void getMvcRequestMappingListByClass(List<String> retVal, String className) {
		try {
			
			Class<?> cls=Class.forName(className);
		
			Annotation[] classAnnotations=cls.getAnnotations();//得到类级别的所有注解

			int classRequestMappingCount=0;//类级别的RequestMapping统计
			
			for(Annotation classAnnotation:classAnnotations){
				
			    if(classAnnotation instanceof RequestMapping){
			    	
			    	classRequestMappingCount=classRequestMappingCount+1;
			    	
			    	Method annotationMethod = classAnnotation.getClass().getDeclaredMethod("value", null); 
			    	
		            String[] annotationValues = (String[])annotationMethod.invoke(classAnnotation, null);
		            
		            for (String classRequestMappingPath : annotationValues) {
		            	getMvcRequestMappingListByMethod(retVal, cls, classRequestMappingPath);  
		            }  
			    	
			    }
			}
			
		    if(classRequestMappingCount==0){//如果没有类级别的RequestMapping
		    	getMvcRequestMappingListByMethod(retVal, cls,""); 
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void getMvcRequestMappingListByMethod(List<String> retVal, Class<?> cls, String classRequestMappingPath)throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method[] methods =cls.getDeclaredMethods();
		for (Method method : methods) {  
		    if (method.isAnnotationPresent(RequestMapping.class)) {  
		        Annotation methodAnnotation = method.getAnnotation(RequestMapping.class);  
		        Method   methodAnnotationMethod=methodAnnotation.getClass().getDeclaredMethod("value", null);  
		        String[] values=(String[]) methodAnnotationMethod.invoke(methodAnnotation, null);  
		        for (String methodRequestMappingPath : values) {  
		        	methodRequestMappingPath=classRequestMappingPath.concat(methodRequestMappingPath).replace("*", "").replace("//", "/");
		        	retVal.add(methodRequestMappingPath.replaceFirst("/",""));
		        }  
		    }  
		}
	}
	

}
