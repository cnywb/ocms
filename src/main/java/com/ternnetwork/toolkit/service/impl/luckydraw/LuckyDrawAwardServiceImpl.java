package com.ternnetwork.toolkit.service.impl.luckydraw;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawAwardDao;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawResultDao;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawRuleDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawAward;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawRule;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawAwardService;



@Service("luckyDrawAwardService")
public class LuckyDrawAwardServiceImpl implements LuckyDrawAwardService {

	@Resource
	private LuckyDrawAwardDao luckyDrawAwardDao;
	@Resource
	private LuckyDrawResultDao luckyDrawResultDao;
	
	@Resource
	private LuckyDrawRuleDao luckyDrawRuleDao;
	
	public long idoAdd(LuckyDrawAward t){
	
		Long totalCount=luckyDrawAwardDao.getTotalCount("select count(t.id) from LuckyDrawAward t where t.code=?1 ",t.getCode());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		t.setCode(t.getCode().toUpperCase());
		t.setRemainingQty(t.getQuantity());
		luckyDrawAwardDao.persist(t);
		return 1L;
	}
	
	public long idoUpdate(LuckyDrawAward t){
		LuckyDrawAward luckyDrawAward=luckyDrawAwardDao.findById(LuckyDrawAward.class,t.getId(),LockModeType.PESSIMISTIC_WRITE);
		Long totalCount=luckyDrawAwardDao.getTotalCount("select count(t.id) from LuckyDrawAward t where t.code=?1 and t.id!=?2 ",t.getCode(),t.getId());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		t.setCode(t.getCode().toUpperCase());
		luckyDrawAward.setCode(t.getCode());
		luckyDrawAward.setName(t.getName());
		luckyDrawAward.setGrade(t.getGrade());
		luckyDrawAward.setImageUrl(t.getImageUrl());
		luckyDrawAward.setMemo(t.getMemo());
	
		if(luckyDrawAward.getQuantity().intValue()>t.getQuantity().intValue()){
			luckyDrawAward.setRemainingQty(luckyDrawAward.getRemainingQty()-(luckyDrawAward.getQuantity().intValue()-t.getQuantity().intValue()));
		}
		if(luckyDrawAward.getQuantity().intValue()<t.getQuantity().intValue()){
			luckyDrawAward.setRemainingQty(luckyDrawAward.getRemainingQty()+(t.getQuantity().intValue()-luckyDrawAward.getQuantity().intValue()));
		}
		luckyDrawAward.setQuantity(t.getQuantity());
		luckyDrawAwardDao.saveOrUpdate(luckyDrawAward);
		return 1L;
	}
	
	public Page<LuckyDrawAward> query(String luckyDrawId,String code,String name,String startTime,String endTime,Page page){
	    StringBuffer jpql=new StringBuffer("from LuckyDrawAward t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(luckyDrawId)){
			params.add(Long.parseLong(luckyDrawId));
			jpql.append(" and t.luckyDraw.id=?"+params.size());
		}
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name);
			jpql.append(" and t.name=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(startTime)){
			params.add(DateUtils.parseDate(startTime+" 00:00:00",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime>=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(endTime)){
			params.add(DateUtils.parseDate(endTime+" 23:59:59",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime<=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return luckyDrawAwardDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(String luckyDrawId,String code,String name,String startTime,String endTime,Page page){
		Page<LuckyDrawAward> result=query(luckyDrawId,code, name, startTime, endTime, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public long idoDelete(long id){
		LuckyDrawAward luckyDrawAward=luckyDrawAwardDao.findById(LuckyDrawAward.class,id,LockModeType.PESSIMISTIC_WRITE);
		if(luckyDrawAward.getLuckyDraw().getEnable()==true){
			return 0L;//活动还在启用中无法删除
		}
		long totalCount=luckyDrawResultDao.getTotalCount("select count(id) from LuckyDrawResult where award.id=?1", luckyDrawAward.getId());
		if(totalCount>0){
			return 1L;//已有中奖记录无法删除
		}
		List<LuckyDrawRule> ruleList=luckyDrawRuleDao.findAll("from LuckyDrawRule where award.id=?1", id);
		for(LuckyDrawRule rule:ruleList){
			rule=luckyDrawRuleDao.findById(LuckyDrawRule.class, rule.getId(),LockModeType.PESSIMISTIC_WRITE);
		
			luckyDrawRuleDao.delete(rule);
		}
		
		luckyDrawAwardDao.delete(luckyDrawAward);
		return 2;
	}
	
	public List<LuckyDrawAward> findAllByluckyDrawId(Long luckyDrawId){
		return luckyDrawAwardDao.findAll("from LuckyDrawAward where luckyDraw.id=?1", luckyDrawId);
	}
	
	public String findAllByluckyDrawIdToJsonStr(Long luckyDrawId){
		return JSONUtils.objectToJson(findAllByluckyDrawId(luckyDrawId));  
	}
	
}
