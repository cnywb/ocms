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

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.JsonDateSerializer;




/**
 * 中奖结果
 * @author xuwen
 *
 */
@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_LUCKY_DRAW_RESULT")
public class LuckyDrawResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8391228790099332353L;

	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_LUCKY_DRAW_RESULT_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	
	private long id;
	
	@ManyToOne@JoinColumn(name="AWARD_ID",nullable=false)
    private LuckyDrawAward award;//中奖奖品
	
    @ManyToOne@JoinColumn(name="USER_ID",nullable=false)
	private User user;//中奖用户
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
		
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Transient
	private String luckyDrawName;
	
	@Transient
	private String awardName;
	
	@Transient
	private String userName;
	
	@Transient
	private String luckyDrawCode;
	
	@Transient
	private String realName;
	@Transient
	private String mobilePhone;
	@Transient
	private String address;
	
	public String getLuckyDrawName() {
		return award.getLuckyDraw().getName();
	}

	public void setLuckyDrawName(String luckyDrawName) {
		this.luckyDrawName = luckyDrawName;
	}

	public String getUserName() {
		return user.getName();
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAwardName() {
		return award.getName();
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getLuckyDrawCode() {
		return  award.getLuckyDraw().getCode();
	}

	public void setLuckyDrawCode(String luckyDrawCode) {
		this.luckyDrawCode = luckyDrawCode;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRealName() {
		return user.getRealName();
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobilePhone() {
		return user.getMobilePhone();
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAddress() {
		return user.getAddress();
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
