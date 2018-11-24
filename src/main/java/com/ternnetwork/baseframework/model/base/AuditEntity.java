package com.ternnetwork.baseframework.model.base;



import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.model.listener.AuditEntityListener;
import com.ternnetwork.baseframework.util.JsonDateSerializer;

import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class AuditEntity extends BaseEntity{

   
    @Column(name="CREATED_BY_ID")
    private Long createdById;
  
    
    @Column(name="CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /***
     * @Fields updatedById: is update UserId
     */
    @Column(name="UPDATED_BY_ID")
    private Long updatedById;
    /***
     * @Fields updatedById: is update Date
     */
    @Column(name="UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;


    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
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
	
    
    
}
