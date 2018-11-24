package com.ternnetwork.baseframework.util;
import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.httpclient.util.DateUtil;





public class EmailUtils {

	public static void main(String[] args) {
		try {
			sendMailByAliMail("service@changanfordclub.com", "ford@P@ssw0rd888168", "359709421@qq.com", "test", "邮件测试",new File("D:/2016BC.xls"));
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public  static void sendTextEmailWithTSL(String subject,String text,String toEmails,List<File> attachmentList) throws Exception{
		String from=PropUtils.getPropertyValue("mail.properties", "job.mail.from");
		String password=PropUtils.getPropertyValue("mail.properties","job.mail.password");
		String[] tos=toEmails.split(";");
		for(String to:tos){
			if(!to.equals("")){
				sendTextEmailWithTSL(from, password,to, "smtp.office365.com", "smtp.office365.com","587",subject, text, attachmentList);
			}
        }
	}
	
	

	public  static void sendTextEmailWithTSL(String username,String password,String to,String host,String trust,String port,String subject,String text,List<File> attachmentList) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		//不做服务器证书校验
		props.put("mail.smtp.ssl.checkserveridentity", "false");
		
		//添加信任的服务器地址，多个地址之间用空格分开
		props.put("mail.smtp.ssl.trust",trust);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		
		
		final PasswordAuthentication  aut=new PasswordAuthentication(username, password);
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return aut;
					}
				});

		try {

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
			// 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
           contentPart.setContent(text, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);
            
         // 添加附件的内容
            if (attachmentList != null) {
                for(File attachment:attachmentList){
            		   BodyPart attachmentBodyPart = new MimeBodyPart();
                       DataSource source = new FileDataSource(attachment);
                       attachmentBodyPart.setDataHandler(new DataHandler(source));
                       attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));//MimeUtility.encodeWord可以避免文件名乱码
                       multipart.addBodyPart(attachmentBodyPart);
            	}
            }
            
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));
			
			message.setSubject(subject);
			message.setText(text);
		   message.setContent(multipart);
			Transport.send(message);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sendMailByAliMail(String from,String password,String to,String subject,String content,File attachment) throws Exception{
	    Properties prop=new Properties();  
	    prop.put("mail.host","smtp.mxhichina.com" );  
        prop.put("mail.transport.protocol", "smtp");  
        prop.put("mail.smtp.auth", "true");  
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.checkserveridentity", "false");
        
        //1.创建sesssion  
        Session session=Session.getInstance(prop);  
        //开启session的调试模式，可以查看当前邮件发送状态  
       // session.setDebug(true);  
  
  
        //2.通过session获取Transport对象（发送邮件的核心API）  
        Transport ts=session.getTransport();  
        
        //3.通过邮件用户名密码链接，阿里云默认是开启个人邮箱pop3、smtp协议的，所以无需在阿里云邮箱里设置  
        ts.connect(from, password);  
  
        //4.创建邮件  
        Message msg=new MimeMessage(session);  
        //设置发件人  
        msg.setFrom(new InternetAddress(from));  
        //设置收件人  
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));  
        //设置抄送人  
        //mm.setRecipient(Message.RecipientType.CC, new InternetAddress("XXXX@qq.com"));  
  
        msg.setSubject(MimeUtility.encodeText(subject)); 
        
        Multipart multipart = new MimeMultipart();
		// 添加邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(content, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
        if (attachment != null) {
            BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
            multipart.addBodyPart(attachmentBodyPart);
        }
  	        msg.setContent(multipart);   
        ts.sendMessage(msg, msg.getAllRecipients());  
 }  
}