package com.ternnetwork.wechat.service.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.wechat.model.auth.AuthUserInfo;
import com.ternnetwork.wechat.model.auth.UserinfoAccessToken;
import com.ternnetwork.wechat.model.ui.WechatUserGrid;
import com.ternnetwork.wechat.model.user.UserResponse;
import com.ternnetwork.wechat.model.user.WechatUser;
import com.ternnetwork.wechat.service.user.WechatUserService;
import com.ternnetwork.wechat.util.AccessTokenUtil;
import com.ternnetwork.wechat.util.WechatUserUtil;


@Service("wechatUserService")
public class WechatUserServiceImpl implements WechatUserService {

	private static final Logger log = Logger.getLogger(WechatUserServiceImpl.class);

	@Resource
	private SystemParameterService systemParameterService;
	
	public String queryDyUser(Page page,String nextOpenid){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		String json=WechatUserUtil.queryDyUser(appId, appSecret, nextOpenid);
		return convertToUserResponse(page, json);
	}
	
	public String queryFwUser(Page page,String nextOpenid){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		String json=WechatUserUtil.queryDyUser(appId, appSecret, nextOpenid);
		return convertToUserResponse(page, json);
	}

	private String convertToUserResponse(Page page, String json) {
		UserResponse response=JSONUtils.jsonToObject(json, UserResponse.class);
		page.setResult(convertToWechatUser(response.getData().getOpenid()));
		page.setTotalCount(Integer.parseInt(response.getTotal()));
		WechatUserGrid grid=new WechatUserGrid(page);
		grid.setNextOpenid(response.getNext_openid());
		return grid.toString();
	}
	
	private List<WechatUser> convertToWechatUser(String[] openidArray){
		List<WechatUser> retVal=new ArrayList<WechatUser>();
		for(String openid:openidArray){
			WechatUser u=new WechatUser();
			u.setOpenid(openid);
			retVal.add(u);
		}
		return retVal;
	}
	
	
	/**
	 * 订阅号简单授权
	 * 只能得到openid
	 * @param code
	 * @return
	 */
	@Override
	public UserinfoAccessToken getDyUserinfoAccessToken(String code){
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return AccessTokenUtil.getDyUserinfoAccessToken(appId,appSecret,code);
	}
	/**
	 * 订阅号用户信息授权，需要用户点确认
	 * @param code
	 * @return
	 */
	@Override
	public AuthUserInfo getDyAuthUserinfo(String code){
		 try {
		UserinfoAccessToken token=getDyUserinfoAccessToken(code);
		log.info("订阅号用户信息:UserinfoAccessToken"+token.getAccess_token());
		String json=WechatUserUtil.getAuthUserinfo(token.getAccess_token(), token.getOpenid());
		log.info("订阅号用户信息:"+json);
		
		AuthUserInfo userInfo=JSONUtils.jsonToObject(json,AuthUserInfo.class);
		return userInfo;
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return null;
	}
	
	
	@Override
	public UserinfoAccessToken getFwUserinfoAccessToken(String code){
		String appId=systemParameterService.getValueByKey("WECHAT_FW_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_FW_APP_SECRET");
		return AccessTokenUtil.getFwUserinfoAccessToken(appId,appSecret,code);
	}
	/**
	 * 订阅号用户信息授权，需要用户点确认
	 * @param code
	 * @return
	 */
	@Override
	public AuthUserInfo getFwAuthUserinfo(String code){
		UserinfoAccessToken token=getFwUserinfoAccessToken(code);
		String json=WechatUserUtil.getAuthUserinfo(token.getAccess_token(), token.getOpenid());
		log.info("订阅号用户信息:"+json);
		AuthUserInfo userInfo=JSONUtils.jsonToObject(json,AuthUserInfo.class);
		return userInfo;
	}
	
}
