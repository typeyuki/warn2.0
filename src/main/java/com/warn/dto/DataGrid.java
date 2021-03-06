package com.warn.dto;

import java.util.List;

/**
 * 后台向前台返回JSON，用于easyui的datagrid  其格式固定 需要两个属性 total和rows
 * 
 * @author zh
 * 
 */
public class DataGrid implements java.io.Serializable {

	private Long total;	// 总记录数
	private List rows;	// 每行记录
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
