package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.pet.dao.constants.AdoptionDaoConstants;
import com.pet.model.Adoption;

@Mapper
public interface AdoptionDAO {

	@Insert({ "insert into ", AdoptionDaoConstants.TABLE_NAME, "(", AdoptionDaoConstants.INSERT_FIELDS,
			") values (#{receiving_info_id},#{petId},#{userId},#{state},#{transport_way})" })
	@SelectKey(statement = "select last_insert_id() as id", keyProperty = "id", before = false, resultType = Integer.class, 
			statementType = StatementType.PREPARED)
	int addAdoption(Adoption adoption);

	@Select({ "select ", AdoptionDaoConstants.SELECT_FIELDS, " from ", AdoptionDaoConstants.TABLE_NAME })
	List<Adoption> getAll();
	
	@Select({ "select ", AdoptionDaoConstants.SELECT_FIELDS, " from ", AdoptionDaoConstants.TABLE_NAME, " where pet_id=#{0} and user_id=#{1}" })
	Adoption selectByPetAndUser(int petId, int userId);
	
	@Select({ "select ", AdoptionDaoConstants.SELECT_FIELDS, " from ", AdoptionDaoConstants.TABLE_NAME, " where user_id=#{userId}" })
	List<Adoption> selectByUser(int userId);
	
	@Select({ "select ", AdoptionDaoConstants.SELECT_FIELDS, " from ", AdoptionDaoConstants.TABLE_NAME, " where id=#{id}" })
	Adoption selectById(int id);
	
	@Update({ "update ", AdoptionDaoConstants.TABLE_NAME, " set state=#{state} where id=#{id}" })
	void updateAdoptionState(Adoption adoptInfo);
	
	@Update({ "update ", AdoptionDaoConstants.TABLE_NAME, " set transport_way=#{transport_way} where id=#{id}" })
	void updateAdoptionTransport(Adoption adoptInfo);
	
	@Select({ "select ", AdoptionDaoConstants.SELECT_FIELDS, " from ", AdoptionDaoConstants.TABLE_NAME, " where pet_id=#{petId}" })
	Adoption selectByPetId(int petId);
	

}
