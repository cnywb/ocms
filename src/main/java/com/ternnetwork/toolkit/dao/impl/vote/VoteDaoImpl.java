package com.ternnetwork.toolkit.dao.impl.vote;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.vote.VoteDao;
import com.ternnetwork.toolkit.model.vote.Vote;



@Repository("voteDao")
public class VoteDaoImpl extends IBaseDaoImpl<Vote> implements VoteDao {

}
