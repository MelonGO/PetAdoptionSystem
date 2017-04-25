package com.pet.dao.constants;

public class MessageDaoConstants {
	public static final String TABLE_NAME = "message";
	public static final String INSERT_FIELDS = " username,targetUsername, content, readed, sendTime,type ";
	public static final String SELECT_FIELDS = " id, username,targetUsername, content, readed, sendTime,type, create_time, update_time ";
	public static final String SELECT_FIELDS_JOIN = " message.username,message.targetUsername, message.content, message.readed, message.sendTime,message.type ";

	private MessageDaoConstants() {

	}
}
