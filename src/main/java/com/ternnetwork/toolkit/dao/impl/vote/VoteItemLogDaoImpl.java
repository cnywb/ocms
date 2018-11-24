package com.ternnetwork.toolkit.dao.impl.vote;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.vote.VoteItemLogDao;
import com.ternnetwork.toolkit.model.vote.VoteItemLog;

@Repository("voteItemLogDao")
public class VoteItemLogDaoImpl extends IBaseDaoImpl<VoteItemLog> implements VoteItemLogDao {

}
