package com.ternnetwork.baseframework.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.cms.model.content.Content;
import com.ternnetwork.cms.model.page.CmsPage;
public class VelocityHtmlFileBuilder {

	private static final Logger log = Logger.getLogger(VelocityHtmlFileBuilder.class);
	public static void buildArticleViewHtml(Content article,String vmDir,String vmName,String outputDir){
		try {
		Properties pros=new Properties();
		pros.put("runtime.log", "v_log.log");
		pros.put("file.resource.loader.path",vmDir);
		pros.put("input.encoding", "UTF-8");
		pros.put("ouput.encoding", "UTF-8");
		Velocity.init(pros);
		File outputDirFile=new File(outputDir);
		if(!outputDirFile.exists()){
			outputDirFile.mkdirs();
		}
		    VelocityContext context=new VelocityContext();
		    context.put("article", article);
			Template template=Velocity.getTemplate(vmName);
			FileOutputStream outputStream=new FileOutputStream(new File(outputDirFile,article.getId()+".html"));
			OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
			BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
			template.merge(context, bufferedWriter);
			bufferedWriter.flush();
			bufferedWriter.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
     }
	
	public static void buildArticlePageViewHtml(Page<Content> page,long caetoryId,String vmDir,String vmName,String outputDir){
		try {
		Properties pros=new Properties();
		pros.put("runtime.log", "v_log.log");
		pros.put("file.resource.loader.path",vmDir);
		pros.put("input.encoding", "UTF-8");
		pros.put("ouput.encoding", "UTF-8");
		Velocity.init(pros);
		File outputDirFile=new File(outputDir);
		if(!outputDirFile.exists()){
			outputDirFile.mkdirs();
		}
		    VelocityContext context=new VelocityContext();
		    
		    context.put("firstPage","page_"+caetoryId+"_"+"1.html");
		    context.put("lastPage","page_"+caetoryId+"_"+page.getTotalPages()+".html");
		    context.put("prePage","page_"+caetoryId+"_"+((page.getPageNo()+1)==1?1:(page.getPageNo()))+".html");
		    context.put("nextPage","page_"+caetoryId+"_"+((page.getPageNo()+1)==page.getTotalPages()?page.getTotalPages():(page.getNextPage()+1))+".html");
		    context.put("totalPage",page.getTotalPages());
		    context.put("pageNo",(page.getPageNo()+1));
		    context.put("articles",page.getResult());
		    context.put("totalCount",page.getTotalCount());
			Template template=Velocity.getTemplate(vmName);
			FileOutputStream outputStream=new FileOutputStream(new File(outputDirFile,"page_"+caetoryId+"_"+(page.getPageNo()+1)+".html"));
			OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
			BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
			template.merge(context, bufferedWriter);
			bufferedWriter.flush();
			bufferedWriter.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
     }
	
	
	public static void buildViewHtml(VelocityContext context,String vmDir,String vmName,String outputDir,String outputFileName){
		try {
		log.info("模版路径:"+vmDir+vmName);
		log.info("页面路径:"+outputDir+outputFileName);
		Properties pros=new Properties();
		pros.put("runtime.log", "v_log.log");
		pros.put("file.resource.loader.path",vmDir);//设置模版根目录，初始化后无法更改
		pros.put("input.encoding", "UTF-8");
		pros.put("ouput.encoding", "UTF-8");
		
		
		Velocity.init(pros);
		File outputDirFile=new File(outputDir);
		if(!outputDirFile.exists()){
			outputDirFile.mkdirs();
		}
			Template template=Velocity.getTemplate(vmName);
			FileOutputStream outputStream=new FileOutputStream(new File(outputDirFile,outputFileName));
			OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
			BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
			template.merge(context, bufferedWriter);
			bufferedWriter.flush();
			bufferedWriter.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
     }
	

	public static void buildStaticContent(HttpServletRequest request, Content t) {
	
	}
}
