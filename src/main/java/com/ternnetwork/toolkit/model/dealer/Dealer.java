package com.ternnetwork.toolkit.model.dealer;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import com.ternnetwork.baseframework.model.base.VersionEntity;
import com.ternnetwork.baseframework.util.JSONUtils;


@JsonIgnoreProperties(value={"city","latitude","longitude","list","deleteTime","deleted","optCounter","updateTime","createdById","updatedById"})
@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_DEALER")
public class Dealer extends VersionEntity{
	
	@JsonProperty("dealer_id")
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_DEALER_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	
	
	@Column(name="CITY_OR_TERRITORY")
	private String cityOrTerritory;//城市/区域
	
	@JsonProperty("dealer_code")
	@Column(name="DEALER_CODE",unique=true)
	private String dealerCode;//经销商代码
	
	@JsonProperty("dealer_service_code")
	@Column(name="DEALER_SERVICE_CODE",unique=true)
	private String dealerServiceCode;//经销商服务代码
	
	@JsonProperty("dealer_name")
	@Column(name="DEALER_NAME")
	private String dealerName;//经销商名称 
	
	@JsonProperty("address")
	@Column(name="FORD_BRAND_SHOP_ADDRESS")
	private String fordBrandShopAddress;//福特品牌店地址
	
	@Column(name="POSTCODE")
	private String postcode;//邮编
	
	@JsonProperty("area_code")
	@Column(name="AREA_CODE")
	private String areaCode;//区号
	
	@JsonProperty("tel")
	@Column(name="SALES_HOT_LINE")
	private String salesHotline;//销售热线
	
	@JsonProperty("service_tel")
	@Column(name="SERVICE_TEL")
	private String serviceTel;
	
	@JsonProperty("fex")
	@Column(name="FAX")
	private String fax;//传真
	
	@JsonProperty("email")
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	
	@JsonProperty("pointx")
	@Column(name="LONGITUDE")
	private Double longitude;//经度
	
	@JsonProperty("pointy")
	@Column(name="LATITUDE")
	private Double latitude;//纬度
	
	@Column(name="ADDRESS_UPDATED",nullable=false)
	private Boolean addressUpdated;//地址是否有更新
	
	@JsonIgnore
	@ManyToOne@JoinColumn(name="CITY_ID")
	private City city;
	
	@Transient
	private String addressUpdatedName;
	
	@Transient
	private String province;//省
	
	@Transient
	private String bigArea;//大区
	

	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}



	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	
	

	public String getCityOrTerritory() {
		return cityOrTerritory;
	}

	public void setCityOrTerritory(String cityOrTerritory) {
		this.cityOrTerritory = cityOrTerritory;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getFordBrandShopAddress() {
		return fordBrandShopAddress;
	}

	public void setFordBrandShopAddress(String fordBrandShopAddress) {
		this.fordBrandShopAddress = fordBrandShopAddress;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSalesHotline() {
		return salesHotline;
	}

	public void setSalesHotline(String salesHotline) {
		this.salesHotline = salesHotline;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setId(long id) {
		this.id = id;
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

	public Boolean getAddressUpdated() {
		return addressUpdated;
	}

	public void setAddressUpdated(Boolean addressUpdated) {
		this.addressUpdated = addressUpdated;
	}

	

	public String getAddressUpdatedName() {
		return (addressUpdated==true?"是":"否");
	}

	public void setAddressUpdatedName(String addressUpdatedName) {
		this.addressUpdatedName = addressUpdatedName;
	}

	
	
	
	
	public String getProvince() {
		if(city!=null){
			return city.getProvince().getTitle();
		}
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getBigArea() {
		return city==null?"":city.getProvince().getBigArea().getTitle();
	}

	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}

	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}



	public String getServiceTel() {
		return serviceTel;
	}



	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}



	public String getDealerServiceCode() {
		return dealerServiceCode;
	}



	public void setDealerServiceCode(String dealerServiceCode) {
		this.dealerServiceCode = dealerServiceCode;
	}

	

	 
	

	

}
