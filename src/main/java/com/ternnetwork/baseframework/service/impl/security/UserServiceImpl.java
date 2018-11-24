package com.ternnetwork.baseframework.service.impl.security;


import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.dao.org.DepartmentDao;
import com.ternnetwork.baseframework.dao.security.UserDao;
import com.ternnetwork.baseframework.enums.Gender;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.org.Department;
import com.ternnetwork.baseframework.model.security.Role;
import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.model.security.UserRole;
import com.ternnetwork.baseframework.model.security.UserRoleId;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.service.security.RoleService;
import com.ternnetwork.baseframework.service.security.UserRoleService;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.service.util.HashService;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.ValidateUtils;
import com.ternnetwork.wechat.model.auth.AuthUserInfo;




@Service("userService")
public class UserServiceImpl implements UserService {
	
	
	@Resource
	private UserDao userDao;
	@Resource
	private HashService hashService;
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private RoleService roleService;

	@Autowired
	private DepartmentDao departmentDao;
	
	
	public Page<User> querySellerByName(String userName,Page page){
		    StringBuffer jpql=new StringBuffer("from User t where 1=1");
			List<Object> params=new ArrayList<Object>();
			if(userName!=null&&!"".equals(userName)){
				params.add(userName);
				jpql.append(" and t.name=?"+params.size());
			}
			jpql.append(" and t.id in(select ur.id.userId from UserRole ur where ur.role.nameEn='SELLER')");
			return userDao.query("select count(t.id) "+jpql.toString(), jpql.toString(), page, params.toArray());
	}
	
