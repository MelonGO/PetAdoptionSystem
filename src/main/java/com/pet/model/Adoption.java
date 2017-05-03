package com.pet.model;

import com.pet.model.Base;

public class Adoption extends Base {

	private int receivingInfoId;
	private int petId;
	private int userId;
	private int state;
	private String transportWay;

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
	
	public int getReceivingInfoId() {
		return receivingInfoId;
	}
	public void setReceivingInfoId(int receivingInfoId) {
		this.receivingInfoId = receivingInfoId;
	}
	
	public String getTransportWay() {
		return transportWay;
	}
	public void setTransportWay(String transportWay) {
		this.transportWay = transportWay;
	}
	
	

}
