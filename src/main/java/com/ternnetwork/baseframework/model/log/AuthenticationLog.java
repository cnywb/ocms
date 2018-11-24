package com.ternnetwork.baseframework.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.enums.AuthenticationLogResult;
import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.JsonDateSerializer;


@Entity
@Table(name="VS_AUTHENTICATION_LOG")
@JsonAutoDetect
public class AuthenticationLog {
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="AUTHENTICATION_LOG_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private Long id;
	
	
	@Column(name="SESSION_ID")
	private String sessionId;
	
	
	@Column(nullable=false,name="ADDRESS")
	private String address;
	
	
	@Column(nullable=false,name="USER_NAME")
	private String userName;//因为用户可能用了一个不存在的用户或登录
	
	@Enumerated@Column(name="RESULT")
	private AuthenticationLogResult result=AuthenticationLogResult.SUCCESS;
	
	
	@ManyToOne(fetch=FetchType.EAGER)@JoinColumn(name="USER_ID")
	private User user;
	
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Temporal(TemporalType.TIMESTAMP)@Column(name="UPDATE_TIME")
	private Date updateTime;
	
	@Transient
	private String resultName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public AuthenticationLogResult getResult() {
		return result;
	}

	public void setResult(AuthenticationLogResult result) {
		this.result = result;
	}

	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getResultName() {
		return result.getName();
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
