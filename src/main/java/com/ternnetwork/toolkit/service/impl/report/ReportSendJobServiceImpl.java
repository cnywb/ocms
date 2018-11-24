package com.ternnetwork.toolkit.service.impl.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ternnetwork.baseframework.dao.base.IBaseDao;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.EmailUtils;
import com.ternnetwork.baseframework.util.ExcelUtils;
import com.ternnetwork.baseframework.util.ExtendedStringUtils;
import com.ternnetwork.toolkit.enums.ReportFormat;
import com.ternnetwork.toolkit.enums.ReportSendFrequency;
import com.ternnetwork.toolkit.model.report.Report;
import com.ternnetwork.toolkit.model.report.ReportItem;
import com.ternnetwork.toolkit.service.report.ReportSendJobService;
import com.ternnetwork.toolkit.service.report.ReportService;


@Service("reportSendJobService")
public class ReportSendJobServiceImpl implements ReportSendJobService {
	
	private static final Logger log = LoggerFactory.getLogger(ReportSendJobServiceImpl.class);
	
	
	
	@Resource
	private ReportService reportService;
	
	@Resource
	private IBaseDao iBaseDao;
	
	@Resource
	private SystemParameterService  systemParameterService;
	
	
    public void sendAllReport(ReportSendFrequency sendFrequency){
     	List<Report> list=reportService.findAll(sendFrequency);//得到所有指定发送频率的报告
    	for(Report t:list){
	      sendReport(t,null,null);
		}
	}
    
    public void sendReport(Report t,String startDate,String endDate){
		if(t.getFormat().equals(ReportFormat.FORMAT_EXCEL)){
			sendExcelReport(t,startDate,endDate);
		}
	}
    
    public BaseResponse sendReport(Long id,String startDate,String endDate){
    	BaseResponse res=new BaseResponse();
	   	if(!ExtendedStringUtils.isEmpty(startDate)&&DateUtils.parseDate(startDate, DateUtils.FORMAT_DATE_DEFAULT)==null){
    		res.setStatus(0);
       		res.setMessage("操作失败，startDate参数值不是日期格式！");
    		return res;
    	}
    	if(!ExtendedStringUtils.isEmpty(endDate)&&DateUtils.parseDate(endDate, DateUtils.FORMAT_DATE_DEFAULT)==null){
    		res.setStatus(0);
    		res.setMessage("操作失败，endDate参数值不是日期格式！");
    		return res;
    	}
    	Report t=reportService.findById(id);
    	sendReport(t,startDate,endDate);
    	res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
    
    public HSSFWorkbook createExcel(Report t,String startTime,String endTime){
    	
    	HSSFWorkbook wb = new HSSFWorkbook(); // create new workbook object
    	
    	startTime=startTime+" 00:00:00";
    	
    	endTime=endTime+" 23:59:59";
    	
      	List<ReportItem> items=t.getItems();
     	
    	for(ReportItem item:items){
    		String querySql=item.getSql().replace("${startTime}", startTime).replace("${endTime}", endTime);
    		log.info("正在查询报告项:"+item.getName()+";  SQL:"+querySql);
      		List<Object[]> resultList=iBaseDao.queryAllObjectArrayByNativeSQL(querySql,null);
    		ExcelUtils.createExcelSheet(wb,resultList,item.getName(), item.getHeader().split(","));
    	}
    	
    	return wb;
    }
    
    
    
    public void sendExcelReport(Report t,String startDate,String endDate){
    
    	if(ExtendedStringUtils.isEmpty(startDate)&&ExtendedStringUtils.isEmpty(endDate)){
    		if(t.getSendFrequency().equals(ReportSendFrequency.EVERY_DAY)){
        		startDate=DateUtils.format(DateUtils.getYesterday(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
        		endDate=DateUtils.format(DateUtils.getYesterday(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
        	}else if(t.getSendFrequency().equals(ReportSendFrequency.EVERY_WEEK)){
        		startDate=DateUtils.format(DateUtils.getLastMonday(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
        		endDate=DateUtils.format(DateUtils.getLastSunday(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
        	}else if(t.getSendFrequency().equals(ReportSendFrequency.EVERY_MONTH)){
        		startDate=DateUtils.format(DateUtils.getLastMonthStart(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
        		endDate=DateUtils.format(DateUtils.getLastMonthEnd(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
        	}
    	}
       	HSSFWorkbook wb=createExcel(t, startDate, endDate);
    	String filepath=systemParameterService.getValueByKey("EMAIL_ATTACHMENT_PATH");
		File outputFile=new File(filepath);
    	if(!outputFile.exists()){
    		outputFile.mkdir();
    	}
    	
    	String filename=t.getName()+"_"+startDate+"_"+endDate+".xls";
		log.info("生成数据文件 "+filename);
    	outputFile=new File(filepath+filename);
     	try {
			OutputStream fileOutputStream=new FileOutputStream(outputFile);
			wb.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			String from=systemParameterService.getValueByKey("EMAIL_FROM");
			String fromPassword=systemParameterService.getValueByKey("EMAIL_FROM_PASSWORD");
			//String host=systemParameterService.getValueByKey("EMAIL_HOST");
			//String trust=systemParameterService.getValueByKey("EMAIL_TRUST");
			//String port=systemParameterService.getValueByKey("EMAIL_PORT");
			String dataReceiveEmail=t.getReceiveEmail();
		
			String[] tos=dataReceiveEmail.split(";");
			for(String to:tos){
				if(!to.equals("")){
					EmailUtils.sendMailByAliMail(from, fromPassword, to, t.getName(), "该邮件由OCMS系统自动发出!", outputFile);
				}
	        }
			
		} catch (Exception e) {
	        e.printStackTrace();
		}
	   
	}
    
    

}
