package com.pet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pet.dao.constants.PetDaoConstants;
import com.pet.model.Pet;

@Mapper
public interface PetDAO {
	@Select({"select ", PetDaoConstants.SELECT_FIELDS, " from ", PetDaoConstants.TABLE_NAME, " where id=#{id}"})
	Pet selectById(int petId);
	
	@Select({"select ", PetDaoConstants.SELECT_FIELDS, " from ", PetDaoConstants.TABLE_NAME, "limit #{page}, 5"})
    List<Pet> selectByPage(int page);
	
	@Select({"select count(*) from ", PetDaoConstants.TABLE_NAME})
    int allPetsNumber();
	
	@Select({"select ", PetDaoConstants.SELECT_FIELDS, " from ", PetDaoConstants.TABLE_NAME})
    List<Pet> getAll();
	
	@Select({"select ", PetDaoConstants.SELECT_FIELDS, " from ", PetDaoConstants.TABLE_NAME, "order by update_time desc", "limit #{start}, 3"})
    List<Pet> getLatesPets(int start);

	@Update({ "update ", PetDaoConstants.TABLE_NAME, " set name=#{name} age=#{age} type=#{type} sex=#{sex} price=#{price} profile=#{profile} where id=#{id}" })
	int update(Pet pet);
	
	
	
}
