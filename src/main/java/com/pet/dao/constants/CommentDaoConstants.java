package com.pet.dao.constants;

public class CommentDaoConstants {

	public static final String TABLE_NAME = "comment";
	public static final String INSERT_FIELDS = " petID, age, type, sex, price, profile ";
	public static final String SELECT_FIELDS = " id, petID, username, content, fatherCommentID, support, create_time, update_time ";
	public static final String SELECT_FIELDS_JOIN = " pet.id, pet.name, pet.age, pet.type, pet.sex , pet.price, pet.profile, pet.create_time, pet.update_time ";
	
	private CommentDaoConstants(){}

}
