package com.ternnetwork.toolkit.model.infocollection;

import java.util.ArrayList;
import java.util.List;

public class InfoCollectionDataRequest {
	
	private String campaignCode;
	
	private String channelCode;
	
	private List<InfoCollectionData> dataList=new ArrayList<InfoCollectionData>();

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public List<InfoCollectionData> getDataList() {
		return dataList;
	}

	public void setDataList(List<InfoCollectionData> dataList) {
		this.dataList = dataList;
	}
	
	
	
	
	
	
	
	
	

}
