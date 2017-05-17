package com.mingyang.sorm.core;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingyang.sorm.common.configuration.DBConfig;
import com.mingyang.sorm.pool.DBConnPool;


/**
 * 
 * <h1>DBManager</h1>
 * <h1>根据配置信息，维持连接对象的管理</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class DBManager {
	
	/**
	 * 日志变量
	 */
	private static final Logger log = LoggerFactory.getLogger(DBManager.class);
	
	/**
	 * 数据库连接池对象
	 */
	private static DBConnPool pool;
	
	static {
		try {
			Class.forName("com.mingyang.sorm.core.TableContext");  //加载TableContext
		} catch (ClassNotFoundException e) {
			log.error("加载TableContext类失败！", e);
		}
	}
	
	/**
	 * 获取数据库连接 
	 * @return java.sql.Connection
	 */
	public static Connection getConn() {
		if(pool == null) {
			pool = new DBConnPool();
		}
		return pool.getConn();
	}
	
	/**
	 * 创建一个新的数据库连接 
	 * @return java.sql.Connection
	 */
	public static Connection createConn() {
		DBConfig conf = new DBConfig();
		try {
			Class.forName(conf.getVal("db.conn.driver"));
			return DriverManager.getConnection(conf.getVal("db.conn.url"), conf.getVal("db.conn.user"), conf.getVal("db.conn.pwd"));
		} catch (Exception e) {
			log.error("获取数据库连接失败", e);
			return null;
		}
	}
	
	/**
	 * 通用关闭方法(Closeable)
	 * @param c Closeable...(不定参数)
	 */
	public static void closeAll(Closeable... c) {
		
		for (Closeable temp : c) {
			try {
				if (null != temp) {
					temp.close();
				}
			} catch (IOException e) {
				log.error("关闭资源失败{}", temp.getClass().toString(), e);
			}
		}
	}
	
	/**
	 * 通用关闭方法(AutoCloseable)
	 * @param ac AutoCloseable...(不定参数)
	 */
	public static void closeAll(AutoCloseable... ac) {
		
		for (AutoCloseable temp : ac) {
			try {
				if (null != temp) {
					if(temp instanceof Connection) {
						pool.close((Connection) temp);  //如果是connection则交给连接池类关闭
					}
					temp.close();
				}
			} catch (Exception e) {
				log.error("关闭资源失败{}", temp.getClass().toString(), e);
			}
		}
	}
	
	/**
	 * Just for test
	 * @param args
	 */
	public static void main(String[] args) {
		DBManager.getConn();
	}
	
}
