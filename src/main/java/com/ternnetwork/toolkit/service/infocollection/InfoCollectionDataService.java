package com.ternnetwork.toolkit.service.infocollection;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionData;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionDataRequest;

public interface InfoCollectionDataService {
	
	public BaseResponse idoAdd(InfoCollectionDataRequest infoCollectionDataRequest);
    public BootstrapGrid queryToBootstrapGrid(String campaignId,String channelId,String startTime,String endTime,String buyerMobilePhoneNo,String potentialBuyerMobilePhoneNo,String subject,Page page);
	public BaseResponse idoDelete(Long id);
	public HSSFWorkbook exportExcel(String campaignId,String channelId,String startTime,String endTime,String buyerMobilePhoneNo,String potentialBuyerMobilePhoneNo,String subject);
    public List<InfoCollectionData> queryAll(String campaignId,String startTime,String endTime);
    public HSSFWorkbook createExcel(List<InfoCollectionData> list);
    public BaseResponse upload(MultipartFile[] file, String currentDir, String dir,HttpServletRequest request);
    public BaseResponse idoAddForBp(InfoCollectionData t,HttpServletRequest request);
}
