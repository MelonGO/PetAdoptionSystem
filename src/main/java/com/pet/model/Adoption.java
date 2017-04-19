package com.pet.model;

import com.pet.model.Base;

public class Adoption extends Base {

	private int id;
	private int receiving_info_id;
	private int petId;
	private int userId;
	private int state;
	private String transport_way;

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
	
	public String getTransport_way() {
		return transport_way;
	}
	public void setTransport_way(String transport_way) {
		this.transport_way = transport_way;
	}
	
	public int getReceiving_info_id() {
		return receiving_info_id;
	}
	public void setReceiving_info_id(int receiving_info_id) {
		this.receiving_info_id = receiving_info_id;
	}

}
