/**
 * 
 */
package com.ternnetwork.baseframework.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 *
 * @author wenfeng.xu
 *2011-4-24下午10:14:35
 */
@Cache(usage =CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="VS_RESC_ROLE")
public class RescRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1787066029325828326L;
	
	@EmbeddedId
	@AttributeOverrides( {
	@AttributeOverride(name="resourcesId", column=@Column(name="RESOURCES_ID", nullable=false) ), 
	@AttributeOverride(name="roleId", column=@Column(name="ROLE_ID", nullable=false) ) } )
	private RescRoleId id;
		
	@ManyToOne
	@JoinColumn(name="ROLE_ID", nullable=false, insertable=false, updatable=false)
	private Role role;
		
	@ManyToOne
	@JoinColumn(name="RESOURCES_ID", nullable=false, insertable=false, updatable=false)
	private Resources resources;
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();

	public RescRoleId getId() {
		return id;
	}

	public void setId(RescRoleId id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

}
