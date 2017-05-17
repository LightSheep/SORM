package com.mingyang.sorm.common.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <h1>DBConfig</h1>
 * <h1>数据库配置信息</h1>
 * <p>@date 2016年11月28日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class DBConfig extends BaseConfig{
	
	/**
	 * 日志变量
	 */
	private static final Logger log = LoggerFactory.getLogger(DBConfig.class);
	
	static {
		path = "conf/db.properties";
		try {
			configs.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
			log.info("成功加载数据库配置信息！");
		} catch (IOException e) {
			log.error("加载数据库配置信息失败！", e);
		}
	}

	/**
	 * Just for test
	 * @param args
	 */
	public static void main(String[] args) {
		DBConfig conf = new DBConfig();
		System.out.println(conf.getVal("db.conn.usingDB"));
	}

}
