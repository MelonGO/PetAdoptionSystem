package com.pet.dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.MessageDaoConstants;
import com.pet.model.Message;

@Mapper
public interface MessageDAO {
	
	@Insert({ "insert into ", MessageDaoConstants.TABLE_NAME, "(", MessageDaoConstants.INSERT_FIELDS,") values (#{username},#{targetUsername},#{content},#{readed},#{sendTime},#{type})" })
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, statementType = StatementType.PREPARED)
	int addMessage(Message message);
	
	@Select({ "select ", MessageDaoConstants.SELECT_FIELDS, " from ", MessageDaoConstants.TABLE_NAME, " where targetUsername=#{targetUsername} and type='私信'" })
	List<Message> selectPrivateByTargetUserName(String targetUsername);
	
	@Select({ "select ", MessageDaoConstants.SELECT_FIELDS, " from ", MessageDaoConstants.TABLE_NAME, " where targetUsername=#{targetUsername} and type='评论'" })
	List<Message> selectCommentByTargetUserName(String targetUsername);
	
	@Select({ "select ", MessageDaoConstants.SELECT_FIELDS, " from ", MessageDaoConstants.TABLE_NAME, " where targetUsername=#{targetUsername} and type='通知'" })
	List<Message> selectNotifyByTargetUserName(String targetUsername);
	
	@Update({ "update ", MessageDaoConstants.TABLE_NAME, " set readed=#{readed} where id=#{id}" })
	void updateMessage(Message message);
}
