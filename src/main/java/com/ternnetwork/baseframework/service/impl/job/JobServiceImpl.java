package com.ternnetwork.baseframework.service.impl.job;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import  org.quartz.JobBuilder;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.dao.config.QuartzJobDao;
import com.ternnetwork.baseframework.model.config.QuartzJob;
import com.ternnetwork.baseframework.model.config.QuartzJobTrigger;
import com.ternnetwork.baseframework.service.job.JobService;





@Service("jobService")
public class JobServiceImpl implements JobService {
	
	@Resource
    private QuartzJobDao quartzJobDao;
	
	public void doInitScheduler() throws Exception{
		startAllJob();
	}
	public void deleteJob(QuartzJob quartzJob) throws SchedulerException{
	     JobKey jk=new JobKey(quartzJob.getName(),quartzJob.getGroup());
		 Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
		  Set<QuartzJobTrigger>  quartzJobTriggers=quartzJob.getTriggers();
	         for(QuartzJobTrigger quartzJobTrigger:quartzJobTriggers){
	        	 TriggerKey triggerKey=new TriggerKey(quartzJobTrigger.getName(),quartzJobTrigger.getGroup());
	           	 sched.pauseTrigger(triggerKey);
	        	 sched.unscheduleJob(triggerKey);
	         }
	
		 sched.deleteJob(jk);
	}
	
	public void deleteJobTrigger(QuartzJobTrigger quartzJobTrigger) throws SchedulerException{
		    Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
		    TriggerKey triggerKey=new TriggerKey(quartzJobTrigger.getName(),quartzJobTrigger.getGroup());
	        sched.pauseTrigger(triggerKey);
	        sched.unscheduleJob(triggerKey);
	}
	
	public void deleteJobs(List<String> keys) throws SchedulerException{
		 List<JobKey> JobKeys=new ArrayList<JobKey>();
		 for(String key:keys){
			 JobKey jk=new JobKey(key);
			 JobKeys.add(jk);
		 }
		 Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
		 sched.deleteJobs(JobKeys);
	}
	public void startAllJob() throws ClassNotFoundException, SchedulerException{
		List<QuartzJob> quartzJobList=quartzJobDao.findAll("from QuartzJob where enable=?1", true);
		for(QuartzJob quartzJob:quartzJobList){
			startJob(quartzJob);
		}
	}
	
	
  	public void startJob(QuartzJob quartzJob) throws SchedulerException, ClassNotFoundException{
		     Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
   	         Map<JobDetail,List<Trigger>> map=new HashMap<JobDetail,List<Trigger>>();
	         JobDetail job =JobBuilder.newJob((Class<? extends Job>)Class.forName(quartzJob.getClassName())).withIdentity(quartzJob.getName(),quartzJob.getGroup()).build();
	         List<Trigger> triggerList=new ArrayList<Trigger>();
	         Set<QuartzJobTrigger>  quartzJobTriggers=quartzJob.getTriggers();
	         for(QuartzJobTrigger quartzJobTrigger:quartzJobTriggers){
	        	 CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzJobTrigger.getName(), quartzJobTrigger.getGroup()).withSchedule(CronScheduleBuilder.cronSchedule(quartzJobTrigger.getCronExpression())).build();
	  	    	 triggerList.add(trigger);
	         }
	         map.put(job, triggerList);
	  	     sched.scheduleJobs(map,true);
	         if(!sched.isStarted()){
	  		      sched.start();   // 启动调度器 
             }
		
	}
  

}