package com.ternnetwork.baseframework.security;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.wechat.model.auth.AuthUserInfo;
import com.ternnetwork.wechat.model.auth.QyAuthUserInfo;
import com.ternnetwork.wechat.model.auth.QyExtAttr;
import com.ternnetwork.wechat.model.auth.QyUserDetailInfo;
import com.ternnetwork.wechat.service.qy.txl.WechatQyUserService;
import com.ternnetwork.wechat.service.user.WechatUserService;



public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String WECHAT_QY_AUTH_CODE = "wechatQyAuthCode";
	public static final String WECHAT_DY_AUTH_CODE = "wechatDyAuthCode";
	public static final String WECHAT_FW_AUTH_CODE = "wechatFwAuthCode";
	
	
	private static final Logger log = Logger.getLogger(MyUsernamePasswordAuthenticationFilter.class);
	
	@Resource
	private UserService userService;
	

	@Resource
	private WechatUserService wechatUserService;
	
	@Resource
	private WechatQyUserService wechatQyUserService;
	
	
	
	
	

		@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		//验证用户账号与密码是否对应
		    username = username.trim();
		    
		    MyUsernamePasswordAuthenticationToken myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(username, password);

		    String wechatQyAuthCode=obtainWechatQyAuthCode(request);
		    String wechatDyAuthCode=obtainWechatDyAuthCode(request);
		    String wechatFwAuthCode=obtainWechatFwAuthCode(request);
		    if(StringUtils.isNotEmpty(wechatQyAuthCode)){//如果是微信企业号跳转登录
		    	log.info(".....微信企业号登录......wechatQyAuthCode:"+wechatQyAuthCode);
		        myAuthenticationToken = updateAuthentiocatonTokenForWechatQyAuth(request, password, myAuthenticationToken, wechatQyAuthCode);
		    }else if(StringUtils.isNotEmpty(wechatDyAuthCode)){//如果是微信订阅号跳转登录
		    	log.info(".....微信订阅号登录......wechatDyAuthCode:"+wechatDyAuthCode);
		    	myAuthenticationToken = updateAuthentiocatonTokenForWechatDyAuth(request, password, myAuthenticationToken, wechatDyAuthCode);
			}else if(StringUtils.isNotEmpty(wechatFwAuthCode)){//如果是微信服务号跳转登录
		    	log.info(".....微信服务号登录......wechatFwAuthCode:"+wechatFwAuthCode);
		    	myAuthenticationToken = updateAuthentiocatonTokenForWechatFwAuth(request, password, myAuthenticationToken, wechatDyAuthCode);
			}else{
				log.info(".....正常登录......");
			    setDetails(request,myAuthenticationToken);
			}
		    
	        	return this.getAuthenticationManager().authenticate(myAuthenticationToken);
	}

		private MyUsernamePasswordAuthenticationToken updateAuthentiocatonTokenForWechatQyAuth(HttpServletRequest request, String password,MyUsernamePasswordAuthenticationToken myAuthenticationToken,String wechatAuthCode) {
			QyAuthUserInfo qyAuthUserInfo=wechatQyUserService.getAuthUserInfo(wechatAuthCode);//得到授权用户信息
	        if(qyAuthUserInfo!=null&&StringUtils.isNotEmpty(qyAuthUserInfo.getUserId())){//已关注的情况
	        	    User user=userService.findWechatId(qyAuthUserInfo.getUserId());
	        	    if(user!=null){//已在关注时注册过用户名
	        	    	myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(user.getName(), password);
	        	    }else{
	        	    	QyUserDetailInfo qyUserDetailInfo=wechatQyUserService.getById(qyAuthUserInfo.getUserId());
	        	    	List<QyExtAttr> qyExtAttrList=qyUserDetailInfo.getExtattr().getAttrs();
	        	    	userService.idoAddByQyWechat(qyAuthUserInfo.getUserId(), qyUserDetailInfo.getName(), qyUserDetailInfo.getMobile(), qyUserDetailInfo.getGender(), qyUserDetailInfo.getAvatar(),"");
		      	    	myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(qyAuthUserInfo.getUserId(), password);
	        	    }
	        }else{//未关注企业号的情况,只有能得到openid
	        	myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(qyAuthUserInfo.getUserId(), password);
    	    }
	        setDetails(request,myAuthenticationToken);
	        if(qyAuthUserInfo!=null&&StringUtils.isNotEmpty(qyAuthUserInfo.getUserId())){
	         	  myAuthenticationToken.setNeedAuth(false);//如果微信授权成功那么无需再校验密码，直接反回成功
	        }
			return myAuthenticationToken;
		}
	
		private MyUsernamePasswordAuthenticationToken updateAuthentiocatonTokenForWechatDyAuth(HttpServletRequest request, String password,MyUsernamePasswordAuthenticationToken myAuthenticationToken,String wechatAuthCode) {
			AuthUserInfo dyAuthUserInfo=wechatUserService.getDyAuthUserinfo(wechatAuthCode);//得到授权用户信息
	        if(dyAuthUserInfo!=null&&StringUtils.isNotEmpty(dyAuthUserInfo.getOpenid())){
	        	 User user=userService.findWechatId(dyAuthUserInfo.getOpenid());
	        	 if(user!=null){
	        		 myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(user.getName(), password);
	        	 }else{
	        		 myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(dyAuthUserInfo.getOpenid(), password);
	        	 }
	        }
	        setDetails(request,myAuthenticationToken);
	        if(dyAuthUserInfo!=null&&StringUtils.isNotEmpty(dyAuthUserInfo.getOpenid())){
	         	  myAuthenticationToken.setNeedAuth(false);//如果微信授权成功那么无需再校验密码，直接反回成功
	         	  userService.idoUpdateGzWechatUserInfo(dyAuthUserInfo);
	        }
			return myAuthenticationToken;
		}
		
		private MyUsernamePasswordAuthenticationToken updateAuthentiocatonTokenForWechatFwAuth(HttpServletRequest request, String password,MyUsernamePasswordAuthenticationToken myAuthenticationToken,String wechatAuthCode) {
			AuthUserInfo fwAuthUserInfo=wechatUserService.getFwAuthUserinfo(wechatAuthCode);//得到授权用户信息
	        if(fwAuthUserInfo!=null&&StringUtils.isNotEmpty(fwAuthUserInfo.getOpenid())){
	        	 User user=userService.findWechatId(fwAuthUserInfo.getOpenid());
	        	 if(user!=null){
	        		 myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(user.getName(), password);
	        	 }else{
	        		 myAuthenticationToken = new MyUsernamePasswordAuthenticationToken(fwAuthUserInfo.getOpenid(), password);
	        	 }
	        }
	        setDetails(request,myAuthenticationToken);
	        if(fwAuthUserInfo!=null&&StringUtils.isNotEmpty(fwAuthUserInfo.getOpenid())){
	         	  myAuthenticationToken.setNeedAuth(false);//如果微信授权成功那么无需再校验密码，直接反回成功
	         	  userService.idoUpdateGzWechatUserInfo(fwAuthUserInfo);
	        }
			return myAuthenticationToken;
		}
	protected void checkValidateCode(HttpServletRequest request) { 
		HttpSession session = request.getSession();
		
	    String sessionValidateCode = obtainSessionValidateCode(session); 
	    //让上一次的验证码失效
	    session.setAttribute(VALIDATE_CODE, null);
	    String validateCodeParameter = obtainValidateCodeParameter(request);  
	    if (validateCodeParameter==null||"".equals(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {  
	        throw new AuthenticationServiceException("validateCode.notEquals");  
	    }  
	}
	
	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
	
	protected String obtainWechatQyAuthCode(HttpServletRequest request) {
		Object obj = request.getParameter(WECHAT_QY_AUTH_CODE);
		return null == obj ? "" : obj.toString();
	}
	
	protected String obtainWechatDyAuthCode(HttpServletRequest request) {
		Object obj = request.getParameter(WECHAT_DY_AUTH_CODE);
		return null == obj ? "" : obj.toString();
	}
	
	protected String obtainWechatFwAuthCode(HttpServletRequest request) {
		Object obj = request.getParameter(WECHAT_FW_AUTH_CODE);
		return null == obj ? "" : obj.toString();
	}
	
}
