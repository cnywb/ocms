package com.ternnetwork.toolkit.model.luckydraw;

import com.ternnetwork.baseframework.util.JSONUtils;





public class LuckyDrawResponse {
	
	private int status=0;
	private String message="";//提示
	
	private String code="";//奖品代码
	
	private String award="";//奖品名称
	private String resultId="";//中奖序号
	
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	
	

}
