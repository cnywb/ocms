package com.ternnetwork.toolkit.model.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.toolkit.enums.SurveyQuestionType;

/**
 * 调研问题
 * @author xuwenfeng
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(value={"group"})//因为group没有配及时加载所以不作解析 
@Entity
@Table(name="VS_TOOLKIT_SURVEY_QUESTION")
public class SurveyQuestion  implements Serializable{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = -242184207196083540L;

	   @Id@Column(name="ID",nullable=false)
	   @GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
       @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_SURVEY_QUESTION_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	   private long id;
		
		
		@ManyToOne@JoinColumn(name="SURVEY_QUESTION_GROUP_ID",nullable=false)
	    private SurveyQuestionGroup group;
		
		@Column(nullable=false,name="NAME")
		private String name;//名称
		    
		@Column(nullable=false,name="CODE",unique=true)
		private String code;//代码
		
		@Column(name="SEQUENCE")
		private Integer sequence;//顺序
		
		@Enumerated@Column(name="TYPE")
	    private SurveyQuestionType type;
	 
		@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
		private Date createTime=new Date();
			
		@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
		private Date updateTime=new Date();
		
		@Transient
		private String typeName;
	    
		@Transient
	    private List<SurveyAnswer> answerList=new ArrayList<SurveyAnswer>();
	    

		public List<SurveyAnswer> getAnswerList() {
			return answerList;
		}

		public void setAnswerList(List<SurveyAnswer> answerList) {
			this.answerList = answerList;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public SurveyQuestionGroup getGroup() {
			return group;
		}

		public void setGroup(SurveyQuestionGroup group) {
			this.group = group;
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

		public Integer getSequence() {
			return sequence;
		}

		public void setSequence(Integer sequence) {
			this.sequence = sequence;
		}

		
		
		
	
		public SurveyQuestionType getType() {
			return type;
		}

		public void setType(SurveyQuestionType type) {
			this.type = type;
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

		public String getTypeName() {
			return type.getName();
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
	    
		@Override
		public String toString() {
			return JSONUtils.objectToJson(this);
		}

}
