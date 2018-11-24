package com.ternnetwork.cms.dao.impl.site;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.cms.dao.site.SiteDao;
import com.ternnetwork.cms.model.site.Site;


@Repository("siteDao")
public class SiteDaoImpl extends IBaseDaoImpl<Site> implements SiteDao {

}
