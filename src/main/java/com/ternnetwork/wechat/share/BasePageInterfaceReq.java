package com.ternnetwork.wechat.share;

import java.io.Serializable;

/**
 * Created by huangwen on 17/26/6.
 */
public abstract class BasePageInterfaceReq  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1724379310771628812L;
	public String campaignCode;
	public String channelCode;
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
	
	
}
