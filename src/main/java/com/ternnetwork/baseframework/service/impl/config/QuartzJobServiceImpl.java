package com.ternnetwork.baseframework.service.impl.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.dao.config.QuartzJobDao;
import com.ternnetwork.baseframework.dao.config.QuartzJobTriggerDao;
import com.ternnetwork.baseframework.model.config.QuartzJob;
import com.ternnetwork.baseframework.model.config.QuartzJobTrigger;
import com.ternnetwork.baseframework.model.ui.QuartzJobZtree;
import com.ternnetwork.baseframework.service.config.QuartzJobService;
import com.ternnetwork.baseframework.service.job.JobService;


@Service("quartzJobService")
public class QuartzJobServiceImpl implements QuartzJobService {
	
	
	@Resource
	private QuartzJobDao quartzJobDao;
	
	@Resource
	private QuartzJobTriggerDao quartzJobTriggerDao;

	@Resource
	private JobService jobService;
	
	
	public long idoAdd(QuartzJob t){
		if(StringUtils.isEmpty(t.getName())){
			return 0L;
		}
		if(StringUtils.isEmpty(t.getGroup())){
			return 1L;
		}
		if(StringUtils.isEmpty(t.getClassName())){
			return 2L;
		}
		Long totalCount=quartzJobDao.getTotalCount("select count(t.id) from QuartzJob t where t.name=?1", t.getName());
		if(totalCount.longValue()>0L){
			return 3L;
		}
		quartzJobDao.persist(t);
		return 4L;
	}
	
	
	
	public long idoUpdate(QuartzJob t) throws Exception{
		if(StringUtils.isEmpty(t.getName())){
			return 0L;
		}
		if(StringUtils.isEmpty(t.getGroup())){
			return 1L;
		}
		if(StringUtils.isEmpty(t.getClassName())){
			return 2L;
		}
		Long totalCount=quartzJobDao.getTotalCount("select count(t.id) from QuartzJob t where t.name=?1 and t.id!=?2", t.getName(),t.getId());
		
		if(totalCount.longValue()>0L){
			return 3L;
		}
		
		QuartzJob quartzJob=quartzJobDao.findById(QuartzJob.class, t.getId());
		quartzJobDao.saveOrUpdate(t);
		List<QuartzJobTrigger> quartzJobTriggerList=quartzJobTriggerDao.findAll("from QuartzJobTrigger where job.id=?1",t.getId());
		for (QuartzJobTrigger quartzJobTrigger:quartzJobTriggerList){
			t.getTriggers().add(quartzJobTrigger);
		}
		if(t.getEnable()==true){
			if(t.getTriggers().size()>0){
				jobService.deleteJob(quartzJob);
				jobService.startJob(t);
			}
		}else{
		     jobService.deleteJob(quartzJob);
		}
	  	return 4L;
	}
	
	public long idoDelete(Long id)throws Exception{
		QuartzJob t=quartzJobDao.findById(QuartzJob.class, id);
		if(t==null){
			return 0L;
		}
		quartzJobDao.delete(t);
		jobService.deleteJob(t);
		return 1L;
	}
	
	public List<QuartzJobZtree>getZtreeList(){
		
		List<QuartzJobZtree> retVal=new ArrayList<QuartzJobZtree>();
		
		List<QuartzJob> jobList=quartzJobDao.findAll("from QuartzJob");
		
		for(QuartzJob quartzJob:jobList){
			
			QuartzJobZtree tree=new QuartzJobZtree();
			tree.setClassName(quartzJob.getClassName());
			tree.setName(quartzJob.getName());
            tree.setGroup(quartzJob.getGroup());	
            tree.setEnable(quartzJob.getEnable());
            tree.setId("J_"+quartzJob.getId());
            tree.setParentId(0L);
    		tree.setRealId(quartzJob.getId());
    		tree.setIsParent(true);
            retVal.add(tree);
            Set<QuartzJobTrigger>  quartzJobTriggers=quartzJob.getTriggers();
   	        for(QuartzJobTrigger quartzJobTrigger:quartzJobTriggers){
	 			QuartzJobZtree triggerTree=new QuartzJobZtree();
	 			triggerTree.setName(quartzJobTrigger.getName());
	 			triggerTree.setGroup(quartzJobTrigger.getGroup());	
	 			triggerTree.setCronExpression(quartzJobTrigger.getCronExpression());
	 			triggerTree.setId("T_"+quartzJobTrigger.getId());
	 			triggerTree.setpId(tree.getId());
	 			triggerTree.setParentId(tree.getRealId());
	 			triggerTree.setRealId(quartzJobTrigger.getId());
	 			triggerTree.setIsParent(false);
	            retVal.add(triggerTree);
            }
		}
		return retVal;
	}
	
	
	
	
}