	public String querySellerByNameToJsonStr(String userName,Page page){
		Page<User> result=querySellerByName(userName, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
	
	public Page<User> query(User t,Page page){
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer jpql=new StringBuffer("from User t where 1=1");
		
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(t.getName())){
			params.add(t.getName()+"%");
			jpql.append(" and t.name like?"+params.size());
		}
		
		if(t.getEmail()!=null&&!"".equals(t.getEmail())){
			params.add(t.getEmail());
			jpql.append(" and t.email=?"+params.size());
		}
		if(t.getMobilePhone()!=null&&!"".equals(t.getMobilePhone())){
			params.add(t.getMobilePhone());
			jpql.append(" and t.mobilePhone=?"+params.size());
		}
		if(t.getAccountNonLocked()!=null&&!"".equals(t.getAccountNonLocked())){
			params.add(t.getAccountNonLocked());
			jpql.append(" and t.accountNonLocked=?"+params.size());
		}
		if(t.getEnable()!=null&&!"".equals(t.getEnable())){
			params.add(t.getEnable());
			jpql.append(" and t.enable=?"+params.size());
		}
		
		if(t.getTimeMin()!=null&&!"".equals(t.getTimeMin())){
			try {
				params.add(sdf.parse(t.getTimeMin()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jpql.append(" and t.createTime>=?"+params.size());
		}
		if(t.getTimeMax()!=null&&!"".equals(t.getTimeMax())){
			try {
				params.add(sdf.parse(t.getTimeMax()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jpql.append(" and t.createTime<=?"+params.size());
		}
		return userDao.query("select count(t.id) "+jpql.toString(), jpql.toString(), page, params.toArray());
	}
	
	
	public String queryToJsonStr(User t,Page page) {
		Page<User> result=query(t, page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}


	
	public long idoUpdate(User t) {
		User ut=userDao.findById(User.class, t.getId(),LockModeType.PESSIMISTIC_WRITE);
		ut.setRealName(t.getRealName());
		ut.setEmail(t.getEmail());
		ut.setMobilePhone(t.getMobilePhone());
		ut.setGender(t.getGender());
		ut.setAccountNonLocked(t.getAccountNonLocked());
		ut.setDepartment(t.getDepartment());
	    ut.setWechatId(t.getWechatId());
		if(!StringUtils.isEmpty(t.getPassword())){
		try {
			ut.setPassword(hashService.encryptSHA(t.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	
		ut=deleteUserRoleByRoleIds(t.getDeleteRoleIds(), ut);
		ut=setUserRoleByRoleIds(t.getRoleIds(), ut);
	    userDao.saveOrUpdate(ut);
		return 1L;
	}

	
	

	
	public long idoAdd(User t) {	
		if(userDao.findByName(t.getName())!=null){
		   return 0L;
	    }
		return doAdd(t);
	}
	
	public long idoAddByWechat(String wechatId){
		return idoAddByWechat(wechatId, "普通用户", "", "");
	}
	public long idoAddByWechat(String wechatId,String roleName){
		return idoAddByWechat(wechatId, roleName,"", "");
	}
	
	public long idoAddByWechat(String wechatId,String roleName,String realName,String mobilePhoneNo){
		if(!StringUtils.isEmpty(mobilePhoneNo)&&!ValidateUtils.isMobileNO(mobilePhoneNo)){
			  return 0L;
		}
		User t=new User();
		if(userDao.findWechatId(wechatId)!=null){
			   return 1L;
		}
		t.setName(wechatId);
		t.setPassword(wechatId);
		t.setWechatId(wechatId);
		t.setRealName(realName);
		List<Role> list=roleService.findAll("from Role where nameZh=?1",roleName);
		if(list.size()==0){
			return 2L;
		}
		Role role=list.get(0);
		t.setRoleIds(String.valueOf(role.getId()));
		doAdd(t);
		return 3L;
	}
	
	


	private long doAdd(User t) {
		if(t.getGender()==null){
			t.setGender(Gender.UNKNOWN);
		}
		t.setAccountNonExpired(true);
		t.setAccountNonLocked(true);
		t.setEnable(true);
		t.setCredentialsNonExpired(true);
		t.setCreateTime(new Date());
		t.setUpdateTime(t.getCreateTime());
		try {
			t.setPassword(hashService.encryptSHA(t.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		userDao.persist(t);
		setUserRoleByRoleIds(t.getRoleIds(),t);
		return 1L;
	}

	
	
	public String idoLockUser(long id){
		User u=userDao.findById(User.class, id);
		if(u.getName().equals("admin")){
			return "0";//超级管理员不能被锁定
		}
		u.setAccountLockedTime(new Date());
		u.setAccountNonLocked(false);
		u.setUpdateTime(new Date());
		userDao.saveOrUpdate(u);
		return "1";
	}
	
	public void idoUnlockUser(long id){
		User u=userDao.findById(User.class, id);
		u.setAccountLockedTime(null);
		u.setAccountNonLocked(true);
		userDao.saveOrUpdate(u);
	}
	
	public void idoResetPassword(long id,String newPassword){
		User u=userDao.findById(User.class, id);
		try {
			newPassword=hashService.encryptSHA(newPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		u.setPassword(newPassword);
		u.setUpdateTime(new Date());
		userDao.saveOrUpdate(u);
	}
	
	public String idoUpdatePassword(String orinPassword,String newPassword){
		if(StringUtils.isEmpty(orinPassword)){
			return "0";
		}
		if(StringUtils.isEmpty(newPassword)){
			return "1";
		}
		User u=getCurrentUser();
		try {
			orinPassword=hashService.encryptSHA(orinPassword);
			newPassword=hashService.encryptSHA(newPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if(!orinPassword.equals(u.getPassword())){
			return "2";
		}
		u.setPassword(newPassword);
		userDao.saveOrUpdate(u);
		return "3";
	}
	
	
	public User getCurrentUser(){
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
			UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userName=userDetails.getUsername();
			User user = userDao.findByName(userName);
			return user;
		}
		return null;
	}
	
	public String getCurrentUserName(){
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   return userDetails.getUsername();
	}


	
	public void idoUpdateUserInfo(User t) {
		User user=getCurrentUser();
		user.setAddress(t.getAddress());
		user.setCompany(t.getCompany());
		user.setEmail(t.getEmail());
		user.setGender(t.getGender());
		user.setRealName(t.getRealName());
		user.setPhone(t.getPhone());
		user.setMobilePhone(t.getMobilePhone());
		user.setPhoto(t.getPhoto());
		userDao.saveOrUpdate(user);
	}
	
	/**
	 * 用户注册
	 * @param t
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */

	public String idoUserRegister(String name,String password,Integer gender,String birthday,Integer sexuality) throws NoSuchAlgorithmException{
		if(StringUtils.isEmpty(name)){
			return "0";
		}
		if(StringUtils.isEmpty(password)){
			return "1";
		}
		if(gender==null){
			return "2";
		}
		if(sexuality==null){
			return "3";
		}
		if(StringUtils.isEmpty(birthday)){
			return "4";
		}
		password=hashService.encryptSHA(password);
		Date d=DateUtils.parseDate(birthday,DateUtils.FORMAT_DATE_DEFAULT);
		String retVal=userDao.getSingleResultByNativeSQL("select doUserRegister(?1,?2,?3,?4,?5)",name,password,gender.intValue(),d,sexuality.intValue()).toString();
		return retVal;
	}
	
	public List<User>queryByLikeName(String name){
		String userName=getCurrentUserName();
		if(StringUtils.isEmpty(name)){
			return userDao.findAll("from User t where t.name!=?1",userName);
		}
		return userDao.findAll("from User t where t.name like ?1 and t.name!=?2",name+"%",userName);
	}
	
	public List<User>queryByName(String name){
		return userDao.findAll("from User t where t.name＝?1",name);
	}
	
	
	public String queryByNameJsonStr(String name){
		List<User> list=queryByName(name);
		return JSONUtils.objectToJson(list);
	}
	public String queryByLikeNameJsonStr(String name){
		List<User> list=queryByLikeName(name);
		return JSONUtils.objectToJson(list);
	}
	

	public List<User> queryAllByDepartmentId(Long departmentId){
		return userDao.findAll("from User where department.id=?1",departmentId);
	}
	
	public User queryById(Long id){
		return userDao.findById(User.class, id);
	}
	
	
	public String getAllGroupByDeparmentZTreeJSON(){
		List<Department> result=departmentDao.findAll("from Department sm order by sm.seqNum");
		StringBuffer retVal=new StringBuffer("[");
		for(int i=0;i<result.size();i++){
			Department t=(Department)result.get(i);
			if(i!=0){
				retVal.append(",");	
			}
			String dId="d"+t.getId();
			String dpId="d"+t.getParentId();
			retVal.append("{");
			retVal.append("\"id\":\""+dId+"\"");
			retVal.append(",\"pId\":\""+dpId+"\"");
			retVal.append(",\"name\":\""+t.getNameZh()+"\"");
			retVal.append(",\"type\":\"department\"");
			//retVal.append(",\"icon\":\"../../../plugin/ztree/css/zTreeStyle/img/diy/1_open.png\"");
			retVal.append("}");
			List<User> users=queryAllByDepartmentId(t.getId());
			for(User u:users){
				retVal.append(",");	
				retVal.append("{");
				retVal.append("\"id\":\""+u.getId()+"\"");
				retVal.append(",\"pId\":\""+dId+"\"");
				retVal.append(",\"name\":\""+u.getName()+"("+u.getRealName()+")"+"\"");
				retVal.append(",\"type\":\"user\"");
				//retVal.append(",\"icon\":\"../../../plugin/ztree/css/zTreeStyle/img/diy/3.png\"");
				retVal.append("}");
			}
			
		}
		retVal.append("]");
		return retVal.toString();
	}
	
	public long idoUpdateUserRole(long userId,String roleIds){
		User t=userDao.findById(User.class, userId);
		setUserRoleByRoleIds(roleIds, t);
		userDao.update(t);
		return 1L;
	}

	private User deleteUserRoleByRoleIds(String roleIds, User t){
		String[] roleIdsArray=roleIds.equals("")?new String[]{}:roleIds.split(",");
		Iterator it=t.getUserRoles().iterator();
		while(it.hasNext()) {
			UserRole ur = (UserRole)it.next();
		     for(String roleId:roleIdsArray){
				long roleIdL=Long.parseLong(roleId);
				if(ur.getRole().getId()==roleIdL){
				 userRoleService.doDelete(ur);
				}
							
			}
		}
		return t;
	}
	
	private User setUserRoleByRoleIds(String roleIds, User t) {
		Set<UserRole> userRoles=new HashSet<UserRole>();
		String[] roleIdsArray=roleIds.equals("")?new String[]{}:roleIds.split(",");
		for(String roleId:roleIdsArray){
			Role r=new Role();
			r.setId(Long.parseLong(roleId));
			UserRole ur=new UserRole();
			ur.setRole(r);
			ur.setUser(t);
			UserRoleId id=new UserRoleId();
			id.setRoleId(r.getId());
			id.setUserId(t.getId());
			ur.setId(id);
			userRoles.add(ur);
		}
		t.setUserRoles(userRoles);
		return t;
	}

	
	public User findByName(String username) {
		return userDao.findByName(username);
	}
	
	/**
	 * 更新系统中微信公众号用户信息
	 * @param t
	 */
	public void idoUpdateGzWechatUserInfo(AuthUserInfo t){
		if(t==null){
			return;
		}
		Gender gender=Gender.UNKNOWN;
		if(t.getSex().equals("1")){
			gender=Gender.M;
		}else if(t.getSex().equals("0")){
			gender=Gender.FM;
		}
		userDao.bulkUpdate("update User set photo=?1,nickname=?2,province=?3,city=?4,country=?5,gender=?6 where wechatId=?7",t.getHeadimgurl(),t.getNickname(),t.getProvince(),t.getCity(),t.getCountry(),gender,t.getOpenid());
	}

	
	public User findWechatId(String wechatId) {
		return userDao.findWechatId(wechatId);
	}
	
	
	public BaseResponse idoUpdateFullUserInfo(User t) {
		BaseResponse res=new BaseResponse();
		
		User user=getCurrentUser();
		if(t.getName().equals(user.getWechatId())){//如果微信id与用户名相同，则可以修改用户名
			if(StringUtils.isEmpty(t.getName())){
				res.setStatus(0);
				res.setMessage("操作失败，用户名不能为空！");
				return res;
			}
			long userCount=userDao.getTotalCount("select count(t.id) from User t where t.name=?1 and t.id!=?2",t.getName(),user.getId());
			if(userCount>0L){
				res.setStatus(1);
				res.setMessage("操作失败，用户名已经存在！");
				return res;
			}
			user.setName(t.getName());
		}
		user.setAddress(t.getAddress());
		user.setCompany(t.getCompany());
		user.setEmail(t.getEmail());
		user.setGender(t.getGender());
		user.setRealName(t.getRealName());
		user.setPhone(t.getPhone());
		user.setMobilePhone(t.getMobilePhone());
		user.setPhoto(t.getPhoto());
		userDao.saveOrUpdate(user);
		res.setStatus(2);
		res.setMessage("操作成功！");
		return res;
	}
	
	public long idoAddPotentialCustomerUser(String mobilePhoneNo){
		return doAdd(mobilePhoneNo,"潜客用户");
	}
	public long idoAddCarOwnerUser(String mobilePhoneNo){
		return doAdd(mobilePhoneNo,"车主用户");
	}
	private long doAdd(String mobilePhoneNo,String roleName) {
		User t=new User();
		if(userDao.findByName(mobilePhoneNo)!=null){
			   return 0L;
		}
		t.setName(mobilePhoneNo);
		t.setPassword(mobilePhoneNo);
		List<Role> list=roleService.findAll("from Role where nameZh=?1",roleName);
		if(list.size()==0){
			return 3L;
		}
		Role role=list.get(0);
		t.setRoleIds(String.valueOf(role.getId()));
		return doAdd(t);
	}
	
	public long idoAddOrUpdateCarOwnerUser(String wechatId,String mobilePhoneNo,String realName,String company,String address,String nickname){
		User t=userDao.findWechatId(wechatId);
		if(t!=null){
			t.setName(wechatId);
			t.setPassword(wechatId);
			t.setRealName(realName);
			t.setMobilePhone(mobilePhoneNo);
			t.setCompany(company);
			t.setAddress(address);
			t.setNickname(nickname);
			userDao.saveOrUpdate(t);
			return 0L;
		}
		t=new User();
		t.setName(wechatId);
		t.setPassword(wechatId);
		t.setRealName(realName);
		t.setMobilePhone(mobilePhoneNo);
		t.setCompany(company);
		t.setAddress(address);
		t.setNickname(nickname);
		t.setWechatId(wechatId);
		List<Role> list=roleService.findAll("from Role where nameZh=?1","车主用户");
		if(list.size()==0){
			return 3L;
		}
		Role role=list.get(0);
		t.setRoleIds(String.valueOf(role.getId()));
		return doAdd(t);
	}
	
	public long idoAddByQyWechat(String wechatId,String realName,String mobilePhone,String gender,String photo,String roleName){
		User t=new User();
		if(userDao.findWechatId(wechatId)!=null){
			   return 0L;
		}
		t.setRealName(realName);
		t.setMobilePhone(mobilePhone);
		if("1".equals(gender)){
			t.setGender(Gender.M);
		}else{
			t.setGender(Gender.FM);
		}
	    t.setPhoto(photo);
		t.setName(wechatId);
		t.setPassword(wechatId);
		t.setWechatId(wechatId);
		List<Role> list=roleService.findAll("from Role where nameZh=?1",roleName);
		if(list.size()==0){
			return 3L;
		}
		Role role=list.get(0);
		t.setRoleIds(String.valueOf(role.getId()));
		return doAdd(t);
	}

}
