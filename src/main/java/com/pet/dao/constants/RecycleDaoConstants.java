package com.pet.dao.constants;

public class RecycleDaoConstants {
	public static final String TABLE_NAME = "recycle";
	public static final String INSERT_FIELDS = " pet_id, user_id, state, sponsorship,money,reason,address,phone ";
	public static final String SELECT_FIELDS = " id, pet_id, user_id, state, sponsorship,money,reason,address,phone, create_time, update_time ";
	public static final String SELECT_FIELDS_JOIN = " recycle.pet_id, recycle.user_id, recycle.state, recycle.sponsorship,recycle.money,recycle.reason,recycle.address,recycle.phone ";

	private  RecycleDaoConstants() {

	}
}
