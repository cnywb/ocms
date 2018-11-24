package com.ternnetwork.toolkit.service.report;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.toolkit.enums.ReportSendFrequency;

public interface ReportSendJobService {
	public void sendAllReport(ReportSendFrequency sendFrequency);
	public BaseResponse sendReport(Long id,String startDate,String endDatte);
}
