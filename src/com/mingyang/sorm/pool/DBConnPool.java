package com.mingyang.sorm.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingyang.sorm.common.configuration.DBConfig;
import com.mingyang.sorm.core.DBManager;

/**
 * 
 * <h1>DBConnPool</h1>
 * <h1>连接池类</h1>
 * <p>@date 2016年11月28日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class DBConnPool {
	
	/**
	 * 日志变量
	 */
	private static final Logger log = LoggerFactory.getLogger(DBConnPool.class);
	
	/**
	 * 连接池对象
	 */
	private List<Connection> pool;
	
	/**
	 * 连接池现容量
	 */
	private int size = 0;
	
	private static DBConfig conf = new DBConfig();
	
	/**
	 * 连接池最小连接数
	 */
	private static final int POOL_MIN_SIZE = conf.getIntVal("db.pool.POOL_MIN_SIZE");
	
	/**
	 * 连接池最大连接数
	 */
	private static final int POOL_MAX_SIZE = conf.getIntVal("db.pool.POOL_MAX_SIZE");
	
	/**
	 * 构造器
	 */
	public DBConnPool() {
		initPoll();  //初始化连接池
	}
	
	/**
	 * 初始化连接池
	 */
	private void initPoll() {
		if(null == pool) {
			pool = new ArrayList<Connection>();
		}
		
		while(pool.size() < POOL_MIN_SIZE) {
			pool.add(DBManager.createConn());
			size++;
		}
	}
	
	/**
	 * 返回连接池现容量
	 * @return 连接池现容量
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * 从连接池中取一个连接
	 * @return 数据库连接
	 */
	public synchronized Connection getConn() {
		int last_index = size - 1;
		Connection conn = pool.get(last_index);
		pool.remove(last_index); //从连接池中移除取出的连接
		size--;
		return conn;
	}
	
	/**
	 * 关闭连接
	 * 实际是将连接放回池中
	 */
	public synchronized void close(Connection conn) {
		if(size >= POOL_MAX_SIZE) {  //如果连接池容量超出了指定的 POOL_MAX_SIZE 则进行真的关闭
			try {
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.error("", e);
			}
		} else {
			pool.add(conn);
			size--;
		}
	}
	
	public static void main(String[] args) {
		DBConnPool pool = new DBConnPool();
		System.out.println(pool.getConn());
	}

}
