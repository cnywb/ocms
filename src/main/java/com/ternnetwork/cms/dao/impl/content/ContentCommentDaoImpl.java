package com.ternnetwork.cms.dao.impl.content;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.cms.dao.content.ContentCommentDao;
import com.ternnetwork.cms.model.content.ContentComment;

@Repository("contentCommentDao")
public class ContentCommentDaoImpl extends IBaseDaoImpl<ContentComment>
		implements ContentCommentDao {

}
