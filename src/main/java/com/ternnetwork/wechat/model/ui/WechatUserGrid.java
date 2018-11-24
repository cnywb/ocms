package com.ternnetwork.wechat.model.ui;

import java.util.ArrayList;
import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.util.JSONUtils;

public class WechatUserGrid {
     
	private int current=1;
	
	private int rowCount=10000;
	
	private List rows = new ArrayList();
	
	private int total=0;

	private Page page;
	
	private String nextOpenid;
	
	public String getNextOpenid() {
		return nextOpenid;
	}

	public void setNextOpenid(String nextOpenid) {
		this.nextOpenid = nextOpenid;
	}

	public int getCurrent() {
		return page.getPageNo()+1;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public List getRows() {
		return page.getResult();
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return page.getTotalCount();
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

	public WechatUserGrid(Page page) {
		super();
		this.page = page;
	}
}
