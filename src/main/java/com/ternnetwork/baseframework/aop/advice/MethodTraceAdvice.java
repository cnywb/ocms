package com.ternnetwork.baseframework.aop.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;;






public class MethodTraceAdvice implements MethodBeforeAdvice,
		AfterReturningAdvice,ThrowsAdvice,CombinedThrowAdvice {
	
	private static final Logger log = Logger.getLogger(MethodTraceAdvice.class);

	
	public void before(Method method, Object[] args, Object cls)
			throws Throwable {
		
        log.info(method.getName()+" 方法开始执行..............");
	}


	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
	
		 log.info(arg1.getName()+"  方法执行正常结束..........");

	}

	
	public void afterThrowing(Method paramMethod, Object[] paramArrayOfObject,
			Object paramObject, Exception paramException) throws Throwable {
		
		 log.info(paramMethod.getName()+" 方法执行异常结束...... ");
	}

	
	


	
	
	

	

}
