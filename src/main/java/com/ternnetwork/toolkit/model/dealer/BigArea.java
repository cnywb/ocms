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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import com.ternnetwork.baseframework.model.base.VersionEntity;
import com.ternnetwork.baseframework.util.JSONUtils;



@JsonIgnoreProperties(value={"latitude","longitude","list","deleteTime","deleted","optCounter","createTime","updateTime","createdById","updatedById"})
@Entity
@Table(name="VS_TOOLKIT_BIG_AREA")
public class BigArea extends VersionEntity{

	@JsonProperty("area_id")
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_BIG_AREA_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
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
	
	@JsonProperty("total")
	@Transient
	private Integer total=0;
	
	@JsonProperty("provinces")
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER,mappedBy="bigArea")
	@Fetch(FetchMode.SUBSELECT)
	private List<Province> list=new ArrayList<Province>();
	
	
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




	public List<Province> getList() {
		return list;
	}




	public void setList(List<Province> list) {
		this.list = list;
	}




	public Integer getTotal() {
		for(Province province:list){
			List<City> cityList=province.getList();
			for(City city:cityList){
			  total=total+city.getList().size();
			}
		}
		return total;
	}




	public void setTotal(Integer total) {
		this.total = total;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		BigArea other = (BigArea) obj;
		if (id != other.id)
			return false;
		return true;
	}




	
	
	public Long getId() {
		return id;
	}




	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}


}
