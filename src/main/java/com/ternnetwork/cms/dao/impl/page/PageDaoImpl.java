package com.ternnetwork.cms.dao.impl.page;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.cms.dao.page.PageDao;
import com.ternnetwork.cms.model.page.CmsPage;


@Repository("pageDao")
public class PageDaoImpl extends IBaseDaoImpl<CmsPage> implements PageDao {

}
