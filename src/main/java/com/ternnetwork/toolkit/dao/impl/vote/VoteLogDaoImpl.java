package com.ternnetwork.toolkit.dao.impl.vote;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.vote.VoteLogDao;
import com.ternnetwork.toolkit.model.vote.VoteLog;




@Repository("voteLogDao")
public class VoteLogDaoImpl extends IBaseDaoImpl<VoteLog> implements VoteLogDao {

}
