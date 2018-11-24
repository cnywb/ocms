package com.ternnetwork.wechat.service.impl.qy.txl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.wechat.model.auth.QyAuthUserInfo;
import com.ternnetwork.wechat.model.auth.QyUserDetailInfo;
import com.ternnetwork.wechat.model.qy.txl.WechatQyUserResponse;
import com.ternnetwork.wechat.service.qy.txl.WechatQyUserService;
import com.ternnetwork.wechat.util.WechatQyUserUtil;


@Service("wechatQyUserService")
public class WechatQyUserServiceImpl implements WechatQyUserService{

	
	@Resource
	private SystemParameterService systemParameterService;
	
	
	public  String queryByDepartmentId(String departmentId,String status){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String json=WechatQyUserUtil.getByDepartmentId(corpId, corpSecret, departmentId, "1", status);
		WechatQyUserResponse wur=JSONUtils.jsonToObject(json, WechatQyUserResponse.class);
		Page page=new Page();
		page.setResult(wur.getUserlist());
		page.setPageSize(wur.getUserlist().size());
		page.setPageNo(0);
		page.setTotalCount(wur.getUserlist().size());
		BootstrapGrid grid=new BootstrapGrid(page);
		return grid.toString();
	}
	
	
	public String create(String json){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		return WechatQyUserUtil.create(corpId, corpSecret, json);
	}
	
	public String update(String json){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		return WechatQyUserUtil.update(corpId, corpSecret, json);
	}
	
	public String delete(String id){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		return WechatQyUserUtil.delete(corpId, corpSecret, id);
	}
	
	
	public String queryById(String id){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		return WechatQyUserUtil.getById(corpId, corpSecret, id);
	}
	public String getAuthUserInfoJsonStr(String code){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		return WechatQyUserUtil.getAuthUserInfo(corpId,corpSecret,code) ;
	}
	
    /**
     * 得到授权用户信息
     * @param code
     * @return
     */
	public QyAuthUserInfo getAuthUserInfo(String code){
		String json=getAuthUserInfoJsonStr(code);
		QyAuthUserInfo userInfo=JSONUtils.jsonToObject(json, QyAuthUserInfo.class);
		return userInfo;
	}
	
	
	public QyUserDetailInfo getById(String id){
		 String json=queryById(id);
		QyUserDetailInfo userInfo=JSONUtils.jsonToObject(json, QyUserDetailInfo.class);
		return userInfo;
	}
	
}
