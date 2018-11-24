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
 * 调研答题卡
 * @author xuwenfeng
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(value={"answer"})
@Entity
@Table(name="VS_TOOLKIT_SURVEY_ANSWER_SHEET")
public class SurveyAnswerSheet  implements Serializable{
	
	    /**
	 * 
	 */
	   private static final long serialVersionUID = -5720581711141600270L;
	   
	   @Id@Column(name="ID",nullable=false)
	   @GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
       @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_SURVEY_ANSWER_SHEET_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
       private long id;
		
	   @ManyToOne@JoinColumn(name="SURVEY_ANSWER_ID",nullable=false)
	    private SurveyAnswer answer;//答案
	    
	   @ManyToOne@JoinColumn(name="USER_ID",nullable=false)
	    private User user;//答题用户
	    
	   @Column(name="ANSWER_MEMO")
	    private String answerMemo;//答案备注
		
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

		public SurveyAnswer getAnswer() {
			return answer;
		}

		public void setAnswer(SurveyAnswer answer) {
			this.answer = answer;
		}

		

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getAnswerMemo() {
			return answerMemo;
		}

		public void setAnswerMemo(String answerMemo) {
			this.answerMemo = answerMemo;
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
