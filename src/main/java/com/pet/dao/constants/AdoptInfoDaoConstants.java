package com.pet.dao.constants;

public class AdoptInfoDaoConstants {
	public static final String TABLE_NAME = "adopt_info";
	public static final String INSERT_FIELDS = " pet_id, user_id, user_name, real_name, address, sex, state ";
	public static final String SELECT_FIELDS = " id, pet_id, user_id, user_name, real_name, address, sex, state ";
	public static final String SELECT_FIELDS_JOIN = " adopt_info.pet_id, adopt_info.user_id, adopt_info.user_name, adopt_info.real_name, adopt_info.address, adopt_info.sex, adopt_info.state ";

	private AdoptInfoDaoConstants() {

	}
}
