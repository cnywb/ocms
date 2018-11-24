package com.ternnetwork.baseframework.model.ui;

import com.ternnetwork.baseframework.util.JSONUtils;

public class DepartmentZtree extends Ztree {
	
	private String nameEn;
	
	private int seqNum;

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	
	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}


}
