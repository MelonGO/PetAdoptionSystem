package com.pet.dao.constants;

public class ReceivingInfoDAOConstants {
	public static final String TABLE_NAME = "receiving_info";
	public static final String INSERT_FIELDS = " user_id, real_name, address, phone ";
	public static final String SELECT_FIELDS = " id, user_id, real_name, address, phone, create_time, update_time ";
	public static final String SELECT_FIELDS_JOIN = " receiving_info.user_id, receiving_info.real_name, receiving_info.address , receiving_info.phone ";

	private ReceivingInfoDAOConstants() {

	}
}
