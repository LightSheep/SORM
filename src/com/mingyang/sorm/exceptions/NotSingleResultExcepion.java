package com.mingyang.sorm.exceptions;

/**
 * 
 * <h1>NotSingleResult</h1>
 * <h1>业务异常类</h1>
 * <p>返回非单一结果</p>
 * <p>@date 2016年11月24日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class NotSingleResultExcepion extends Exception{
	
	private static final long serialVersionUID = -8464998976233389349L;
	
	/**
	 * 无参构造器
	 */
	public NotSingleResultExcepion() {
		super();
	}
	
	/**
	 * 指定错误信息
	 * @param msg 错误信息
	 */
	public NotSingleResultExcepion(String msg) {
		super(msg);
	}
	
	/**
	 * 指定Throwable对象
	 * @param cause Throwable对象
	 */
	public NotSingleResultExcepion(Throwable cause) {
		super(cause);
	}

	/**
	 * 指定错误信息和Throwable对象
	 * @param msg 错误信息
	 * @param cause Throwable对象
	 */
	public NotSingleResultExcepion(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
