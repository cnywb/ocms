package com.ternnetwork.toolkit.model.vote;

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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;



@JsonAutoDetect
@JsonIgnoreProperties(value={"vote"})
@Entity
@Table(name="VS_TOOLKIT_VOTE_ITEM")
public class VoteItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047064123563593845L;

	
	    @Id@Column(name="ID",nullable=false)
	    @GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
        @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_VOTE_ITEM_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	    private long id;
	
	    
		@ManyToOne@JoinColumn(name="VOTE_ID",nullable=false)
	    private Vote vote;
	    
	    @Column(nullable=false,name="NAME")
		private String name;//名称
		
	    @Column(nullable=false,name="CODE",unique=true)
		private String code;//代码
		
	    @Column(nullable=false,name="VOTE_COUNT")
	 	private Integer voteCount=0;
	    
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

		public Vote getVote() {
			return vote;
		}

		public void setVote(Vote vote) {
			this.vote = vote;
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

		public Integer getVoteCount() {
			return voteCount;
		}

		public void setVoteCount(Integer voteCount) {
			this.voteCount = voteCount;
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
		@Override
		public String toString() {
			return JSONUtils.objectToJson(this);
		}
}
