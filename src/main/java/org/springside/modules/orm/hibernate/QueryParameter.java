/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package org.springside.modules.orm.hibernate;

import org.apache.commons.lang.StringUtils;

public class QueryParameter {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	protected int pageNo;
	protected int pageSize;
	protected String orderBy;
	protected String order;
	protected boolean autoCount;

	public QueryParameter() {
		this.pageNo = 1;
		this.pageSize = -1;
		this.orderBy = null;
		this.order = "asc";
		this.autoCount = false;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isPageSizeSetted() {
		return (this.pageSize > -1);
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getFirst() {
		if ((this.pageNo < 1) || (this.pageSize < 1)) {
			return -1;
		}
		return ((this.pageNo - 1) * this.pageSize);
	}

	public boolean isFirstSetted() {
		return ((this.pageNo > 0) && (this.pageSize > 0));
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(this.orderBy);
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		if (("asc".equalsIgnoreCase(order)) || ("desc".equalsIgnoreCase(order)))
			this.order = order.toLowerCase();
		else
			throw new IllegalArgumentException("order should be 'desc' or 'asc'");
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
}