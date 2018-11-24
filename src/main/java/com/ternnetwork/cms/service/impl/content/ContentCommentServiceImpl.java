package com.ternnetwork.cms.service.impl.content;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ternnetwork.cms.dao.content.ContentCommentDao;
import com.ternnetwork.cms.model.content.ContentComment;
import com.ternnetwork.cms.service.content.ContentCommentService;


@Service("contentCommentService")
public class ContentCommentServiceImpl implements ContentCommentService {
	
	@Resource
	private ContentCommentDao contentCommentDao;
	
	
	public long idoAdd(ContentComment t){
		contentCommentDao.persist(t);
		return t.getId();
	}

}
