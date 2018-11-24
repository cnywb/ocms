package com.ternnetwork.wechat.service.qy.txl;

import com.ternnetwork.wechat.model.auth.QyAuthUserInfo;
import com.ternnetwork.wechat.model.auth.QyUserDetailInfo;




public interface WechatQyUserService {
	public  String queryByDepartmentId(String departmentId,String status);
	public String create(String json);
	public String update(String json);
	public String delete(String id);
	public String queryById(String id);
	public QyAuthUserInfo getAuthUserInfo(String code);
	public QyUserDetailInfo getById(String id);
}
