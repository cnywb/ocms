package com.ternnetwork.baseframework.model.listener;


import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


import com.ternnetwork.baseframework.model.base.AuditEntity;
import com.ternnetwork.baseframework.model.base.VersionEntity;

import java.util.Date;


public class VersionEntityListener  extends  AbstractEntityListener{


    @PrePersist
    public void prePersist(AuditEntity entity) {
        Date createdDate = new Date();
        entity.setCreatedById(1L);
        entity.setCreateTime(createdDate);
        entity.setUpdateTime(createdDate);
        entity.setUpdatedById(1L);
    }

    @PreUpdate
    public void preUpdate(VersionEntity entity) {
        Date updatedDate = new Date();
        entity.setUpdateTime(updatedDate);
        //TODO
        entity.setCreatedById(1L);
        if(entity.getDeleted()){
            entity.setDeleteTime(updatedDate);
        }
    }
}
