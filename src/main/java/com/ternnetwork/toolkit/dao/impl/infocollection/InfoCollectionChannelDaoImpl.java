package com.ternnetwork.toolkit.dao.impl.infocollection;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionChannelDao;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionChannel;

@Repository("infoCollectionChannelDao")
public class InfoCollectionChannelDaoImpl extends IBaseDaoImpl<InfoCollectionChannel>
		implements InfoCollectionChannelDao {

}
