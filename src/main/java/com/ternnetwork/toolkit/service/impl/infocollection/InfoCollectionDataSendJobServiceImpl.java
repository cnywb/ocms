package com.ternnetwork.toolkit.service.impl.infocollection;

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
import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.EmailUtils;
import com.ternnetwork.toolkit.enums.CampaignDataSendFrequency;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionCampaign;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionData;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionCampaignService;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionDataSendJobService;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionDataService;


@Service("infoCollectionDataSendJobService")
public class InfoCollectionDataSendJobServiceImpl implements
		InfoCollectionDataSendJobService {
	
	
	@Resource
	private InfoCollectionCampaignService infoCollectionCampaignService;
	
	
	@Resource
	private InfoCollectionDataService infoCollectionDataService;
	
	@Resource
	private SystemParameterService  systemParameterService;
	
	
	private static final Logger log = LoggerFactory.getLogger(InfoCollectionDataSendJobServiceImpl.class);
	
	/**
	 * 发送所有活动数据
	 * @param dataSendFrequency
	 */
	public void sendAllCampaignDataByEmail(CampaignDataSendFrequency dataSendFrequency){
		
	    List<InfoCollectionCampaign> campaignList=infoCollectionCampaignService.findAll(dataSendFrequency);//得到所有指定发送频率的活动
		
		for(InfoCollectionCampaign campaign:campaignList){
			
	    sendSingleCampaignDataByEmail(campaign, dataSendFrequency);
	
		}
	}
	
	/**
	 * 发送单个活动数据
	 * @param campaign
	 * @param dataSendFrequency
	 */
	private void sendSingleCampaignDataByEmail(InfoCollectionCampaign campaign,CampaignDataSendFrequency dataSendFrequency){
		if(CampaignDataSendFrequency.EVERY_DAY.equals(dataSendFrequency)){
			String startTime=DateUtils.format(DateUtils.getYesterday(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
			sendEmail(campaign, startTime, startTime);//发送昨天的数据
		}else if(CampaignDataSendFrequency.EVERY_WEEK.equals(dataSendFrequency)){
			String startTime=DateUtils.format(DateUtils.getLastMonday(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
			String endTime=DateUtils.format(DateUtils.getLastSunday(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
			sendEmail(campaign, startTime,endTime);//发送上周的数据
		}else if(CampaignDataSendFrequency.EVERY_MONTH.equals(dataSendFrequency)){//发送上月的数据
			String startTime=DateUtils.format(DateUtils.getLastMonthStart(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
			String endTime=DateUtils.format(DateUtils.getLastMonthEnd(new Date()), DateUtils.FORMAT_DATE_DEFAULT);
			sendEmail(campaign, startTime, endTime);
		}
	}
	
	
	
	private void sendEmail(InfoCollectionCampaign campaign,String startTime,String endTime){
		List<InfoCollectionData> list=infoCollectionDataService.queryAll(String.valueOf(campaign.getId()),startTime,endTime);
		HSSFWorkbook wb=infoCollectionDataService.createExcel(list);
		String filepath=systemParameterService.getValueByKey("EMAIL_ATTACHMENT_PATH");
		File outputFile=new File(filepath);
    	if(!outputFile.exists()){
    		outputFile.mkdir();
    	}
		String filename=campaign.getCode()+"_"+campaign.getName()+"_"+startTime+"_"+endTime+".xls";
		log.info("生成数据文件 "+filename);
    	outputFile=new File(filepath+filename);
     	try {
			OutputStream fileOutputStream=new FileOutputStream(outputFile);
			wb.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			String from=systemParameterService.getValueByKey("EMAIL_FROM");
			String fromPassword=systemParameterService.getValueByKey("EMAIL_FROM_PASSWORD");
			String host=systemParameterService.getValueByKey("EMAIL_HOST");
			String trust=systemParameterService.getValueByKey("EMAIL_TRUST");
			String port=systemParameterService.getValueByKey("EMAIL_PORT");
			String dataReceiveEmail=campaign.getDataReceiveEmail();
			String title=campaign.getName()+"数据(共计"+list.size()+"条)";
			String[] tos=dataReceiveEmail.split(";");
			for(String to:tos){
				if(!to.equals("")){
					List<File> fileList=new ArrayList<File>();
					fileList.add(outputFile);
					EmailUtils.sendTextEmailWithTSL(from, fromPassword,to, host,trust,port,title, "该邮件由OCMS系统自动发出!", fileList);
				}
	        }
			
		} catch (Exception e) {
	        e.printStackTrace();
		}
	
	}
	

	

	
	
	
	

}
