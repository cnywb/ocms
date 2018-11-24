package com.ternnetwork.baseframework.service.impl.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.dao.log.AuthenticationLogDao;
import com.ternnetwork.baseframework.model.log.AuthenticationLog;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.log.AuthenticationLogService;
import com.ternnetwork.baseframework.util.DateUtils;


@Service("authenticationLogService")
public class AuthenticationLogServiceImpl implements AuthenticationLogService {
	
	@Resource
	private AuthenticationLogDao authenticationLogDao;
	
	
	public void idoAdd(AuthenticationLog t){
		authenticationLogDao.persist(t);
	}
	
	public void idoAddLogoutLog(String sessionId){
		authenticationLogDao.bulkUpdate("update AuthenticationLog set updateTime=?1 where sessionId=?2", (new Date()),sessionId);
	}
	
	
	public void idoAdd(String address,String userName){
		AuthenticationLog t=new AuthenticationLog();
		t.setAddress(address);
		t.setUserName(userName);
		authenticationLogDao.persist(t);
	}
	
	public Page<AuthenticationLog> query(AuthenticationLog t,String createTimeMin,String createTimeMax,Page page){
	    StringBuffer jpql=new StringBuffer("from AuthenticationLog t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(t.getUserName())){
			params.add(t.getUserName());
			jpql.append(" and t.userName=?"+params.size());
		}
		if(t.getResult()!=null){
			params.add(t.getResult());
			jpql.append(" and t.result=?"+params.size());
		}
		if(!StringUtils.isEmpty(createTimeMin)){
			params.add(DateUtils.parseDate(createTimeMin+" 00:00:00",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime>=?"+params.size());
		}
		if(!StringUtils.isEmpty(createTimeMax)){
			params.add(DateUtils.parseDate(createTimeMax+" 23:59:59",DateUtils.FORMAT_DATE_TIME_DEFAULT));
			jpql.append(" and t.createTime<=?"+params.size());
		}
		return authenticationLogDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	public String queryToJsonStr(AuthenticationLog t,String createTimeMin,String createTimeMax,Page page){
		Page<AuthenticationLog> result=query(t, createTimeMin, createTimeMax, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
    }

}
