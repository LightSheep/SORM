package com.mingyang.sorm.bean;

import java.util.List;
import java.util.Map;

/**
 * 
 * <h1>TableInfo</h1>
 * <h1>存储表结构信息</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class TableInfo {
	
	/**
	 * 表名
	 */
	private String name;
	
	/**
	 * 字段信息
	 */
	private Map<String, ColumnInfo> columns;
	
	/**
	 * 唯一主键
	 */
	private ColumnInfo onlyPriKey;
	
	/**
	 * 联合主键
	 */
	private List<ColumnInfo> priKeys;

	/**
	 * 无参构造器
	 */
	public TableInfo() {
	}

	/**
	 * 有参构造器
	 * @param name 表名
	 * @param columns 字段信息
	 * @param onlyPriKey 唯一主键
	 * @param priKeys 联合主键
	 */
	public TableInfo(String name, Map<String, ColumnInfo> columns,
			ColumnInfo onlyPriKey, List<ColumnInfo> priKeys) {
		this.name = name;
		this.columns = columns;
		this.onlyPriKey = onlyPriKey;
		this.priKeys = priKeys;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ColumnInfo> columns) {
		this.columns = columns;
	}

	public ColumnInfo getOnlyPriKey() {
		return onlyPriKey;
	}

	public void setOnlyPriKey(ColumnInfo onlyPriKey) {
		this.onlyPriKey = onlyPriKey;
	}

	public List<ColumnInfo> getPriKeys() {
		return priKeys;
	}

	public void setPriKeys(List<ColumnInfo> priKeys) {
		this.priKeys = priKeys;
	}
	
}
