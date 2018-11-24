package com.ternnetwork.baseframework.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class MyDaoAuthenticationProvider extends MyAbstractUserDetailsAuthenticationProvider {

	
	   private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
	 
	   private SaltSource saltSource;
	    
	   private UserDetailsService userDetailsService;
	   
	   private boolean includeDetailsObject = true;
	 
	            
	
	    protected void doAfterPropertiesSet()
	    throws Exception
      {
	  Assert.notNull(userDetailsService, "A UserDetailsService must be set");
	  }

	  public void setPasswordEncoder(PasswordEncoder passwordEncoder)
	  {
	    this.passwordEncoder = passwordEncoder;
	  }
	    
	  protected PasswordEncoder getPasswordEncoder() {
	     return passwordEncoder;
	   }
	
	  public void setSaltSource(SaltSource saltSource)
	  {
	     this.saltSource = saltSource;
	  }
	   
	   protected SaltSource getSaltSource() {
	    return saltSource;
	   }
	  
	  public void setUserDetailsService(UserDetailsService userDetailsService) {
	     this.userDetailsService = userDetailsService;
	    }
	   
	   protected UserDetailsService getUserDetailsService() {
	      return userDetailsService;
	  }
	   
	  protected boolean isIncludeDetailsObject() {
	    return includeDetailsObject;
	   }
	

	   public void setIncludeDetailsObject(boolean includeDetailsObject)
	  {
	    this.includeDetailsObject = includeDetailsObject;
	  }
	
	
	 protected void additionalAuthenticationChecks(UserDetails userDetails, MyUsernamePasswordAuthenticationToken authentication)
			      throws AuthenticationException
			   {
			      Object salt = null;
			      
			     if (saltSource != null) {
			      salt = saltSource.getSalt(userDetails);
			     }
			      
			     if (authentication.getCredentials() == null) {
			        logger.debug("Authentication failed: no credentials provided");
			       
			      throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			     }
			   String presentedPassword = authentication.getCredentials().toString();
			   //此处是关键，isNeedAuth是指需不需要密码确证，如果是已经从第三方认证过了就无需进行密码验证了   
			   if (authentication.isNeedAuth()&&!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
			       logger.debug("Authentication failed: password does not match stored value");
			       throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			     }
			   }
			    
			 
			   protected final UserDetails retrieveUser(String username, MyUsernamePasswordAuthenticationToken authentication) throws AuthenticationException
			    {
			     UserDetails loadedUser;
			      try
			     {
			        loadedUser = getUserDetailsService().loadUserByUsername(username);
			    } catch (UsernameNotFoundException notFound) {
			        throw notFound;
			      } catch (Exception repositoryProblem) {
			        throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
			      }
			      
			      if (loadedUser == null) {
			        throw new AuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
			      }
		     
			    return loadedUser;
			    }
	 
	 
}
