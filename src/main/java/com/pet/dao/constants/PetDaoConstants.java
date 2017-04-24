package com.pet.dao.constants;

public class PetDaoConstants {

	public static final String TABLE_NAME = "pet";
	public static final String INSERT_FIELDS = " name, age, type, sex, price, profile ";
	public static final String SELECT_FIELDS = " id, name, age, type, sex, price, profile, create_time, update_time ";
	public static final String SELECT_FIELDS_JOIN = " pet.id, pet.name, pet.age, pet.type, pet.sex , pet.price, pet.profile, pet.create_time, pet.update_time ";
	
	private PetDaoConstants(){
		
	}
	
}
