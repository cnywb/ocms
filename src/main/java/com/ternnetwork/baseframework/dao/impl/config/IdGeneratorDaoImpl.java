package com.ternnetwork.baseframework.dao.impl.config;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.config.IdGeneratorDao;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.model.config.IdGenerator;




@Repository("idGeneratorDao")
public class IdGeneratorDaoImpl extends IBaseDaoImpl<IdGenerator> implements IdGeneratorDao {

}
