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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;

/**
 * 调研问题答案选项
 * @author xuwenfeng
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(value={"question"})
@Entity
@Table(name="VS_TOOLKIT_SURVEY_ANSWER")
public class SurveyAnswer  implements Serializable{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 6309675197737675070L;

	   @Id@Column(name="ID",nullable=false)
	   @GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
       @TableGenerator(name = "id_gen",pkColumnValue="TOOLKIT_SURVEY_ANSWER_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	  private long id;
		
		@ManyToOne@JoinColumn(name="SURVEY_QUESTION_ID",nullable=false)
	    private SurveyQuestion question;//所属问题
		
		@Column(nullable=false,name="NAME")
		private String name;//名称
		    
		@Column(nullable=false,name="CODE",unique=true)
		private String code;//代码
		
		@Column(name="SEQUENCE")
		private Integer sequence;//顺序
		
		@Column(name="NEED_MEMO")
		private Boolean needMemo;//是否需要说明
		 
		@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
		private Date createTime=new Date();
			
		@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
		private Date updateTime=new Date();
		
		@Transient
		private String needMemoName;
		
		@Transient
		private Long sheetCount;//答题数量
		
		
		

		public String getNeedMemoName() {
			return needMemo==true?"是":"否";
		}

		public void setNeedMemoName(String needMemoName) {
			this.needMemoName = needMemoName;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public SurveyQuestion getQuestion() {
			return question;
		}

		public void setQuestion(SurveyQuestion question) {
			this.question = question;
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

		public Boolean getNeedMemo() {
			return needMemo;
		}

		public void setNeedMemo(Boolean needMemo) {
			this.needMemo = needMemo;
		}
	    
		@Override
		public String toString() {
			return JSONUtils.objectToJson(this);
		}

		public Long getSheetCount() {
			return sheetCount;
		}

		public void setSheetCount(Long sheetCount) {
			this.sheetCount = sheetCount;
		}

}
