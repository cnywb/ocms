package com.ternnetwork.baseframework.model.config;

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



@Entity
@Table(name="VS_QUARTZ_JOB")
public class QuartzJob {

	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="QUARTZ_JOB_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	
	@Column(name="NAME",nullable=false,unique=true)
	private String name;//名称
	
	@Column(name="MEMO")
	private String memo;//名称
	

	@Column(name="JOB_GROUP",nullable=false)
	private  String group;
	
	@Column(name="CLASS_NAME",nullable=false)
	private String className;
	
	@Column(name="ENABLE",nullable=false)
	private Boolean enable;
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
	
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();

	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.EAGER, mappedBy="job")
	private Set<QuartzJobTrigger> triggers=new HashSet<QuartzJobTrigger>();

	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public Set<QuartzJobTrigger> getTriggers() {
		return triggers;
	}


	public void setTriggers(Set<QuartzJobTrigger> triggers) {
		this.triggers = triggers;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public Boolean getEnable() {
		return enable;
	}


	public void setEnable(Boolean enable) {
		this.enable = enable;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
