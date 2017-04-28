package com.pet.model;

public class MyOrder extends Base {
	private int userId;
	private String pets;
	private String props;
	private Double total;
	private int state;
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getPets() {
		return pets;
	}
	public void setPets(String pets) {
		this.pets = pets;
	}
	public String getProps() {
		return props;
	}
	public void setProps(String props) {
		this.props = props;
	}
	public Double getTotal() {
		return total;
	}
	public int getUserId() {
		return userId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	
	
	
}
