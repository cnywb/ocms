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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.util.JsonDateSerializer;



/**
 * 抽奖规则
 * @author xuwen
 *
 */

@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_LUCKY_DRAW_RULE")
public class LuckyDrawRule implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 3554701565555304643L;
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_LUCKY_DRAW_RULE_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)

	private long id;
	  
	@ManyToOne@JoinColumn(name="AWARD_ID",nullable=false)
    private LuckyDrawAward award;
	  
	@Column(nullable=false,name="AWARD_QTY")
    private Integer awardQty=0;//出奖数量
	  
   
    @Column(nullable=false,name="REMAINING_QTY")
    private Integer remainingQty=0;//剩余数量
    
    
    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="AWARD_DATE")
    private Date awardDate;//出奖日期
    
 
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
		
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Transient
	private String awardCode;
	
	@Transient
	private String awardName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LuckyDrawAward getAward() {
		return award;
	}

	public void setAward(LuckyDrawAward award) {
		this.award = award;
	}

	public Integer getAwardQty() {
		return awardQty;
	}

	public void setAwardQty(Integer awardQty) {
		this.awardQty = awardQty;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
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
	   public Integer getRemainingQty() {
			return remainingQty;
		}

		public void setRemainingQty(Integer remainingQty) {
			this.remainingQty = remainingQty;
		}

		public String getAwardCode() {
			if(award!=null){
				return award.getCode();
			}
			return awardCode;
		}

		public void setAwardCode(String awardCode) {
			this.awardCode = awardCode;
		}

		public String getAwardName() {
			if(award!=null){
				return award.getName();
			}
			return awardName;
		}

		public void setAwardName(String awardName) {
			this.awardName = awardName;
		}
	  
	
}
