package com.ternnetwork.baseframework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ternnetwork.baseframework.service.job.JobService;




/**
 * Application Lifecycle Listener implementation class JobListener
 *
 */

public class JobListener implements ServletContextListener {

	
	 private static WebApplicationContext applicationContext;
    /**
     * Default constructor. 
     */
    public JobListener() {
    	super();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
   	     applicationContext=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());    
   	    JobService jobService=(JobService)applicationContext.getBean("jobService"); 
     	try {
			jobService.doInitScheduler();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	System.out.println("<------    quartz调度监听器启动完成   ----------->");
    
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

	public static WebApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(
			WebApplicationContext applicationContext) {
		JobListener.applicationContext = applicationContext;
	}
	
}
