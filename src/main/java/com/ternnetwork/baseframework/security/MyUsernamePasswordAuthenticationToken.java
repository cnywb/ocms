package com.ternnetwork.baseframework.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MyUsernamePasswordAuthenticationToken extends
		UsernamePasswordAuthenticationToken {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 7205732108574369975L;
	private boolean needAuth=true;
	
	public MyUsernamePasswordAuthenticationToken(Object principal,
			Object credentials) {
		super(principal, credentials);
		
	}

	public MyUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities){
		super(principal,credentials,authorities);
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return super.getAuthorities();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return super.isAuthenticated();
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return super.getDetails();
	}

	@Override
	public void setDetails(Object details) {
		// TODO Auto-generated method stub
		super.setDetails(details);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return super.getCredentials();
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return super.getPrincipal();
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		super.setAuthenticated(isAuthenticated);
	}

	@Override
	public void eraseCredentials() {
		// TODO Auto-generated method stub
		super.eraseCredentials();
	}

	public boolean isNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}

}
