/**
 * 
 */
package com.ternnetwork.baseframework.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 *bsierp_ssj
 * @author wenfeng.xu
 *2011-4-25下午10:44:11
 */
@Cache(usage =CacheConcurrencyStrategy.READ_WRITE)
@Embeddable
public class UserRoleId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1013111640016703195L;
	private long userId;
	private long roleId;
	@Column(name="USER_ID",nullable=false)
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Column(name="ROLE_ID",nullable=false)
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoleId other = (UserRoleId) obj;
		if (roleId != other.roleId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	

}
