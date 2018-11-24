package com.ternnetwork.toolkit.model.vote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;




@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_VOTE")
public class Vote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1522725404155861326L;

	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_VOTE_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	       @Column(nullable=false,name="NAME")
			private String name;//名称
			
		    @Column(nullable=false,name="CODE",unique=true)
			private String code;//代码
			
		   
			
		    @Column(nullable=false,name="ENABLE")
			private Boolean enable=true;//是否可用 //
		    
		    @Column(nullable=false,name="MULTIPLE_VOTE")
		    private Boolean multipleVote=false;//是否可以投多个选项
		    
		    
		    @Column(nullable=false,name="VOTE_COUNT")
			private Integer voteCount=0;
			
		    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="START_TIME")
			private Date startTime;//开始时间
			 
		    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="END_TIME")
			private Date endTime;//结束时间
			 
		    @Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
			private Date createTime=new Date();
			
			@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
			private Date updateTime=new Date();
			
			@Transient
			private List<VoteItem> items=new ArrayList<VoteItem>();

			@Transient
		    private String enableName;
			
			@Transient
		    private String multipleVoteName;
			
			public String getEnableName() {
				return enable==true?"是":"否";
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

			public Boolean getEnable() {
				return enable;
			}

			public void setEnable(Boolean enable) {
				this.enable = enable;
			}

			public Boolean getMultipleVote() {
				return multipleVote;
			}

			public void setMultipleVote(Boolean multipleVote) {
				this.multipleVote = multipleVote;
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
			@JsonSerialize(using=JsonDateSerializer.class)
			public Date getUpdateTime() {
				return updateTime;
			}

			public void setUpdateTime(Date updateTime) {
				this.updateTime = updateTime;
			}

			public String getMultipleVoteName() {
				return multipleVote==true?"是":"否";
			}

			public void setMultipleVoteName(String multipleVoteName) {
				this.multipleVoteName = multipleVoteName;
			}

			public Integer getVoteCount() {
				return voteCount;
			}

			public void setVoteCount(Integer voteCount) {
				this.voteCount = voteCount;
			}

			public List<VoteItem> getItems() {
				return items;
			}

			public void setItems(List<VoteItem> items) {
				this.items = items;
			}

			@Override
			public String toString() {
				return JSONUtils.objectToJson(this);
			}

}
