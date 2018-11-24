package com.ternnetwork.baseframework.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.util.Assert;

public abstract class MyAbstractUserDetailsAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware{
	            protected final Log logger;
	/*     */   protected MessageSourceAccessor messages;
	/*     */   private UserCache userCache;
	/*     */   private boolean forcePrincipalAsString;
	/*     */   protected boolean hideUserNotFoundExceptions;
	/*     */   private UserDetailsChecker preAuthenticationChecks;
	/*     */   private UserDetailsChecker postAuthenticationChecks;
	/*     */   private GrantedAuthoritiesMapper authoritiesMapper;
	public MyAbstractUserDetailsAuthenticationProvider(){
	   
		 logger = LogFactory.getLog(getClass());
		 messages = SpringSecurityMessageSource.getAccessor();
		 /*  84 */     userCache = new NullUserCache();
		 /*  85 */     forcePrincipalAsString = false;
		 /*  86 */     hideUserNotFoundExceptions = true;
		 /*  87 */     preAuthenticationChecks = new DefaultPreAuthenticationChecks();
		 /*  88 */     postAuthenticationChecks = new DefaultPostAuthenticationChecks();
		 /*  89 */     authoritiesMapper = new NullAuthoritiesMapper();
	}
	
	  protected abstract void additionalAuthenticationChecks(UserDetails paramUserDetails, MyUsernamePasswordAuthenticationToken paramMyUsernamePasswordAuthenticationToken)
			  /*     */     throws AuthenticationException;
			  /*     */   
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */   public final void afterPropertiesSet()
			  /*     */     throws Exception
			  /*     */   {
			  /* 112 */     Assert.notNull(userCache, "A user cache must be set");
			  /* 113 */     Assert.notNull(messages, "A message source must be set");
			  /* 114 */     doAfterPropertiesSet();
			  /*     */   }
			  /*     */   
			  /*     */   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			  /* 118 */     Assert.isInstanceOf(MyUsernamePasswordAuthenticationToken.class, authentication, messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports", "Only MyUsernamePasswordAuthenticationToken is supported"));
			  /*     */     
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /* 123 */     String username = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
			  /*     */     
			  /* 125 */     boolean cacheWasUsed = true;
			  /* 126 */     UserDetails user = userCache.getUserFromCache(username);
			  /*     */     
			  /* 128 */     if (user == null) {
			  /* 129 */       cacheWasUsed = false;
			  /*     */       try
			  /*     */       {
			  /* 132 */         user = retrieveUser(username, (MyUsernamePasswordAuthenticationToken)authentication);
			  /*     */       } catch (UsernameNotFoundException notFound) {
			  /* 134 */         logger.debug("User '" + username + "' not found");
			  /*     */         
			  /* 136 */         if (hideUserNotFoundExceptions) {
			  /* 137 */           throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			  /*     */         }
			  /*     */         
			  /* 140 */         throw notFound;
			  /*     */       }
			  /*     */       
			  /*     */ 
			  /* 144 */       Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
			  /*     */     }
			  /*     */     try
			  /*     */     {
			  /* 148 */       preAuthenticationChecks.check(user);
			  /* 149 */       additionalAuthenticationChecks(user, (MyUsernamePasswordAuthenticationToken)authentication);
			  /*     */     } catch (AuthenticationException exception) {
			  /* 151 */       if (cacheWasUsed)
			  /*     */       {
			  /*     */ 
			  /* 154 */         cacheWasUsed = false;
			  /* 155 */         user = retrieveUser(username, (MyUsernamePasswordAuthenticationToken)authentication);
			  /* 156 */         preAuthenticationChecks.check(user);
			  /* 157 */         additionalAuthenticationChecks(user, (MyUsernamePasswordAuthenticationToken)authentication);
			  /*     */       } else {
			  /* 159 */         throw exception;
			  /*     */       }
			  /*     */     }
			  /*     */     
			  /* 163 */     postAuthenticationChecks.check(user);
			  /*     */     
			  /* 165 */     if (!cacheWasUsed) {
			  /* 166 */       userCache.putUserInCache(user);
			  /*     */     }
			  /*     */     
			  /* 169 */     Object principalToReturn = user;
			  /*     */     
			  /* 171 */     if (forcePrincipalAsString) {
			  /* 172 */       principalToReturn = user.getUsername();
			  /*     */     }
			  /*     */     
			  /* 175 */     return createSuccessAuthentication(principalToReturn, authentication, user);
			  /*     */   }
			  /*     */   
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */   protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user)
			  /*     */   {
			  /* 196 */     MyUsernamePasswordAuthenticationToken result = new MyUsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), authoritiesMapper.mapAuthorities(user.getAuthorities()));
			  /*     */     
			  /* 198 */     result.setDetails(authentication.getDetails());
			  /*     */     
			  /* 200 */     return result;
			  /*     */   }
			  /*     */   
			  /*     */   protected void doAfterPropertiesSet() throws Exception
			  /*     */   {}
			  /*     */   
			  /* 206 */   public UserCache getUserCache() { return userCache; }
			  /*     */   
			  /*     */   public boolean isForcePrincipalAsString()
			  /*     */   {
			  /* 210 */     return forcePrincipalAsString;
			  /*     */   }
			  /*     */   
			  /*     */   public boolean isHideUserNotFoundExceptions() {
			  /* 214 */     return hideUserNotFoundExceptions;
			  /*     */   }
			  /*     */   
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */   protected abstract UserDetails retrieveUser(String paramString, MyUsernamePasswordAuthenticationToken paramMyUsernamePasswordAuthenticationToken)
			  /*     */     throws AuthenticationException;
			  /*     */   
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */   public void setForcePrincipalAsString(boolean forcePrincipalAsString)
			  /*     */   {
			  /* 249 */     this.forcePrincipalAsString = forcePrincipalAsString;
			  /*     */   }
			  /*     */   
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */   public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions)
			  /*     */   {
			  /* 264 */     this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
			  /*     */   }
			  /*     */   
			  /*     */   public void setMessageSource(MessageSource messageSource) {
			  /* 268 */     messages = new MessageSourceAccessor(messageSource);
			  /*     */   }
			  /*     */   
			  /*     */   public void setUserCache(UserCache userCache) {
			  /* 272 */     this.userCache = userCache;
			  /*     */   }
			  /*     */   
			  /*     */   public boolean supports(Class<?> authentication) {
			  /* 276 */     return MyUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
			  /*     */   }
			  /*     */   
			  /*     */   protected UserDetailsChecker getPreAuthenticationChecks() {
			  /* 280 */     return preAuthenticationChecks;
			  /*     */   }
			  /*     */   
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */ 
			  /*     */   public void setPreAuthenticationChecks(UserDetailsChecker preAuthenticationChecks)
			  /*     */   {
			  /* 290 */     this.preAuthenticationChecks = preAuthenticationChecks;
			  /*     */   }
			  /*     */   
			  /*     */   protected UserDetailsChecker getPostAuthenticationChecks() {
			  /* 294 */     return postAuthenticationChecks;
			  /*     */   }
			  /*     */   
			  /*     */   public void setPostAuthenticationChecks(UserDetailsChecker postAuthenticationChecks) {
			  /* 298 */     this.postAuthenticationChecks = postAuthenticationChecks;
			  /*     */   }
			  /*     */   
			  /*     */ 
			  /* 302 */   public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) { this.authoritiesMapper = authoritiesMapper; }
			  /*     */   
			  /*     */   private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
			  /*     */     private DefaultPreAuthenticationChecks() {}
			  /*     */     
			  /* 307 */     public void check(UserDetails user) { if (!user.isAccountNonLocked()) {
			  /* 308 */         logger.debug("User account is locked");
			  /*     */         
			  /* 310 */         throw new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
			  /*     */       }
			  /*     */       
			  /*     */ 
			  /* 314 */       if (!user.isEnabled()) {
			  /* 315 */         logger.debug("User account is disabled");
			  /*     */         
			  /* 317 */         throw new DisabledException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
			  /*     */       }
			  /*     */       
			  /*     */ 
			  /* 321 */       if (!user.isAccountNonExpired()) {
			  /* 322 */         logger.debug("User account is expired");
			  /*     */         
			  /* 324 */         throw new AccountExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
			  /*     */       }
			  /*     */     }
			  /*     */   }
			  /*     */   
			  /*     */   private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
			  /*     */     private DefaultPostAuthenticationChecks() {}
			  /*     */     
			  /* 332 */     public void check(UserDetails user) { if (!user.isCredentialsNonExpired()) {
			  /* 333 */         logger.debug("User account credentials have expired");
			  /*     */         
			  /* 335 */         throw new CredentialsExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
			  /*     */       }
			  /*     */     }
			  /*     */   }
}
