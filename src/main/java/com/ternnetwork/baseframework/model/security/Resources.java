/**
 * 
 */
package com.ternnetwork.baseframework.model.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ternnetwork.baseframework.enums.ResourcesType;
import com.ternnetwork.baseframework.enums.RoleType;
import com.ternnetwork.baseframework.util.JSONUtils;






/**
 * 系统资源
 *bsierp_ssj
 * @author wenfeng.xu
 *2011-4-10下午01:24:21
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="VS_RESOURCES")
@JsonIgnoreProperties(value={"rescRoles"})
public class Resources implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1891455076505342282L;
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="RESOURCES_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@Column(name="NAME_ZH",nullable=false,length=100)
	private String nameZh;//中文名
	
	@Column(name="NAME_EN",nullable=false,length=100,unique=true)
	private String nameEn;//代码

	//如果不标注EnumType.STRING则字段将记录0，1，2 等索引顺序
	@Enumerated@Column(length=5,name="TYPE",nullable=false)
	private ResourcesType type=ResourcesType.MENU;
	
	@Column(name="URL")
	private String url="";//链接
	
	@Column(name="TARGET")
	private String target="";//链接目标
	
	@Column(name="FUNCTION")
	private String function="";//JS方法
	
	@Column(name="REMARK")
	private String remark="";//备注
	
	@Column(name="PARENT_ID",nullable=false)
	private long parentId=0;//父ID
	
	@Column(name="ICON_CLS")
	private String iconCls="";//图标样式
	
	@Enumerated@Column(length=5,name="ROLE_TYPE",nullable=false)
	private RoleType roleType=RoleType.ADMIN;//所属角色类型 
	
	@Column(name="SEQ_NUM")
	private int seqNum=0;//
	
	@Column(name="CREATE_TIME")
	private Date createTime=new Date();//创建时间
	
	@Column(name="UPDATE_TIME")
	private Date updateTime=new Date();//更新时间
	
	@Column(name="leaf")
	private Boolean leaf=false;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="resources")
	private Set<RescRole> rescRoles=new HashSet<RescRole>();

	@Transient
	private String roleIds="";

	@Transient
	private String deleteRoleIds="";
	
	@Transient
	private int status;
	@Transient
	private String message;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNameZh() {
		return nameZh;
	}
	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}
	
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public ResourcesType getType() {
		return type;
	}
	public void setType(ResourcesType type) {
		this.type = type;
	}
	
	public Set<RescRole> getRescRoles() {
		return rescRoles;
	}
	public void setRescRoles(Set<RescRole> rescRoles) {
		this.rescRoles = rescRoles;
	}
	
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

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
	
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resources other = (Resources) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getRoleIds() {
		if(roleIds!=null&&!"".equals(roleIds)){
			return roleIds;
		}
		int i=0;
		StringBuffer sb=new StringBuffer();
		for(RescRole ur:rescRoles){
			if(i!=0){
				sb.append(",");
			}
			sb.append(ur.getRole().getId());
			i=i+1;
		}
		return sb.toString();
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getDeleteRoleIds() {
		return deleteRoleIds;
	}
	public void setDeleteRoleIds(String deleteRoleIds) {
		this.deleteRoleIds = deleteRoleIds;
	}
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	
	
	
	
	
	

}
