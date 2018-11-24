package com.ternnetwork.toolkit.model.infocollection;

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

import com.ternnetwork.baseframework.enums.Gender;



@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_INFO_COLL_DATA")
public class InfoCollectionData {
	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_INFO_COLLECTION_DATA_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	
	@ManyToOne@JoinColumn(name="CAMPAIGN_ID",nullable=false)
	private InfoCollectionCampaign campaign;
	
	@ManyToOne@JoinColumn(name="CHANNEL_ID",nullable=false)
	private InfoCollectionChannel  channel;
	
	
	
	@Column(name="PURCHASED_PRODUCT_MODEL")
	private String purchasedProductModel;//已购产品
	
	@Column(name="PURCHASED_PRODUCT_SN")
	private String purchasedProductSerialNumber;//已购产品系列号
	
    @Column(name="BUYER_NAME")
	private String buyerName;//买家姓名
	
    @Enumerated@Column(name="BUYER_GENDER")
	private Gender buyerGender;//买家性别
	
	@Column(name="BUYER_MOBILE_HPONE_NO")
	private String buyerMobilePhoneNo;//买家手机号
	
	@Column(name="BUYER_PROVINCE")
	private String buyerProvince;//买家所在省
	
	@Column(name="BUYER_CITY")
	private String buyerCity;//买家所在市
	
    @Column(name="BUYER_DEALER")
	private String buyerDealer;//买家经销商
    

	
	@Column(name="BUY_TIME")
	private String buyTime;//购买时间
	
	@Column(name="PT_BUYER_NAME",nullable=false)
	private String potentialBuyerName;//潜客姓名
	
	@Enumerated@Column(name="PT_BUYER_GENDER")
	private Gender potentialBuyerGender;//潜客性别
	
    @Column(name="PT_BUYER_MOBILE_HPONE_NO",nullable=false)
	private String potentialBuyerMobilePhoneNo;//潜客手机
	
	@Column(name="PT_BUYER_PROVINCE")
    private String potentialBuyerProvince;//潜客所在省
	
    @Column(name="PT_BUYER_CITY")
	private String potentialBuyerCity;//潜客所在市
	
	@Column(name="PT_BUYER_DEALER")
	private String potentialBuyerDealer;//潜客经销商
	
	
	
    @Column(name="PT_BUY_PRODUCT_MODEL",nullable=false)
	private String potentialBuyProductModel;//潜在购买产品型号
	
	@Column(name="PT_BUY_TIME")
	private String potentialBuyTime;//潜在购买时间
	
	@Column(name="PT_BUY_BUDGET")
    private String potentialBuyBudget;//潜在购买预算
	
	@Column(name="FILE_URL")
	private String fileUrl;//用户上传文件的URL
	
	@Column(name="WECHAT_ID")
	private String wechatId;//微信ID
		
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
    private Date updateTime=new Date();
	
	@Column(name="BUYER_DEALER_CODE")
	private String buyerDealerCode;//买家经销商代码  
	
	@Column(name="PT_BUYER_DEALER_CODE")
	private String potentialBuyerDealerCode;//潜客经销商代码

	@Column(name="STATUS")
	private Integer status;//状态
	
	
	@Temporal(TemporalType.TIMESTAMP)@Column(name="STATUS_UPDATE_TIME")
    private Date statusUpdateTime;
	
	@Column(name="SUBJECT")
	private String subject;//活动主题
	
	@Transient
	private String buyerGenderName;
	
	@Transient
	private String potentialBuyerGenderName;
	
	@Transient
	private String campaignName;
	@Transient
	private String channelName;
	
	@Column(name="DATA_KEY",unique=true)
	private String dataKey;//数据键，保证在业务上是惟一
	
	

	public String getBuyerGenderName() {
		return buyerGender==null?"":buyerGender.getName();
	}

	public void setBuyerGenderName(String buyerGenderName) {
		this.buyerGenderName = buyerGenderName;
	}

	public String getPotentialBuyerGenderName() {
		return potentialBuyerGender==null?"":potentialBuyerGender.getName();
	}

	public void setPotentialBuyerGenderName(String potentialBuyerGenderName) {
		this.potentialBuyerGenderName = potentialBuyerGenderName;
	}

