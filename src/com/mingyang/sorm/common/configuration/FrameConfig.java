package com.mingyang.sorm.common.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <h1>FrameConfig</h1>
 * <h1>框架配置信息</h1>
 * <p>@date 2016年11月28日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class FrameConfig extends BaseConfig{
	
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(FrameConfig.class);
	
	static {
		path = "conf/frame.properties";
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
		FrameConfig conf = new FrameConfig();
		System.out.println(conf.getVal("frame.queryClass"));
	}
}
