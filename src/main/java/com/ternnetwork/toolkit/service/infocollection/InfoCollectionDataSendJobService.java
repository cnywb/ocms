package com.ternnetwork.toolkit.service.infocollection;

import com.ternnetwork.toolkit.enums.CampaignDataSendFrequency;

public interface InfoCollectionDataSendJobService {
	public void sendAllCampaignDataByEmail(CampaignDataSendFrequency dataSendFrequency);

}
