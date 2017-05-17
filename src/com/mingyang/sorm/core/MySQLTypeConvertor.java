package com.mingyang.sorm.core;

/**
 * 
 * <h1>MySQLTypeConvertor</h1>
 * <h1>MySQL数据类型和Java数据类型的转换</h1>
 * <p>@date 2016年11月22日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class MySQLTypeConvertor implements TypeConvertor{
	
	@Override
	public String dbType2JavaType(String columnType) {
		switch (columnType.toLowerCase()) {
			case "varchar":
			case "char":
			case "text":
				return "String";
			case "int":
			case "tinyint":
			case "smallint":
			case "integer":
				return "Integer";
			case "bigint":
				return "Long";
			case "double":
				return "Double";
			case "float":
				return "Float";
			case "clob":
				return "java.sql.Clob";
			case "blob":
				return "java.sql.Blob";
			case "date":
				return "java.sql.Date";
			case "time":
				return "java.sql.Time";
			case "timstamp":
				return "java.sql.Timstamp";
			default:
				return null;
		}
	}

	@Override
	public String JavaType2dbType(String javaDataType) {
		return null;
	}
	
	public static void main(String[] args) {
	}
}
