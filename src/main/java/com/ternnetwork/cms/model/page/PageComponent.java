package com.ternnetwork.cms.model.page;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;

@Cache(usage =CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="VS_CMS_PAGE_COMPONENT")
@JsonAutoDetect
public class PageComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1480499780223987903L;
	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="CMS_PAGE_COMPONENT_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@Column(name="NAME",nullable=false,length=500)
	private String name;
	
	@Column(name="CODE",nullable=false,unique=true,length=500)
	private String code;//代码
	
	
	@Column(name="SOURCE_CODE")@Lob@Basic(fetch = FetchType.LAZY)
	private String sourceCode;
	
	@Column(name="MEMO")
	private String memo;
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();
	
	
	@Column(name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@ManyToOne(fetch=FetchType.EAGER)@JoinColumn(name="USER_ID")
    private User user;//创建者
	
	@Transient
	private String javaScript;
	
	@Transient
	private String css;
	
	
	@Transient
	private  String realPath;
	

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

	

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
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

	public User getUser() {
		return user;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	public String getJavaScript() {
		return javaScript;
	}

	public void setJavaScript(String javaScript) {
		this.javaScript = javaScript;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

	

}
