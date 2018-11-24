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
 *2011-4-25下午10:55:01
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Embeddable
public class RescRoleId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8198939785252711329L;
	@Column(name="ROLE_ID",nullable=false)
	private long roleId;
	@Column(name="RESOURCES_ID",nullable=false)
	private long resourcesId;
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(long resourcesId) {
		this.resourcesId = resourcesId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (resourcesId ^ (resourcesId >>> 32));
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
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
		RescRoleId other = (RescRoleId) obj;
		if (resourcesId != other.resourcesId)
			return false;
		if (roleId != other.roleId)
			return false;
		return true;
	}
	
	

	
	
	
	

}
