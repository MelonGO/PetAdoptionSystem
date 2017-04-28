package com.pet.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.ReceivingInfoDAOConstants;
import com.pet.model.ReceivingInfo;


@Mapper
public interface ReceivingInfoDAO {
	@Insert({ "insert into ", ReceivingInfoDAOConstants.TABLE_NAME, "(", ReceivingInfoDAOConstants.INSERT_FIELDS,
		") values (#{userId},#{realName},#{address},#{phone})" })
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
		statementType = StatementType.PREPARED)
	int addReceivingInfo(ReceivingInfo recInfo);
	
	@Select({ "select ", ReceivingInfoDAOConstants.SELECT_FIELDS, " from ", ReceivingInfoDAOConstants.TABLE_NAME, " where user_id=#{userId}" })
	ReceivingInfo selectByUserId(int userId);
	
	@Update({ "update ", ReceivingInfoDAOConstants.TABLE_NAME, " set real_name=#{realName},address=#{address},phone=#{phone} where id=#{id}" })
	void updateReceivingInfo(ReceivingInfo recInfo);
	
	@Select({ "select ", ReceivingInfoDAOConstants.SELECT_FIELDS, " from ", ReceivingInfoDAOConstants.TABLE_NAME, " where id=#{id}" })
	ReceivingInfo selectById(int id);
	
}
