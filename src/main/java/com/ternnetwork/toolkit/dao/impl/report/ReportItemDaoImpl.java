package com.ternnetwork.toolkit.dao.impl.report;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.report.ReportItemDao;
import com.ternnetwork.toolkit.model.report.ReportItem;


@Repository("reportItemDao")
public class ReportItemDaoImpl extends IBaseDaoImpl<ReportItem> implements ReportItemDao {

}
