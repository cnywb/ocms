/**
 * 
 */
package com.ternnetwork.baseframework.model.security;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;




/**
 *bsierp_ssj
 * @author wenfeng.xu
 *2011-4-24下午08:35:40
 */
@Cache(usage =CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="VS_ROLE")
@JsonAutoDetect
@JsonIgnoreProperties(value={"userRoles","rescRoles"})

public class Role {
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="ROLE_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)

	private long id;
	
	@Column(name="NAME_ZH",nullable=false)
	private String nameZh;
	
	@Column(name="NAME_EN",nullable=false,unique=true)
	private String nameEn;
	
	@Column(name="DESCRIPTION")
	private String description="";
	
	//如果不标注EnumType.STRING则字段将记录0，1，2 等索引顺序
    @Enumerated@Column(length=5,name="TYPE",nullable=false)
	private RoleType type=RoleType.ADMIN;//
    
    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
    
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="role")
	private Set<UserRole> userRoles=new HashSet<UserRole>();
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER, mappedBy="role")
	private Set<RescRole> rescRoles=new HashSet<RescRole>();
  
	@Transient
	private String typeName;
	
	
	public String getTypeName() {
		return type.getName();
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNameZh() {
		return nameZh;
	}
	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}

	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public RoleType getType() {
		return type;
	}
	public void setType(RoleType type) {
		this.type = type;
	}
	
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	/**
	 * 如果cascade=CascadeType.ALL,或cascade=CascadeType.Persist, 同时fetch=FetchType.EAGER,
	 * 那么RescRole 在delete时会报deleted entity passed to persist异常
	 * @return
	 */
	
	public Set<RescRole> getRescRoles() {
		return rescRoles;
	}
	public void setRescRoles(Set<RescRole> rescRoles) {
		this.rescRoles = rescRoles;
	}
	
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	
     public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String toString() {
		return JSONUtils.objectToJson(this);
	}


	

}
