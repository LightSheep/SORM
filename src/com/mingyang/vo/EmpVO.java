package com.mingyang.vo;


/**
 * 
 * <h1>EmpVO</h1>
 * <h1>查询结果VO类</h1>
 * <p>@date 2016年11月24日</p>
 * <p>@author mingyangzhao(email-mingyang0526@qq.com)</p>
 * <p>@Version 1.0</p>
 */
public class EmpVO {

	/**
	 * 主键ID
	 */
	private Integer id;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 增加1000之后的salary
	 */
	private Double salaryAdd1000;
	
	/**
	 * 部门名称
	 */
	private String depname;
	
	/**
	 * 无参构造器
	 */
	public EmpVO() {
	}

	/**
	 * 有参构造器
	 * @param id 主键ID
	 * @param name 姓名
	 * @param salaryAdd1000 增加1000之后的salary
	 * @param depName 部门名称
	 */
	public EmpVO(Integer id, String name, Double salaryAdd1000, String depName) {
		super();
		this.id = id;
		this.name = name;
		this.salaryAdd1000 = salaryAdd1000;
		this.depname = depName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalaryAdd1000() {
		return salaryAdd1000;
	}

	public void setSalaryAdd1000(Double salaryAdd1000) {
		this.salaryAdd1000 = salaryAdd1000;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}
	
}
