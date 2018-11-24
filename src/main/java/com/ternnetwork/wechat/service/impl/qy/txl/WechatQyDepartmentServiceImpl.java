package com.ternnetwork.wechat.service.impl.qy.txl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.wechat.model.qy.txl.WechatQyDepartment;
import com.ternnetwork.wechat.model.qy.txl.WechatQyDepartmentResponse;
import com.ternnetwork.wechat.model.ui.WechatDepartmentZtree;
import com.ternnetwork.wechat.service.qy.txl.WechatQyDepartmentService;
import com.ternnetwork.wechat.util.WechatQyDepartmentUtil;


@Service("wechatQyDepartmentService")
public class WechatQyDepartmentServiceImpl implements WechatQyDepartmentService {
	
	
	@Resource
	private SystemParameterService systemParameterService;
	
	public  String query(boolean checked){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String json= WechatQyDepartmentUtil.get(corpId, corpSecret);
		WechatQyDepartmentResponse wdr=JSONUtils.jsonToObject(json, WechatQyDepartmentResponse.class);
	    return convertToZTreeJSON(wdr.getDepartment());
	}
	
	
	private String convertToZTreeJSON(List<WechatQyDepartment> department){
		List<WechatDepartmentZtree> retVal=new ArrayList<WechatDepartmentZtree>();
		for(WechatQyDepartment d:department){
			WechatDepartmentZtree tree=new WechatDepartmentZtree();
		    tree.setId(d.getId());
		    tree.setpId(d.getParentid());
		    tree.setName(d.getName());
		    tree.setOrder(d.getOrder());
		    tree.setParentid(d.getParentid());
		    retVal.add(tree);
		}
		return retVal.toString();
	}
	
	
	public  String create(WechatQyDepartment t){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String json= WechatQyDepartmentUtil.create(corpId, corpSecret,JSONUtils.objectToJson(t));
	    return json;
	}
	
	public  String delete(String id){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String json= WechatQyDepartmentUtil.delete(corpId, corpSecret, id);
	    return json;
	}
	
	public  String update(WechatQyDepartment t){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		String json= WechatQyDepartmentUtil.update(corpId, corpSecret, JSONUtils.objectToJson(t));
	    return json;
	}
	
	
	
	

}
