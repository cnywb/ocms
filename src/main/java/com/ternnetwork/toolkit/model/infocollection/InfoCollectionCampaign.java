package com.ternnetwork.toolkit.model.infocollection;

import java.io.Serializable;
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

import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.toolkit.enums.CampaignDataSendFrequency;
import com.ternnetwork.toolkit.enums.CampaignType;




@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_INFO_COLL_CAMPAIGN")
public class InfoCollectionCampaign implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_INFO_COLLECTION_CAMPAIGN_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
    @Column(nullable=false,name="NAME")
	private String name;//活动名称
	
    @Column(nullable=false,name="CODE",unique=true)
	private String code;//活动代码
    
    @Column(name="KV_URL")
   	private String kvUrl;//活动KV路径
    
    @Column(name="SUBJECT")
   	private String subject;//活动主题
    
    @Column(name="PRODUCT")
	private String product;//活动产品
	
    @Column(name="AWARD")
    private String award;//活动奖项
    
    @Column(name="THUMBNAIL_URL")
   	private String thumbnailUrl;//缩略图路径
 
    @Column(name="DATA_RECEIVE_EMAIL")
 	private String dataReceiveEmail;//活动数据接收邮箱，多个邮箱用分号隔开
    
    @Enumerated@Column(nullable=false,name="DATA_SEND_FREQUENCY")
    private CampaignDataSendFrequency dataSendFrequency;//数据发送频率
     
    @Enumerated@Column(nullable=false,name="TYPE")  
    private CampaignType type; //活动类型
	
    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="START_TIME")
	private Date startTime;//开始时间
	 
    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="END_TIME")
	private Date endTime;//结束时间
	 
    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
	
    @Column(nullable=false,name="ENABLE")
   	private Boolean enable=true;//是否可用 //
    
    @Column(name="TIME_CHECK")
   	private Boolean timeCheck=true;//是否校验时间 //
    
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Column(name="MEMO")
	private String memo;//备注
	    
	
	@Transient
	private String enableName;
	
	@Transient
	private String dataSendFrequencyName;
	
	@Transient
	private String typeName;
	
	@Transient
	private String timeCheckName;
	
	public String getDataSendFrequencyName() {
		return dataSendFrequency.getName();
	}

	public void setDataSendFrequencyName(String dataSendFrequencyName) {
		this.dataSendFrequencyName = dataSendFrequencyName;
	}

	public String getEnableName() {
		return enable==true?"是":"否";
	}

	public Boolean getTimeCheck() {
		return timeCheck;
	}

	public void setTimeCheck(Boolean timeCheck) {
		this.timeCheck = timeCheck;
	}

	public String getTimeCheckName() {
		return timeCheck==null?"是":(timeCheck==true?"是":"否");
	}

	public void setTimeCheckName(String timeCheckName) {
		this.timeCheckName = timeCheckName;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
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

	public String getDataReceiveEmail() {
		return dataReceiveEmail;
	}

	public void setDataReceiveEmail(String dataReceiveEmail) {
		this.dataReceiveEmail = dataReceiveEmail;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampaignDataSendFrequency getDataSendFrequency() {
		return dataSendFrequency;
	}

	public void setDataSendFrequency(CampaignDataSendFrequency dataSendFrequency) {
		this.dataSendFrequency = dataSendFrequency;
	}

	public CampaignType getType() {
		return type;
	}

	public void setType(CampaignType type) {
		this.type = type;
	}

	public String getTypeName() {
		return type.getName();
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getKvUrl() {
		return kvUrl;
	}

	public void setKvUrl(String kvUrl) {
		this.kvUrl = kvUrl;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	
	
}
