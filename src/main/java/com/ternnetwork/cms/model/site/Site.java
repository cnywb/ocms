package com.ternnetwork.cms.model.site;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.cms.enums.SiteStatus;


@Entity
@JsonAutoDetect
@Table(name="VS_CMS_SITE")
public class Site{
	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="CMS_SITE_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	
	@Column(nullable=false,name="NAME")
	private String name;
	
	@Column(nullable=false,name="DOMAIN",unique=true)
	private String domain;
	
	@Column(nullable=false,name="CODE",unique=true)
    private String code;//代码
	
	
	@Column(name="INDEX_TEMPLATE")
    private String indexTemplate;//首页模版
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Enumerated@Column(name="STATUS")
	private SiteStatus status;
	
	
	
	
	@Transient
	private String statusName;

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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public SiteStatus getStatus() {
		return status;
	}

	public void setStatus(SiteStatus status) {
		this.status = status;
	}
	
	public String getStatusName() {
		return status.getName();
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

	public String getIndexTemplate() {
		return indexTemplate;
	}

	public void setIndexTemplate(String indexTemplate) {
		this.indexTemplate = indexTemplate;
	}

	
	public String toString() {
		return JSONUtils.objectToJson(this);
	}


}
