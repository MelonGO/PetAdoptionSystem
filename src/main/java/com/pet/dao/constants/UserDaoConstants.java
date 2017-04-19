package com.pet.dao.constants;

public class UserDaoConstants {
	public static final String TABLE_NAME = "user";
	public static final String INSERT_FIELDS = " name, password, sex, role ";
	public static final String SELECT_FIELDS = " id, name, password, sex, role ";
	public static final String SELECT_FIELDS_JOIN = " user.id, user.name, user.password, user.sex, user.role ";
	
	private UserDaoConstants(){
		
	}
}
