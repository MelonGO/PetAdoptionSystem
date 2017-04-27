package com.pet.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	
	private int userId;
	private List<Integer> adoptionList  = new ArrayList<>();
	private List<Integer> propList  = new ArrayList<>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public List<Integer> getAdoptionList() {
		return adoptionList;
	}
	public void setAdoptionList(List<Integer> adoptionList) {
		this.adoptionList = adoptionList;
	}
	
	public List<Integer> getPropList() {
		return propList;
	}
	public void setPropList(List<Integer> propList) {
		this.propList = propList;
	}
	
	
}
