package com.mingyang.sorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <h1>JDBCUtils</h1>
 * <h1>封装了JDBC常用的操作</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class JDBCUtils {
	
	/**
	 * 日志对象
	 */
	public static final Logger log = LoggerFactory.getLogger(JDBCUtils.class);
	
	/**
	 * 设置参数
	 * @param pstmt 预编译SQL语句对象
	 * @param params 参数数据
	 * @throws SQLException 异常抛出给调用者进行处理
	 */
	public static void handleParams(PreparedStatement pstmt, Object[] params) throws SQLException {
		if(null != params) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
		}
	}

}
