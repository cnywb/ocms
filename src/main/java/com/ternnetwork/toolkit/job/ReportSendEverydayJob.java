package com.ternnetwork.toolkit.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ternnetwork.baseframework.annotation.QuartzJobImpl;
import com.ternnetwork.baseframework.listener.JobListener;
import com.ternnetwork.toolkit.enums.ReportSendFrequency;
import com.ternnetwork.toolkit.service.report.ReportSendJobService;
@QuartzJobImpl
public class ReportSendEverydayJob implements Job{
	
private static final Logger log = LoggerFactory.getLogger(ReportSendEverydayJob.class);

	
	// cron express 0 10 9 ? * *
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ReportSendFrequency f=ReportSendFrequency.EVERY_DAY;
		log.info(f.getName()+"发送信息收集数据作业开始");
		ReportSendJobService reportSendJobService=(ReportSendJobService)JobListener.getApplicationContext().getBean("reportSendJobService");
		reportSendJobService.sendAllReport(f);
		log.info(f.getName()+"发送信息收集数据作业结束");
	}

}
