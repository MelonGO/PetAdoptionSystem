package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.AdoptInfoDaoConstants;
import com.pet.model.AdoptInfo;

@Mapper
public interface AdoptInfoDAO {

	@Insert({ "insert into ", AdoptInfoDaoConstants.TABLE_NAME, "(", AdoptInfoDaoConstants.INSERT_FIELDS,
			") values (#{petId},#{userId},#{userName},#{realName},#{address},#{sex},#{state})" })
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
			statementType = StatementType.PREPARED)
	int addAdoptInfo(AdoptInfo adoptInfo);

	@Select({ "select ", AdoptInfoDaoConstants.SELECT_FIELDS, " from ", AdoptInfoDaoConstants.TABLE_NAME })
	List<AdoptInfo> getAll();
	
	@Select({ "select ", AdoptInfoDaoConstants.SELECT_FIELDS, " from ", AdoptInfoDaoConstants.TABLE_NAME, " where pet_id=#{0} and user_id=#{1}" })
	AdoptInfo selectByPetAndUser(int petId, int userId);
	
	@Select({ "select ", AdoptInfoDaoConstants.SELECT_FIELDS, " from ", AdoptInfoDaoConstants.TABLE_NAME, " where user_id=#{userId}" })
	List<AdoptInfo> selectByUser(int userId);
	
	@Select({ "select ", AdoptInfoDaoConstants.SELECT_FIELDS, " from ", AdoptInfoDaoConstants.TABLE_NAME, " where id=#{id}" })
	AdoptInfo selectById(int id);
	
	@Update({ "update ", AdoptInfoDaoConstants.TABLE_NAME,
	" set state=#{state} where id=#{id}" })
	void updateAdoptInfo(AdoptInfo adoptInfo);

}
