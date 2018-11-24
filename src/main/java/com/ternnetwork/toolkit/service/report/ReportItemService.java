package com.ternnetwork.toolkit.service.report;


import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.report.ReportItem;

public interface ReportItemService {
	
	public BaseResponse idoAdd(ReportItem t);
	public BaseResponse idoUpdate(ReportItem t);
	public BaseResponse idoDelete(long id);
	public Page<ReportItem> query(Page page,String name,String reportId);
    public BootstrapGrid queryToBootstrapGrid(Page page,String name,String reportId);

}
