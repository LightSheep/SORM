package com.mingyang.sorm.bean;

/**
 * 
 * <h1>JavaFieldGetSet</h1>
 * <h1>封装了Java属性和get、set方法的源代码</h1>
 * <p>@date 2016年11月22日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class JavaFieldGetSet {
	
	/**
	 * 属性的源码信息
	 * 如：private String name;
	 */
	private String fieldInfo;
	
	/**
	 * get方法的源码信息
	 * 如:public String getName(){return name;}
	 */
	private String getInfo;
	
	/**
	 * set方法的源码信息
	 * 如：public void setName(String name){this.name=name;}
	 */
	private String setInfo;

	/**
	 * 无参构造器
	 */
	public JavaFieldGetSet() {
	}

	/**
	 * 有参构造器
	 * @param fieldInfo 属性的源码信息
	 * @param getInfo get方法的源码信息
	 * @param setInfo set方法的源码信息
	 */
	public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
		super();
		this.fieldInfo = fieldInfo;
		this.getInfo = getInfo;
		this.setInfo = setInfo;
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getGetInfo() {
		return getInfo;
	}

	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}

	public String getSetInfo() {
		return setInfo;
	}

	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}
	
}
