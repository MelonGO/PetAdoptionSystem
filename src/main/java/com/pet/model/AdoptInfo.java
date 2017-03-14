package com.pet.model;

import com.pet.model.Base;

public class AdoptInfo extends Base {

	private int id;
	private int petId;
	private int userId;
	private String userName;
	private String realName;
	private String address;
	private String sex;
	private int state;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

}
