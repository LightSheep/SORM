package com.mingyang.sorm.common.configuration;

import java.util.Properties;

/**
 * 
 * <h1>BaseConfig</h1>
 * <h1>配置信息基类</h1>
 * <p>@date 2016年11月28日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class BaseConfig {
	
	/**
	 * properties文件路径
	 */
	protected static String path = "";
	
	/**
	 * 配置信息对象
	 */
	protected static Properties configs = new Properties();
	
	/**
	 * 通过配置项获取值
	 * @param propName 配置项
	 * @return 配置项对应的值
	 */
	public String getVal(String propName) {
		return configs.getProperty(propName);
	}
	
	/**
	 * 通过配置项获整型数值
	 * @param propName 配置项
	 * @return 配置项对应的整型数值
	 */
	public int getIntVal(String propName) {
		return Integer.parseInt(configs.getProperty(propName));
	}
	
	/**
	 * 返回所有的配置项信息
	 * @return 配置项信息
	 */
	public Properties getProps() {
		return configs;
	}
}
