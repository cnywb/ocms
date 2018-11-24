package com.ternnetwork.wechat.dao.impl.share;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.wechat.dao.share.ShareLogDao;
import com.ternnetwork.wechat.model.share.ShareLog;


@Repository("shareLogDao")
public class ShareLogDaoImpl extends IBaseDaoImpl<ShareLog> implements ShareLogDao {

}
