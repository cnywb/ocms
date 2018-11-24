package com.ternnetwork.toolkit.dao.impl.vote;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.vote.VoteItemDao;
import com.ternnetwork.toolkit.model.vote.VoteItem;



@Repository("voteItemDao")
public class VoteItemDaoImpl extends IBaseDaoImpl<VoteItem> implements
		VoteItemDao {

}
