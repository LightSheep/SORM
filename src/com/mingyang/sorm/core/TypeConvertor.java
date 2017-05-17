package com.mingyang.sorm.core;

/**
 * 
 * <h1>TypeConvertor</h1>
 * <h1>负责数据库数据类型和Java数据类型的相互转换</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public interface TypeConvertor {
	
	/**
	 * 将数据库数据类型转化成Java的数据类型
	 * @param columnType 数据库字段的数据类型
	 * @return Java的数据类型
	 */
	public String dbType2JavaType(String columnType);
	
	/**
	 * 将Java的数据类型转化成数据库数据类型
	 * @param javaDataType Java的数据类型
	 * @return 数据库字段的数据类型
	 */
	public String JavaType2dbType(String javaDataType);
}
