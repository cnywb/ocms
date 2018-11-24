package com.ternnetwork.wechat.service.qy.txl;

import com.ternnetwork.wechat.model.qy.txl.WechatQyDepartment;

public interface WechatQyDepartmentService {
	
	public  String query(boolean checked);
	public  String create(WechatQyDepartment t);
	public  String delete(String id);
	public  String update(WechatQyDepartment t);

}
