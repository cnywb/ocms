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
 * 抽奖日志
 * @author xuwen
 *
 */
@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_LUCKY_DRAW_LOG")
public class LuckyDrawLog implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3941327730123838269L;

	/**
	 * 
	 */
	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_LUCKY_DRAW_LOG_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
    @Column(nullable=false,name="WIN")
	private Boolean win=false;//是否中奖 
	
	@ManyToOne@JoinColumn(name="USER_ID",nullable=false)
	private User user;//抽奖用户
	
	@ManyToOne@JoinColumn(name="LUCKY_DRAW_ID",nullable=false)
	private LuckyDraw luckyDraw;//所属活动
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
		
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Transient
	private String winName;
	
	@Transient
	private String luckyDrawName;
	
	@Transient
	private String userName;
	

	@Transient
	private String luckyDrawCode;
	
	public String getWinName() {
	
		return win==true?"是":"否";
	}

	public void setWinName(String winName) {
		this.winName = winName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	


	public Boolean getWin() {
		return win;
	}

	public void setWin(Boolean win) {
		this.win = win;
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

	public LuckyDraw getLuckyDraw() {
		return luckyDraw;
	}

	public void setLuckyDraw(LuckyDraw luckyDraw) {
		this.luckyDraw = luckyDraw;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLuckyDrawName() {
		return luckyDraw.getName();
	}

	public void setLuckyDrawName(String luckyDrawName) {
		this.luckyDrawName = luckyDrawName;
	}

	public String getLuckyDrawCode() {
		return luckyDraw.getCode();
	}

	public void setLuckyDrawCode(String luckyDrawCode) {
		this.luckyDrawCode = luckyDrawCode;
	}

	public String getUserName() {
		return user.getName();
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
