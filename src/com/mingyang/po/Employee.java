package com.mingyang.po;

public class Employee {

	private Integer id;

	private java.sql.Date birthday;

	private Integer age;

	private String empname;

	private Integer depid;

	private Double salary;


	public Employee() {
	}

	public Integer getId() {
		return id;
	}
	public java.sql.Date getBirthday() {
		return birthday;
	}
	public Integer getAge() {
		return age;
	}
	public String getEmpname() {
		return empname;
	}
	public Integer getDepid() {
		return depid;
	}
	public Double getSalary() {
		return salary;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public void setDepid(Integer depid) {
		this.depid = depid;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}

}
