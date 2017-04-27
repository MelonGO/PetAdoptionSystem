package com.pet.dao.constants;

public class MyOrderDaoConstants {
	public static final String TABLE_NAME = "myOrder";
	public static final String INSERT_FIELDS = " user_id, pets, props, total, state ";
	public static final String SELECT_FIELDS = " id, user_id, pets, props, total, state, create_time, update_time ";
	public static final String SELECT_FIELDS_JOIN = " myOrder.user_id, myOrder.pets, myOrder.props, myOrder.total, myOrder.state ";

	private MyOrderDaoConstants() {
		
	}
}
