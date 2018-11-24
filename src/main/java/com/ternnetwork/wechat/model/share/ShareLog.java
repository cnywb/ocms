package com.ternnetwork.wechat.model.share;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.wechat.enums.ShareType;
import com.ternnetwork.wechat.enums.WechatAccountType;

@JsonAutoDetect
@Entity
@Table(name="VS_WECHAT_SHARE_LOG")

@JsonIgnoreProperties(value={"user"})
public class ShareLog {

	@Id@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="WECHAT_SHARE_LOG_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
			
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	
	@ManyToOne@JoinColumn(name="USER_ID",nullable=false)
    private User user;//用户
	
	@Column(name="CAMPAIGN",nullable=false)
    private  String campaign;
	
	@Enumerated@Column(name="SHARE_TYPE")
	private ShareType shareType;//分享类型 

	@Enumerated@Column(name="WECHAT_ACCOUNT_TYPE")
	private  WechatAccountType wechatAccountType;//分享账号类型
	
	@Transient
	private String wechatAccountTypeName;
	
	@Transient
	private String shareTypeName;
	
	@Transient
	private String userName;
	
	
	

	public String getWechatAccountTypeName() {
		return wechatAccountType.getName();
	}


	public void setWechatAccountTypeName(String wechatAccountTypeName) {
		this.wechatAccountTypeName = wechatAccountType.getName();
	}


	public String getShareTypeName() {
		return shareType.getName();
	}


	public void setShareTypeName(String shareTypeName) {
		this.shareTypeName = shareTypeName;
	}


	public String getUserName() {
		return user.getName();
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public ShareType getShareType() {
		return shareType;
	}


	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}


	public WechatAccountType getWechatAccountType() {
		return wechatAccountType;
	}


	public void setWechatAccountType(WechatAccountType wechatAccountType) {
		this.wechatAccountType = wechatAccountType;
	}


	public String getCampaign() {
		return campaign;
	}


	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	   
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

	
	
}
