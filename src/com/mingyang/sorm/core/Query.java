package com.mingyang.sorm.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingyang.sorm.bean.ColumnInfo;
import com.mingyang.sorm.bean.TableInfo;
import com.mingyang.sorm.exceptions.NotSingleResultExcepion;
import com.mingyang.sorm.exceptions.PriKeyNullExcepion;
import com.mingyang.sorm.utils.JDBCUtils;
import com.mingyang.sorm.utils.ReflectUtils;


/**
 * 
 * <h1>Query</h1>
 * <h1>负责查询（对外提供服务的核心类）</h1>
 * <p>提高一些标准SQL语句的执行，屏蔽个个数据库的方言</p>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public abstract class Query implements Cloneable{
	
	/**
	 * 日志对象
	 */
	public static final Logger log = LoggerFactory.getLogger(Query.class);
	
	/**
	 * 执行一个DML语句（增删改语句）
	 * @param sql SQL语句
	 * @param params 参数数组
	 * @return 执行SQL语句后影响记录的行数
	 */
	public int executeDML(String sql, Object[] params) {
		
		int count = 0;
		
		Connection conn = DBManager.getConn();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			JDBCUtils.handleParams(pstmt, params);
			
			count = pstmt.executeUpdate();
			
			log.info("执行SQL语句->{}", sql);
			
		} catch (SQLException e) {
			log.error("执行SQL语句->{}失败", sql, e);
		} finally {
			DBManager.closeAll(pstmt, conn);
		}
		
		return count;
		
	}
	
	/**
	 * 将一个对象存储到数据库中
	 * 原则：
	 * 1、把对象中不为null的属性进行存储
	 * 2、如果数字为null则存放0
	 * @param obj 待存储对象
	 */
	public int insert(Object obj) {

		Class<?> clazz = obj.getClass();
		TableInfo ti = TableContext.getPOTableMap().get(clazz);
		
		StringBuffer sql = new StringBuffer("insert into " + ti.getName() + " (");
		
		Field[] fields = clazz.getDeclaredFields();
		List<Object> params = new ArrayList<Object>();
		
		int count = 0;//用于计数，非null的属性个数
		for (Field temp : fields) {
			String fieldName = temp.getName();
			Object fieldValue = ReflectUtils.invokeGet(obj, fieldName);
			
			if(null != fieldName) {
				count ++;
				sql.append(fieldName + ",");
				params.add(fieldValue);
			}
		}
		
		sql.setCharAt(sql.length()-1, ')'); //处理最后一个 ','
		sql.append(" value(");
		for (int i = 0; i < count; i++) {
			sql.append("?,");
		}
		sql.setCharAt(sql.length()-1, ')'); //处理最后一个 ','
		
		return executeDML(sql.toString(), params.toArray());
	}
	
	/**
	 * 删除clazz表示类对应表中的主键为id的记录
	 * @param clazz 表映射的类
	 * @param id 主键
	 */
	public int delete(Class<?> clazz, Object id) {
		
		TableInfo ti = TableContext.getPOTableMap().get(clazz);
		ColumnInfo onlyPriKey = ti.getOnlyPriKey();
		
		String sql = "delete from " + ti.getName() + " where " + onlyPriKey.getName() +"=?";
		
		return executeDML(sql, new Object[]{id});
		
	}
	
	/**
	 * 删除对象在数据库中对应的记录
	 * 对象.class属性对应表，对象.id对应要删除记录的主键
	 * @param obj 待删除的记录对应的对象
	 */
	public int delete(Object obj) {

		Class<?> clazz = obj.getClass();
		TableInfo ti = TableContext.getPOTableMap().get(clazz);
		ColumnInfo onlyPriKey = ti.getOnlyPriKey();
		
		Object pkValue = ReflectUtils.invokeGet(obj, onlyPriKey.getName());
		
		//调用重载的delete方法
		return delete(clazz, pkValue);
	
	}
	
	/**
	 * 更新对象对应记录指定字段的值
	 * @param obj 待更新记录对应的对象
	 * @param fileNames 指定的属性列表
	 * @return 执行SQL语句后影响记录的行数
	 * @throws PriKeyNullExcepion 
	 */
	public int update(Object obj, String[] fileNames) throws PriKeyNullExcepion {

		Class<?> clazz = obj.getClass();
		TableInfo ti = TableContext.getPOTableMap().get(clazz);
		
		String pkName = ti.getOnlyPriKey().getName();
		Object pkValue = ReflectUtils.invokeGet(obj, pkName);
		
		if(pkValue == null) {
			throw new PriKeyNullExcepion("对象["+obj +"]的主键值为空！");
		}
		
		StringBuffer sql = new StringBuffer("update " + ti.getName() + " set ");
		List<Object> params = new ArrayList<Object>();
		
		for (String name : fileNames) {
			//1、拼接sql语句
			sql.append(name + "=?,");
			//2、通过反射获取obj中相应的属性值，并放入List中
			params.add(ReflectUtils.invokeGet(obj, name));
		}
		
		sql.setCharAt(sql.length()-1, ' '); //处理最后一个 ','
		sql.append("where " + pkName + "=?");
		
		params.add(pkValue); //将主键值添加到List中
		
		return executeDML(sql.toString(), params.toArray());
	}
	
	/**
	 * 采用模板方法模式将执行查询的的操作封装成模板，便于重用
	 * @param sql SQL语句
	 * @param params 参数数组
	 * @param back 回调方法CallBack
	 * @return 查询结果
	 */
	public Object executeQueryTemplate(String sql, Object[] params, CallBack back) {
		
		Connection conn = DBManager.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			JDBCUtils.handleParams(pstmt, params);
			rs = pstmt.executeQuery();
			
			log.info("执行SQL语句->{}", sql);
			return back.execute(conn, pstmt, rs);
			
		} catch (Exception e) {
			log.error("执行SQL语句->{}失败", sql, e);
			return null;
		} finally {
			DBManager.closeAll(rs, pstmt, conn);
		}
	}
	
	/**
	 * 查询返回多行记录，并将记录封装到clazz指定的javabean中
	 * @param clazz 封装数据的javabean
	 * @param sql 查询语句
	 * @param params 参数数组
	 * @return 查询到的结果集合(如果没查到结果会返回空的List)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> queryRows(final Class<?> clazz, final String sql, final Object[] params) {
		
		CallBack back = new CallBack() {
			@Override
			public Object execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws Exception {
				List<Object> list = new ArrayList<Object>();
				ResultSetMetaData metaData = rs.getMetaData();
				while (rs.next()) {
					
					Object obj = clazz.newInstance();
					for (int i = 0; i < metaData.getColumnCount(); i++) {
						String columnName = metaData.getColumnLabel(i+1); //1、getColumnLabel() 返回别名（没有别名返回字段名） 2、计数从1开始
						Object coluValue = rs.getObject(i+1);
						
						ReflectUtils.invokeSet(obj, columnName, coluValue);
					}
					
					list.add(obj);
				}
				return list;
			}
		};
		
		return (List<Object>) executeQueryTemplate(sql, params, back);
	}
	
	/**
	 * 查询返回一条记录，并将记录封装到clazz指定的javabean中
	 * @param clazz 封装数据的javabean
	 * @param sql 查询语句
	 * @param params 参数数组
	 * @return 查询到的结果
	 */
	public Object querySingleRow(Class<?> clazz, String sql, Object[] params)  throws NotSingleResultExcepion {

		
		List<Object> list = queryRows(clazz, sql, params);
		
		//和queryRows()方法保持一致，在没有查询到记录的时候返回空List
		if(list == null || list.size() == 0) {
			return null;
		} else if(list.size() > 1){
			throw new NotSingleResultExcepion("查询结果非一条记录！");
		} else {
			return list.get(0);
		}
	
	}
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object queryById(Class<?> clazz, Object id) {
		TableInfo ti = TableContext.getPOTableMap().get(clazz);
		ColumnInfo onlyPriKey = ti.getOnlyPriKey();
		
		String sql = "select * from " + ti.getName() + " where " + onlyPriKey.getName() +"=?";
		
		try {
			return querySingleRow(clazz, sql, new Object[]{id});
		} catch (NotSingleResultExcepion e) {
			log.error("", e);
			return null;
		}
	}
	
	/**
	 * 查询返回一条记录中的某一列数据
	 * @param sql 查询语句
	 * @param params 参数数组
	 * @return 查询到的结果
	 */
	public Object queryValue(String sql, Object[] params) {

		CallBack back = new CallBack() {
			
			@Override
			public Object execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
				
				Object value = null; //查询结果值
				while (rs.next()) {
					value = rs.getObject(1);
				}
				return value;
			}
		};
		
		return executeQueryTemplate(sql, params, back);
	}
	
	/**
	 * 查询返回数值型结果
	 * @param sql 查询语句
	 * @param params 参数数组
	 * @return 数值型结果
	 */
	public Number queryNumber(String sql, Object[] params) {
		return (Number) queryValue(sql, params);
	}
	
	/**
	 * 分页查询
	 * @param pageNum 页数
	 * @param pageSize 每页大小
	 * @return
	 */
	public abstract Object queryPagenate(int pageNum, int pageSize);
	
	/**
	 * 克隆方法(浅克隆)
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
	}
}
