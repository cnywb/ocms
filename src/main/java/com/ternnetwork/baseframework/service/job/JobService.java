package com.ternnetwork.baseframework.service.job;

import org.quartz.SchedulerException;

import com.ternnetwork.baseframework.model.config.QuartzJob;
import com.ternnetwork.baseframework.model.config.QuartzJobTrigger;





public interface JobService {
	
	public void doInitScheduler() throws Exception;
	public void startJob(QuartzJob quartzJob) throws SchedulerException, ClassNotFoundException;
	public void deleteJob(QuartzJob quartzJob) throws SchedulerException;
	public void deleteJobTrigger(QuartzJobTrigger quartzJobTrigger) throws SchedulerException;


}
