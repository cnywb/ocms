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
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawRuleDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawAward;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawRule;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawRuleService;



@Service("luckyDrawRuleService")
public class LuckyDrawRuleServiceImpl implements LuckyDrawRuleService {
	
	@Resource
	private LuckyDrawRuleDao luckyDrawRuleDao;
	
	@Resource
	private LuckyDrawAwardDao luckyDrawAwardDao;
	
	public long idoAdd(LuckyDrawRule t){
		List<LuckyDrawRule> ruleList=findAllByAwardId(t.getAward().getId());
		Integer totalAwardQty=0;
		for(LuckyDrawRule rule:ruleList){
			rule=luckyDrawRuleDao.findById(LuckyDrawRule.class, rule.getId(),LockModeType.PESSIMISTIC_WRITE);
			totalAwardQty=totalAwardQty+rule.getAwardQty();
		}
		totalAwardQty=totalAwardQty+t.getAwardQty();
		LuckyDrawAward award=luckyDrawAwardDao.findById(LuckyDrawAward.class, t.getAward().getId(),LockModeType.PESSIMISTIC_WRITE);
		if(totalAwardQty>award.getQuantity()){
			return 0L;
		}
		t.setRemainingQty(t.getAwardQty());
		luckyDrawRuleDao.persist(t);
		return 1;
	}
	
	public long idoUpdate(LuckyDrawRule t){
		List<LuckyDrawRule> ruleList=findAllByAwardId(t.getAward().getId());
		Integer totalAwardQty=0;
		for(LuckyDrawRule rule:ruleList){
			if(rule.getId()!=t.getId()){
			  rule=luckyDrawRuleDao.findById(LuckyDrawRule.class, rule.getId(),LockModeType.PESSIMISTIC_WRITE);
			  totalAwardQty=totalAwardQty+rule.getAwardQty();
			}
		}
		totalAwardQty=totalAwardQty+t.getAwardQty();
		LuckyDrawAward award=luckyDrawAwardDao.findById(LuckyDrawAward.class, t.getAward().getId(),LockModeType.PESSIMISTIC_WRITE);

		if(totalAwardQty>award.getQuantity()){
			return 0L;
		}
		LuckyDrawRule luckyDrawRule=luckyDrawRuleDao.findById(LuckyDrawRule.class, t.getId(),LockModeType.PESSIMISTIC_WRITE);
		
		if(luckyDrawRule.getAwardQty().intValue()<t.getAwardQty().intValue()){
			luckyDrawRule.setRemainingQty(luckyDrawRule.getRemainingQty()+(t.getAwardQty().intValue()-luckyDrawRule.getAwardQty().intValue()));
		}
		if(luckyDrawRule.getAwardQty().intValue()>t.getAwardQty().intValue()){
			luckyDrawRule.setRemainingQty(luckyDrawRule.getRemainingQty()-(luckyDrawRule.getAwardQty().intValue()-t.getAwardQty().intValue()));
		}
		luckyDrawRule.setAwardQty(t.getAwardQty());
		luckyDrawRule.setAwardDate(t.getAwardDate());
		luckyDrawRule.setAward(t.getAward());
		luckyDrawRuleDao.saveOrUpdate(luckyDrawRule);
		return 1;
	}
	
	public long idoDelete(long id){
		LuckyDrawRule luckyDrawRule= luckyDrawRuleDao.findById(LuckyDrawRule.class, id,LockModeType.PESSIMISTIC_WRITE);
		LuckyDrawAward luckyDrawAward=luckyDrawAwardDao.findById(LuckyDrawAward.class,luckyDrawRule.getAward().getId());
		if(luckyDrawAward.getLuckyDraw().getEnable()==true){
			return 0L;//活动还在启用中无法删除
		}
		
		luckyDrawRuleDao.delete(luckyDrawRule);
		return 1L;
	}
	public List<LuckyDrawRule> findAllByAwardId(long  awardId){
		return luckyDrawRuleDao.findAll("from LuckyDrawRule t where t.award.id=?1", awardId);
	}

	public Page<LuckyDrawRule> query(String luckyDrawId,String code,String name,String startTime,String endTime,Page page){
	    StringBuffer jpql=new StringBuffer("from LuckyDrawRule t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(luckyDrawId)){
			params.add(Long.parseLong(luckyDrawId));
			jpql.append(" and t.award.luckyDraw.id=?"+params.size());
		}
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.award.luckyDraw.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name);
			jpql.append(" and t.award.name=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(startTime)){
			params.add(DateUtils.parseDate(startTime+" 00:00:00",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.awardDate>=?"+params.size());
		}
		
		if(!StringUtils.isEmpty(endTime)){
			params.add(DateUtils.parseDate(endTime+" 23:59:59",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.awardDate<=?"+params.size());
		}
		jpql.append(" order by t.id desc");
		return luckyDrawRuleDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(String luckyDrawId,String code,String name,String startTime,String endTime,Page page){
		Page<LuckyDrawRule> result=query(luckyDrawId,code, name, startTime, endTime, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
}
