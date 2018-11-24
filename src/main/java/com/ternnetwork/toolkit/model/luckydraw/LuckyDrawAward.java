package com.ternnetwork.toolkit.model.luckydraw;

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

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.util.JsonDateSerializer;




/**
 * 抽奖奖品
 * @author xuwen
 *
 */
@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_LUCKY_DRAW_AWARD")
public class LuckyDrawAward implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5069347220996727192L;

	
	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_LUCKY_DRAW_AWARD_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	

	
	@Column(name="NAME",nullable=false)
	private String name;//奖品名称
	
	@Column(name="CODE",unique=true,nullable=false)
    private String code;//代码
	
	@Column(name="GRADE")
	private String grade="";//奖品等级
	
	@Column(name="QUANTITY")
	private Integer quantity=0;//总数量
	
	@Column(name="REMAINING_QTY")
	private Integer remainingQty=0;//剩余数量
	
	@Column(name="MEMO")
	private String memo="";//备注
	
	@Column(name="IMAGE_URL")
	private String imageUrl="";//奖品图片
	
	@ManyToOne@JoinColumn(name="LUCK_YDRAW_ID",nullable=false)
	private LuckyDraw luckyDraw;//所属活动
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
		
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();


    
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LuckyDraw getLuckyDraw() {
		return luckyDraw;
	}

	public void setLuckyDraw(LuckyDraw luckyDraw) {
		this.luckyDraw = luckyDraw;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(Integer remainingQty) {
		this.remainingQty = remainingQty;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
