package com.mingyang.sorm.bean;

/**
 * 
 * <h1>ColumnBean</h1>
 * <h1>封装了表中一个字段的信息</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class ColumnInfo {
	
	/**
	 * 字段名称
	 */
	private String name;
	
	/**
	 * 字段的数据类型
	 */
	private String dataType;
	
	/**
	 * 字段的键类型
	 * 0 - 无 ， 1 - 主键 ， 2 - 外键
	 */
	private int keyType;

	/**
	 * 无参构造器
	 */
	public ColumnInfo() {
	}

	/**
	 * 有参构造器
	 * @param name 字段名称
	 * @param dataType 字段的数据类型
	 * @param keyType 字段的键类型（0 - 无 ， 1 - 主键 ， 2 - 外键）
	 */
	public ColumnInfo(String name, String dataType, int keyType) {
		this.name = name;
		this.dataType = dataType;
		this.keyType = keyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}
	
	/**
	 * 重写打印方法
	 */
	@Override
	public String toString() {
		return "{name=" + name + ";dataType=" + dataType + ";keyType=" + keyType + "}";
	}
	
}
