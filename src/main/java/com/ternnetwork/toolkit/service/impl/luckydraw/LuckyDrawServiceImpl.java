package com.ternnetwork.toolkit.service.impl.luckydraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.DateUtils;

import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawAwardDao;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawDao;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawLogDao;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawResultDao;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawRuleDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDraw;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawAward;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawLog;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawResponse;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawResult;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawRule;
import com.ternnetwork.toolkit.service.luckydraw.LuckyDrawService;



@Service("luckyDrawService")
public class LuckyDrawServiceImpl implements LuckyDrawService {
	
	@Resource
	private LuckyDrawDao  luckyDrawDao;
	@Resource
	private LuckyDrawAwardDao  luckyDrawAwardDao;
	
	@Resource
	private LuckyDrawRuleDao  luckyDrawRuleDao;
	
	@Resource
	private LuckyDrawLogDao  luckyDrawLogDao;
	
	@Resource
	private LuckyDrawResultDao  luckyDrawResultDao;
	
	
	@Resource
	private UserService userService;
	
	public long idoAdd(LuckyDraw t){
		if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
			return 0L;
		}
		t.setCode(t.getCode().toUpperCase());
		Long totalCount=luckyDrawDao.getTotalCount("select count(t.id) from LuckyDraw t where t.code=?1", t.getCode());
		if(totalCount.longValue()>0L){
			return 1L;
		}
     	luckyDrawDao.persist(t);
		return 2L;
	}
	
	public long idoUpdate(LuckyDraw t){
		if(t.getStartTime().getTime()>=t.getEndTime().getTime()){
			return 0L;
		}
		t.setCode(t.getCode().toUpperCase());
		Long totalCount=luckyDrawDao.getTotalCount("select count(t.id) from LuckyDraw t where t.code=?1 and t.id!=?2", t.getCode(),t.getId());
		if(totalCount.longValue()>0L){
			return 1L;
		}
		luckyDrawDao.saveOrUpdate(t);
		return 2L;
	}
	
	
	public List<LuckyDraw> findAllByCurrentWeek(String codePrefix){
		Date startTime=DateUtils.getMonday(new Date());
		Date endTime=DateUtils.getSunday(new Date());
		String startTimeStr=DateUtils.format(startTime, DateUtils.FORMAT_DATE_DEFAULT)+" 00:00:00";
		String endTimeStr=DateUtils.format(endTime, DateUtils.FORMAT_DATE_DEFAULT)+" 23:59:59";
		startTime=DateUtils.parseDate(startTimeStr, DateUtils.FORMAT_DATE_TIME_DEFAULT);
		endTime=DateUtils.parseDate(endTimeStr, DateUtils.FORMAT_DATE_TIME_DEFAULT);
		return luckyDrawDao.findAll("from LuckyDraw t where t.startTime>=?1 and t.endTime<=?2 and t.code like ?3", startTime,endTime,codePrefix+"%");
	}
	
	
	public Page<LuckyDraw> query(String code,String name,String startTime,String endTime,Boolean enable,Page page){
	    StringBuffer jpql=new StringBuffer("from LuckyDraw t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(code)){
			params.add(code);
			jpql.append(" and t.code=?"+params.size());
		}
		if(StringUtils.isNotEmpty(name)){
			params.add(name);
			jpql.append(" and t.name=?"+params.size());
		}
		if(enable!=null){
			params.add(enable);
			jpql.append(" and t.enable=?"+params.size());
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
		return luckyDrawDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(String code,String name,String startTime,String endTime,Boolean enable,Page page){
		Page<LuckyDraw> result=query(code, name, startTime, endTime, enable,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public LuckyDraw findByCode(String code){
		List<LuckyDraw> list=luckyDrawDao.findAll("from LuckyDraw where code=?1", code);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public LuckyDrawResponse idoDraw(String code){
		User user=userService.getCurrentUser();
        LuckyDrawResponse resp=new LuckyDrawResponse();
		
		if(user==null){
			resp.setStatus(0);
			resp.setMessage("用户未登录!");
			return resp;
		}
		LuckyDraw luckyDraw=findByCode(code);
		if(luckyDraw==null){
			resp.setStatus(1);
			resp.setMessage("抽奖活动不存在!");
			return resp;
		}
		if(luckyDraw.getEnable()==false){
			resp.setStatus(2);
			resp.setMessage("抽奖活动已停用!");
			return resp;
		}
		if(luckyDraw.getStartTime().getTime()>(new Date()).getTime()){
			resp.setStatus(3);
			resp.setMessage("抽奖活动未开始!");
			return resp;
		}
		if(luckyDraw.getEndTime().getTime()<(new Date()).getTime()){
			resp.setStatus(4);
			resp.setMessage("抽奖活动已结束!");
			return resp;
		}
		if(!checkAuth(luckyDraw.getParticipantRules(),user.getRoleIds())){
			resp.setStatus(5);
			resp.setMessage("当前用户无参与抽奖资格!");
			return resp;
		}
		if(luckyDraw.getChanceQty()!=0){//如果有设定最大参与抽奖次数
			long c=getLuckyDrawLogTotalCountByLuckyDrawIdAndCreaterId(luckyDraw.getId(), user.getId());
			if(c>=luckyDraw.getChanceQty().longValue()){
			 resp.setStatus(7);
			 resp.setMessage("抽奖次数超限!");
			 return resp;
			}
		}
		if(luckyDraw.getAwardQty()!=0){//如果有设定最大中奖次数
			long c=getLuckyDrawResultTotalCountByLuckyDrawIdAndCreaterId(luckyDraw.getId(), user.getId());
			if(c>=luckyDraw.getAwardQty().longValue()){
			 resp.setStatus(8);
			 resp.setMessage("中奖次数超限!");
			 return resp;
			}
		}
		LuckyDrawLog drawLog=new LuckyDrawLog();
		drawLog.setUser(user);
		drawLog.setLuckyDraw(luckyDraw);
		List<LuckyDrawRule> luckyDrawRuleList=queryAllLuckyDrawRuleByLuckyDrawId(luckyDraw.getId());
		Date currentDate=new Date();
		List<LuckyDrawRule> needToDrawList=new ArrayList<LuckyDrawRule>();
		for(LuckyDrawRule t:luckyDrawRuleList){
			if(t.getAwardDate().getTime()<=currentDate.getTime()){//抽奖日期在当天或是当天之前
				if(t.getRemainingQty()>0){
					needToDrawList.add(t);
				}
			}
		}
		    int rate=luckyDraw.getAwardRate();
		    for(int i=0;i<rate;i++){//添加非奖品随机对象
		    	LuckyDrawRule randomLDR=new LuckyDrawRule();
		    	randomLDR.setId(0L);
		    	needToDrawList.add(randomLDR);
		    }
		    Collections.shuffle(needToDrawList); //随机打乱
			LuckyDrawRule needRule=needToDrawList.get(0);//抽取第一条
			if(needRule.getId()!=0L){//抽到合法奖品
				
			    Long awardId=needRule.getAward().getId();
				luckyDrawAwardDao.evictEntity(LuckyDrawAward.class, awardId);//清除缓存中的对象
				LuckyDrawAward needAward=luckyDrawAwardDao.findById(LuckyDrawAward.class, awardId,LockModeType.PESSIMISTIC_WRITE);
				luckyDrawRuleDao.evictEntity(LuckyDrawRule.class,needRule.getId());//清除缓存中的对象，否则无法锁定
				needRule=luckyDrawRuleDao.findById(LuckyDrawRule.class,needRule.getId(),LockModeType.PESSIMISTIC_WRITE);//锁定每日抽奖规则
				if(needAward.getRemainingQty()>0&&needRule.getRemainingQty()>0){//抽中奖品奖池余数与当前抽奖规则中奖品的余数大于0
				needAward.setRemainingQty(needAward.getRemainingQty()-1);//奖品总余数减一
				luckyDrawAwardDao.update(needAward);
				needRule.setRemainingQty(needRule.getRemainingQty()-1);//当前规则奖品余下奖品减一
				luckyDrawRuleDao.update(needRule);
				LuckyDrawResult result=new LuckyDrawResult();
				result.setAward(needAward);
				result.setUser(user);
				luckyDrawResultDao.persist(result);//保存中奖结果 
				drawLog.setWin(true);//设置抽奖日志状态为抽中
		        resp.setStatus(9);
				resp.setMessage(needRule.getAward().getName());
				resp.setCode(needRule.getAward().getCode());
				resp.setResultId(String.valueOf(result.getId()));
				}else{
					drawLog.setWin(false);
					resp.setStatus(10);
				    resp.setMessage("抱歉,未抽中!");
				}
			
		}else{
			drawLog.setWin(false);
			resp.setStatus(10);
		    resp.setMessage("抱歉,未抽中!");
		}
		luckyDrawLogDao.persist(drawLog);
		return resp;
	}
	
	public LuckyDrawResponse idoDraw(String code,String mobilePhoneNo){
	   User user=userService.findByName(mobilePhoneNo);
       LuckyDrawResponse resp=new LuckyDrawResponse();
		
		if(user==null){
			resp.setStatus(0);
			resp.setMessage("用户未登录!");
			return resp;
		}
		LuckyDraw luckyDraw=findByCode(code);
		if(luckyDraw==null){
			resp.setStatus(1);
			resp.setMessage("抽奖活动不存在!");
			return resp;
		}
		if(luckyDraw.getEnable()==false){
			resp.setStatus(2);
			resp.setMessage("抽奖活动已停用!");
			return resp;
		}
		if(luckyDraw.getStartTime().getTime()>(new Date()).getTime()){
			resp.setStatus(3);
			resp.setMessage("抽奖活动未开始!");
			return resp;
		}
		if(luckyDraw.getEndTime().getTime()<(new Date()).getTime()){
			resp.setStatus(4);
			resp.setMessage("抽奖活动已结束!");
			return resp;
		}
		if(!checkAuth(luckyDraw.getParticipantRules(),user.getRoleIds())){
			resp.setStatus(5);
			resp.setMessage("当前用户无参与抽奖资格!");
			return resp;
		}
		if(luckyDraw.getChanceQty()!=0){//如果有设定最大参与抽奖次数
			long c=getLuckyDrawLogTotalCountByLuckyDrawIdAndCreaterId(luckyDraw.getId(), user.getId());
			if(c>=luckyDraw.getChanceQty().longValue()){
			 resp.setStatus(7);
			 resp.setMessage("抽奖次数超限!");
			 return resp;
			}
		}
		if(luckyDraw.getAwardQty()!=0){//如果有设定最大中奖次数
			long c=getLuckyDrawResultTotalCountByLuckyDrawIdAndCreaterId(luckyDraw.getId(), user.getId());
			if(c>=luckyDraw.getAwardQty().longValue()){
			 resp.setStatus(8);
			 resp.setMessage("中奖次数超限!");
			 return resp;
			}
		}
		LuckyDrawLog drawLog=new LuckyDrawLog();
		drawLog.setUser(user);
		drawLog.setLuckyDraw(luckyDraw);
		List<LuckyDrawRule> luckyDrawRuleList=queryAllLuckyDrawRuleByLuckyDrawId(luckyDraw.getId());
		Date currentDate=new Date();
		List<LuckyDrawRule> needToDrawList=new ArrayList<LuckyDrawRule>();
		for(LuckyDrawRule t:luckyDrawRuleList){
			if(t.getAwardDate().getTime()<=currentDate.getTime()){//抽奖日期在当天或是当天之前
				if(t.getRemainingQty()>0){
					needToDrawList.add(t);
				}
			}
		}
		    int rate=luckyDraw.getAwardRate();
		    for(int i=0;i<rate;i++){//添加非奖品随机对象
		    	LuckyDrawRule randomLDR=new LuckyDrawRule();
		    	randomLDR.setId(0L);
		    	needToDrawList.add(randomLDR);
		    }
		    Collections.shuffle(needToDrawList); //随机打乱
			LuckyDrawRule needRule=needToDrawList.get(0);//抽取第一条
			if(needRule.getId()!=0L){//抽到合法奖品
				
			    Long awardId=needRule.getAward().getId();
				luckyDrawAwardDao.evictEntity(LuckyDrawAward.class, awardId);//清除缓存中的对象
				LuckyDrawAward needAward=luckyDrawAwardDao.findById(LuckyDrawAward.class, awardId,LockModeType.PESSIMISTIC_WRITE);
				luckyDrawRuleDao.evictEntity(LuckyDrawRule.class,needRule.getId());//清除缓存中的对象，否则无法锁定
				needRule=luckyDrawRuleDao.findById(LuckyDrawRule.class,needRule.getId(),LockModeType.PESSIMISTIC_WRITE);//锁定每日抽奖规则
				if(needAward.getRemainingQty()>0&&needRule.getRemainingQty()>0){//抽中奖品奖池余数与当前抽奖规则中奖品的余数大于0
				needAward.setRemainingQty(needAward.getRemainingQty()-1);//奖品总余数减一
				luckyDrawAwardDao.update(needAward);
				needRule.setRemainingQty(needRule.getRemainingQty()-1);//当前规则奖品余下奖品减一
				luckyDrawRuleDao.update(needRule);
				LuckyDrawResult result=new LuckyDrawResult();
				result.setAward(needAward);
				result.setUser(user);
				luckyDrawResultDao.persist(result);//保存中奖结果 
				drawLog.setWin(true);//设置抽奖日志状态为抽中
		        resp.setStatus(9);
				resp.setMessage(needRule.getAward().getName());
				resp.setCode(needRule.getAward().getCode());
				resp.setResultId(String.valueOf(result.getId()));
				}else{
					drawLog.setWin(false);
					resp.setStatus(10);
				    resp.setMessage("抱歉,未抽中!");
				}
			
		}else{
			drawLog.setWin(false);
			resp.setStatus(10);
		    resp.setMessage("抱歉,未抽中!");
		}
		luckyDrawLogDao.persist(drawLog);
		return resp;
	}
	
	public LuckyDrawResponse idoDrawForWehcatUser(String code,String wechatId){
		   User user=userService.findWechatId(wechatId);
	       LuckyDrawResponse resp=new LuckyDrawResponse();
			
			if(user==null){
				resp.setStatus(0);
				resp.setMessage("用户未登录!");
				return resp;
			}
			LuckyDraw luckyDraw=findByCode(code);
			if(luckyDraw==null){
				resp.setStatus(1);
				resp.setMessage("抽奖活动不存在!");
				return resp;
			}
			if(luckyDraw.getEnable()==false){
				resp.setStatus(2);
				resp.setMessage("抽奖活动已停用!");
				return resp;
			}
			if(luckyDraw.getStartTime().getTime()>(new Date()).getTime()){
				resp.setStatus(3);
				resp.setMessage("抽奖活动未开始!");
				return resp;
			}
			if(luckyDraw.getEndTime().getTime()<(new Date()).getTime()){
				resp.setStatus(4);
				resp.setMessage("抽奖活动已结束!");
				return resp;
			}
			if(!checkAuth(luckyDraw.getParticipantRules(),user.getRoleIds())){
				resp.setStatus(5);
				resp.setMessage("当前用户无参与抽奖资格!");
				return resp;
			}
			if(luckyDraw.getChanceQty()!=0){//如果有设定最大参与抽奖次数
				long c=getLuckyDrawLogTotalCountByLuckyDrawIdAndCreaterId(luckyDraw.getId(), user.getId());
				if(c>=luckyDraw.getChanceQty().longValue()){
				 resp.setStatus(7);
				 resp.setMessage("抽奖次数超限!");
				 return resp;
				}
			}
			if(luckyDraw.getAwardQty()!=0){//如果有设定最大中奖次数
				long c=getLuckyDrawResultTotalCountByLuckyDrawIdAndCreaterId(luckyDraw.getId(), user.getId());
				if(c>=luckyDraw.getAwardQty().longValue()){
				 resp.setStatus(8);
				 resp.setMessage("中奖次数超限!");
				 return resp;
				}
			}
			LuckyDrawLog drawLog=new LuckyDrawLog();
			drawLog.setUser(user);
			drawLog.setLuckyDraw(luckyDraw);
			List<LuckyDrawRule> luckyDrawRuleList=queryAllLuckyDrawRuleByLuckyDrawId(luckyDraw.getId());
			Date currentDate=new Date();
			List<LuckyDrawRule> needToDrawList=new ArrayList<LuckyDrawRule>();
			for(LuckyDrawRule t:luckyDrawRuleList){
				if(t.getAwardDate().getTime()<=currentDate.getTime()){//抽奖日期在当天或是当天之前
					if(t.getRemainingQty()>0){
						needToDrawList.add(t);
					}
				}
			}
			    int rate=luckyDraw.getAwardRate();
			    for(int i=0;i<rate;i++){//添加非奖品随机对象
			    	LuckyDrawRule randomLDR=new LuckyDrawRule();
			    	randomLDR.setId(0L);
			    	needToDrawList.add(randomLDR);
			    }
			    Collections.shuffle(needToDrawList); //随机打乱
				LuckyDrawRule needRule=needToDrawList.get(0);//抽取第一条
				if(needRule.getId()!=0L){//抽到合法奖品
					
				    Long awardId=needRule.getAward().getId();
					luckyDrawAwardDao.evictEntity(LuckyDrawAward.class, awardId);//清除缓存中的对象
					LuckyDrawAward needAward=luckyDrawAwardDao.findById(LuckyDrawAward.class, awardId,LockModeType.PESSIMISTIC_WRITE);
					luckyDrawRuleDao.evictEntity(LuckyDrawRule.class,needRule.getId());//清除缓存中的对象，否则无法锁定
					needRule=luckyDrawRuleDao.findById(LuckyDrawRule.class,needRule.getId(),LockModeType.PESSIMISTIC_WRITE);//锁定每日抽奖规则
					if(needAward.getRemainingQty()>0&&needRule.getRemainingQty()>0){//抽中奖品奖池余数与当前抽奖规则中奖品的余数大于0
					needAward.setRemainingQty(needAward.getRemainingQty()-1);//奖品总余数减一
					luckyDrawAwardDao.update(needAward);
					needRule.setRemainingQty(needRule.getRemainingQty()-1);//当前规则奖品余下奖品减一
					luckyDrawRuleDao.update(needRule);
					LuckyDrawResult result=new LuckyDrawResult();
					result.setAward(needAward);
					result.setUser(user);
					luckyDrawResultDao.persist(result);//保存中奖结果 
					drawLog.setWin(true);//设置抽奖日志状态为抽中
			        resp.setStatus(9);
					resp.setMessage(needRule.getAward().getName());
					resp.setCode(needRule.getAward().getCode());
					resp.setAward(needRule.getAward().getName());
					resp.setResultId(String.valueOf(result.getId()));
					}else{
						drawLog.setWin(false);
						resp.setStatus(10);
					    resp.setMessage("抱歉,未抽中!");
					}
				
			}else{
				drawLog.setWin(false);
				resp.setStatus(10);
			    resp.setMessage("抱歉,未抽中!");
			}
			luckyDrawLogDao.persist(drawLog);
			return resp;
		}
	
	
	
	public long getLuckyDrawLogTotalCountByLuckyDrawIdAndCreaterId(long awardId,long userId){
		return luckyDrawLogDao.getTotalCount("select count(t.id) from LuckyDrawLog t where t.luckyDraw.id=?1 and t.user.id=?2 ", awardId,userId);
	}
	
	
	public long getLuckyDrawResultTotalCountByLuckyDrawIdAndCreaterId(long luckyDrawId,long userId){
		return luckyDrawResultDao.getTotalCount("select count(id) from LuckyDrawResult  where award.luckyDraw.id=?1 and user.id=?2 ", luckyDrawId,userId);
    }
	
	public List<LuckyDrawRule> queryAllLuckyDrawRuleByLuckyDrawId(long luckyDrawId){
		return luckyDrawRuleDao.findAll("from LuckyDrawRule where award.luckyDraw.id=?1", luckyDrawId);
	}
	
	
	private Boolean checkAuth(String participantRules,String userRoleIds){
		if(participantRules==null||"".equals(participantRules)){
			return true;
		}
		String[] participantRulesArray=participantRules.split(",");
		String[] userRoleIdsArray=userRoleIds.split(",");
		for(int i=0;i<participantRulesArray.length;i++){
			String participantRule=participantRulesArray[i];
			for(int k=0;k<userRoleIdsArray.length;k++){
				String userRoleId=userRoleIdsArray[k];
				if(participantRule.equals(userRoleId)){
					return true;
				}
				
			}
		}
		return false;
	}

}
