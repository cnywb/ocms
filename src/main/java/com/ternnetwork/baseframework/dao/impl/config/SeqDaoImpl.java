package com.ternnetwork.baseframework.dao.impl.config;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.config.SeqDao;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.baseframework.model.config.Seq;


@Repository("seqDao")
public class SeqDaoImpl extends IBaseDaoImpl<Seq> implements SeqDao {

}
