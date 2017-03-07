package com.pet.dao.constants;

public class PetDaoConstants {

	public static final String TABLE_NAME = "pet";
	public static final String INSERT_FIELDS = " name, age, type ";
	public static final String SELECT_FIELDS = " id, name, age, type ";
	public static final String SELECT_FIELDS_JOIN = " pet.id, pet.name, pet.age, pet.type ";
	
	private PetDaoConstants(){
		
	}
	
}
