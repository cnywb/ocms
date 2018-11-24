package com.ternnetwork.toolkit.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.toolkit.enums.ReportFormat;
import com.ternnetwork.toolkit.enums.ReportSendFrequency;

/**
 * 数据报告
 * @author xuwenfeng
 *
 */
@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_REPORT")
@JsonIgnoreProperties(value={"items"})
public class Report implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9045450100661309948L;
	
	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_REPORT_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@Column(nullable=false,name="NAME",unique=true)
	private String name;//报告名称
	
	@Enumerated@Column(nullable=false,name="FORMAT")
	private ReportFormat format;//报告格式
	
	@Enumerated@Column(nullable=false,name="SEND_FREQUENCY")
	private ReportSendFrequency sendFrequency;//发送频率
	
	@Column(name="RECEIVE_EMAIL")
	private String receiveEmail;//接收邮箱，多个邮箱用分号隔开
	
	@Column(name="MEMO")
	private String memo;//备注
	
	
	
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
		
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.EAGER,mappedBy="report")
	@Fetch(FetchMode.SUBSELECT)
	private List<ReportItem> items=new ArrayList<ReportItem>();
	
   
	@Transient
	private String formatName;
	@Transient
	private String sendFrequencyName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ReportFormat getFormat() {
		return format;
	}

	public void setFormat(ReportFormat format) {
		this.format = format;
	}

	public ReportSendFrequency getSendFrequency() {
		return sendFrequency;
	}

	public void setSendFrequency(ReportSendFrequency sendFrequency) {
		this.sendFrequency = sendFrequency;
	}

	public String getReceiveEmail() {
		return receiveEmail;
	}

	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<ReportItem> getItems() {
		return items;
	}

	public void setItems(List<ReportItem> items) {
		this.items = items;
	}

	public String getFormatName() {
		return format==null?"":format.getName();
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public String getSendFrequencyName() {
		return sendFrequency==null?"":sendFrequency.getName();
	}

	public void setSendFrequencyName(String sendFrequencyName) {
		this.sendFrequencyName = sendFrequencyName;
	}
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

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
		Report other = (Report) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
