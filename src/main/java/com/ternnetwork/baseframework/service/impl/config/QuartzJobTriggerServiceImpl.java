package com.ternnetwork.baseframework.service.impl.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.dao.config.QuartzJobDao;
import com.ternnetwork.baseframework.dao.config.QuartzJobTriggerDao;
import com.ternnetwork.baseframework.model.config.QuartzJob;
import com.ternnetwork.baseframework.model.config.QuartzJobTrigger;
import com.ternnetwork.baseframework.service.config.QuartzJobTriggerService;
import com.ternnetwork.baseframework.service.job.JobService;


@Service("quartzJobTriggerService")
public class QuartzJobTriggerServiceImpl implements QuartzJobTriggerService {
	
	@Resource
	private QuartzJobTriggerDao quartzJobTriggerDao;
	
	@Resource
	private QuartzJobDao quartzJobDao;
	
	@Resource
	private JobService jobService;
	
	public QuartzJobTrigger doFindById(long id){
		return quartzJobTriggerDao.getReferenceById(QuartzJobTrigger.class, id);
	}
	
	public long idoAdd(QuartzJobTrigger t)throws Exception{
		if(StringUtils.isEmpty(t.getName())){
			return 0L;
		}
		if(StringUtils.isEmpty(t.getGroup())){
			return 1L;
		}
		if(StringUtils.isEmpty(t.getCronExpression())){
			return 2L;
		}
		Long totalCount=quartzJobTriggerDao.getTotalCount("select count(t.id) from QuartzJobTrigger t where t.name=?1", t.getName());
		if(totalCount.longValue()>0L){
			return 3L;
		}
		quartzJobTriggerDao.persist(t);
		addJob(t.getJob().getId(),t);
		return 4L;
	}
	
	
	private void addJob(long jobId,QuartzJobTrigger quartzJobTriggerNew) throws SchedulerException, ClassNotFoundException{
		QuartzJob job=quartzJobDao.findById(QuartzJob.class,jobId);
		if(job.getEnable()==true){
			job.getTriggers().removeAll(job.getTriggers());
			job.getTriggers().add(quartzJobTriggerNew);
			jobService.startJob(job);
		}
	}
	
	private void updateJob(long jobId,QuartzJobTrigger oldQuartzJobTrigger,QuartzJobTrigger quartzJobTriggerNew) throws SchedulerException, ClassNotFoundException{
		QuartzJob job=quartzJobDao.findById(QuartzJob.class,jobId);
		if(job.getEnable()==true){
			job.getTriggers().add(oldQuartzJobTrigger);
			jobService.deleteJob(job);
			job.getTriggers().remove(oldQuartzJobTrigger);
			job.getTriggers().add(quartzJobTriggerNew);
			jobService.startJob(job);
		}
	}
	
	
	
	
	private void deleteJob(long jobId,QuartzJobTrigger quartzJobTriggerNew) throws SchedulerException, ClassNotFoundException{
		QuartzJob job=quartzJobDao.findById(QuartzJob.class,jobId);
		if(job.getEnable()==true){
			job.getTriggers().removeAll(job.getTriggers());
			job.getTriggers().add(quartzJobTriggerNew);
			jobService.deleteJob(job);
		}
	}
	
	public long idoUpdate(QuartzJobTrigger t)throws Exception{
		if(StringUtils.isEmpty(t.getName())){
			return 0L;
		}
		if(StringUtils.isEmpty(t.getGroup())){
			return 1L;
		}
		if(StringUtils.isEmpty(t.getCronExpression())){
			return 2L;
		}
		Long totalCount=quartzJobTriggerDao.getTotalCount("select count(t.id) from QuartzJobTrigger t where t.name=?1 and t.id!=?2", t.getName(),t.getId());

		if(totalCount.longValue()>0L){
			return 3L;
		}
		QuartzJobTrigger oldQuartzJobTrigger=quartzJobTriggerDao.findById(QuartzJobTrigger.class, t.getId());
		quartzJobTriggerDao.saveOrUpdate(t);
		updateJob(t.getJob().getId(),oldQuartzJobTrigger,t);
		return 4L;
	}
	
	public long idoDelete(Long id)throws Exception{
		QuartzJobTrigger t=quartzJobTriggerDao.findById(QuartzJobTrigger.class, id);
		if(t==null){
			return 0L;
		}
		quartzJobTriggerDao.delete(t);
		jobService.deleteJobTrigger(t);
		return 1L;
	}

}
