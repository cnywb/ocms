package com.ternnetwork.baseframework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.StringUtils;

import com.ternnetwork.baseframework.dao.security.ResourcesDao;
import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.model.security.Resources;





//1 加载资源与权限的对应关系
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private static final Logger log = Logger.getLogger(MySecurityMetadataSource.class);
	
	//由spring调用
	public MySecurityMetadataSource() {
		//loadResourceDefine();
	}

	@Resource
	private ResourcesDao resourcesDao;
	
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap=new ConcurrentHashMap<String, Collection<ConfigAttribute>>();//注意这里必须是线程安全的,否则在并发下put方法会卡死

	

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	//加载所有资源与权限的关系
	private void loadResourceDefine() {
		
			resourceMap.clear();
			List<Resources> resources =resourcesDao.findAll("from Resources t where 1=?1 and t.roleType!=?2", 1,RoleType.APP);
			for (Resources resource : resources) {
				if(!StringUtils.isEmpty(resource.getUrl())){//只对有写请求路径的资源进行管理
				   Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				   ConfigAttribute configAttribute = new SecurityConfig(resource.getNameEn());
				   configAttributes.add(configAttribute);
				   resourceMap.put("/"+resource.getUrl(), configAttributes);
				}
			}
		
	}
	//返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
    	log.info("请求路径：" + requestUrl);
		//if(resourceMap == null) {
			loadResourceDefine();
		//}
		if(requestUrl.indexOf("?")!=-1){//如果请求路径后有参数
			requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
		}
		return resourceMap.get(requestUrl);
	}

}
