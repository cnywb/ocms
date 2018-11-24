package com.ternnetwork.baseframework.service.security;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.wechat.model.auth.AuthUserInfo;



public interface UserService {
	public Page<User> query(User t,Page page);
	public String queryToJsonStr(User t,Page page);
	public long idoUpdate(User t);
	public long idoAdd(User t);
	public long idoAddByWechat(String wechatId);
	public long idoAddByWechat(String wechatId,String roleName,String realName,String mobilePhoneNo);
	public String idoLockUser(long id);
	public void idoUnlockUser(long id);
	public void idoResetPassword(long id,String newPassword);
	public String idoUpdatePassword(String orinPassword,String newPassword);
	public User getCurrentUser();
	public String getCurrentUserName();
	public void idoUpdateUserInfo(User t);
	public String querySellerByNameToJsonStr(String userName,Page page);
	public String idoUserRegister(String name,String password,Integer gender,String birthday,Integer sexuality) throws NoSuchAlgorithmException;
	public List<User>queryByLikeName(String name);
	public String queryByLikeNameJsonStr(String name);
	public String queryByNameJsonStr(String name);
	public List<User> queryAllByDepartmentId(Long departmentId);
	public User queryById(Long id);
	public String getAllGroupByDeparmentZTreeJSON();
	public long idoUpdateUserRole(long userId,String roleIds);
	public User findByName(String username);
	public void idoUpdateGzWechatUserInfo(AuthUserInfo t);
	public User findWechatId(String wechatId);
	public BaseResponse idoUpdateFullUserInfo(User t);
	public long idoAddByWechat(String wechatId,String roleName);
	public long idoAddPotentialCustomerUser(String mobilePhoneNo);
	public long idoAddCarOwnerUser(String mobilePhoneNo);
	public long idoAddOrUpdateCarOwnerUser(String wechatId,String mobilePhoneNo,String realName,String company,String address,String nickname);
	public long idoAddByQyWechat(String wechatId,String realName,String mobilePhone,String gender,String photo,String roleName);
}
