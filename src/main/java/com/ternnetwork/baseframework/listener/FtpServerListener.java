package com.ternnetwork.baseframework.listener;

import java.io.File;  

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  
  



import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.apache.ftpserver.ConnectionConfig;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;  
import org.apache.ftpserver.impl.DefaultFtpServer;  
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.springframework.web.context.WebApplicationContext;  
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ternnetwork.baseframework.model.config.WebAppInstance;
import com.ternnetwork.baseframework.model.ftp.FtpUser;
import com.ternnetwork.baseframework.service.config.ClusterService;
import com.ternnetwork.baseframework.util.PropUtils;

public class FtpServerListener implements ServletContextListener {
	private Log logger = LogFactory.getLog(FtpServerListener.class); 
	
	private  String ftpPath;
	private  String ftpUserName="ftpuser";
	private  String ftpPassword="P@ssw0rd888168";
    
    /** 
     * {@inheritDoc} 
     */  
    public void contextDestroyed(ServletContextEvent sce)  
    {  
        logger.error("Stopping FtpServer");  
        DefaultFtpServer server = (DefaultFtpServer)sce.getServletContext().getAttribute("FTPSERVER_CONTEXT_NAME");  
        if (server != null)  
        {  
            server.stop();  
            sce.getServletContext().removeAttribute("FTPSERVER_CONTEXT_NAME");  
            logger.info("FtpServer stopped");  
        }  
        else  
        {  
            logger.info("No running FtpServer found");  
        }  
    }  
      
    /** 
     * {@inheritDoc} 
     */  
    public void contextInitialized(ServletContextEvent sce)  
    {  
    	
    	
        
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());  
        
        ftpUserName=PropUtils.getPropertyValue("webapp.properties","instanceName");
        
        ClusterService clusterService= (ClusterService)ctx.getBean("clusterService");
        WebAppInstance webAppInstance=clusterService.findWebAppInstanceByName(ftpUserName);
        
        if(webAppInstance==null){
        	logger.info("集群中并未配置当前应用实例，所以FTP并未启动。");  
        	return;
        }
        
        logger.info("Starting FtpServer");  
        DefaultFtpServer server = (DefaultFtpServer)ctx.getBean("ftpServer");
        sce.getServletContext().setAttribute("FTPSERVER_CONTEXT_NAME", server);
        
        try{  
           
              
        	//web应用根目录作为服务根目录  
            ftpPath = sce.getServletContext().getRealPath("/");
         
           
              
            // 更新FTP服务器用户信息  
            // 首先获取到FTP用户管理对象  
            UserManager userManager = server.getUserManager();  
              
            // 删除原有的用户信息  
            userManager.delete(ftpUserName);  
              
            
            // 构造新的用户并保存到数据库  
            FtpUser base = new FtpUser();  
            base.setName(ftpUserName);  
            base.setPassword(webAppInstance.getFtpPassword());  
            base.setHomeDirectory(ftpPath);  
            userManager.save(base);  
              
            
            FtpServerFactory serverFactory = new FtpServerFactory();
            
            ListenerFactory listenerFactory = new ListenerFactory();
            listenerFactory.setPort(webAppInstance.getFtpPort());
            
          /*  SslConfigurationFactory sslf=new SslConfigurationFactory();
            sslf.setKeystoreFile(new File("D:\\workspace\\avocado_cms\\configs\\ftpserver.jks"));
            sslf.setKeystorePassword("password");
            SslConfiguration ssl=sslf.createSslConfiguration();
            listenerFactory.setSslConfiguration(ssl);
            listenerFactory.setImplicitSsl(true);*/
           
            serverFactory.setUserManager(userManager);
            serverFactory.addListener("default", listenerFactory.createListener());
            ConnectionConfigFactory connectionFactory=new ConnectionConfigFactory();
            connectionFactory.setAnonymousLoginEnabled(false);
            connectionFactory.setMaxAnonymousLogins(0);
            connectionFactory.setMaxLoginFailures(3);
            connectionFactory.setLoginFailureDelay(500);
            connectionFactory.setMaxThreads(500);
            ConnectionConfig connectionConfig=connectionFactory.createConnectionConfig();
            serverFactory.setConnectionConfig(connectionConfig);
            server=(DefaultFtpServer)serverFactory.createServer();
            server.start(); // 启动FTP服务 
            logger.info("FtpServer started");  
        }  
        catch (Exception e)  
        {  
            logger.error("Failed to start FtpServer");  
            
        }  
    }  
}
