package com.pet.model;

public class Demo {
	private String name;
	
	private String sex;
	
	public Demo() {
		this("Jim", "male");
	}
	
	public Demo(String name, String sex) {
		this.name = name;
		this.sex = sex;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
