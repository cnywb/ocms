package com.ternnetwork.toolkit.model.survey;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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



/**
 * 调研
 * @author xuwenfeng
 *
 */
@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_SURVEY")
public class Survey implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3448705052390850489L;

	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_SURVEY_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	

	@Column(nullable=false,name="NAME")
	private String name;//名称
	
	 @Column(nullable=false,name="CODE",unique=true)
	private String code;//代码
	 
	 @Column(name="MEMO")
	private String memo;//备注
	
	@Column(nullable=false,name="ENABLE")
	private Boolean enable=true;//是否可用 //
		
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="START_TIME")
	private Date startTime;//开始时间
		 
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="END_TIME")
	private Date endTime;//结束时间
		 
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
		
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();

	
	@Transient
    private String enableName;
	
	
	
	public String getEnableName() {
		return enable==true?"是":"否";
	}

	public void setEnableName(String enableName) {
		this.enableName = enableName;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
    
	
}
