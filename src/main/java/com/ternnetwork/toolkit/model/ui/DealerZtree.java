package com.ternnetwork.toolkit.model.ui;

import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.toolkit.enums.DealerBigArea;

public class DealerZtree {

	   private String id;
		
	   private String pId;
	   
	   private Long realId;
	
	   private String name;
	   
	   private String title;
	   
	   private Double longitude;
	   
	   private Double latitude;//纬度
	   
       private Long parentId;
	 
	   private Boolean isParent;
	   
	   private String emailAddress;
	   
	   private String fax;//传真
	   
	   private String salesHotline;//销售热线
	   
	   private String serviceTel;//服务电话
	   
	   private String postcode;//邮编
	   
	   private String fordBrandShopAddress;//福特品牌店地址
	   
	   private String dealerName;//经销商名称 
	   
	   private String dealerCode;//经销商代码
	   
	   private String  dealerServiceCode; //经销商代码
	   
	   private String cityOrTerritory;//城市/区域
	   
	   private String facilityStatus;//设施状态
	   
	   private String no;//序号
	   
	   private DealerBigArea dealerBigArea;
	   
	   private String cityId;
	   
	   private String provinceId;

	   private String bigAreaId;
	   
	   private String areaCode;
	   
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSalesHotline() {
		return salesHotline;
	}

	public void setSalesHotline(String salesHotline) {
		this.salesHotline = salesHotline;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getFordBrandShopAddress() {
		return fordBrandShopAddress;
	}

	public void setFordBrandShopAddress(String fordBrandShopAddress) {
		this.fordBrandShopAddress = fordBrandShopAddress;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getCityOrTerritory() {
		return cityOrTerritory;
	}

	public void setCityOrTerritory(String cityOrTerritory) {
		this.cityOrTerritory = cityOrTerritory;
	}

	public String getFacilityStatus() {
		return facilityStatus;
	}

	public void setFacilityStatus(String facilityStatus) {
		this.facilityStatus = facilityStatus;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public DealerBigArea getDealerBigArea() {
		return dealerBigArea;
	}

	public void setDealerBigArea(DealerBigArea dealerBigArea) {
		this.dealerBigArea = dealerBigArea;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Long getRealId() {
		return realId;
	}

	public void setRealId(Long realId) {
		this.realId = realId;
	}
	   
	  public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	

	public String getBigAreaId() {
		return bigAreaId;
	}

	public void setBigAreaId(String bigAreaId) {
		this.bigAreaId = bigAreaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Override
			public String toString() {
				return JSONUtils.objectToJson(this);
			}

	public String getDealerServiceCode() {
		return dealerServiceCode;
	}

	public void setDealerServiceCode(String dealerServiceCode) {
		this.dealerServiceCode = dealerServiceCode;
	}
	   
	
}
