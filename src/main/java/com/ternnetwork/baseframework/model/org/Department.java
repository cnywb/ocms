/**
 * 
 */
package com.ternnetwork.baseframework.model.org;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;



/**
 * 
 *
 * @author wenfeng.xu
 *2011-4-10下午01:24:21
 */
@Entity
@Table(name="VS_DEPARTMENT")
@JsonAutoDetect
@JsonIgnoreProperties(value={"users"})

public class Department implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4953857671795115260L;
	private long id;
	private String nameZh;//中文名
	private String nameEn;//代码
	private long parentId=0;//父节点点ID
	private Integer seqNum=0;//排序
	private Set<User> users=new HashSet<User>();
	private Date createTime=new Date();
	private Date updateTime=new Date();

	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="DEPARTMENT_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name="NAME_ZH",nullable=false,length=20)
	public String getNameZh() {
		return nameZh;
	}
	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}
	@Column(name="NAME_EN",nullable=false,length=20)
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	@Column(name="PARENT_ID",nullable=false)
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	
	
	
	@Column(name="SEQ_NUM",nullable=false)
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER, mappedBy="department")
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	

	private int status;

	private String message;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Department other = (Department) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public Department(String nameZh, String nameEn, long parentId,
			Integer seqNum) {
		super();
		this.nameZh = nameZh;
		this.nameEn = nameEn;
		this.parentId = parentId;
		this.seqNum = seqNum;
		
	}
	public Department() {
		super();
	}
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	@Transient
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Transient
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
	

}
