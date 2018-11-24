package com.ternnetwork.toolkit.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ternnetwork.baseframework.annotation.QuartzJobImpl;
import com.ternnetwork.baseframework.listener.JobListener;
import com.ternnetwork.toolkit.enums.CampaignDataSendFrequency;
import com.ternnetwork.toolkit.service.infocollection.InfoCollectionDataSendJobService;

@QuartzJobImpl
public class InfoCollectionDataSendEverydayJob implements Job{
	
	
	private static final Logger log = LoggerFactory.getLogger(InfoCollectionDataSendEverydayJob.class);

	
	// cron express 0 10 9 ? * *

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CampaignDataSendFrequency f=CampaignDataSendFrequency.EVERY_DAY;
		log.info(f.getName()+"发送信息收集数据作业开始");
		InfoCollectionDataSendJobService infoCollectionDataSendJobService=(InfoCollectionDataSendJobService)JobListener.getApplicationContext().getBean("infoCollectionDataSendJobService");
		infoCollectionDataSendJobService.sendAllCampaignDataByEmail(f);
		log.info(f.getName()+"发送信息收集数据作业结束");
	}

}
