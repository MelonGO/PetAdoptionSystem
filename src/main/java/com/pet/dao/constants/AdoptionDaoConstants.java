package com.pet.dao.constants;

public class AdoptionDaoConstants {
	public static final String TABLE_NAME = "adoption";
	public static final String INSERT_FIELDS = " receiving_info_id, pet_id, user_id, state, transport_way ";
	public static final String SELECT_FIELDS = " id, receiving_info_id, pet_id, user_id, state, transport_way, create_time, update_time ";
	public static final String SELECT_FIELDS_JOIN = " adopt_info.receiving_info_id, adopt_info.pet_id, adopt_info.user_id, adopt_info.state, adopt_info.transport_way ";

	private AdoptionDaoConstants() {

	}
}
