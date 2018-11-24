package com.ternnetwork.baseframework.model.security;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ternnetwork.baseframework.enums.Gender;
import com.ternnetwork.baseframework.enums.Sexuality;
import com.ternnetwork.baseframework.model.org.Department;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;





@Cache(usage =CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="VS_USER")
@JsonAutoDetect
@JsonIgnoreProperties(value={"password","payPassword","payPassword","userRoles"})
public class User implements Serializable {
	

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5178830032814719224L;

	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="USER_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@Column(name="PARENT_ID")
	private long parentId=0;//父级ID

	@Column(name="NAME",unique=true,nullable=false)
	private String name;
	
	@Column(name="WECHAT_ID",unique=true)
	private String wechatId;
	
	@Column(name="PASSWORD")
	private String password;
	
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Column(name="ENABLE")
	private Boolean enable;//账号启用
	
	@Column(name="ACCOUNT_NON_EXPIRED")
	private Boolean accountNonExpired;//账号未过期
	
	@Column(name="CREDENTIALS_NON_EXPIRED")
	private Boolean credentialsNonExpired;//密码未过期
	
	@Column(name="ACCOUNT_NON_LOCKED")
	private Boolean accountNonLocked;//账号未锁定
	
	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name="ACCOUNT_EXPIRED_TIME")
	private Date accountExpiredTime;
	
	@Column(name="CREDENTIALS_EXPIRED_TIME")
	private Date credentialsExpiredTime;
	
	@Column(name="ACCOUNT_LOCKED_TIME")
	private Date accountLockedTime;
	
	@Enumerated@Column(name="GENDER")
	private Gender gender;
	
	@Column(name="EMAIL")
	private String email="";
	
	@Column(name="PHONE")
	private String phone="";
	
	@Column(name="MOBILE_PHONE")
	private String mobilePhone="";
	
	@Column(name="ADDRESS")
	private String address="";
	
	@Column(name="REAL_NAME")
	private String realName="";
	
	@Column(name="COMPANY")
	private String company="";
	
	@Column(name="BALANCE")
    private BigDecimal balance=new BigDecimal("0.00");//当前余额
	@Column(name="PAY_PASSWORD")
    private String payPassword;//支付密码
	@Column(name="DEALED_SUCCESS_RATE")
    private Double dealedSuccessRate=0.00D;
	@Column(name="FAVORABLE_RATE")
    private Double favorableRate=0.00D;
	@Column(name="IS_INFO_CONFIRM")
    private Boolean isInfoConfirm;
	
	@Enumerated@Column(name="SEXUALITY")
    private Sexuality sexuality=Sexuality.heterosex;
	
	@Column(name="BIRTHDAY")
	private Date birthday;
	@Column(name="PHOTO")
	private String photo;
	
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",nullable=true)
	private Department department; 
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER,mappedBy="user")
	private Set<UserRole> userRoles=new HashSet<UserRole>();
	
	@Column(name="NICKNAME")
	private String nickname;
	@Column(name="PROVINCE")
	private String province;
	@Column(name="CITY")
	private String city;
	@Column(name="COUNTRY")
	private String country;
	
	
	
	
	@Transient
	private String timeMax;
	@Transient
	private String timeMin;
	@Transient
	private String roleIds="";
	
	@Transient
	private String deleteRoleIds="";
	@Transient
	private String roles="";
	
	@Transient
	private String genderName="";
	
	@Transient
	private String enableName="";
	
	@Transient
    private String accountLockedName="";
	
	@Transient
	private String departmentName="";
	
	@Transient
	private String departmentId="";
	
	@Transient
	private Boolean isZoneModerator=false;//是否区管理员
	@Transient
	private Boolean isBoardModerator=false;//是否版块管理员
	
	public Boolean getIsZoneModerator() {
		for(UserRole ur:userRoles){
			if(ur.getRole().getNameEn().equals("BBS_ZONE_MODERATOR")){
				return true;
			}
		}
		return isZoneModerator;
	}

	public void setIsZoneModerator(Boolean isZoneModerator) {
		this.isZoneModerator = isZoneModerator;
	}

	public Boolean getIsBoardModerator() {
		for(UserRole ur:userRoles){
			if(ur.getRole().getNameEn().equals("BBS_BOARD_MODERATOR")){
				return true;
			}
		}
		return isBoardModerator;
	}

	public void setIsBoardModerator(Boolean isBoardModerator) {
		this.isBoardModerator = isBoardModerator;
	}

	public String getDepartmentId() {
		if(department!=null){
			return String.valueOf(department.getId());
		}
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		if(department!=null){
			return department.getNameZh();
		}
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAccountLockedName() {
		return accountNonLocked==true?"否":"是";
	}

	public void setAccountLockedName(String accountLockedName) {
		this.accountLockedName = accountLockedName;
	}

	public String getGenderName() {
		return gender.getName();
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getEnableName() {
		return (enable==true)?"启用":"停用";
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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Date getAccountExpiredTime() {
		return accountExpiredTime;
	}

	public void setAccountExpiredTime(Date accountExpiredTime) {
		this.accountExpiredTime = accountExpiredTime;
	}

	public Date getCredentialsExpiredTime() {
		return credentialsExpiredTime;
	}

	public void setCredentialsExpiredTime(Date credentialsExpiredTime) {
		this.credentialsExpiredTime = credentialsExpiredTime;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getAccountLockedTime() {
		return accountLockedTime;
	}

	public void setAccountLockedTime(Date accountLockedTime) {
		this.accountLockedTime = accountLockedTime;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
	
		StringBuffer roles=new StringBuffer("");
		for(UserRole ur :userRoles){
			roles.append(" ");
			roles.append(ur.getRole().getNameZh());
		}
		return JSONUtils.objectToJson(this);
	}

	public String getTimeMax() {
		return timeMax;
	}

	public void setTimeMax(String timeMax) {
		this.timeMax = timeMax;
	}

	public String getTimeMin() {
		return timeMin;
	}

	public void setTimeMin(String timeMin) {
		this.timeMin = timeMin;
	}

	public String getRoleIds() {
		if(roleIds!=null&&!"".equals(roleIds)){
			return roleIds;
		}
		
		int i=0;
		StringBuffer sb=new StringBuffer();
		for(UserRole ur:userRoles){
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Double getDealedSuccessRate() {
		return dealedSuccessRate;
	}

	public void setDealedSuccessRate(Double dealedSuccessRate) {
		this.dealedSuccessRate = dealedSuccessRate;
	}

	public Double getFavorableRate() {
		return favorableRate;
	}

	public void setFavorableRate(Double favorableRate) {
		this.favorableRate = favorableRate;
	}

	public Boolean getIsInfoConfirm() {
		return isInfoConfirm;
	}

	public void setIsInfoConfirm(Boolean isInfoConfirm) {
		this.isInfoConfirm = isInfoConfirm;
	}

	/**
	 * @return the roles
	 */
	public String getRoles() {
		if(roles==null){
			roles="";
		}
		for(UserRole ur:userRoles){
			roles=roles+" "+ur.getRole().getNameZh();
		}
		
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Sexuality getSexuality() {
		return sexuality;
	}

	public void setSexuality(Sexuality sexuality) {
		this.sexuality = sexuality;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDeleteRoleIds() {
		return deleteRoleIds;
	}

	public void setDeleteRoleIds(String deleteRoleIds) {
		this.deleteRoleIds = deleteRoleIds;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}



	
	
	
	
	
	

}
