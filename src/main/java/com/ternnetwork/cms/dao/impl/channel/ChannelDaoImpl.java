package com.ternnetwork.cms.dao.impl.channel;



import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.cms.dao.channel.ChannelDao;
import com.ternnetwork.cms.model.channel.Channel;

@Repository("channelDao")
public class ChannelDaoImpl extends IBaseDaoImpl<Channel> implements ChannelDao {

}
