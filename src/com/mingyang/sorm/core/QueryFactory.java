package com.mingyang.sorm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingyang.sorm.common.configuration.FrameConfig;

/**
 * 
 * <h1>QueryFactory</h1>
 * <h1>Query对象工厂，根据配置信息创建Query对象</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class QueryFactory {
	
	/**
	 * 日志变量
	 */
	private static final Logger log = LoggerFactory.getLogger(QueryFactory.class);
	
	/**
	 * Query类对象
	 */
	private static Query prototypeObj;  //原型对象 
	
	static {
		
		try {
			Class<?> clazz = Class.forName(new FrameConfig().getVal("frame.queryClass")); //加载配置中指定的Query类
			prototypeObj = (Query) clazz.newInstance();
		} catch (Exception e) {
			log.error("",e);
		}
	}
	
	/**
	 * 
	 * @return Query对象
	 */
	public static Query createQuery() {
		
		try {
			return (Query) prototypeObj.clone();
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(QueryFactory.createQuery());
		System.out.println(QueryFactory.createQuery());
	}
	
}
