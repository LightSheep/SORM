package com.mingyang.sorm.common.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mingyang.sorm.core.DBManager;

/**
 * 
 * <h1>AppConfig</h1>
 * <h1>已废弃</h1>
 * <h1>用于加载conf文件夹下所有的properties文件</h1>
 * <p>@date 2016年11月22日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
@Deprecated
public class AppConfig {

	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
	
	/**
	 * 配置信息对象
	 */
	private static Properties configs = new Properties();
	
	static {
		//获取conf配置文件路径
		File dir = new File(Thread.currentThread().getContextClassLoader().getResource("conf").getFile());
		//过滤获得properties文件
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("properties");
			}
		});
		if(files != null && files.length > 0) {
			for (File temp : files) {
				FileReader fr = null;
				try {
					fr = new FileReader(temp);
					configs.load(fr);
				} catch (IOException e) {
					log.error("关闭资源失败{}", temp.getClass().toString(), e);
				} finally {
					DBManager.closeAll(fr);
				}
			}
		}
	}
	
	/**
	 * 通过配置项获取值
	 * @param propName 配置项
	 * @return 配置项对应的值
	 */
	public static String getVal(String propName) {
		return configs.getProperty(propName);
	}
	
	/**
	 * 返回所有的配置项信息
	 * @return 配置项信息
	 */
	public static Properties getProps() {
		return configs;
	}
	
	public static void main(String[] args) {
		System.out.println(FrameConfig.class);
		System.out.println(new DBConfig().getVal("frame.queryClass"));
	}
	
}
