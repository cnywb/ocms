package com.ternnetwork.toolkit.dao.impl.report;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.report.ReportDao;
import com.ternnetwork.toolkit.model.report.Report;


@Repository("reportDao")
public class ReportDaoImpl extends IBaseDaoImpl<Report> implements ReportDao {

}
