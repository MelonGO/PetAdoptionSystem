package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.RecycleDaoConstants;
import com.pet.model.Recycle;

@Mapper
public interface  RecycleDAO {	
	@Insert({ "insert into ", RecycleDaoConstants.TABLE_NAME, "(", RecycleDaoConstants.INSERT_FIELDS,
	") values (#{petId},#{userId},#{state},#{sponsorship},#{money},#{reason},#{address},#{phone})" })
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
	statementType = StatementType.PREPARED)
	int addRecycle(Recycle recycle);
	
	@Select({ "select ", RecycleDaoConstants.SELECT_FIELDS, " from ", RecycleDaoConstants.TABLE_NAME, " where user_id=#{userId}" })
	List<Recycle> selectByUser(int userId);
	
	@Select({ "select ",RecycleDaoConstants.SELECT_FIELDS, " from ", RecycleDaoConstants.TABLE_NAME, " where pet_id=#{0} and user_id=#{1}" })
	Recycle selectByPetAndUser(int petId, int userId);
	
	@Update({ "update ", RecycleDaoConstants.TABLE_NAME, " set state=#{state} where id=#{id}" })
	void updateRecycle(Recycle recycle);
	
	
}
