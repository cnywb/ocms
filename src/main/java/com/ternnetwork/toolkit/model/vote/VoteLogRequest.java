package com.ternnetwork.toolkit.model.vote;

import java.util.List;

public class VoteLogRequest {
	
  private long voteId;
  
  private String realName;
  
  private String mobilePhoneNo;
  
  private List<VoteItemLog> items;
  
  private String wechatId;

public long getVoteId() {
	return voteId;
}

public void setVoteId(long voteId) {
	this.voteId = voteId;
}

public List<VoteItemLog> getItems() {
	return items;
}

public void setItems(List<VoteItemLog> items) {
	this.items = items;
}

public String getWechatId() {
	return wechatId;
}

public void setWechatId(String wechatId) {
	this.wechatId = wechatId;
}

public String getRealName() {
	return realName;
}

public void setRealName(String realName) {
	this.realName = realName;
}

public String getMobilePhoneNo() {
	return mobilePhoneNo;
}

public void setMobilePhoneNo(String mobilePhoneNo) {
	this.mobilePhoneNo = mobilePhoneNo;
}


  
  
  

}
