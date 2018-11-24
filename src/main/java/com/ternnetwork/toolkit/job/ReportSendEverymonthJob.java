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
public class ReportSendEverymonthJob implements Job{
	
private static final Logger log = LoggerFactory.getLogger(ReportSendEverymonthJob.class);

	
   //0 15 10 1 * ? 每月1日上午10:15触发

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ReportSendFrequency f=ReportSendFrequency.EVERY_MONTH;
		log.info(f.getName()+"发送信息收集数据作业开始");
		ReportSendJobService reportSendJobService=(ReportSendJobService)JobListener.getApplicationContext().getBean("reportSendJobService");
		reportSendJobService.sendAllReport(f);
		log.info(f.getName()+"发送信息收集数据作业结束");
	}

}
