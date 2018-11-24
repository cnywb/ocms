package com.ternnetwork.wechat.service.user;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.wechat.model.auth.AuthUserInfo;
import com.ternnetwork.wechat.model.auth.UserinfoAccessToken;

public interface WechatUserService {
	public String queryDyUser(Page page,String nextOpenid);

	public UserinfoAccessToken getDyUserinfoAccessToken(String code);

	public AuthUserInfo getDyAuthUserinfo(String code);

	public UserinfoAccessToken getFwUserinfoAccessToken(String code);

	public AuthUserInfo getFwAuthUserinfo(String code);

	public String queryFwUser(Page page, String nextOpenid);
}
