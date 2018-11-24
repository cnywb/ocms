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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



/**
 *bsierp_ssj
 * @author wenfeng.xu
 *2011-4-24下午08:43:36
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="VS_USER_ROLE")
public class UserRole implements Serializable{

 private UserRoleId id;
 private User user; //用户
 private Role role;//角色
 private Date expiredDate=new Date();//过期日期
 private Date createTime=new Date();

 
 @EmbeddedId
 @AttributeOverrides( {
 @AttributeOverride(name="userId", column=@Column(name="USER_ID", nullable=false) ), 
 @AttributeOverride(name="roleId", column=@Column(name="ROLE_ID", nullable=false) ) } )
public UserRoleId getId() {
	return id;
}
public void setId(UserRoleId id) {
	this.id = id;
}
@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="USER_ID", nullable=false, insertable=false, updatable=false)
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
@ManyToOne(fetch=FetchType.EAGER)
@JoinColumn(name="ROLE_ID", nullable=false, insertable=false, updatable=false)
public Role getRole() {
	return role;
}
public void setRole(Role role) {
	this.role = role;
}


@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}


@Temporal(TemporalType.TIMESTAMP)
@Column(name="EXPIRED_DATE")
public Date getExpiredDate() {
	return expiredDate;
}
public void setExpiredDate(Date expiredDate) {
	this.expiredDate = expiredDate;
}
 
 
}