	public String getCampaignName() {
		
		return campaign==null?"":campaign.getName();
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getChannelName() {
		return channel==null?"":channel.getName();
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public InfoCollectionCampaign getCampaign() {
		return campaign;
	}

	public void setCampaign(InfoCollectionCampaign campaign) {
		this.campaign = campaign;
	}

	public InfoCollectionChannel getChannel() {
		return channel;
	}

	public void setChannel(InfoCollectionChannel channel) {
		this.channel = channel;
	}



	public String getPurchasedProductModel() {
		return purchasedProductModel;
	}

	public void setPurchasedProductModel(String purchasedProductModel) {
		this.purchasedProductModel = purchasedProductModel;
	}

	public String getPurchasedProductSerialNumber() {
		return purchasedProductSerialNumber;
	}

	public void setPurchasedProductSerialNumber(String purchasedProductSerialNumber) {
		this.purchasedProductSerialNumber = purchasedProductSerialNumber;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Gender getBuyerGender() {
		return buyerGender;
	}

	public void setBuyerGender(Gender buyerGender) {
		this.buyerGender = buyerGender;
	}

	public String getBuyerMobilePhoneNo() {
		return buyerMobilePhoneNo;
	}

	public void setBuyerMobilePhoneNo(String buyerMobilePhoneNo) {
		this.buyerMobilePhoneNo = buyerMobilePhoneNo;
	}

	public String getBuyerProvince() {
		return buyerProvince;
	}

	public void setBuyerProvince(String buyerProvince) {
		this.buyerProvince = buyerProvince;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerDealer() {
		return buyerDealer;
	}

	public void setBuyerDealer(String buyerDealer) {
		this.buyerDealer = buyerDealer;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public String getPotentialBuyerName() {
		return potentialBuyerName;
	}

	public void setPotentialBuyerName(String potentialBuyerName) {
		this.potentialBuyerName = potentialBuyerName;
	}

	public Gender getPotentialBuyerGender() {
		return potentialBuyerGender;
	}

	public void setPotentialBuyerGender(Gender potentialBuyerGender) {
		this.potentialBuyerGender = potentialBuyerGender;
	}

	public String getPotentialBuyerMobilePhoneNo() {
		return potentialBuyerMobilePhoneNo;
	}

	public void setPotentialBuyerMobilePhoneNo(String potentialBuyerMobilePhoneNo) {
		this.potentialBuyerMobilePhoneNo = potentialBuyerMobilePhoneNo;
	}

	public String getPotentialBuyerProvince() {
		return potentialBuyerProvince;
	}

	public void setPotentialBuyerProvince(String potentialBuyerProvince) {
		this.potentialBuyerProvince = potentialBuyerProvince;
	}

	public String getPotentialBuyerCity() {
		return potentialBuyerCity;
	}

	public void setPotentialBuyerCity(String potentialBuyerCity) {
		this.potentialBuyerCity = potentialBuyerCity;
	}

	public String getPotentialBuyerDealer() {
		return potentialBuyerDealer;
	}

	public void setPotentialBuyerDealer(String potentialBuyerDealer) {
		this.potentialBuyerDealer = potentialBuyerDealer;
	}

	
	public String getPotentialBuyProductModel() {
		return potentialBuyProductModel;
	}

	public void setPotentialBuyProductModel(String potentialBuyProductModel) {
		this.potentialBuyProductModel = potentialBuyProductModel;
	}

	public String getPotentialBuyTime() {
		return potentialBuyTime;
	}

	public void setPotentialBuyTime(String potentialBuyTime) {
		this.potentialBuyTime = potentialBuyTime;
	}

	public String getPotentialBuyBudget() {
		return potentialBuyBudget;
	}

	public void setPotentialBuyBudget(String potentialBuyBudget) {
		this.potentialBuyBudget = potentialBuyBudget;
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

	

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getBuyerDealerCode() {
		return buyerDealerCode;
	}

	public void setBuyerDealerCode(String buyerDealerCode) {
		this.buyerDealerCode = buyerDealerCode;
	}

	public String getPotentialBuyerDealerCode() {
		return potentialBuyerDealerCode;
	}

	public void setPotentialBuyerDealerCode(String potentialBuyerDealerCode) {
		this.potentialBuyerDealerCode = potentialBuyerDealerCode;
	}

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStatusUpdateTime() {
		return statusUpdateTime;
	}

	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	
	
	
	

}
