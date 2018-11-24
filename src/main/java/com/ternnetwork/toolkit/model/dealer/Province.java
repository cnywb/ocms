package com.ternnetwork.toolkit.model.dealer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import com.ternnetwork.baseframework.model.base.VersionEntity;
import com.ternnetwork.baseframework.util.JSONUtils;


@JsonIgnoreProperties(value={"bigArea","latitude","longitude","list","deleteTime","deleted","optCounter","createTime","updateTime","createdById","updatedById"})
@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_PROVINCE")
public class Province extends VersionEntity{

	@JsonProperty("province_id")
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_PROVINCE_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@JsonProperty("title")
	@Column(name="title",unique=true)
	private String title;
	
	
	
	@JsonProperty("pointx")
	@Column(name="LONGITUDE")
	private Double longitude;//经度
	
	@JsonProperty("pointy")
	@Column(name="LATITUDE")
	private Double latitude;//纬度
	

	@ManyToOne@JoinColumn(name="BIG_AREA_ID",nullable=false)
	private BigArea bigArea;
	
	
	@JsonProperty("total")
	@Transient
	private Integer total=0;
	
	@JsonProperty("citys")
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER,mappedBy="province")
	@Fetch(FetchMode.SUBSELECT)
	private List<City> list=new ArrayList<City>();
	
	
	
	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
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




	public void setId(long id) {
		this.id = id;
	}




	public BigArea getBigArea() {
		return bigArea;
	}




	public void setBigArea(BigArea bigArea) {
		this.bigArea = bigArea;
	}




	public Integer getTotal() {
		for(City city:list){
			  total=total+city.getList().size();
		}
		return total;
	}




	public void setTotal(Integer total) {
		this.total = total;
	}




	public List<City> getList() {
		return list;
	}




	public void setList(List<City> list) {
		this.list = list;
	}




	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}


}
