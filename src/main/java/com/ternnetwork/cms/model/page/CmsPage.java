package com.ternnetwork.cms.model.page;

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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.cms.enums.CmsPageStatus;
import com.ternnetwork.cms.model.channel.Channel;


@Cache(usage =CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="VS_CMS_PAGE")
@JsonAutoDetect
public class CmsPage {
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="CMS_PAGE_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@Column(name="NAME")
	private String name;//页面名称
	
	@Column(name="CODE")
	private String code;//页面代码，栏目内惟一
	
	@ManyToOne@JoinColumn(name="CHANNEL_ID")
	private Channel channel;//所属栏目
	
    @Column(name="TEMPLATE")
	private String template;//页面模版
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Column(name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Enumerated@Column(name="STATUS")
	private CmsPageStatus status=CmsPageStatus.PUBLISHED;
	
	@Transient
	private String statusName;
	
	@Transient
	private String channelName;
	
	
	
	@Transient
	private String url;//动态页面的访问路径
	
	@Transient
	private String channelCode;
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getChannelName() {
		return channel.getNameZh();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}



	public Channel getChannel() {
		return channel;
	}


	public void setChannel(Channel channel) {
		this.channel = channel;
	}





	
	public CmsPageStatus getStatus() {
		return status;
	}

	public void setStatus(CmsPageStatus status) {
		this.status = status;
	}

	public String getStatusName() {
		return status.getName();
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	
	
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

	public String getUrl() {
		return "channel/".concat(this.getChannel().getCode()).concat("/").concat(this.getCode()).concat(".htm");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChannelCode() {
		return channel.getCode();
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	

	

	
	
	

}
