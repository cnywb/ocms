package com.ternnetwork.baseframework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.dao.security.UserDao;
import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.model.security.RescRole;
import com.ternnetwork.baseframework.model.security.Resources;
import com.ternnetwork.baseframework.model.security.UserRole;






//2
@Service("myUserDetailServiceImpl")
public class MyUserDetailServiceImpl implements UserDetailsService {
	
	private static final Logger log = Logger.getLogger(MyUserDetailServiceImpl.class);

	
	@Resource
	private UserDao userDao;

	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	//登录验证
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("登录用户名：" + username);
		
		com.ternnetwork.baseframework.model.security.User users = this.userDao.findByName(username);
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
		
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked =users.getAccountNonLocked();
		//封装成spring security的user
		User userdetail = new User(users.getName(), users.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
		return userdetail;
	}
	
	//取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(com.ternnetwork.baseframework.model.security.User user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
	    List<Resources> resources=getUserResources(user);
		for(Resources res : resources) {
			authSet.add(new SimpleGrantedAuthority(res.getNameEn()));
			
		}
		return authSet;
	}
	
	private List<Resources> getUserResources(com.ternnetwork.baseframework.model.security.User user){
		List<Resources> resources=new ArrayList<Resources>();
		for(UserRole userRole:user.getUserRoles()){
			for(RescRole rescRole:userRole.getRole().getRescRoles()){
				if(!StringUtils.isEmpty(rescRole.getResources().getUrl())&&!rescRole.getResources().getRoleType().equals(RoleType.APP)){
					resources.add(rescRole.getResources());
				}
			}
		}
		return resources;
	}
}
