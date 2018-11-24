package com.ternnetwork.baseframework.redis;
import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;




	/* 
 * redis安装命令切换到安装包所在目录运行 make test
 * 启动命令 src/redis-server
 * 关闭命令 shutdown
 * 运行客户端命令 # src/redis-cli
 * 
 * 可用redis命令 src/redis-cli 登录
 * 可用redis命令 HKEYS SECURITY_CONTEXT 查看所有的键
 * 可用redis命令 HMGET SECURITY_CONTEXT 键   查看键对应的值 
 * 可用redis命令 HDEL SECURITY_CONTEXT 键  删除键与值
 * 
*/
@Service("redisTemplateService")
public class RedisTemplateServiceImpl implements RedisTemplateService {

	 
	@SuppressWarnings("rawtypes")
	@Resource
    private RedisTemplate redisTemplate;

    @SuppressWarnings("unchecked")
	
    public void putSecurityContext(String userSessionId, Object value) {
          redisTemplate.opsForHash().put("SECURITY_CONTEXT", userSessionId, value);
    }

    @SuppressWarnings("unchecked")
	
    public Object getSecurityContext(String userSessionId) {
        return redisTemplate.opsForHash().get("SECURITY_CONTEXT", userSessionId);
    }

    @SuppressWarnings("unchecked")

    public void deleteSecurityContext(String userSessionId) {
        redisTemplate.opsForHash().delete("SECURITY_CONTEXT", userSessionId);
    }
}
