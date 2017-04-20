package com.pet.model;

import com.pet.model.Base;
public class Recycle extends Base {
	private int id;
	private int petId;
	private int userId;
	private int state;//审核状态
	private int sponsorship;//是否助养
	private double money;
	private String reason;
	private String address;
	private String phone;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getSponsorship() {
		return sponsorship;
	}
	public void setSponsorship(int sponsorship) {
		this.sponsorship = sponsorship;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
}
