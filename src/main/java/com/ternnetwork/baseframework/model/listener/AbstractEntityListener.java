package com.ternnetwork.baseframework.model.listener;



import javax.persistence.PrePersist;

import com.ternnetwork.baseframework.model.base.AuditEntity;

import java.util.Date;


public abstract class AbstractEntityListener {

    @PrePersist
    public void prePersist(AuditEntity entity) {
        Date createdDate = new Date();
        entity.setCreatedById(1L);
        entity.setCreateTime(createdDate);
        entity.setUpdateTime(createdDate);
        entity.setUpdatedById(1L);
    }
}
