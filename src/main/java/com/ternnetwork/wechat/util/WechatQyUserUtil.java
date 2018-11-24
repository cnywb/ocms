package com.ternnetwork.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ternnetwork.wechat.model.auth.AccessToken;

public class WechatQyUserUtil {

	private static Logger log = LoggerFactory.getLogger(WechatQyUserUtil.class);

    public static String create_url = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
    
 	public static String get_by_id_url="https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USER_ID";
 	
	public static String update_url="https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	
	public static String delete_url="https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=DEL_ID";
	
	public static String get_by_department_id_url="https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DP_ID&fetch_child=FC&status=ST";

	
	public static String get_auth_user_info_url="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
	
	 public static String create(String corpId,String corpSecret,String json){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = create_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",json);
			return respJson;
     }
	 
	 public static String update(String corpId,String corpSecret,String json){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = update_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",json);
			return respJson;
     }
	 
	 public static String delete(String corpId,String corpSecret,String id){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = delete_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("DEL_ID",id);
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",null);
			return respJson;
    }
	 
	 public static String getById(String corpId,String corpSecret,String id){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = get_by_id_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("USER_ID", id);
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",null);
			return respJson;
    }
	 
	 /**
	  * 
	  * @param corpId
	  * @param corpSecret
	  * @param departmentId
	  * @param fetchChild 1/0：是否递归获取子部门下面的成员 
	  * @param status 0获取全部员工，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加
	  * @return
	  */
	 public static String getByDepartmentId(String corpId,String corpSecret,String departmentId,String fetchChild,String status){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = get_by_department_id_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("DP_ID", departmentId).replace("FC", fetchChild).replace("ST", status);
			String respJson = HttpConnectionUtil.httpRequest(url, "POST",null);
 			return respJson;
     }
	 
	 /**
	  * 按授权页面传过来的CODE来取得
	  * 微信用户的id或openid
	  * @param corpId
	  * @param corpSecret
	  * @param code
	  * @return
	  */
	 public static String getAuthUserInfo(String corpId,String corpSecret,String code){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = get_auth_user_info_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("CODE", code);
			String respJson = HttpConnectionUtil.httpRequest(url, "GET",null);
			return respJson;
    }
}
