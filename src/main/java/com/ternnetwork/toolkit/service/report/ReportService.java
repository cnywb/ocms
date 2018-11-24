package com.ternnetwork.toolkit.service.report;


import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.enums.ReportSendFrequency;
import com.ternnetwork.toolkit.model.report.Report;

public interface ReportService {
	public Report findById(Long id);
	public BaseResponse idoAdd(Report t);
	public BaseResponse idoUpdate(Report t);
	public BaseResponse idoDelete(long id);
	public Page<Report> query(Page page,String name);
	public BootstrapGrid queryToBootstrapGrid(Page page,String name);
	public List<Report>findAll(ReportSendFrequency sendFrequency);
}
