package com.ternnetwork.wechat.service.impl.share;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.wechat.dao.share.ShareLogDao;
import com.ternnetwork.wechat.model.share.ShareLog;
import com.ternnetwork.wechat.service.share.ShareLogService;



@Service("shareLogService")
public class ShareLogServiceImpl implements ShareLogService {
	
	
	@Resource
	private ShareLogDao shareLogDao;
	
	@Resource
	private UserService userService;
	
	
	
	public BaseResponse idoAdd(ShareLog t){
		BaseResponse res=new BaseResponse();
		t.setUser(userService.getCurrentUser());
		if(t.getUser()==null){
			res.setMessage("用户未登录！");
			res.setStatus(0);
			return res;
		}
		if(t.getShareType()==null){
			res.setMessage("分享类型不能为空！");
			res.setStatus(1);
			return res;
		}
		if(t.getWechatAccountType()==null){
			res.setMessage("微信账号类型不能为空！");
			res.setStatus(2);
			return res;
		}
		if(StringUtils.isEmpty(t.getCampaign())){
			res.setMessage("活动名不能为空！");
			res.setStatus(3);
			return res;
		}
		res.setMessage("操作成功！");
		res.setStatus(4);
		shareLogDao.persist(t);
		return res;
	}
	
	
	public BootstrapGrid queryToBootstrapGrid(String userName,String startTime,String endTime,String campaign,Page page){
		Page<ShareLog> result=query(userName, startTime, endTime,campaign,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid;
	}
	
	public String queryToJsonStr(String userName,String startTime,String endTime,String campaign,Page page){
		Page<ShareLog> result=query(userName, startTime, endTime,campaign,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Page<ShareLog> query(String userName,String startTime,String endTime,String campaign,Page page){
	    StringBuffer jpql=new StringBuffer("from ShareLog t where 1=1");
		List<Object> params=new ArrayList<Object>();
		
		if(StringUtils.isNotEmpty(userName)){
			params.add(userName);
			jpql.append(" and t.user.name=?"+params.size());
		}
		
		if(StringUtils.isNotEmpty(campaign)){
			params.add(campaign);
			jpql.append(" and t.campaign=?"+params.size());
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
		return shareLogDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
	
	

}
