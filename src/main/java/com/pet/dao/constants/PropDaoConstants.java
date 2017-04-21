package com.pet.dao.constants;

public class PropDaoConstants {
	
	public static final String TABLE_NAME = "prop";
	public static final String INSERT_FIELDS = " name, price ";
	public static final String SELECT_FIELDS = " id, name, price ";
	public static final String SELECT_FIELDS_JOIN = " prop.id, prop.name, prop.price ";
	
	private PropDaoConstants(){
		
	}
}
