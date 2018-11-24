package com.ternnetwork.baseframework.model.listener;




import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.ternnetwork.baseframework.model.base.AuditEntity;

import java.util.Date;


public class AuditEntityListener extends  AbstractEntityListener {


    @PrePersist
    public void prePersist(AuditEntity entity) {
        Date createdDate = new Date();
        entity.setCreatedById(1L);
        entity.setCreateTime(createdDate);
        entity.setUpdateTime(createdDate);
        entity.setUpdatedById(1L);
    }

    @PreUpdate
    public void preUpdate(AuditEntity entity) {
        Date updatedDate = new Date();
        entity.setUpdateTime(updatedDate);
        //TODO
        entity.setCreatedById(1L);
    }

}