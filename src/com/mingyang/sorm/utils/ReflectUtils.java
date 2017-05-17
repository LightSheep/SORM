package com.mingyang.sorm.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <h1>ReflectUtils</h1>
 * <h1>封装了反射常用操作</h1>
 * <p>@date 2016年11月21日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class ReflectUtils {
	
	/**
	 * 日志变量
	 */
	private static final Logger log = LoggerFactory.getLogger(ReflectUtils.class);

	/**
	 * 调用obj对象对应属性fieldName的get方法
	 * @param fieldName 属性名
	 * @param obj 实体对象
	 * @return 调用get方法后的返回值
	 */
	public static Object invokeGet(Object obj, String fieldName) {
		Method method = null;
		try {
			method = obj.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName));
			return method.invoke(obj);
		} catch (Exception e) {
			log.error("获取get方法失败", e);
			return null;
		}
	}
	
	/**
	 * 调用obj对象对应属性fieldName的set方法
	 * @param obj 实体对象
	 * @param fieldName 属性名
	 * @param fieldValue 属性值
	 */
	public static void invokeSet(Object obj, String fieldName, Object fieldValue) {
		Method method = null;
		try {
			if (fieldValue != null) {
				method = obj.getClass().getDeclaredMethod(
						"set" + StringUtils.capitalize(fieldName),
						fieldValue.getClass());
				method.invoke(obj, fieldValue);
			}
		} catch (Exception e) {
			log.error("获取set方法失败", e);
		}
	}
}
