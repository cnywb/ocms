package com.ternnetwork.toolkit.model.infocollection;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@JsonAutoDetect
@Entity
@Table(name="VS_TK_INFO_COLL_CAMPAIGN_PAGE")
public class InfoCollectionCampaignPage implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7253881982948779224L;

	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_INFO_COLLECTION_CAMPAIGN_PAGE_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
    @Column(nullable=false,name="NAME")
	private String name;//名称
	
    @Column(nullable=false,name="CODE")
	private String code;//代码
    
    @Column(name="TEMPLATE")
	private String template;//页面模版
    
    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
   	private Date createTime=new Date();
   	
  
       
   	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
   	private Date updateTime=new Date();
   	
   	@ManyToOne@JoinColumn(name="CAMPAIGN_ID",nullable=false)
   	private InfoCollectionCampaign campaign;
   	
   	
   	@Transient
   	private String url;

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

	public InfoCollectionCampaign getCampaign() {
		return campaign;
	}

	public void setCampaign(InfoCollectionCampaign campaign) {
		this.campaign = campaign;
	}

	public String getUrl() {
		return "toolkit/infocollection/campaign/page/"+getCampaign().getCode()+"/"+getCode()+".htm";
	}

	public void setUrl(String url) {
		this.url = url;
	}
   	
   	
   	
    
    
	

}
