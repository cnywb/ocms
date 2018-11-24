package com.ternnetwork.cms.model.content;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.cms.model.site.Site;




@Entity
@JsonAutoDetect
@Table(name="VS_CMS_CONTENT_CATEGORY")
public class ContentCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2581961283540371796L;

	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="CMS_CONTENT_CATEGORY_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@Column(name="CODE",nullable=false,length=500)
	private String code;//代码
	
	@Column(name="NAME_ZH",nullable=false,length=500)
	private String nameZh;//中文名
	
	@Column(name="NAME_EN",nullable=false,length=500)
	private String nameEn;//英文名
	
	@Column(name="PARENT_ID",nullable=false)
	private long parentId=0;//父菜单ID
	
	@Column(name="SEQ_NUM",nullable=false)
	private Integer seqNum=0;//排序
	
	@Column(name="PAGE_URL")
	private String pageUrl;
	
	@Column(name="VISIBLE")
	private Boolean visible;//是否可见
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@ManyToOne@JoinColumn(name="SITE_ID",nullable=false)
	private Site site;
	
	@Column(name="DESCRIPTION")@Lob
	private byte[] description;
	

	
	@Transient
	private String tempContentDescriptionString;


	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
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
		ContentCategory other = (ContentCategory) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	
	
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public byte[] getDescription() {
		return description;
	}
	public void setDescription(byte[] description) {
		this.description = description;
	}
	public String getTempContentDescriptionString() {
		return tempContentDescriptionString;
	}
	public void setTempContentDescriptionString(String tempContentDescriptionString) {
		this.tempContentDescriptionString = tempContentDescriptionString;
	}
	
	
}
