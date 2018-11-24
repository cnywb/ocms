package com.ternnetwork.cms.dao.impl.content;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.cms.dao.content.ContentDao;
import com.ternnetwork.cms.model.content.Content;

@Repository("contentDao")
public class ContentDaoImpl extends IBaseDaoImpl<Content> implements ContentDao {

}
