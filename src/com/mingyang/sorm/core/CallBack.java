package com.mingyang.sorm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 * <h1>CallBack</h1>
 * <h1>回调方法</h1>
 * <p>@date 2016年11月25日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public interface CallBack {
	
	//public Object execute(Connection conn, PreparedStatement pstmt);
	
	/**
	 * 回调函数
	 * @param conn 连接对象
	 * @param pstmt 预编译执行对象
	 * @param rs 结果集
	 * @return Object 执行返回的结果
	 * @throws Exception
	 */
	public Object execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws Exception;

}
