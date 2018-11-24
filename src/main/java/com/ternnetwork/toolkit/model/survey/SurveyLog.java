package com.ternnetwork.toolkit.model.survey;

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

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;

/**
 * 调研参与日志
 * @author xuwenfeng
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
@Entity
@Table(name="VS_TOOLKIT_SURVEY_LOG")
public class SurveyLog  implements Serializable{
	
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 7275044339089215006L;
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_SURVEY_LOG_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)

		private long id;
	
		@ManyToOne@JoinColumn(name="USER_ID",nullable=false)
	    private User user;
	    
		@ManyToOne@JoinColumn(name="SURVEY_ID",nullable=false)
	    private Survey survey;
	    
		@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
		private Date createTime=new Date();
				
		@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
		private Date updateTime=new Date();

		public Survey getSurvey() {
			return survey;
		}

		public void setSurvey(Survey survey) {
			this.survey = survey;
		}

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
