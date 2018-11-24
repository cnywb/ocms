package com.ternnetwork.baseframework.model.base;


import com.ternnetwork.baseframework.model.listener.VersionEntityListener;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
@EntityListeners(VersionEntityListener.class)
public abstract class VersionEntity extends  AuditEntity {
    /**
     *  操作Version (乐观锁)
    */
    @Version
    @Column(name="OPT_COUNTER")
    private Long optCounter;
    @XStreamOmitField
    /** 是否删除 */
    @Column(name="MARK_FOR_DELETE")
    private Boolean deleted =Boolean.FALSE;
    /** 删除日期 */
    @Column(name="DELETE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteTime;

   

    public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getOptCounter() {
        return optCounter;
    }

    public void setOptCounter(Long optCounter) {
        this.optCounter = optCounter;
    }
  
}
