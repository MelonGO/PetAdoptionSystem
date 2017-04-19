package com.pet.model;

public class Pet extends Base {
	private String name;
	private int age;
	private String type;
	private String sex;
	private Double price;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
