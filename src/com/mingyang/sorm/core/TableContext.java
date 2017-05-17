package com.mingyang.sorm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingyang.sorm.bean.ColumnInfo;
import com.mingyang.sorm.bean.TableInfo;
import com.mingyang.sorm.common.configuration.FrameConfig;
import com.mingyang.sorm.utils.JavaFileUtils;

/**
 * 
 * <h1>TableContext</h1>
 * <h1>管理数据库所有表结构和类结构的关系，根据表结构可以生成类结构</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class TableContext {
	
	/**
	 * 日志变量
	 */
	private static final Logger log = LoggerFactory.getLogger(TableContext.class);
	
	/**
	 * 表信息
	 * key为表名，value为表信息bean
	 */
	private static Map<String, TableInfo> tables = new HashMap<String, TableInfo>();
	
	/**
	 * PO类与表信息的映射关系
	 * key为po的class对象，value为表信息bean
	 */
	private static Map<Class<?>, TableInfo> poTableMap = new HashMap<Class<?>, TableInfo>();

	/**
	 * 无参构造器
	 */
	public TableContext() {
	}
	
	static {
		
		try {
			Connection conn = DBManager.getConn();
			// DatabaseMetaData 封装了数据库的源信息
			DatabaseMetaData dbmd = conn.getMetaData();
			
			ResultSet tableRs = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			
			while(tableRs.next()) {
				
				String tableName = (String) tableRs.getObject("TABLE_NAME");
				
				TableInfo ti = new TableInfo(tableName,
						new HashMap<String, ColumnInfo>(), new ColumnInfo(),
						new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				
				//查询表中的所有字段
				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");
				while(set.next()) {
					ColumnInfo ci = new ColumnInfo(
							set.getString("COLUMN_NAME"),
							set.getString("TYPE_NAME"), 0);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
				
				//查询表中的主键
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);
				while(set2.next()) {
					ColumnInfo ci2 = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);
					ti.getPriKeys().add(ci2);
				}
				
				//取唯一主键，
				if(ti.getPriKeys().size()>0) {
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
				
			}
		} catch (SQLException e) {
			log.error("加载表信息失败", e);
		}
		
		//更新po类结构
		genericJavaPOFile();
		
		//加载po包下的类与表信息的关联关系 ->poClassTableMap
		loadPOClassTableMap();
	}
	
	/**
	 * 获取表信息Map
	 * @return key为表名，value为表信息bean
	 */
	public static Map<String, TableInfo> getTableInfos() {
		return tables;
	}
	
	/**
	 * 返回PO类与表信息的映射关系
	 * @return PO类与表信息的映射关系Map
	 */
	public static Map<Class<?>, TableInfo> getPOTableMap() {
		return poTableMap;
	}
	
	/**
	 * 根据表结构，生成PO类文件(可以进行覆盖更新)
	 * 实现了从表结构转换为类结构
	 */
	public static void genericJavaPOFile() {
		Map<String, TableInfo> map = TableContext.getTableInfos();
		for (TableInfo ti : map.values()) {
			JavaFileUtils.createJavaPOFile(ti, new MySQLTypeConvertor());
		}
	}
	
	/**
	 * 加载类与表信息的映射关系
	 */
	public static void loadPOClassTableMap() {
		
		FrameConfig conf = new FrameConfig();
		
		for (TableInfo ti : tables.values()) {
			try {
				Class<?> clazz = Class.forName(conf.getVal("frame.poPackage") + "." + StringUtils.capitalize(ti.getName()));
				poTableMap.put(clazz, ti);
			} catch (ClassNotFoundException e) {
				log.error("加载表{}与类的关联关系Map失败", ti.getName(), e);
			}
		}
	}
	
	/**
	 * Just for test
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(TableContext.poTableMap.get(Class.forName("com.mingyang.po.Department")).getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
