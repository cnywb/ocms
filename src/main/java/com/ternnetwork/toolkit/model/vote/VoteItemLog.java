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
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.JsonDateSerializer;

@JsonAutoDetect
@Entity
@Table(name="VS_TOOLKIT_VOTE_ITEM_LOG")
public class VoteItemLog implements Serializable{
	

	    /**
	 * 
	 */
	private static final long serialVersionUID = -6720763213633743322L;


		@Id@Column(name="ID",nullable=false)
	    @GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
        @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_VOTE_ITEM_LOG_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	    private long id;
	
	   
	   @ManyToOne@JoinColumn(name="USER_ID",nullable=false)
	   private User user;//用户
	   
	   @ManyToOne@JoinColumn(name="VOTE_ITEM_ID",nullable=false)
	   private VoteItem voteItem;
	   
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

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public VoteItem getVoteItem() {
			return voteItem;
		}

		public void setVoteItem(VoteItem voteItem) {
			this.voteItem = voteItem;
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
}
